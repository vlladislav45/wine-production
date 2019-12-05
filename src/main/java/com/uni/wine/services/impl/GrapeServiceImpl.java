package com.uni.wine.services.impl;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.impl.VarietyDaoImpl;
import com.uni.wine.services.GrapeService;

import java.util.Map;

public class GrapeServiceImpl implements GrapeService {
    private GrapeDAO grapeDao;
    private VarietyDaoImpl varietyDao;

    public GrapeServiceImpl(GrapeDAO grapeDao, VarietyDaoImpl varietyDao) {
        this.grapeDao = grapeDao;
        this.varietyDao = varietyDao;
    }

    @Override
    public void addToWh(float quantity, String variety) {

    }

    @Override
    public Map<String, Integer> getAvailableVarieties() {
        //varietyDao.getVarietyById();
        return null;
    }
}
