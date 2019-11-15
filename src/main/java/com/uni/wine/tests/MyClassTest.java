package com.uni.wine.tests;

import com.uni.wine.dao.impl.UserDaoImpl;
import com.uni.wine.dao.impl.UserRoleDaoImpl;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.models.User;
import com.uni.wine.models.UserRole;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class MyClassTest {
    @Test
    public void testUserDao()  throws SQLException, ClassNotFoundException {
        JDBCConnector conn = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");
        //CONST
        final String TABLE_NAME = "users";

        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl(conn);
        UserDaoImpl userDao = new UserDaoImpl(conn, userRoleDao);

        //test model
        User user1 = new User();
        user1.setLogin("vladislav");
        user1.setPassword("asgasg");
        user1.setAccessLevel(new UserRole("OPERATOR"));

        //Add to the database
        //userDao.add(user1);

        //first param pass / username
        userDao.update("asgasg", user1);

        System.out.println("User: " + userDao.getById(1).getLogin() + "  password: " + userDao.getById(1).getPassword());
        //userDao.changeRole("admin", 3);
    }

}