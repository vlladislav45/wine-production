package com.uni.wine.dao.impl;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.GrapeMapper;
import com.uni.wine.models.Grape;

import java.util.Map;

public class GrapeDaoImpl implements GrapeDAO {
    private static final String TABLE_NAME = "grapes";
    private final JDBCConnector connector;
    private final VarietyDaoImpl varietyDao;
    private final UserDaoImpl userDao;

    public GrapeDaoImpl(JDBCConnector connector, VarietyDaoImpl variety, UserDaoImpl user) {
        this.connector = connector;
        this.varietyDao = variety;
        this.userDao = user;
    }

    @Override
    public void add(Grape grape){

        String query = "INSERT INTO " + TABLE_NAME +
                       "(id_variety, grape_quantity, id_user) VALUES (?,?,?)";

        int idVariety = varietyDao.getVarietyId(grape.getVariety().getVarietyName());
        int idUser = userDao.getId(grape.getUser().getLogin());

        connector.executeQuery(query, idVariety, grape.getQuantity(), idUser);
    }

    @Override
    public Grape getById(int id) {
       // String query = "SELECT * FROM " + TABLE_NAME +
             return null;
    }

    @Override
    public Grape getSumById(int idVariety) {
        String query = "SELECT SUM(grape_quantity) " +
                       "FROM " + TABLE_NAME +
                       "WHERE id_variety = " + idVariety;

       Map<String, Object> result = connector.executeQueryWithSingleResult(query);

       return GrapeMapper.map(result);
    }

    @Override
    public int removeById(int id) {
        return 0;
    }

    @Override
    public int count() {
        return 0;
    }
}
