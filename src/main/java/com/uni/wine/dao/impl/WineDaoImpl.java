package com.uni.wine.dao.impl;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.mappers.WineMapper;
import com.uni.wine.businesslayer.entities.Wine;

import java.util.List;
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
    public int checkWineName(Wine wine) {
        int idGrape = wine.getGrape().getIdGrape();

        String query = "SELECT * FROM " + TABLE_NAME +
                       " INNER JOIN grapes on grapes.id_grape = wines.id_grape " +
                       " WHERE wine_name = '" + wine.getWineName() + "' AND grapes.id_grape = " + idGrape;

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);

        if(result.isEmpty()) {
            return 0;
        }else {
            String idWine = result.values().stream().findFirst().get().toString();
            int res=Integer.parseInt(idWine);
            return res;
        }

    }

    @Override
    public void add(Wine wine) {
        String query = "INSERT INTO " + TABLE_NAME +
                       "(wine_name,id_type,id_grape) VALUES(?,?,?)";

        int idTypeWine = wineTypeDao.getWineTypeId(wine.getWineType().getTypeName());
        int idGrape = grapeDao.getIdGrapeById(wine.getGrape().getIdGrape());

        connector.executeQuery(query, wine.getWineName(), idTypeWine, idGrape);
    }

    @Override
    public int getIdByGrape(String wineName, int idGrape) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE wine_name = '" + wineName + "' AND id_grape = " + idGrape;

        return (int) connector.executeQueryWithSingleResult(query).get("id_wine");
    }

    @Override
    public int getId(String wineName) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE wine_name = '" + wineName + "'";

        return (int) connector.executeQueryWithSingleResult(query).get("id_wine");
    }

    @Override
    public List<Map<String,Object>> getById(int idWine) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_wine = " + idWine;

        List<Map<String,Object>> result = connector.executeQueryWithMultipleResult(query);

        return result;
    }

    @Override
    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }
}
