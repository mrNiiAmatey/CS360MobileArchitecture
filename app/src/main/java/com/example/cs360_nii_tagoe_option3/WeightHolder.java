package com.example.cs360_nii_tagoe_option3;

public class WeightHolder {
    private int id;
    private String date;
    private String weight;

    public WeightHolder(int id, String date, String weight) {
        this.id = id;
        this.date = date;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }
}
