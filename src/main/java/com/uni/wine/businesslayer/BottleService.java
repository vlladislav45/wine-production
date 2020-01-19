package com.uni.wine.businesslayer;

import com.uni.wine.businesslayer.entities.Bottle;

import java.util.List;
import java.util.Map;

public interface BottleService {
    void removeBottle(int bottleVolume, int bottleQuantity);

    int getBottledWineByWineType(String wineType); // Get all wines with their types

    List<Map<String,Object>> getBottledWineOnUser(String username); // get bottled wine on one user

    List<Map<String,Object>> getAvailableBottles(); // get available bottles (Empty bottles)

    boolean addBottle(int volume, int quantity);

    boolean updateBottle(int volume, int quantity);

    boolean updateBottledWines(String wineName, String username, String varietyName ,int bottleVolume, int bottleQuantity);

    void addbottledWines(String wineName, String username,String varietyName, int bottleVolume, int bottleQuantity);

    List<Integer> fillTheBottles(float Qwine);
}
