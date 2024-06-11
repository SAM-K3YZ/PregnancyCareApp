package com.finalYearProject.myapplication.model;

public class FetusDetails {
    private String sizeComparison;
    private double length;
    private double weight;
    private String description;

    // Required empty constructor for Firestore
    public FetusDetails() {
    }

    public FetusDetails(String sizeComparison, double length, double weight, String description) {
        this.sizeComparison = sizeComparison;
        this.length = length;
        this.weight = weight;
        this.description = description;
    }

    public String getSizeComparison() {
        return sizeComparison;
    }

    public void setSizeComparison(String sizeComparison) {
        this.sizeComparison = sizeComparison;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
