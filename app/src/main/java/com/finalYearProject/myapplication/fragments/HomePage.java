package com.finalYearProject.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.activities.MedPage;
import com.finalYearProject.myapplication.activities.MeditatePage;
import com.finalYearProject.myapplication.activities.PregnancyLogPage;
import com.finalYearProject.myapplication.activities.WeightPage;
import com.finalYearProject.myapplication.model.UserModel;
import com.finalYearProject.myapplication.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String TAG = "MainActivity";
    String conceptionDateStr;
    Button pregLogBtn, weightBtn, medBtn, meditateBtn;
    TextView deliveryDate, trimesterStage, daysPregnant, username, conception, countdown;
    UserModel currentUserModel;


    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AndroidThreeTen.init(getContext());

        pregLogBtn = view.findViewById(R.id.pregLogBtn);
        weightBtn = view.findViewById(R.id.weightBtn);
        medBtn = view.findViewById(R.id.medLogBtn);
        meditateBtn = view.findViewById(R.id.meditateBtn);
        deliveryDate = view.findViewById(R.id.expectedDeliveryDateText);
        trimesterStage = view.findViewById(R.id.trimester);
        countdown = view.findViewById(R.id.countdownText);
        username = view.findViewById(R.id.userDetails);
        conception = view.findViewById(R.id.conceptionDateText);
        daysPregnant = view.findViewById(R.id.pregnancyDate);


        //getting the user's details and replacing 'hello user' with the user's username
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            currentUserModel = task.getResult().toObject(UserModel.class);
            if (currentUserModel != null) {
                username.setText(String.format("Hello, " + currentUserModel.getUsername()));
                //i used this to check if the already existing text will change
                //conception.setText(String.format("Conception Date: " + currentUserModel.getConceptionDate()));
            }
        });

        retrieveConceptionDate();
        action();

    }

    private void action() {
        pregLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preg = new Intent(getContext(), PregnancyLogPage.class);
                startActivity(preg);
             }
        });

        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weightIntent = new Intent(getContext(), WeightPage.class);
                startActivity(weightIntent);
            }
        });

        medBtn.setOnClickListener(v -> {
            Intent medIntent = new Intent(getContext(), MedPage.class);
            startActivity(medIntent);
        });

        meditateBtn.setOnClickListener(v ->{
            startActivity(new Intent(getContext(), MeditatePage.class));
        });
    }

    private void retrieveConceptionDate(){
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                currentUserModel = task.getResult().toObject(UserModel.class);
                if (currentUserModel != null) {
                    conceptionDateStr = currentUserModel.getConceptionDate();
                    if (conceptionDateStr != null) {
                        // Directly set the text if the format is already correct
                        conception.setText("Conception Date: " + conceptionDateStr);

                        // Optional: Logging for debugging
                        Log.d(TAG, "Conception Date: " + conceptionDateStr);

                        try {
                            LocalDate conceptionDate = LocalDate.parse(conceptionDateStr, DATE_FORMATTER);
                            LocalDate estimatedDeliveryDate = calculateDeliveryDate(conceptionDate);

                            // Calculate and display countdown
                            calculateAndDisplayCountdown(estimatedDeliveryDate);

                            // Determine and display trimester stage
                            determineAndDisplayTrimesterStage(conceptionDate);

                            // Calculate and display days pregnant
                            calculateAndDisplayDaysPregnant(conceptionDate);

                            Log.d(TAG, "Estimated Delivery Date: " + estimatedDeliveryDate);

                            //i will set the text for the estimated delivery date here
                            deliveryDate.setText(String.format("Delivery Date: " + estimatedDeliveryDate));
                        } catch (DateTimeParseException e) {
                            Log.d(TAG, "Failed to parse conception date: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, "Conception date is null");
                        conception.setText("No conception date available");
                    }
                } else {
                    Log.d(TAG, "User data is null");
                    conception.setText("No user data available");
                }
            } else {
                Log.d(TAG, "Task failed with exception: ", task.getException());
                conception.setText("Failed to retrieve conception date");
            }
        });

    }

    private LocalDate calculateDeliveryDate(LocalDate conceptionDate) {
        //38 weeks will be added to the conception date
        return conceptionDate.plusWeeks(38);
    }

    private void calculateAndDisplayCountdown(LocalDate estimatedDeliveryDate) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(estimatedDeliveryDate)) {
            Period period = Period.between(today, estimatedDeliveryDate);
            int weeksRemaining = period.getDays() / 7;
            int daysRemaining = period.getDays() % 7;

            String countdownText = String.format("%d weeks and %d days remaining", weeksRemaining, daysRemaining);
            countdown.setText(countdownText);
        } else {
            countdown.setText("Delivery date has passed");
        }
    }

    private void determineAndDisplayTrimesterStage(LocalDate conceptionDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(conceptionDate, today);
        int weeksSinceConception = period.getDays() / 7;

        String trimester;
        if (weeksSinceConception <= 12) {
            trimester = "First";
        } else if (weeksSinceConception <= 27) {
            trimester = "Second";
        } else {
            trimester = "Third";
        }

        trimesterStage.setText(trimester);
    }

    private void calculateAndDisplayDaysPregnant(LocalDate conceptionDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(conceptionDate, today);
        int totalDaysPregnant = period.getDays();

        String daysPregnantText = String.format("%d days pregnant", totalDaysPregnant);
        //String daysPregnantText = String.valueOf(totalDaysPregnant);
        daysPregnant.setText(daysPregnantText);
    }
}