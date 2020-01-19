package com.uni.wine.dao;

import com.uni.wine.businesslayer.entities.Bottle;

import java.util.List;
import java.util.Map;

public interface BottleDAO {
    List<Map<String,Object>> getAllBottles(); // Get available bottles

    void add(Bottle bottle);

    void updateQuantity(Bottle bottle);

    int getQuantity(int volume);

    void removeBottle(Bottle bottle);
}
