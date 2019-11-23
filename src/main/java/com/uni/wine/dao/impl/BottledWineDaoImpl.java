package com.uni.wine.dao.impl;

import com.uni.wine.db.JDBCConnector;
import com.uni.wine.dao.BottledWineDAO;
import com.uni.wine.models.Bottle;
import com.uni.wine.models.User;
import com.uni.wine.models.Wine;
import com.uni.wine.models.WineType;

import java.util.Map;

public class BottledWineDaoImpl implements BottledWineDAO {
    private static final String TABLE_NAME = "bottled_wine";
    private final JDBCConnector connector;
    private final BottleDaoImpl bottleDao;
    private final UserDaoImpl userDao;
    private final WineDaoImpl wineDao;
    private final WineTypeDaoImpl typeDao;

    public BottledWineDaoImpl(JDBCConnector connector, BottleDaoImpl bottle, UserDaoImpl user, WineDaoImpl wine, WineTypeDaoImpl type)
    {
        this.connector = connector;
        this.bottleDao = bottle;
        this.userDao = user;
        this.wineDao = wine;
        this.typeDao = type;
    }
    @Override
    public void add(Wine wine, User user, Bottle bottle, int quantity)
    {
        String query =
                "INSERT INTO " + TABLE_NAME +
                "(id_user,id_wine,bottle_type,quantity_bottled) VALUES(?,?,?,?)";

        int idUser = userDao.getId(user.getLogin());
        int idWine = wineDao.getId(wine.getWineName());
        int bottletype = bottle.getVolume();

        connector.executeQuery(query,idUser,idWine,bottletype,quantity);
    }
    @Override
    public int getQuantityByType(Wine wine)
    {
        String query =
            "SELECT SUM(quantity_bottled) from "+ TABLE_NAME +
                    " WHERE id_wine = " + wineDao.getId(wine.getWineName());

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);
        String quantity = result.values().stream().findFirst().get().toString();
        int res=Integer.parseInt(quantity);
        return res;
    }
    @Override
    public int getQuantityByTypeandUser(Wine wine, User user)
    {
        String query =
                "SELECT quantity_bottled from "+ TABLE_NAME +
                        " WHERE id_wine = " + wineDao.getId(wine.getWineName()) +
                        " and id_user = "+ userDao.getId(user.getLogin());

        Map<String,Object> result = connector.executeQueryWithSingleResult(query);
        String quantity = result.values().stream().findFirst().get().toString();
        int res=Integer.parseInt(quantity);
        return res;
    }
    @Override
    public void addByTypeandUser(Wine wine,User user,int quantity)
    {
        String query =
                "UPDATE "+TABLE_NAME+
                        " SET quantity_bottled = quantity_bottled + "+ quantity +
                        " WHERE id_user = " + userDao.getId(user.getLogin()) +
                        " and id_wine = " + wineDao.getId(wine.getWineName());
        connector.executeQuery(query);
    }
    @Override
    public void removeByTypeandUser(Wine wine,User user,int quantity)
    {
        String query =
                "UPDATE "+TABLE_NAME+
                        " SET quantity_bottled = quantity_bottled - "+ quantity +
                        " WHERE id_user = " + userDao.getId(user.getLogin()) +
                        " and id_wine = " + wineDao.getId(wine.getWineName());
        connector.executeQuery(query);
    }
}