package com.uni.wine.businesslayer.impl;

import com.uni.wine.businesslayer.entities.Grape;
import com.uni.wine.businesslayer.entities.Wine;
import com.uni.wine.businesslayer.entities.WineType;
import com.uni.wine.dao.BottleDAO;
import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.WineService;
import com.uni.wine.dao.impl.GrapeDaoImpl;
import com.uni.wine.dao.impl.WineTypeDaoImpl;

import java.util.List;
import java.util.Map;

public class WineServiceImpl implements WineService {
    private WineDAO wineDao;
    private BottleDAO bottleDao;
    private WineTypeDaoImpl wineTypeDao;
    private GrapeDAO grapeDao;

    public WineServiceImpl(WineDAO wineDao, BottleDAO bottleDao, WineTypeDaoImpl wineTypeDao, GrapeDAO grapeDao) {
        this.wineDao = wineDao;
        this.bottleDao = bottleDao;
        this.wineTypeDao = wineTypeDao;
        this.grapeDao = grapeDao;
    }

    @Override
    public int checkWineExist(String wineName, String username, String variety) {
        Wine wine = new Wine();
        wine.setWineName(wineName);

        int idGrape = grapeDao.getGrapeId(username, variety); // Get grape id
        Grape grape = new Grape(idGrape);

        wine.setGrape(grape);

        int idWine = wineDao.checkWineName(wine);
        if(idWine == 0) { // IF NOT EXIST
            return 0;
        }else { // IF EXIST
            return idWine;
    }
    }

    @Override
    public boolean addWine(String wineName, String wineType, String username, String varietyName) {
        int idGrape = grapeDao.getGrapeId(username, varietyName);

        Wine wine = new Wine();
        wine.setWineName(wineName);
        wine.setWineType(new WineType(wineType));

        Grape grape = new Grape();
        grape.setIdGrape(idGrape);
        wine.setGrape(grape);

        wineDao.add(wine);

        return true;
    }

    @Override
    public List<Map<String,Object>> getUserWine(int idWine) {
        return wineDao.getById(idWine);
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
