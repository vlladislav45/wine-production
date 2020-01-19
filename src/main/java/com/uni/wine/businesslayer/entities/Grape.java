package com.uni.wine.businesslayer.entities;

public class Grape {
    private int idGrape;
    Variety variety;
    private float quantity;
    User user;

    public Grape() { }

    public Grape(int idGrape) {
        this.idGrape = idGrape;
    }

    public int getIdGrape() {
        return idGrape;
    }

    public void setIdGrape(int idGrape) {
        this.idGrape = idGrape;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
