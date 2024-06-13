package com.finalYearProject.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedMedicineAdapter extends RecyclerView.Adapter<SelectedMedicineAdapter.ViewHolder> {

    private List<String> selectedMedicines;

    public SelectedMedicineAdapter(List<String> selectedMedicines) {
        this.selectedMedicines = selectedMedicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String medicine = selectedMedicines.get(position);
        holder.medicineNameTextView.setText(medicine);
    }

    @Override
    public int getItemCount() {
        return selectedMedicines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicineNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            medicineNameTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
