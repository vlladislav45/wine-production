package com.uni.wine;


import com.uni.wine.controllers.MainController;
import com.uni.wine.dao.BottleDAO;
import com.uni.wine.dao.BottledWineDAO;
import com.uni.wine.dao.GrapeDAO;
import com.uni.wine.dao.WineDAO;
import com.uni.wine.dao.impl.*;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.services.*;
import com.uni.wine.services.impl.BottleServiceImpl;
import com.uni.wine.services.impl.GrapeServiceImpl;
import com.uni.wine.services.impl.UserServiceImpl;
import com.uni.wine.services.impl.WineServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class App extends Application {
    private static ServiceWrapper serviceWrapper = new ServiceWrapper();
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/MainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ((MainController)loader.getController()).setServiceWrapper(serviceWrapper);

        stage.setResizable(false);
        stage.setTitle("Wine");
        stage.getIcons().add(new Image("images/wine_ico.png"));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("======Started======");

        initServicesWrapper();
        Logger.getRootLogger().setLevel(Level.OFF);
        //connector.initTables();
        launch(args);
    }

    private static void initServicesWrapper() throws SQLException, ClassNotFoundException {
        JDBCConnector connector = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");
        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl(connector);
        UserDaoImpl userDao = new UserDaoImpl(connector, userRoleDao);
        VarietyDaoImpl varietyDao = new VarietyDaoImpl(connector);
        GrapeDAO grapeDao = new GrapeDaoImpl(connector, varietyDao, userDao);
        WineTypeDaoImpl wineTypeDao = new WineTypeDaoImpl(connector);
        WineDAO wineDao = new WineDaoImpl(connector, wineTypeDao, grapeDao);
        BottleDAO bottleDao = new BottleDaoImpl(connector);
        BottledWineDAO bottledWineDao = new BottledWineDaoImpl(connector, userDao, wineDao);

        UserService userService = new UserServiceImpl(userDao, userRoleDao);
        GrapeService grapeService = new GrapeServiceImpl(grapeDao, varietyDao);
        BottleService bottleService = new BottleServiceImpl(bottleDao);
        WineService wineService = new WineServiceImpl(wineDao, bottleDao);

        serviceWrapper.setServices(userService, grapeService, bottleService, wineService);
    }
}
