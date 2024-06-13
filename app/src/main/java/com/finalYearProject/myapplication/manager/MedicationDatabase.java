package com.finalYearProject.myapplication.manager;

import android.content.Context;

import com.finalYearProject.myapplication.model.MedicineModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicationDatabase {
    private final DatabaseReference databaseReference;

    public MedicationDatabase(Context context){
        databaseReference = FirebaseDatabase.getInstance().getReference("medications");
    }

    public void addNewMedicine(String category, MedicineModel medicineModel) {
        databaseReference.child(category).push().setValue(medicineModel);
    }

    public void populateMedicineDetails() {
        Map<String, ArrayList<MedicineModel>> medicationsMap = new HashMap<>();

        medicationsMap.put("Analgesics", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Acetaminophen (Tylenol)"));
            add(new MedicineModel("Aspirin (low-dose, under medical supervision)"));
            add(new MedicineModel("Ibuprofen (generally avoided, especially in the third trimester)"));
        }});

        medicationsMap.put("Antibiotics", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Amoxicillin"));
            add(new MedicineModel("Ampicillin"));
            add(new MedicineModel("Azithromycin"));
            add(new MedicineModel("Cephalexin"));
            add(new MedicineModel("Clindamycin"));
            add(new MedicineModel("Erythromycin"));
            add(new MedicineModel("Metronidazole (Flagyl)"));
            add(new MedicineModel("Nitrofurantoin (Macrobid)"));
            add(new MedicineModel("Penicillin"));
        }});

        medicationsMap.put("Anticoagulants", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Heparin"));
            add(new MedicineModel("Low Molecular Weight Heparin (e.g., Enoxaparin)"));
        }});

        medicationsMap.put("Anticonvulsants", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Lamotrigine"));
            add(new MedicineModel("Levetiracetam"));
        }});

        medicationsMap.put("Antidepressants", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Citalopram (Celexa)"));
            add(new MedicineModel("Fluoxetine (Prozac)"));
            add(new MedicineModel("Sertraline (Zoloft)"));
        }});

        medicationsMap.put("Antiemetics", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Doxylamine and Pyridoxine (Diclegis, Bonjesta)"));
            add(new MedicineModel("Metoclopramide (Reglan)"));
            add(new MedicineModel("Ondansetron (Zofran)"));
        }});

        medicationsMap.put("Antifungals", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Clotrimazole"));
            add(new MedicineModel("Miconazole"));
            add(new MedicineModel("Nystatin"));
        }});

        medicationsMap.put("Antihistamines", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Cetirizine (Zyrtec)"));
            add(new MedicineModel("Diphenhydramine (Benadryl)"));
            add(new MedicineModel("Loratadine (Claritin)"));
        }});

        medicationsMap.put("Antihypertensives", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Labetalol"));
            add(new MedicineModel("Methyldopa"));
            add(new MedicineModel("Nifedipine"));
        }});

        medicationsMap.put("Asthma Medications", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Albuterol"));
            add(new MedicineModel("Budesonide (inhaled)"));
            add(new MedicineModel("Montelukast (Singulair)"));
        }});

        medicationsMap.put("Diabetes Medications", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Insulin"));
            add(new MedicineModel("Metformin"));
            add(new MedicineModel("Glyburide"));
        }});

        medicationsMap.put("Gastrointestinal Medications", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Calcium Carbonate (Tums)"));
            add(new MedicineModel("Famotidine (Pepcid)"));
            add(new MedicineModel("Magnesium Hydroxide (Milk of Magnesia)"));
            add(new MedicineModel("Ranitidine (Zantac)"));
        }});

        medicationsMap.put("Hormonal Medications", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Levothyroxine (Synthroid)"));
        }});

        medicationsMap.put("Immunizations", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Influenza vaccine"));
            add(new MedicineModel("Tetanus, Diphtheria, and Pertussis (Tdap)"));
        }});

        medicationsMap.put("Laxatives", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Psyllium (Metamucil)"));
            add(new MedicineModel("Docusate (Colace)"));
            add(new MedicineModel("Polyethylene Glycol (MiraLAX)"));
        }});

        medicationsMap.put("Prenatal Vitamins", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Folic Acid"));
            add(new MedicineModel("Iron Supplements"));
            add(new MedicineModel("Calcium"));
            add(new MedicineModel("Vitamin D"));
        }});

        medicationsMap.put("Topical Medications", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Hydrocortisone (for skin conditions)"));
            add(new MedicineModel("Benzoyl Peroxide (for acne)"));
        }});

        medicationsMap.put("Miscellaneous", new ArrayList<MedicineModel>() {{
            add(new MedicineModel("Acyclovir (for herpes infections)"));
            add(new MedicineModel("Low-dose Aspirin (for preeclampsia prevention, under medical supervision)"));
            add(new MedicineModel("Progesterone (for certain high-risk pregnancies)"));
        }});

        // Save to Firebase
        for (Map.Entry<String, ArrayList<MedicineModel>> entry : medicationsMap.entrySet()) {
            databaseReference.child(entry.getKey()).setValue(entry.getValue());
        }
    }
}
