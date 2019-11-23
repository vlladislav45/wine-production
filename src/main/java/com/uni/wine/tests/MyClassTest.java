package com.uni.wine.tests;

import com.uni.wine.dao.impl.*;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.models.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class MyClassTest {

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

    public void testGrape() throws SQLException, ClassNotFoundException {
        JDBCConnector conn = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");
        //CONST
        final String TABLE_NAME = "varieties";

        //Dao impl
        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl(conn);
        UserDaoImpl userDao = new UserDaoImpl(conn, userRoleDao);
        VarietyDaoImpl varietyDao = new VarietyDaoImpl(conn);
        GrapeDaoImpl grapeDao = new GrapeDaoImpl(conn, varietyDao, userDao);

        User newUser = new User();
        if(varietyDao.count() == 0) {
            Variety white = new Variety();
            Variety red = new Variety();

            white.setVarietyName("white");
            red.setVarietyName("red");
            varietyDao.addAll(white, red);

            newUser.setLogin("vva");
            newUser.setPassword("gga");
            newUser.setAccessLevel(new UserRole("OPERATOR"));
            userDao.add(newUser);

            Grape grape1 = new Grape();
            grape1.setQuantity(1354.22f);
            grape1.setUser(newUser);
            grape1.setVariety(new Variety("red"));
            grapeDao.add(grape1);

        }

        if(varietyDao.count() == 2) {
            int id = 8;
            //grapeDao.update(600.20f, id);
            Grape grape1 = new Grape();
          // System.out.println(grapeDao.getById(4).getQuantity());
            //System.out.println(grapeDao.getSumById("red"));
           grapeDao.getGrapeByUsername("vladislav");
        }

    }

    @Test
    public void testWineDao() throws SQLException, ClassNotFoundException {
        JDBCConnector connector = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");

        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl(connector);
        UserDaoImpl userDao = new UserDaoImpl(connector, userRoleDao);

        VarietyDaoImpl varietyDao = new VarietyDaoImpl(connector);
        GrapeDaoImpl grapeDao = new GrapeDaoImpl(connector, varietyDao, userDao);

        WineTypeDaoImpl wineTypeDao = new WineTypeDaoImpl(connector);
        WineDaoImpl wineDao = new WineDaoImpl(connector, wineTypeDao, grapeDao);

        if(wineTypeDao.count() == 0) {
            WineType white = new WineType("white");
            WineType red = new WineType("red");
            WineType rose = new WineType("rose");

            wineTypeDao.addAll(white,red,rose);
        }

        //wineTypeDao.removeTypeByName("red"); // remove type by name

        //System.out.println(wineTypeDao.getWineTypeById(4).getTypeName()); //Get anything by id_type

        Wine wine = new Wine();
        wine.setWineName("Bear paw");
        wine.setGrape(new Grape(4));
        wine.setWineType(new WineType("red"));
        wineDao.add(wine);
    }


}