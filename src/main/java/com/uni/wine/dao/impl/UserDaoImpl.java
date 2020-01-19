package com.uni.wine.dao.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.mappers.UserMapper;
import com.uni.wine.businesslayer.entities.User;
import com.uni.wine.businesslayer.entities.UserRole;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDAO {
    private static final String TABLE_NAME = "users";
    private final JDBCConnector jdbcConnector;
    private final UserRoleDaoImpl roleDao;

    public UserDaoImpl(JDBCConnector jdbcConnector,
                       UserRoleDaoImpl roleDao) {
        this.jdbcConnector = jdbcConnector;
        this.roleDao = roleDao;
    }

    @Override
    public int getHosts() {
        User user = new User();
        user.setAccessLevel(new UserRole("HOST"));
        int roleId = roleDao.getRoleId(user.getAccessLevel().getRoleName());

        return jdbcConnector.getCount(TABLE_NAME, " Where id_role = " + roleId);
    }

    @Override
    public void changeRole(String username, int idRole) {

        String query = "UPDATE " + TABLE_NAME +
                                    " SET id_role=?" +
                                    " WHERE username=?";

        jdbcConnector.executeQuery(query, idRole, username);
    }

    @Override
    public List<Map<String,Object>> getAllUsers() { // Get all users at database

        String query = "SELECT * FROM " + TABLE_NAME;

        List<Map<String, Object>> result = jdbcConnector.executeQueryWithMultipleResult(query);
        return result;
    }

    //Add user to the database
    @Override
    public void add(User user) {

        String query =
                "INSERT INTO "
                        + TABLE_NAME +
                        "(username, user_pass,id_role) VALUES(?,?,?)";

        int roleId = roleDao.getRoleId(user.getAccessLevel().getRoleName());

        jdbcConnector.executeQuery(query, user.getLogin(), user.getPassword(), roleId);
    }

    @Override
    public User getById(int idUser) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_user=" + idUser;
        Map<String,Object> result = jdbcConnector.executeQueryWithSingleResult(query);

        int roleId = (int) result.get("id_role");
        UserRole userRole = roleDao.getRoleById(roleId);

        User user = UserMapper.map(result);
        user.setAccessLevel(userRole);

        return user;
    }

    @Override
    public User getByParams(String username, String password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username='" + username + "'" +
                       " AND user_pass='" + password + "'";

        Map<String,Object> result = jdbcConnector.executeQueryWithSingleResult(query);

        if(result.isEmpty()) {
            return null;
        }
        User user = UserMapper.map(result);
        int roleId = Integer.parseInt(String.valueOf(result.get("id_role")));
        UserRole role = roleDao.getRoleById(roleId);
        user.setAccessLevel(role);

        return user;

    }

    @Override
    public int getId(String username) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = '" + username + "'";

        return (int) jdbcConnector.executeQueryWithSingleResult(query).get("id_user");
    }

    @Override
    public void removeByUsername(String username) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE username=?";

        jdbcConnector.executeQuery(query, username);
    }

    @Override
    public void update(String passUser, User user) {
        String query = "UPDATE " + TABLE_NAME +
                       " SET user_pass=? " +
                       " WHERE username=?";

        jdbcConnector.executeQuery(query, passUser, user.getLogin());
    }

    @Override
    public int count() {
        return jdbcConnector.getCount(TABLE_NAME, "");
    }
}
