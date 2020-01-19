package com.uni.wine.controllers;

import com.uni.wine.businesslayer.ServiceWrapper;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainController {


    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane initialView;
    @FXML
    private Button loginBtn;
    private ServiceWrapper serviceWrapper;

    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    @FXML
    void onCloseAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onUsernameKeyPressed(KeyEvent e) {
        if (username.getLength() >= 15) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Max length for username is 15");
            alert.show();
        }

        if(e.getCode() == KeyCode.ENTER) {
            loginCheck();
        }
    }

    @FXML
    void onPasswordKeyPressed(KeyEvent e) {
        if (password.getLength() >= 15) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Max length for password is 15");
            alert.show();
        }

        if(e.getCode() == KeyCode.ENTER) {
            loginCheck();
        }
    }

    void loginCheck() {
        if (username.getText() == null || username.getText().trim().isEmpty()
                || password.getText() == null || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "The username or password was incorrect. Please try again");
            alert.show();
        } else {
            boolean isSuccessful =
                    this.serviceWrapper
                            .getUserService()
                            .checkLogin(username.getText(), password.getText());

            if (isSuccessful) {
                openNewScene();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "WRONG CREDENTIALS!");
                alert.show();
            }
        }
    }

    @FXML
    void onLoginClick(ActionEvent e) {
       loginCheck();
    }

    public void openNewScene() {
        boolean isOperator = serviceWrapper.getUserService().getLoggedUser().getAccessLevel().getRoleName().equals("OPERATOR");
        boolean isHost = serviceWrapper.getUserService().getLoggedUser().getAccessLevel().getRoleName().equals("HOST");

        String operatorScene = "scenes/OperatorScene.fxml";
        String adminScene = "scenes/AdminScene.fxml";
        String hostScene = "scenes/HostScene.fxml";

        String scene;
        if(isOperator) {
            scene = operatorScene;
        }else if(isHost) {
            scene = hostScene;
        }else {
            scene = adminScene;

        }

        //String scene = isOperator ? operatorScene : adminScene : hostScene;

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(scene));
        Parent newParent;
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

            if(scene.equals(adminScene)) {
                AdminController adminController = loader.getController();
                adminController.setServiceWrapper(this.serviceWrapper);
                adminController.initUI();
            }else if(scene.equals(operatorScene)) {
                OperatorController operatorController = loader.getController();
                operatorController.setServiceWrapper(serviceWrapper); // Add service wrapper init to the operator controller
                operatorController.setUsername(this.username.getText()); // Add username to the next scene
                operatorController.InitUI();
            }else {
                HostController hostController = loader.getController();
                hostController.setServiceWrapper(serviceWrapper);
                hostController.InitUI();
            }

//            nextController.setMainController(this);

        } catch (IOException ex) {
            LOGGER.error("Could not load next scene " + ex);

        }
    }

    @FXML
    void initialize() {
    }

    public void setServiceWrapper(ServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

}
