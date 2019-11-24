package com.uni.wine.models;

public class Bottle {
    private int volume;
    private int quantity;

    public Bottle() {

    }

    public Bottle(int volume, int quantity) {
        this.volume = volume;
        this.quantity = quantity;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
