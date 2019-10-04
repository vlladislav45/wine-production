package com.uni.wine.dao.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.models.User;
import com.uni.wine.models.UserRole;

public class UserDaoImpl implements UserDAO {

    private JDBCConnector jdbcConnector;

    public UserDaoImpl(JDBCConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public void changeRole(User u, UserRole role) {

    }

    @Override
    public void add(User element) {

        String query = "INSERT INTO USERS(username, password) VALUES(?,?)";


        jdbcConnector.executeQuery(query, element.getLogin(), element.getPassword());
    }

    @Override
    public User getById(int id) {
        String query = "SELECT FROM USERS WHERE id = " + id;
        jdbcConnector.executeQueryWithSingleResult(query);
        //TODO: mapper
        return null;
    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public void update(User element) {

    }
}
