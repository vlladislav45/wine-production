package com.uni.wine.services.impl;

import com.uni.wine.dao.BottleDAO;
import com.uni.wine.models.Bottle;
import com.uni.wine.services.BottleService;

import java.util.Map;

public class BottleServiceImpl implements BottleService {
    private BottleDAO bottleDao;

    public BottleServiceImpl(BottleDAO bottleDao) {
        this.bottleDao = bottleDao;
    }

    @Override
    public void registerAvailableBottles(Map<Integer, Integer> bottlesAmount) {

    }

    @Override
    public void fillTheBottles() {

    }

    @Override
    public Map<Bottle, Integer> getEmptyBottles() {
        return null;
    }
}
