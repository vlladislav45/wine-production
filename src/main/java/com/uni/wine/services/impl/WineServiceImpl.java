package com.uni.wine.services.impl;

import com.uni.wine.dao.BottleDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.models.Bottle;
import com.uni.wine.services.WineService;

import java.util.Map;

public class WineServiceImpl implements WineService {
    private WineDAO wineDao;
    private BottleDAO bottleDao;

    public WineServiceImpl(WineDAO wineDao, BottleDAO bottleDao) {
        this.wineDao = wineDao;
        this.bottleDao = bottleDao;
    }

    @Override
    public void setWineFromKgGrape(float litres) {

    }

    @Override
    public void setWineVarietyQuantity(String wineName, String variety, int quantity) {

    }

    @Override
    public Map<Bottle, Integer> getFilledBottles() {
        return null;
    }
}
