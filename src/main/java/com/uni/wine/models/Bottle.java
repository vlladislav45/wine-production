package com.uni.wine.models;

public class Bottle {
    private int id;
    private int volume;
    private int quantity;

    public Bottle() {

    }

    public Bottle(int id, int volume, int quantity) {
        this.id = id;
        this.volume = volume;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
