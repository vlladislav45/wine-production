package com.uni.wine.controllers;

import com.uni.wine.businesslayer.ServiceWrapper;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdminController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    AnchorPane initialView;
    @FXML
    ComboBox users; // Combobox for all users
    @FXML
    private TextField usernameOperator;
    @FXML
    private PasswordField passwordOperator;
    @FXML
    private TextField usernameHost;
    @FXML
    private PasswordField passwordHost;

    private ObservableList<String> names = FXCollections.observableArrayList(); // Observable list with all usernames
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private ServiceWrapper serviceWrapper;

    public void setServiceWrapper(ServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

    @FXML
    void deleteUser() {
        String selectedUsername = users.getSelectionModel().getSelectedItem().toString(); // get selected name from combo box

        boolean isSuccess = this.serviceWrapper
                .getUserService()
                .removeUserByName(selectedUsername);
        if(isSuccess) {
            listAllUsers();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The user was successfully deleted from the system");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Something is wrong, try again..");
            alert.show();
        }

    }

    @FXML
    void onCreateOperator() {
        String username = usernameOperator.getText();
        String password = passwordOperator.getText();
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "The username or password was incorrect. Please try again");
            alert.show();
        } else {
            boolean isSuccessful =
                    this.serviceWrapper
                            .getUserService()
                            .addOperator(username, password);

            if (isSuccessful) {
                this.serviceWrapper
                        .getGrapeService()
                        .addGrape(0, "black", username);

                this.serviceWrapper
                        .getGrapeService()
                        .addGrape(0, "white", username);

                listAllUsers();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Operator is added successfuly to the system!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "user with that name already exists!");
                alert.show();
            }
        }
    }

    @FXML
    void onCreateHost() {
        String username = usernameHost.getText();
        String password = passwordHost.getText();
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "The username or password was incorrect. Please try again");
            alert.show();
        } else {
            boolean isSuccessful =
                    this.serviceWrapper
                            .getUserService()
                            .addHost(username, password);

            if (isSuccessful) {
                listAllUsers();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Host is added to the system!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot be added host more than once in the system!");
                alert.show();
            }
        }
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/MainScene.fxml"));
        Parent newParent;

        boolean isTrue = false;
        try {
            newParent = loader.load();
            anchorPane.getChildren().add(newParent);

            newParent.setScaleX(0);
            newParent.setScaleY(0);
            anchorPane.getChildren().remove(initialView);
            final Timeline timeline = new Timeline();
            final KeyValue kv3 = new KeyValue(newParent.scaleXProperty(), 1, Interpolator.LINEAR);
            final KeyValue kv4 = new KeyValue(newParent.scaleYProperty(), 1, Interpolator.LINEAR);
            final KeyFrame kf = new KeyFrame(Duration.millis(1500), kv3, kv4);
            timeline.getKeyFrames().add(kf);
            timeline.play();

            MainController mainController = loader.getController(); // When logout get controller
            mainController.setServiceWrapper(this.serviceWrapper);  // and set again service wrapper to the main controller
            isTrue = true;

        }catch (IOException e) {
            LOGGER.error("System problem, try again.. \n");
        }
        if(isTrue) {
            LOGGER.info("Successfully logout \n");
        }
    }

    void listAllUsers() {
        List<Map<String,Object>> allUsers = this.serviceWrapper.getUserService().getUsers(); // List with our users

        names.clear();
        for (Map<String, Object> map : allUsers) { // Loop All users
            for (Map.Entry<String, Object> entry : map.entrySet()) { // Loop every object on current user
                String key = entry.getKey();
                if(key.equals("username")) {
                    //Object value = entry.getValue();
                    String username = (String) entry.getValue();
                    names.add(username);
                }
            }
        }

        users.setItems(names); // Add to our combo box
    }

    void initUI() {
        listAllUsers();
    }
}
