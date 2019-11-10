package com.uni.wine.dao.impl;

import com.uni.wine.dao.UserDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.UserMapper;
import com.uni.wine.models.User;
import com.uni.wine.models.UserRole;

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

    //(User u, UserRole role)
    @Override
    public void changeRole(String username, int id_role) {

        String query = "UPDATE " + TABLE_NAME +
                                    " SET id_role = " + id_role +
                                    " WHERE username = '" + username + "'";

        jdbcConnector.executeQueryWithSingleResult(query);

        System.out.println("Finished");
    }

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
    public User getById(int id) {
        String query = "SELECT * FROM USERS WHERE id_user = '" + id + "'";
        Map<String,Object> result = jdbcConnector.executeQueryWithSingleResult(query);

        int roleId = (int) result.get("id_role");
        UserRole userRole = roleDao.getRoleById(roleId);

        User user = UserMapper.map(result);
        user.setAccessLevel(userRole);

        return user;
    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public void update(User element) {

    }

    @Override
    public int count() {
        return jdbcConnector.getCount(TABLE_NAME, "");
    }
}
