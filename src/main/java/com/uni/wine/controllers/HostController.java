package com.uni.wine.controllers;

import com.uni.wine.businesslayer.BottleService;
import com.uni.wine.businesslayer.GrapeService;
import com.uni.wine.businesslayer.ServiceWrapper;
import com.uni.wine.notifications.NotificationManager;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HostController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane initialView;

    //Curently stored bottled wines
    @FXML
    private ListView curentStoredBottledWines;

    //Curently stored grapes
    @FXML
    private ListView curentStoredGrapes;

    //Right pane
    @FXML
    private ListView freeBottles;
    @FXML
    private TextField firstQuantity; // for 750ml volume
    @FXML
    private TextField secondQuantity; // for 350ml volume
    @FXML
    private TextField thirdQuantity; // for 200ml volume
    @FXML
    private TextField fourthQuantity; // for 187ml volume

    private int firstVolume = 750; // ml
    private int secondVolume = 375; // ml
    private int thirdVolume = 200; // ml
    private int fourthVolume = 187; // ml

    private ServiceWrapper serviceWrapper;
    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    public void setServiceWrapper(ServiceWrapper serviceWrapper) { this.serviceWrapper = serviceWrapper; }

    public void alertBottleQuantity750(int bottleQuantity750, int BOTTLES_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of 750ml bottles: " + bottleQuantity750 + "   MINIMUM " + BOTTLES_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    public void alertBottleQuantity375(int bottleQuantity375, int BOTTLES_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of 375ml bottles: " + bottleQuantity375 + "   MINIMUM " + BOTTLES_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    public void alertBottleQuantity200(int bottleQuantity200, int BOTTLES_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of 200ml bottles: " + bottleQuantity200 + "   MINIMUM " + BOTTLES_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    public void alertBottleQuantity187(int bottleQuantity187, int BOTTLES_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of 187ml bottles: " + bottleQuantity187 + "   MINIMUM " + BOTTLES_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    public void criticalMinimumOfBlackGrapes(double blackGrapes, int VARIETY_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of black grapes: " + blackGrapes + "   MINIMUM " + VARIETY_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    public void criticalMinimumOfWhiteGrapes(double whiteGrapes, int VARIETY_MINIMUM_QUANTITY) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Critical minimum of white grapes: " + whiteGrapes + "   MINIMUM " + VARIETY_MINIMUM_QUANTITY);
                alert.show();
            }
        });
    }

    @FXML
    void onAddBottle(ActionEvent e) throws  IOException {
        int firstQuantity = 0;
        int secondQuantity = 0;
        int thirdQuantity = 0;
        int fourthQuantity = 0;
        if(!this.firstQuantity.getText().isEmpty()) {
            firstQuantity = Integer.parseInt(this.firstQuantity.getText());

            boolean isAddedBottle = this.serviceWrapper
                                                .getBottleService()
                                                .addBottle(this.firstVolume, firstQuantity);

            if(isAddedBottle) {
                this.serviceWrapper
                        .getBottleService()
                        .updateBottle(this.firstVolume, firstQuantity);
            }
            this.firstQuantity.setText("");
        }
        if(!this.secondQuantity.getText().isEmpty()) {
            secondQuantity = Integer.parseInt(this.secondQuantity.getText());

            boolean isAddedBottle = this.serviceWrapper
                                            .getBottleService()
                                            .addBottle(this.secondVolume, secondQuantity);

            if(isAddedBottle) {
                this.serviceWrapper
                        .getBottleService()
                        .updateBottle(this.secondVolume, secondQuantity);
            }
            this.secondQuantity.setText("");
        }
        if(!this.thirdQuantity.getText().isEmpty()) {
            thirdQuantity = Integer.parseInt(this.thirdQuantity.getText());

            boolean isAddedBottle = this.serviceWrapper
                                                .getBottleService()
                                                .addBottle(this.thirdVolume, thirdQuantity);

            if(isAddedBottle) {
                this.serviceWrapper
                        .getBottleService()
                        .updateBottle(this.thirdVolume, thirdQuantity);
            }
            this.thirdQuantity.setText("");
        }
        if(!this.fourthQuantity.getText().isEmpty()) {
            fourthQuantity = Integer.parseInt(this.fourthQuantity.getText());

            boolean isAddedBottle = this.serviceWrapper
                                                .getBottleService()
                                                .addBottle(this.fourthVolume, fourthQuantity);

            if(isAddedBottle) {
                this.serviceWrapper
                        .getBottleService()
                        .updateBottle(this.fourthVolume, fourthQuantity);
            }
            this.fourthQuantity.setText("");
        }

        listAllBottledWines(); // List all types wines
        listAllGrapes(); // List all black and white grapes
        listAvailableBottles(); // List available bottles
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bottle/s are added to the system!");
        alert.show();
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

    void listAllGrapes() {
        curentStoredGrapes.getItems().clear();
        double listAllBlackGrapes = this.serviceWrapper
                                                    .getGrapeService()
                                                    .getAllGrapes("Black");
        curentStoredGrapes.getItems().add("Black grapes:" + listAllBlackGrapes);

        double listAllWhiteGrapes = this.serviceWrapper
                                                    .getGrapeService()
                                                    .getAllGrapes("White");
        curentStoredGrapes.getItems().add("White grapes:" + listAllWhiteGrapes);

    }

    void listAvailableBottles() {
        List<Map<String,Object>> availableBottles = this.serviceWrapper
                .getBottleService()
                .getAvailableBottles(); // List with all available bottles

        freeBottles.getItems().clear();
        for (Map<String, Object> map : availableBottles) { // Loop All bottles
            int bottleVolume = 0;
            int bottleQuantity = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) { // Loop every object on current bottle
                String key = entry.getKey();
                if(key.equals("bottle_volume")) {
                    bottleVolume = (Integer) entry.getValue();
                }else if(key.equals("bottle_quantity")){
                    bottleQuantity = (Integer) entry.getValue();
                }
            }
            freeBottles.getItems().add(bottleVolume + " ml :   " + bottleQuantity);

        }
    }

    void listAllBottledWines() {
        int listAllWhiteWines = this.serviceWrapper
                                        .getBottleService()
                                        .getBottledWineByWineType("white");

        curentStoredBottledWines.getItems().clear();
        if(listAllWhiteWines > 0)
        curentStoredBottledWines.getItems().add("White bottled wines:" + listAllWhiteWines);

        int listAllRedWines = this.serviceWrapper
                                            .getBottleService()
                                            .getBottledWineByWineType("red");
        if(listAllRedWines > 0)
            curentStoredBottledWines.getItems().add("Red bottled wines:" + listAllRedWines);

        int listAllRoseWines = this.serviceWrapper
                                            .getBottleService()
                                            .getBottledWineByWineType("rose");
        if(listAllRoseWines > 0)
        curentStoredBottledWines.getItems().add("Rose bottled wines:" + listAllRoseWines);
    }

    void InitUI() {
        listAllBottledWines(); // List all types wines

        listAllGrapes(); // List all black and white grapes

        listAvailableBottles(); // List available bottles

        BottleService bottleService = this.serviceWrapper.getBottleService();
        GrapeService grapeService = this.serviceWrapper.getGrapeService();

        NotificationManager notificationManager = new NotificationManager(bottleService, grapeService);

    }
}
