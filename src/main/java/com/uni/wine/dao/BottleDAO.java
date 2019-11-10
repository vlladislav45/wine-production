package com.uni.wine.dao;

import com.uni.wine.models.Bottle;

public interface BottleDAO {
    void add(Bottle bottle);

    int getQuantity(int volume);

    void removeBottle(Bottle bottle);
}
