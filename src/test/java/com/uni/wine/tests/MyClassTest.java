package com.uni.wine.tests;

import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.impl.*;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.businesslayer.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.sql.SQLException;

public class MyClassTest {

    @Test
    public void testInitTables() throws SQLException, ClassNotFoundException {
        JDBCConnector connector = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");

        connector.initTables();
    }

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

        UserRole host = new UserRole("HOST");
        UserRole admin = new UserRole("ADMIN");
        UserRole operator = new UserRole("OPERATOR");
        userRoleDao.addAll(admin, host, operator);

        User user1 = new User();
        user1.setLogin("admin");
        user1.setPassword("admin");
        user1.setAccessLevel(new UserRole("ADMIN"));

        userDao.add(user1);

        //Do grape varieties black and white
        VarietyDaoImpl varietyDao = new VarietyDaoImpl(conn);

        Variety black = new Variety();
        black.setVarietyName("Black");

        Variety white = new Variety();
        white.setVarietyName("White");

        varietyDao.addAll(black,white);

        //Do wine varieties red, white, rose
        WineType whiteWine = new WineType("White");
        WineType redWine = new WineType("Red");
        WineType roseWine = new WineType("Rose");

        WineTypeDaoImpl wineTypeDao = new WineTypeDaoImpl(conn);
        wineTypeDao.addAll(whiteWine, redWine, roseWine);
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

        }

    }

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

//        Wine wine = new Wine();
//        wine.setWineName("Bear paw");
//        wine.setGrape(new Grape(4));
//        wine.setWineType(new WineType("red"));
//        wineDao.add(wine);

        System.out.println(wineDao.count());
        System.out.println("\n");
        //System.out.println(wineDao.getById(1).getWineName());
        System.out.println("\n");
        //System.out.println(wineDao.getId("Bear paw"));
    }

    public void testBottles() throws SQLException, ClassNotFoundException {
        JDBCConnector conn = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");
        //CONST
        final String TABLE_NAME = "bottles";

        //Dao impl
        BottleDaoImpl bottleDao = new BottleDaoImpl(conn);

        Bottle bottlesToAdd = new Bottle();
        bottlesToAdd.setQuantity(100);
        bottlesToAdd.setVolume(750);

        //bottleDao.updateQuantity(bottlesToAdd);

        Bottle bottlesToRemove = new Bottle();
        bottlesToRemove.setQuantity(20);
        bottlesToRemove.setVolume(750);

        //bottleDao.removeBottle(bottlesToRemove);
        Bottle newBottle=new Bottle();
        newBottle.setQuantity(100);
        newBottle.setVolume(420);
        bottleDao.add(newBottle);
        //System.out.println(bottleDao.getQuantity(750));
        System.out.println(bottleDao.getQuantity(420));
    }

    public void testALL() throws SQLException, ClassNotFoundException {
        JDBCConnector conn = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");

        BottleDaoImpl bottleDao = new BottleDaoImpl(conn);
        WineTypeDaoImpl typeDao = new WineTypeDaoImpl(conn);
        UserRoleDaoImpl roleDao = new UserRoleDaoImpl(conn);
        VarietyDaoImpl varietyDao = new VarietyDaoImpl(conn);
        UserDaoImpl userDao = new UserDaoImpl(conn,roleDao);
        GrapeDaoImpl grapeDao = new GrapeDaoImpl(conn,varietyDao,userDao);
        WineDaoImpl wineDao = new WineDaoImpl(conn,typeDao,grapeDao);
        //BottledWineDaoImpl bottledWineDao = new BottledWineDaoImpl(conn,bottleDao,userDao,wineDao,typeDao);

        //Създай роля
        UserRole role = new UserRole();
        role.setRoleName("testrole");

        //roleDao.add(role);

        //Създай тип вино
        WineType winetype = new WineType();
        winetype.setTypeName("testtype");

        //typeDao.add(winetype);

        //Създай бутилка
        Bottle bottle = new Bottle();
        bottle.setVolume(333);
        bottle.setQuantity(333);

        //bottleDao.add(bottle);

        //Създай сорт
        Variety variety = new Variety();
        variety.setVarietyName("testvariety");
        variety.setIdVariety(7);

        //varietyDao.add(variety);

        //Създай потребител
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("123123");
        user.setAccessLevel(role);

        //userDao.add(user);

        //Създай грозде
        Grape grape = new Grape();
        grape.setQuantity(100f);
        grape.setUser(user);
        grape.setVariety(new Variety("bqlo"));

        //grapeDao.add(grape);

        //Създай вино
        Wine wine = new Wine();
        wine.setWineName("testwine");
        wine.setGrape(new Grape(1));
        wine.setWineType(new WineType("rose"));

        //wineDao.add(wine);

        //Бутилирай вино
        //bottledWineDao.add(wine,user,bottle,67);

        //bottledWineDao.addByTypeandUser(wine,user,300);
        //bottledWineDao.removeByTypeandUser(wine,user,800);
        //System.out.println(bottledWineDao.getQuantityByTypeandUser(wine,user));
        //System.out.println(bottledWineDao.getQuantityByType(wine));
    }
}