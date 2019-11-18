package com.uni.wine.services;

import com.uni.wine.models.Bottle;

import java.util.Map;

public interface WineService {
    void setWineFromKgGrape(float litres);

    //TODO: collection of wines with quantity
    //wine_id fk, variety_id fk, quantity (many to many join table)
    //wineName to unique
    void setWineVarietyQuantity(String wineName, String variety, int quantity);

    Map<Bottle, Integer> getFilledBottles();
}
