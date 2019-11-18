package com.uni.wine.services;

import java.util.Map;

public interface GrapeService {
    void addToWh(float quantity, String variety);

    Map<String,Integer> getAvailableVarieties();
}
