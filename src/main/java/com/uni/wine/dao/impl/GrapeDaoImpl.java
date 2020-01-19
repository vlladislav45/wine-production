package com.uni.wine.dao.impl;

import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.Variety;
import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.UserDAO;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.mappers.GrapeMapper;
import com.uni.wine.businesslayer.entities.Grape;

import java.util.List;
import java.util.Map;

public class GrapeDaoImpl implements GrapeDAO {
    private static final String TABLE_NAME = "grapes";
    private final JDBCConnector connector;
    private final VarietyDaoImpl varietyDao;
    private final UserDAO userDao;

    public GrapeDaoImpl(JDBCConnector connector, VarietyDaoImpl variety, UserDAO user) {
        this.connector = connector;
        this.varietyDao = variety;
        this.userDao = user;
    }

    @Override
    public float getSingleUserGrapes(String username, String variety) {
        int idUser = userDao.getId(username);
        int idVariety = varietyDao.getVarietyId(variety);

        String query = "SELECT * FROM " + TABLE_NAME +
                " INNER JOIN users ON users.id_user = grapes.id_user" +
                " WHERE grapes.id_user = " + idUser +
                " AND grapes.id_variety = " + idVariety;

        return (float) connector.executeQueryWithSingleResult(query).get("grape_quantity");
    }

    @Override
    public List<Map<String,Object>> getUserGrapes(String username) { // Get all grapes for certain user at database
        int idUser = userDao.getId(username);

        String query = "SELECT * FROM " + TABLE_NAME +
                       " INNER JOIN users ON users.id_user = grapes.id_user" +
                       " WHERE grapes.id_user = " + idUser;

        List<Map<String, Object>> result = connector.executeQueryWithMultipleResult(query);
        return result;
    }


    @Override
    public int getUserId(String username, String variety) {
        User user = new User();
        user.setLogin(username);
        int idUser = userDao.getId(user.getLogin());

        int idVariety = varietyDao.getVarietyId(variety);


        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_user = " + idUser +
                       " AND id_variety = " + idVariety;

        return (int) connector.executeQueryWithSingleResult(query).get("id_user");
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
    public int getIdGrapeById(int idGrape) {
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE id_grape = " + idGrape;

        return (int) connector.executeQueryWithSingleResult(query).get("id_grape");
    }

    @Override
    public int getGrapeId(String username, String varietyName) {
        int idUser = userDao.getId(username);

        String query = "SELECT * FROM " + TABLE_NAME +
                       " INNER JOIN varieties ON varieties.id_variety = grapes.id_variety " +
                       " WHERE id_user = " + idUser + " AND varieties.variety_name = '" + varietyName + "'";

        return (int) connector.executeQueryWithSingleResult(query).get("id_grape");
    }

    @Override
    public double getSumByVariety(String varietyName) {
        String query = "SELECT SUM(grape_quantity)" +
                       " FROM " + TABLE_NAME +
                       " INNER JOIN varieties ON varieties.id_variety = grapes.id_variety " +
                       " WHERE varieties.variety_name = '" + varietyName + "'";

        Map<String, Object> result = connector.executeQueryWithSingleResult(query);

        for(Map.Entry<String, Object> map : result.entrySet()) {
            String key = map.getKey();
            if(key.equals("SUM(grape_quantity)")) {
                Double value = (Double) map.getValue(); // if sum == NULL
                if(value == 0.0) {
                    return 0; // then return 0
                }else {
                    break; // if value is not null break the loop
                }
            }
        }

        Object quantity = result.values().stream().findFirst().orElse(null); // (getOrElse) orElse is equal to .get()

        return (Double) quantity;
    }

    @Override
    public void removeById(int idGrape) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id_grape=?";

        connector.executeQuery(query, idGrape);
    }

    @Override
    public void update(float quantity, int idUser, int idVariety) {
        String query = "UPDATE grapes" +
                       " INNER JOIN users ON users.id_user = grapes.id_user" +
                       " INNER JOIN varieties ON varieties.id_variety = grapes.id_variety" +
                       " SET grape_quantity=grape_quantity + ?" +
                       " WHERE users.id_user=? AND varieties.id_variety=?";

        connector.executeQuery(query, quantity, idUser, idVariety);
    }

}
