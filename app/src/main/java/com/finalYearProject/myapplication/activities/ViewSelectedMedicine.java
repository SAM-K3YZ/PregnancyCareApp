package com.finalYearProject.myapplication.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.adapters.SelectedMedicineAdapter;

import java.util.ArrayList;

public class ViewSelectedMedicine extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SelectedMedicineAdapter adapter;
    private ArrayList<String> selectedMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_selected_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vsMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.selected_medicines_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectedMedicines = getIntent().getStringArrayListExtra("selected_medicines");
        adapter = new SelectedMedicineAdapter(selectedMedicines);
        recyclerView.setAdapter(adapter);
    }
}