package com.uni.wine.dao.impl;

import com.uni.wine.dao.BottleDAO;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.businesslayer.entities.Bottle;

import java.util.List;
import java.util.Map;

public class BottleDaoImpl implements BottleDAO {
    private static final String TABLE_NAME = "bottles";
    private final JDBCConnector connector;

    public BottleDaoImpl(JDBCConnector connector) {
        this.connector = connector;
    }

    @Override
    public List<Map<String,Object>> getAllBottles() { // Get all bottles at database

        String query = "SELECT * FROM " + TABLE_NAME;

        List<Map<String, Object>> result = connector.executeQueryWithMultipleResult(query);
        return result;
    }

    @Override
    public void add(Bottle bottle)
    {
        String query =
                "INSERT INTO "+ TABLE_NAME +
                        "(bottle_volume,bottle_quantity) VALUES(?,?)";
        connector.executeQuery(query,bottle.getVolume(),bottle.getQuantity());
    }
    @Override
    public void updateQuantity(Bottle bottle) {

        String query =
                "UPDATE "+ TABLE_NAME +
                        " SET bottle_quantity = bottle_quantity + " + bottle.getQuantity() +
                        " where bottle_volume = " + bottle.getVolume();

        connector.executeQuery(query);
    }
    @Override
    public int getQuantity(int volume)
    {
        String query =
                "SELECT bottle_quantity FROM " + TABLE_NAME +
                        " where bottle_volume = " + volume;

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);

        if(result.isEmpty()) {
            return -1;
        }else {
            String quantity = result.values().stream().findFirst().get().toString();
            int res=Integer.parseInt(quantity);
            return res;
        }
    }
    @Override
    public void removeBottle(Bottle bottle)
    {
        String query =
                "UPDATE "+ TABLE_NAME +
                        " SET bottle_quantity = bottle_quantity - " + bottle.getQuantity() +
                        " where bottle_volume = " + bottle.getVolume();

        connector.executeQuery(query);
    }
}