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
import com.finalYearProject.myapplication.utils.FetusDetailsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.Map;

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
    private FetusDetailsManager fetusDetailsManager;
    private static final String TAG = "MainActivity";
    private static final String COLLECTION_NAME = "fetus_details";
    private FirebaseFirestore db;
    private String conceptionDateStr;
    private static final String CONCEPTION_DATE_FIELD = "conceptionDate";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TextView weightText, lengthText, babySizeText, fruitName;
    private Map<String, FetusDetails> fetusDetailsMap;

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


    }
}