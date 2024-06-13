package com.finalYearProject.myapplication.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.finalYearProject.myapplication.model.FetusDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FetusDetailsManager {

    private final Map<String, FetusDetails> fetusDetailsMap;
    private static final String COLLECTION_NAME = "fetus_details";
    private static final String TAG = "FetusDetailsManager";

    public FetusDetailsManager() {
        fetusDetailsMap = new HashMap<>();
        //populating the HashMap with fetus details for each week
        populateFetusDetails();
    }

    private void populateFetusDetails() {
        // Populate the HashMap with fetus details for each week
        fetusDetailsMap.put("week_4", new FetusDetails("Poppy Seed", 0.1, 0.7, "Tiny cluster of cells"));
        fetusDetailsMap.put("week_5", new FetusDetails("Sesame Seed", 0.2, 0.8," Beginning of development"));
        fetusDetailsMap.put("week_6", new FetusDetails("Lentil", 0.3, 0.9,"Starting to shape"));
        fetusDetailsMap.put("week_7", new FetusDetails("Blueberry", 1.3, 1, "Major organs developing"));
        fetusDetailsMap.put("week_8", new FetusDetails("Raspberry", 1.6, 2, "Developing limbs"));
        fetusDetailsMap.put("week_9", new FetusDetails("Grape", 2.3, 3, "Eyes and ears forming"));
        fetusDetailsMap.put("week_10", new FetusDetails("Kumquat", 3.1, 4, "Limbs and features more defined"));
        fetusDetailsMap.put("week_11", new FetusDetails("Fig", 4.1, 7, "All major organs formed"));
        fetusDetailsMap.put("week_12", new FetusDetails("Lime", 5.4, 14, "Reflexes developing"));
        fetusDetailsMap.put("week_13", new FetusDetails("Pea Pod", 7.4, 23, "Fingerprints forming"));
        fetusDetailsMap.put("week_14", new FetusDetails("Lemon", 8.6, 43, "Organs continue to mature"));
        fetusDetailsMap.put("week_15", new FetusDetails("Apple", 10.1, 70, "Developing skeleton"));
        fetusDetailsMap.put("week_16", new FetusDetails("Avocado", 11.6, 100, "Limb movements detected"));
        fetusDetailsMap.put("week_17", new FetusDetails("Turnip", 13, 140, "Fat storage begins"));
        fetusDetailsMap.put("week_18", new FetusDetails("Bell Pepper", 14.2, 190, "Ear bones forming"));
        fetusDetailsMap.put("week_19", new FetusDetails("Tomato", 15.3, 240, "Sensory development"));
        fetusDetailsMap.put("week_20", new FetusDetails("Banana", 16.4, 300, "Halfway point"));
        fetusDetailsMap.put("week_21", new FetusDetails("Carrot", 26.7, 360, "Hiccuping, swallowing"));
        fetusDetailsMap.put("week_22", new FetusDetails("Spaghetti squash", 27.8, 430, "Sleeping in cycles"));
        fetusDetailsMap.put("week_23", new FetusDetails("Mango", 28.9, 500, "Muscle coordination"));
        fetusDetailsMap.put("week_24", new FetusDetails("Corn", 30, 600, "Hearing outside sounds"));
        fetusDetailsMap.put("week_25", new FetusDetails("Rutabaga", 34.6, 660, "Gaining baby fat"));
        fetusDetailsMap.put("week_26", new FetusDetails("Butternut Squash", 35.6, 760, "Eyelashes forming"));
        fetusDetailsMap.put("week_27", new FetusDetails("Cauliflower", 36.6, 875, "Opening and closing eyes"));
        fetusDetailsMap.put("week_28", new FetusDetails("Eggplant", 37.6, 1005, "Brain development"));
        fetusDetailsMap.put("week_29", new FetusDetails("Acorn Squash", 38.6, 1153, "Breathing movements"));
        fetusDetailsMap.put("week_30", new FetusDetails("Cucumber", 39.9, 1319, "Stronger Kicks"));
        fetusDetailsMap.put("week_31", new FetusDetails("Coconut", 41.1, 1502, "Rapid weight gain"));
        fetusDetailsMap.put("week_32", new FetusDetails("Jicama", 42.4, 1702, "Fingernails forming"));
        fetusDetailsMap.put("week_33", new FetusDetails("Pineapple", 43.7, 1918, "More coordinated movements"));
        fetusDetailsMap.put("week_34", new FetusDetails("Cantaloupe", 45, 2146, "Practicing breathing"));
        fetusDetailsMap.put("week_35", new FetusDetails("Honeydew Melon", 46.2, 2383, "Gaining weight steadily"));
        fetusDetailsMap.put("week_36", new FetusDetails("Romaine Lettuce", 47.4, 2622, "Stronger movements"));
        fetusDetailsMap.put("week_37", new FetusDetails("Winter Melon", 48.6, 2859, "Head may be engaged"));
        fetusDetailsMap.put("week_38", new FetusDetails("Pumpkin", 49.8, 3083, "Full-term"));
        fetusDetailsMap.put("week_39", new FetusDetails("Watermelon", 50.7, 3288, "Ready for birth"));
        fetusDetailsMap.put("week_40", new FetusDetails("Watermelon", 51.2, 3462, "Average full-term weight"));
    }

    public void saveFetusDetailsToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db == null) {
            Log.e(TAG, "FirebaseFirestore instance is null.");
            return;
        }

        for (Map.Entry<String, FetusDetails> entry : fetusDetailsMap.entrySet()) {
            String week = entry.getKey();
            FetusDetails details = entry.getValue();
            if (details == null) {
                Log.w(TAG, "FetusDetails for " + week + " is null.");
                continue;
            }

            db.collection(COLLECTION_NAME).document(week)
                    .set(details)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Fetus details for " + week + " saved successfully!");
                            } else {
                                Log.w(TAG, "Error saving fetus details for " + week, task.getException());
                            }
                        }
                    });
        }
    }

    public void retrieveFetusDetailsFromFirestore(String week, FetusDetailsCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db == null) {
            Log.e(TAG, "FirebaseFirestore instance is null.");
            callback.onFailure("FirebaseFirestore instance is null.");
            return;
        }

        db.collection(COLLECTION_NAME).document(week)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                FetusDetails fetusDetails = document.toObject(FetusDetails.class);
                                if (fetusDetails != null) {
                                    callback.onSuccess(fetusDetails);
                                } else {
                                    callback.onFailure("Fetus details not found for week " + week);
                                }
                            } else {
                                callback.onFailure("No such document for week " + week);
                            }
                        } else {
                            callback.onFailure("Error getting document for week " + week + ": " + task.getException());
                        }
                    }
                });
    }

    public interface FetusDetailsCallback {
        void onSuccess(FetusDetails fetusDetails);
        void onFailure(String errorMessage);
    }
}

