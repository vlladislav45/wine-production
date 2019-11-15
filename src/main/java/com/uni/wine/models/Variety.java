package com.uni.wine.models;

public class Variety {
    private int idVariety;
    private String varietyName;

    public Variety() { }

    public Variety(String varietyName) {
        this.varietyName = varietyName;
    }

    public int getIdVariety() {
        return idVariety;
    }

    public void setIdVariety(int idVariety) {
        this.idVariety = idVariety;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }
}
