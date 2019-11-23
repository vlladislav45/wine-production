package com.uni.wine.dao.impl;

import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.VarietyMapper;
import com.uni.wine.models.Variety;

import java.util.Map;

public class VarietyDaoImpl {
    private static final String TABLE_NAME = "varieties";
    private final JDBCConnector connector;

    public VarietyDaoImpl(JDBCConnector connector) {
        this.connector = connector;
    }

    public void add(Variety variety) {
        String query = "INSERT INTO " + TABLE_NAME + " (variety_name) VALUES (?)";

        connector.executeQuery(query, variety.getVarietyName());
    }

    public void addAll(Variety... varieties) {
        for(Variety variety : varieties) {
            this.add(variety);
        }
    }

    public int getVarietyId(String variety) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE variety_name = '" + variety + "'";

        return (int) connector.executeQueryWithSingleResult(query).get("id_variety");
    }

    public Variety getVarietyById(int idVariety) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       "WHERE variety_name = " + idVariety;

        Map<String, Object> resultMap = connector.executeQueryWithSingleResult(query);

        return VarietyMapper.map(resultMap);
    }

    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }

}