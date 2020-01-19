package com.uni.wine.businesslayer;

import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.entities.Wine;

import java.util.List;
import java.util.Map;

public interface WineService {
    int checkWineExist(String wineName, String username, String variety);

    boolean addWine(String wineName, String wineType, String username, String varietyName);

    List<Map<String,Object>> getUserWine(int idWine);

    void setWineFromKgGrape(float litres);

    //TODO: collection of wines with quantity
    //wine_id fk, variety_id fk, quantity (many to many join table)
    //wineName to unique
    void setWineVarietyQuantity(String wineName, String variety, int quantity);

    Map<Bottle, Integer> getFilledBottles();
}
