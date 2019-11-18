package com.uni.wine.services;

import com.uni.wine.models.Bottle;

import java.util.Map;

public interface BottleService {
    //Key -> bottle type (375, 500...)/ Value -> Amount
    void registerAvailableBottles(Map<Integer,Integer> bottlesAmount);

    void fillTheBottles();

    Map<Bottle, Integer> getEmptyBottles();
}
