package com.uni.wine.businesslayer.impl;

import com.uni.wine.businesslayer.entities.Grape;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.Variety;
import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.UserDAO;
import com.uni.wine.dao.impl.VarietyDaoImpl;
import com.uni.wine.businesslayer.GrapeService;

import java.util.List;
import java.util.Map;

public class GrapeServiceImpl implements GrapeService {
    private GrapeDAO grapeDao;
    private VarietyDaoImpl varietyDao;
    private UserDAO userDao;

    public GrapeServiceImpl(GrapeDAO grapeDao,VarietyDaoImpl varietyDao, UserDAO userDao) {
        this.grapeDao = grapeDao;
        this.varietyDao = varietyDao;
        this.userDao = userDao;
    }

    @Override
    public float getSingleUserGrapes(String username, String variety) { // get grapes on user by single variety
        return grapeDao.getSingleUserGrapes(username, variety);
    }

    @Override
    public double getAllGrapes(String variety) { // Get all grapes in our database
        return grapeDao.getSumByVariety(variety);
    }

    @Override
    public List<Map<String,Object>> getGrapesOnUser(String username) { // Get grapes in certain user
        return grapeDao.getUserGrapes(username);
    }

    @Override
    public void addGrape(float quantity, String variety, String username) { // add once time grapes to the warehouse
        User currentUser = new User();
        currentUser.setLogin(username);

        Grape grape = new Grape();
        grape.setQuantity(quantity);
        Variety varietyOfGrape = new Variety();
        varietyOfGrape.setVarietyName(variety);
        grape.setVariety(varietyOfGrape);
        grape.setUser(currentUser);

        grapeDao.add(grape);
    }

    @Override
    public boolean updateQuantity(float quantity, String variety, String username){ // add more grapes to the wh from user

        int idUser = grapeDao.getUserId(username, variety);
        if(idUser != 0) {
            User currentUser = new User();
            currentUser.setLogin(username);

            Grape grape = new Grape();
            grape.setQuantity(quantity);
            Variety varietyOfGrape = new Variety();
            varietyOfGrape.setVarietyName(variety);
            grape.setVariety(varietyOfGrape);
            grape.setUser(currentUser);

            int idVariety = varietyDao.getVarietyId(variety);
            grapeDao.update(quantity, idUser, idVariety);
        }else {
           return false;
        }

        return true;
    }

}
