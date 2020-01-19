package com.uni.wine.businesslayer.entities;

public class WineType {
    private int idWine;
    private String typeName;

    public WineType() { }

    public WineType(String typeName) {
        this.typeName = typeName;
    }

    public int getIdWine() {
        return idWine;
    }

    public void setIdWine(int idWine) {
        this.idWine = idWine;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
