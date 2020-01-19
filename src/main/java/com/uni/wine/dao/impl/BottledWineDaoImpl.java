package com.uni.wine.dao.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.dao.BottledWineDAO;
import com.uni.wine.businesslayer.entities.Bottle;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.Wine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class BottledWineDaoImpl implements BottledWineDAO {
    private static final String TABLE_NAME = "bottled_wine";
    private final JDBCConnector connector;
    private final UserDAO userDao;
    private final WineDAO wineDao;
    private final WineTypeDaoImpl wineTypeDao;

    public BottledWineDaoImpl(JDBCConnector connector, UserDAO user, WineDAO wine, WineTypeDaoImpl wineTypeDao)
    {
        this.connector = connector;
        this.userDao = user;
        this.wineDao = wine;
        this.wineTypeDao = wineTypeDao;
    }

    @Override
    public int getUserId(String username) {
        User user = new User();
        user.setLogin(username);
        int idUser = userDao.getId(user.getLogin());


        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id_user = " + idUser;

        return (int) connector.executeQueryWithSingleResult(query).get("id_user");
    }

    @Override
    public int getBottledWineByWineType(String wineType) {
        int idWine = wineTypeDao.getWineTypeId(wineType);

        String query = "SELECT SUM(quantity_bottled)" +
                " FROM " + TABLE_NAME +
                " INNER JOIN wines ON wines.id_wine = bottled_wine.id_wine " +
                " WHERE wines.id_type = " + idWine;

        Map<String, Object> result = connector.executeQueryWithSingleResult(query);

        for(Map.Entry<String, Object> map : result.entrySet()) {
            String key = map.getKey();
            if(key.equals("SUM(quantity_bottled)")) {
                BigDecimal value = (BigDecimal) map.getValue(); // if sum == NULL
                if(value == null) {
                    return 0; // then return 0
                }else {
                    break; // if value is not null break the loop
                }
            }
        }

        // (getOrElse) orElse is equal to .get()
        String quantityBottled = result.values().stream().findFirst().orElse(null).toString();

        return Integer.parseInt(quantityBottled);
    }

    @Override
    public List<Map<String,Object>> getBottledWineOnUser(String username) {
        int idUser = userDao.getId(username);

        String query = "SELECT * FROM " + TABLE_NAME +
                       " INNER JOIN users on users.id_user = bottled_wine.id_user " +
                       "WHERE users.id_user = " + idUser;

        List<Map<String, Object>> result = connector.executeQueryWithMultipleResult(query);
        return result;
    }

    @Override
    public void add(String wineName, String username, int bottleVolume, int bottleQuantity, int idGrape)
    {
        String query =
                "INSERT INTO " + TABLE_NAME +
                "(id_user,id_wine,bottle_volume,quantity_bottled) VALUES(?,?,?,?)";

        Wine wine = new Wine();
        wine.setWineName(wineName);
        User user = new User();
        user.setLogin(username);
        Bottle bottle = new Bottle();
        bottle.setVolume(bottleVolume);

        int idUser = userDao.getId(user.getLogin());
        int idWine = wineDao.getIdByGrape(wineName, idGrape);
        int bottleType = bottle.getVolume();

        connector.executeQuery(query,idUser,idWine,bottleType,bottleQuantity);
    }
    @Override
    public int getQuantityByType(Wine wine)
    {
        String query =
            "SELECT SUM(quantity_bottled) from "+ TABLE_NAME +
                    " WHERE id_wine = " + wineDao.getId(wine.getWineName());

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);
        String quantity = result.values().stream().findFirst().orElse(null).toString();

        return Integer.parseInt(quantity);
    }
    @Override
    public int getQuantityByTypeandUser(Wine wine, User user)
    {
        String query =
                "SELECT quantity_bottled from "+ TABLE_NAME +
                        " WHERE id_wine = " + wineDao.getId(wine.getWineName()) +
                        " and id_user = "+ userDao.getId(user.getLogin());

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);
        String quantity = result.values().stream().findFirst().orElse(null).toString();

        return Integer.parseInt(quantity);
    }

    @Override
    public void addByTypeandUser(String wineName, String username,int bottleVolume, int bottleQuantity, int idGrape)
    {
        int idUser = userDao.getId(username);
        int idWine = wineDao.getIdByGrape(wineName, idGrape);

        String query =
                "UPDATE "+TABLE_NAME+
                        " SET quantity_bottled = quantity_bottled + "+ bottleQuantity +
                        " WHERE id_user = " + idUser +
                        " AND id_wine = " + idWine +
                        " AND bottle_volume = " + bottleVolume;
        connector.executeQuery(query);
    }
    @Override
    public void updateByTypeAndUser(Wine wine,User user,int quantity)
    {
        String query =
                "UPDATE "+TABLE_NAME+
                        " SET quantity_bottled = quantity_bottled - "+ quantity +
                        " WHERE id_user = " + userDao.getId(user.getLogin()) +
                        " and id_wine = " + wineDao.getId(wine.getWineName());
        connector.executeQuery(query);
    }

    @Override
    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }
}