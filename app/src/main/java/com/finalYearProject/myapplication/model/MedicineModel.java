package com.finalYearProject.myapplication.model;

public class MedicineModel {
    private String name;

    //constructor to store data to the database
    public MedicineModel(){

    }

    public MedicineModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
