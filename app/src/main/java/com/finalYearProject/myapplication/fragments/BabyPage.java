package com.finalYearProject.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.model.FetusDetails;
import com.finalYearProject.myapplication.model.UserModel;
import com.finalYearProject.myapplication.manager.FetusDetailsManager;
import com.finalYearProject.myapplication.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import org.threeten.bp.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BabyPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BabyPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FetusDetailsManager manager = new FetusDetailsManager();
    private static final String TAG = "BabyPage";
    private static final String COLLECTION_NAME = "fetus_details";
    private FirebaseFirestore db;
    private String conceptionDateStr, weightTextStr, lengthTextStr;
    private String selectedWeek;
    UserModel currentUserModel;
    private static final String CONCEPTION_DATE_FIELD = "conceptionDate";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TextView weightText;
    TextView lengthText;
    TextView babySizeText;
    TextView fruitName;
    TextView descriptionText;

    public BabyPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BabyPage.
     */
    // TODO: Rename and change types and number of parameters
    public static BabyPage newInstance(String param1, String param2) {
        BabyPage fragment = new BabyPage();
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
        //retrieving the conceptionDate and converting it to string
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            currentUserModel = task.getResult().toObject(UserModel.class);
            if (currentUserModel != null) {
                conceptionDateStr = currentUserModel.getConceptionDate();
                Log.d(TAG, "Conception Date: " + conceptionDateStr);
            }
        });


        //saving the hash map data about the fetus to the database
        manager.saveFetusDetailsToFirestore();
        //retrieving the fetus details based on the selected week
        String week = "week_12"; // Example week to retrieve
        manager.retrieveFetusDetailsFromFirestore(week, new FetusDetailsManager.FetusDetailsCallback() {
            @Override
            public void onSuccess(FetusDetails fetusDetails) {
                // Use the retrieved fetus details
                Log.d(TAG, "Size: " + fetusDetails.getSizeComparison());
                Log.d(TAG, "Weight: " + fetusDetails.getWeight());
                Log.d(TAG, "Length: " + fetusDetails.getLength());
                Log.d(TAG, "Description: " + fetusDetails.getDescription());

                //converting the data types that are in double to strings
                weightTextStr = Double.toString(fetusDetails.getWeight());
                lengthTextStr = Double.toString(fetusDetails.getLength());

                //setting the text in the ui
                fruitName.setText(fetusDetails.getSizeComparison());
                descriptionText.setText(fetusDetails.getDescription());
                weightText.setText(weightTextStr);
                lengthText.setText(lengthTextStr);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baby_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weightText = view.findViewById(R.id.weightText);
        lengthText = view.findViewById(R.id.lengthText);
        fruitName = view.findViewById(R.id.fruitText);
        descriptionText = view.findViewById(R.id.babySizeDescriptionText);
        babySizeText = view.findViewById(R.id.babySizeText);
    }
}