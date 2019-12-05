package com.uni.wine.dao.impl;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.GrapeMapper;
import com.uni.wine.mappers.UserMapper;
import com.uni.wine.mappers.VarietyMapper;
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
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_grape = " + id;
        Map<String,Object> result = connector.executeQueryWithSingleResult(query);

        return GrapeMapper.map(result);
    }

    @Override
    public int getGrapeId(int idGrape) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_grape = " + idGrape;

        return (int) connector.executeQueryWithSingleResult(query).get("id_grape");
    }

    @Override
    public double getSumById(String varietyName) {
        String query = "SELECT SUM(grape_quantity)" +
                       " FROM " + TABLE_NAME +
                       " INNER JOIN varieties ON varieties.id_variety = grapes.id_variety " +
                       " WHERE varieties.variety_name = '" + varietyName + "'";

        Map<String, Object> result = connector.executeQueryWithSingleResult(query);
        Object quantity = result.values().stream().findFirst().orElse(null); // (getOrElse) orElse is equal to .get()

        return (Double) quantity;
    }

    @Override
    public void getGrapeByUsername(String username){
        //TODO
        String query = "SELECT grape_quantity, users.username, varieties.variety_name " +
                       "FROM " + TABLE_NAME +
                       " INNER JOIN users ON users.id_user = grapes.id_user " +
                       "INNER JOIN varieties ON varieties.id_variety = grapes.id_variety " +
                       "WHERE users.username = '" + username + "'";

        Map<String, Object> result = connector.executeQueryWithSingleResult(query);

        Object quantity = result.values().stream().findFirst().orElse(null);
        Object name = result.values().stream().findFirst().orElse(null);

        System.out.println(GrapeMapper.map(result).getQuantity());
        //System.out.println(UserMapper.map(result).getLogin());
        //System.out.println(VarietyMapper.map(result).getVarietyName());
    }

    @Override
    public void removeById(int idGrape) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id_grape=?";

        connector.executeQuery(query, idGrape);
    }

    @Override
    public void update(float quantity, int id) {
        String query = "UPDATE grapes" +
                       " INNER JOIN users ON users.id_user = grapes.id_user" +
                       " SET grape_quantity=?" +
                       " WHERE users.id_user=?";

        connector.executeQuery(query, quantity, id);
    }

}
