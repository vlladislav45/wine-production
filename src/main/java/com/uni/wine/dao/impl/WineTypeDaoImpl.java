package com.uni.wine.dao.impl;

import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.mappers.WineTypeMapper;
import com.uni.wine.businesslayer.entities.WineType;

import java.util.Map;

public class WineTypeDaoImpl {
    private static final String TABLE_NAME = "wine_types";
    private JDBCConnector connector;

    public WineTypeDaoImpl(JDBCConnector connector) {
        this.connector = connector;
    }

    public void add(WineType wineType) {
        String query = "INSERT INTO " + TABLE_NAME +
                       " (type_name) VALUES (?)";

        connector.executeQuery(query, wineType.getTypeName());
    }

    public void addAll(WineType... wineTypes) {
        for (WineType wineType : wineTypes) {
            this.add(wineType);
        }
    }

    public void removeTypeByName(String typeWine) {
        String query = "DELETE FROM " + TABLE_NAME +
                       " WHERE type_name=?";

        connector.executeQuery(query, typeWine);
    }

    public int getWineTypeId(String typeName) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE type_name = '" + typeName + "'";

        return (int) connector.executeQueryWithSingleResult(query).get("id_type");
    }

    public WineType getWineTypeById(int idTypeWine) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_type = " + idTypeWine;

        Map<String, Object> result = connector.executeQueryWithSingleResult(query);

        return WineTypeMapper.map(result);
    }

    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }
}
