package com.uni.wine.businesslayer.entities;

public class BottledWine {
    Wine wine;
    Bottle bottle;
    User user;
    private int quantityBottled;

    public BottledWine() {}

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public Bottle getBottleVolume() {
        return bottle;
    }

    public void setBottleVolume(Bottle bottle) {
        this.bottle = bottle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantityBottled() {
        return quantityBottled;
    }

    public void setQuantityBottled(int quantityBottled) {
        this.quantityBottled = quantityBottled;
    }
}
