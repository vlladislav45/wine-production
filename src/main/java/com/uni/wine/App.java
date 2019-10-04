package com.uni.wine;


import com.uni.wine.dao.impl.UserDaoImpl;
import com.uni.wine.db.JDBCConnector;
import com.uni.wine.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/MainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        JDBCConnector connector = new JDBCConnector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/wines_db?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&useLegacyDatetimeCode=false",
                "root",
                "1234");

        UserDaoImpl userDao = new UserDaoImpl(connector);

        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("pwd");
        userDao.add(user1);
        //connector.initTables();
        //launch(args);
    }
}
