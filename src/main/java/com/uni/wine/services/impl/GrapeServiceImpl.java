package com.uni.wine.services.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.dao.impl.VarietyDaoImpl;
import com.uni.wine.services.GrapeService;

import java.util.Map;

public class GrapeServiceImpl implements GrapeService {
    private VarietyDaoImpl varietyDao;
    private UserDAO userDao;

    public GrapeServiceImpl(VarietyDaoImpl varietyDao, UserDAO userDao) {
        this.varietyDao = varietyDao;
        this.userDao = userDao;
    }

    @Override
    public void addToWh(float quantity, String variety){

    }

    @Override
    public Map<String,Integer> getAvailableVarieties() {
        return null;
    }
}
