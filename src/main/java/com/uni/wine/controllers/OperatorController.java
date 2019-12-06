package com.uni.wine.controllers;

import com.uni.wine.services.UserService;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class OperatorController {

    @FXML
    private TextField attitudeGrapeWine;
    @FXML
    private TextField grapeInput;
    @FXML
    private ComboBox varietyCombobox;
    @FXML
    private Button convertGrape;
    @FXML
    private TextField wineInput;
    @FXML
    private Button convertWine;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane initialView;

    @FXML
    private HBox warehouse;
    @FXML
    private HBox bottleWine;
    @FXML
    private HBox addGrapeToWh;


    @FXML
    void Logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/MainScene.fxml"));
        Parent newParent;

        boolean isTrue = false;
        try {
            //TODO when the main controller is called in second time(error)
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

            MainController nextController = loader.getController();
            isTrue = true;

        }catch (IOException e) {
            System.out.println("Error");

        }
        if(isTrue) {
            System.out.println("Success");
        }
    }

    @FXML
    void ShowAG(ActionEvent event) {
        //Add Grape to the warehouse scene
        warehouse.setVisible(false);
        bottleWine.setVisible(false);
        addGrapeToWh.setVisible(true);
    }

    @FXML
    void ShowBW(ActionEvent event) {
        //Bottle Wine Scene
        warehouse.setVisible(false);
        bottleWine.setVisible(true);
        addGrapeToWh.setVisible(false);

        attitudeGrapeWine.setOnKeyPressed((KeyEvent e) -> {
            if((e.getCode().isDigitKey() && attitudeGrapeWine.getText().length() < 1) ||
                    e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.TAB) { }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Only digits");
                alert.show();
            }
        });


        grapeInput.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE ||
                    e.getCode() == KeyCode.PERIOD || e.getCode() == KeyCode.TAB) { }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "The quantity of the grapes can be only from digits");
                alert.show();
            }
        });

        wineInput.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE ||
                    e.getCode() == KeyCode.PERIOD || e.getCode() == KeyCode.TAB) {

            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "The quantity of the wine can be only from digits");
                alert.show();
            }
        });

        convertGrape.disableProperty().bind(grapeInput.textProperty().isEmpty()
                .or(varietyCombobox.valueProperty().isNull())
                .or(attitudeGrapeWine.textProperty().isEmpty()));
        convertWine.disableProperty().bind(wineInput.textProperty().isEmpty());
    }

    @FXML
    void ShowWH(ActionEvent event) {
        warehouse.setVisible(true);
        bottleWine.setVisible(false);
        addGrapeToWh.setVisible(false);
    }

    @FXML
    void initialize() {


    }
}
