package com.uni.wine;


import com.uni.wine.controllers.AdminController;
import com.uni.wine.controllers.MainController;
import com.uni.wine.dao.*;
import com.uni.wine.dao.impl.*;
import com.uni.wine.databaselayer.JDBCConnector;
import com.uni.wine.businesslayer.*;
import com.uni.wine.businesslayer.impl.BottleServiceImpl;
import com.uni.wine.businesslayer.impl.GrapeServiceImpl;
import com.uni.wine.businesslayer.impl.UserServiceImpl;
import com.uni.wine.businesslayer.impl.WineServiceImpl;
import com.uni.wine.notifications.NotificationManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;

public class App extends Application {
    private static ServiceWrapper serviceWrapper;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/MainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        MainController mainController = loader.getController();
        // Set data in the controller
        mainController.setServiceWrapper(serviceWrapper);



        stage.setTitle("Wine");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/wine_ico.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        initServices();
        launch(args);
    }

    public static void initServices() throws SQLException, ClassNotFoundException {
        JDBCConnector connector = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");

        //DAO repository
        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl(connector);
        UserDAO userDao = new UserDaoImpl(connector, userRoleDao);
        VarietyDaoImpl varietyDao = new VarietyDaoImpl(connector);
        GrapeDAO grapeDao = new GrapeDaoImpl(connector, varietyDao, userDao);
        WineTypeDaoImpl wineTypeDao = new WineTypeDaoImpl(connector);
        WineDAO wineDao = new WineDaoImpl(connector, wineTypeDao, grapeDao);
        BottleDAO bottleDao = new BottleDaoImpl(connector);
        BottledWineDAO bottledWineDAO = new BottledWineDaoImpl(connector, userDao, wineDao, wineTypeDao);

        //Service Layer
        UserService userService = new UserServiceImpl(userDao, userRoleDao);
        GrapeService grapeService = new GrapeServiceImpl(grapeDao,varietyDao,userDao);
        WineService wineService = new WineServiceImpl(wineDao, bottleDao, wineTypeDao, grapeDao);
        BottleService bottleService = new BottleServiceImpl(bottleDao, bottledWineDAO, grapeDao);

        serviceWrapper = new ServiceWrapper();
        serviceWrapper.setServices(userService, grapeService,
                bottleService, wineService);

    }
}
