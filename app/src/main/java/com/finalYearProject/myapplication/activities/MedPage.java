package com.finalYearProject.myapplication.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.manager.MedicationDatabase;
import com.finalYearProject.myapplication.model.MedicineModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedPage extends AppCompatActivity {
    private EditText medicineNameEditText;
    private Button searchButton;
    private Button viewSelectedMedicinesButton;
    private AutoCompleteTextView searchMedicineNameEditText;
    private ImageView filterIcon;

    private DatabaseReference databaseReference;
    private HashMap<String, List<String>> categorizedMedicines;
    private String selectedCategory;
    private ArrayList<String> selectedMedicines;
    private MedicationDatabase medicationDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_med_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.medMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //medicineNameEditText = findViewById(R.id.medicine_name);
        searchButton = findViewById(R.id.search_Btn);
        viewSelectedMedicinesButton = findViewById(R.id.view_selected_medicines_button);
        searchMedicineNameEditText = findViewById(R.id.search_medicine_name);
        filterIcon = findViewById(R.id.filter_icon);

        // Default category
        selectedCategory = "All";
        selectedMedicines = new ArrayList<>();

        medicationDatabase = new MedicationDatabase(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMedicine();
            }
        });

        viewSelectedMedicinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedPage.this, ViewSelectedMedicine.class);
                intent.putStringArrayListExtra("selected_medicines", selectedMedicines);
                startActivity(intent);
            }
        });

        // Initialize Firebase reference and categorized medicines map
        databaseReference = FirebaseDatabase.getInstance().getReference("medications");
        categorizedMedicines = new HashMap<>();

        // Load all medicines from Firebase
        loadAllMedicines();

        // Set up text watcher for searchMedicineNameEditText
        searchMedicineNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up item click listener to handle selection from suggestions
        searchMedicineNameEditText.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMedicine = (String) parent.getItemAtPosition(position);
            if (!selectedMedicines.contains(selectedMedicine)) {
                selectedMedicines.add(selectedMedicine);
                Toast.makeText(MedPage.this, "Medicine added: " + selectedMedicine, Toast.LENGTH_SHORT).show();
            }
        });

        // Set up click listener for filter icon
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });
    }

    private void loadAllMedicines() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categorizedMedicines.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getKey();
                    List<String> medicines = new ArrayList<>();
                    for (DataSnapshot medicineSnapshot : categorySnapshot.getChildren()) {
                        MedicineModel medication = medicineSnapshot.getValue(MedicineModel.class);
                        if (medication != null) {
                            medicines.add(medication.getName());
                        }
                    }
                    categorizedMedicines.put(category, medicines);
                }
                updateSuggestions("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MedPage.this, "Failed to load medicines", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSuggestions(String query) {
        List<String> suggestions = new ArrayList<>();
        for (String category : categorizedMedicines.keySet()) {
            if (selectedCategory.equals("All") || selectedCategory.equals(category)) {
                List<String> medicines = categorizedMedicines.get(category);
                if (medicines != null) {
                    for (String medicine : medicines) {
                        if (medicine.toLowerCase().contains(query.toLowerCase())) {
                            suggestions.add(medicine);
                        }
                    }
                }
            }
        }

        ArrayAdapter<String> suggestionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        searchMedicineNameEditText.setAdapter(suggestionAdapter);
    }

    private void searchMedicine() {
        String query = searchMedicineNameEditText.getText().toString().trim();

        if (!query.isEmpty()) {
            boolean found = false;
            for (List<String> medicines : categorizedMedicines.values()) {
                if (medicines.contains(query)) {
                    found = true;
                    if (!selectedMedicines.contains(query)) {
                        selectedMedicines.add(query);
                        Toast.makeText(MedPage.this, "Medicine added: " + query, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }

            if (!found) {
                showAddMedicineDialog(query);
            }
        } else {
            Toast.makeText(this, "Please enter a medicine name", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddMedicineDialog(String medicineName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Medicine not found");
        builder.setMessage("The medicine " + medicineName + " was not found. Do you want to add it?");

        final EditText input = new EditText(this);
        input.setText(medicineName);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newMedicineName = input.getText().toString().trim();
                showCategoryDialog(newMedicineName);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");

        String[] categories = {
                "All", "Analgesics", "Antibiotics", "Anticoagulants", "Anticonvulsants",
                "Antidepressants", "Antiemetics", "Antifungals", "Antihistamines",
                "Antihypertensives", "Asthma Medications", "Diabetes Medications",
                "Gastrointestinal Medications", "Hormonal Medications", "Immunizations",
                "Laxatives", "Prenatal Vitamins", "Topical Medications", "Miscellaneous"
        };

        builder.setItems(categories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedCategory = categories[which];
                updateSuggestions(searchMedicineNameEditText.getText().toString());
                Toast.makeText(MedPage.this, "Category selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void showCategoryDialog(String medicineName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");

        String[] categories = {
                "Analgesics", "Antibiotics", "Anticoagulants", "Anticonvulsants",
                "Antidepressants", "Antiemetics", "Antifungals", "Antihistamines",
                "Antihypertensives", "Asthma Medications", "Diabetes Medications",
                "Gastrointestinal Medications", "Hormonal Medications", "Immunizations",
                "Laxatives", "Prenatal Vitamins", "Topical Medications", "Miscellaneous"
        };

        builder.setItems(categories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedCategory = categories[which];
                medicationDatabase.addNewMedicine(selectedCategory, new MedicineModel(medicineName));
                Toast.makeText(MedPage.this, "Medicine added to " + selectedCategory, Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

}
