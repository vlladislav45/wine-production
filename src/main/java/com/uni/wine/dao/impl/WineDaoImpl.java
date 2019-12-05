package com.uni.wine.dao.impl;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.WineMapper;
import com.uni.wine.models.Wine;

import java.util.Map;

public class WineDaoImpl implements WineDAO {
    private static final String TABLE_NAME = "wines";
    private final JDBCConnector connector;
    private final WineTypeDaoImpl wineTypeDao;
    private final GrapeDAO grapeDao;

    public WineDaoImpl(JDBCConnector connector, WineTypeDaoImpl wineTypeDao, GrapeDAO grapeDao) {
        this.connector = connector;
        this.wineTypeDao = wineTypeDao;
        this.grapeDao = grapeDao;
    }

    @Override
    public void add(Wine wine) {
        String query = "INSERT INTO " + TABLE_NAME +
                       "(wine_name,id_type,id_grape) VALUES(?,?,?)";

        int idTypeWine = wineTypeDao.getWineTypeId(wine.getWineType().getTypeName());
        int idGrape = grapeDao.getGrapeId(wine.getGrape().getIdGrape());

        connector.executeQuery(query, wine.getWineName(), idTypeWine, idGrape);
    }

    @Override
    public void update(String oldWineName, String newWineName) {
        String query = "UPDATE " + TABLE_NAME +
                       "SET wine_name = " + newWineName +
                       "WHERE wine_name = " + oldWineName;

        connector.executeQuery(query, newWineName, oldWineName);
    }

    @Override
    public int getId(String wineName) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE wine_name = '" + wineName + "'";

        return (int) connector.executeQueryWithSingleResult(query).get("id_wine");
    }

    @Override
    public Wine getById(int idWine) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_wine = " + idWine;

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);

        return WineMapper.map(result);
    }

    @Override
    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }
}
