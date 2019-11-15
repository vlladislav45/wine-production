package com.uni.wine.dao.impl;

import com.uni.wine.dao.BaseDAO;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.mappers.UserRoleMapper;
import com.uni.wine.models.UserRole;

import java.util.Map;

public class UserRoleDaoImpl {
    private static final String TABLE_NAME = "user_roles";
    private final JDBCConnector connector;

    public UserRoleDaoImpl(JDBCConnector connector) {
        this.connector = connector;
    }

    public void add(UserRole element) {
        String query = "INSERT INTO "+ TABLE_NAME +"(user_role) VALUES(?)";

        this.connector.executeQuery(query, element.getRoleName());
    }

    //Добавяне на ролите от които имаме нужда
    public void addAll(UserRole... roles) {
        for (UserRole role : roles) {
            this.add(role);
        }
    }

    public int getRoleId(String role) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_role = '" + role + "'";
        return (int) connector.executeQueryWithSingleResult(query).get("id_role");
    }

    public int count() {
        return connector.getCount(TABLE_NAME, "");
    }

    public UserRole getRoleById(int roleId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_role = " + roleId;
        Map<String, Object> resultMap = connector.executeQueryWithSingleResult(query);

        return UserRoleMapper.map(resultMap);
    }
}
