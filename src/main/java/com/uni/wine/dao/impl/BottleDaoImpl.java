package com.uni.wine.dao.impl;

import com.uni.wine.dao.BottleDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.models.Bottle;
import com.uni.wine.mappers.BottleMapper;

import java.util.Map;

public class BottleDaoImpl implements BottleDAO {
    private static final String TABLE_NAME = "bottles";
    private final JDBCConnector connector;

    public BottleDaoImpl(JDBCConnector connector) {
        this.connector = connector;
    }

    //Add user to the database
    @Override
    public void add(Bottle bottle) {

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
        String quantity = result.values().stream().findFirst().get().toString();
        int res=Integer.parseInt(quantity);
        return res;
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