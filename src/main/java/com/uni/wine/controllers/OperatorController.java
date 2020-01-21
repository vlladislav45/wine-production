package com.uni.wine.controllers;

import com.uni.wine.businesslayer.BottleService;
import com.uni.wine.businesslayer.GrapeService;
import com.uni.wine.businesslayer.ServiceWrapper;
import com.uni.wine.businesslayer.entities.Wine;
import com.uni.wine.businesslayer.entities.WineType;
import com.uni.wine.notifications.NotificationManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OperatorController<ListArray> {

    //User
    @FXML
    private Label currentUser;

    //WareHouse
    @FXML
    private ListView curentlyStoredWine;
    @FXML
    private ListView curentlyStoredGrape;
    @FXML
    private ListView freeBottles;

    //Bottle wine
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
    private TextField wineName;
    @FXML
    private Button convertWine;
    @FXML
    private ListView fillBottles;

    //Add grape to the warehouse
    @FXML
    private TextField addGrapeInput;
    @FXML
    private ComboBox addVarietyCombobox;
    @FXML
    private Button addGrape;

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

    private List<Integer> quantities; // List of bottled wine quantities
    private String typeWine; // Wine type which is done by grape quantity and grape variety
    private String grapeVariety; // Selected grape variety by the user
    private String username;
    private ServiceWrapper serviceWrapper;
    private boolean checkDone=false;
    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    public void setServiceWrapper(ServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

     void getUserWine(Wine wine, int idWine) {

         List<Map<String,Object>> wines = this.serviceWrapper
                                                .getWineService()
                                                .getUserWine(idWine);

        for (Map<String, Object> w : wines) {
            for (Map.Entry<String, Object> secondEntry : w.entrySet()) {
                String secondKey = secondEntry.getKey();
                Object secondValue = secondEntry.getValue();
                if(secondKey.equals("wine_name")) {
                    wine.setWineName((String) secondValue);
                }else if(secondKey.equals("id_type")) {
                    if((int)secondValue == 0) {
                        wine.setWineType(new WineType("white"));
                    }else if((int)secondValue == 1) {
                        wine.setWineType(new WineType("red"));
                    }else if((int)secondValue == 2) {
                        wine.setWineType(new WineType("rose"));
                    }
                }

            }
        }
    }

    public void listBottledWineOnCurrentUser() {
        //curentlyStoredWine
        List<Map<String,Object>> currentUserBottledWine = this.serviceWrapper
                                                                    .getBottleService()
                                                                    .getBottledWineOnUser(username); // List with all bottled wine at the user

        //750 ml 10 variety: rose name: Muskat
        for (Map<String, Object> map : currentUserBottledWine) {
            Wine wine = new Wine();
            int idWine = 0;
            int bottleVolume = 0;
            int bottleQuantity = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if(key.equals("id_wine")) {
                   idWine = (int) entry.getValue();

                   getUserWine(wine, idWine); // Get wine information about the current user
                }else if(key.equals("bottle_volume")) {
                    bottleVolume = (int) entry.getValue();
                }else if(key.equals("quantity_bottled")) {
                    bottleQuantity = (int) entry.getValue();
                }
            }
            curentlyStoredWine.getItems().add(bottleVolume + " ml " + bottleQuantity + " " + wine.getWineType().getTypeName() + " " + wine.getWineName()); // Add to the list view
        }
    }

    public void listGrapesOnCurrentUser() {
        List<Map<String,Object>> currentUserGrapes = this.serviceWrapper
                                                        .getGrapeService()
                                                        .getGrapesOnUser(username); // List with all grapes to the user

        for (Map<String, Object> map : currentUserGrapes) {
            float grapeQuantity = 0;
            String variety = "";
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if(key.equals("grape_quantity")) {
                    grapeQuantity = (Float) entry.getValue();
                }else if(key.equals("id_variety")) {
                    int idVariety = (int) entry.getValue();
                    if(idVariety == 1)
                        variety = "black";
                    else
                        variety = "white";
                    //curentlyStoredGrape.setItems(variety); // Add to our combo box
                }
            }
            curentlyStoredGrape.getItems().add("Quantity: " + grapeQuantity + " variety: " + variety);
        }


    }

    public void listAvailableBottles() {
        List<Map<String,Object>> availableBottles = this.serviceWrapper
                .getBottleService()
                .getAvailableBottles(); // List with all available bottles

        for (Map<String, Object> map : availableBottles) { // Loop All bottles
            int bottleVolume = 0;
            int bottleQuantity = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) { // Loop every object on current bottle
                String key = entry.getKey();
                if(key.equals("bottle_volume")) {
                    bottleVolume = (int) entry.getValue();
                }else if(key.equals("bottle_quantity")){
                    bottleQuantity = (int) entry.getValue();
                }
            }
            freeBottles.getItems().add(bottleVolume + " ml :   " + bottleQuantity);

        }
    }

    public void InitUI() {
        currentUser.setText("hello " + username); // Show username at the interface
        currentUser.setTextFill(Color.web("#0076a3"));

        listBottledWineOnCurrentUser(); // List stored bottled wine for the current user
        listGrapesOnCurrentUser(); // List stored grape for current user
        listAvailableBottles(); // List available bottles in warehouse
    }

    @FXML
    void ShowWH(ActionEvent event) {
        warehouse.setVisible(true);
        bottleWine.setVisible(false);
        addGrapeToWh.setVisible(false);
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

        wineInput.setDisable(true); // Do not input

        wineName.setOnKeyPressed((KeyEvent e) -> {
            if(!e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.TAB) { }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "The quantity of the grapes can be only from digits");
                alert.show();
            }
        });

        convertGrape.disableProperty().bind(grapeInput.textProperty().isEmpty()
                .or(varietyCombobox.valueProperty().isNull())
                .or(attitudeGrapeWine.textProperty().isEmpty())
                .or(wineName.textProperty().isEmpty()));
        convertWine.disableProperty().bind(wineInput.textProperty().isEmpty());
    }

    void cleanConvertScreen() {
        attitudeGrapeWine.clear();
        grapeInput.clear();
        wineInput.clear();
        wineName.clear();
        fillBottles.getItems().clear();
    }

    @FXML
    void onBottleUp(ActionEvent event) {
        curentlyStoredWine.getItems().clear();
        freeBottles.getItems().clear();

        int bottleVolume750 = 750;
            int bottleQuantity750 = quantities.get(0);
            bottledWine(wineName.getText(), username, bottleVolume750, bottleQuantity750);
            deleteFilledBottles(bottleVolume750, bottleQuantity750);


            int bottleVolume375 = 375;
            int bottleQuantity375 = quantities.get(1);
            bottledWine(wineName.getText(), username, bottleVolume375, bottleQuantity375);
            deleteFilledBottles(bottleVolume375, bottleQuantity375);


            int bottleVolume200 = 200;
            int bottleQuantity200 = quantities.get(2);
            bottledWine(wineName.getText(), username, bottleVolume200, bottleQuantity200);
            deleteFilledBottles(bottleVolume200, bottleQuantity200);


            int bottleVolume187 = 187;
            int bottleQuantity187 = quantities.get(3); // 187ml
            bottledWine(wineName.getText(), username, bottleVolume187, bottleQuantity187);
            deleteFilledBottles(bottleVolume187, bottleQuantity187);

        listBottledWineOnCurrentUser();
        listAvailableBottles();

        //Clean the screen
        cleanConvertScreen();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bottled wines are added to the system!");
        alert.show();

        checkDone = false;
    }

    void deleteFilledBottles(int bottleVolume, int bottleQuantity) {
        this.serviceWrapper
                .getBottleService()
                .removeBottle(bottleVolume, bottleQuantity);
    }

    //Automatic system for bottled wine
    void bottledWine(String wineName, String username, int bottleVolume, int bottleQuantity) {
        int idWine = this.serviceWrapper
                                        .getWineService()
                                        .checkWineExist(wineName, username, grapeVariety);

        if(idWine > 0 && !checkDone) {
            this.serviceWrapper
                    .getBottleService()
                    .updateBottledWines(wineName, username,grapeVariety, bottleVolume, bottleQuantity);
        }else {
            if(!checkDone) {
                checkDone = this.serviceWrapper
                        .getWineService()
                        .addWine(wineName, typeWine, username, grapeVariety);
            }

            this.serviceWrapper
                    .getBottleService()
                    .addbottledWines(wineName, username,grapeVariety, bottleVolume, bottleQuantity);
        }
    }

    //Convert grape to wine
    @FXML
    void onConvertGrape(ActionEvent event) {
        int attitudeWine = Integer.parseInt(attitudeGrapeWine.getText()); // 1 : n
        grapeVariety = varietyCombobox.getSelectionModel().getSelectedItem().toString(); // get selected variety from combo box

        if(attitudeWine > 2 && grapeVariety.equals("Black")) {
            //If the attitude is more than 2 and the variety is black
            //then we make rose
            typeWine = "Rose";
            float currentQuantity = this.serviceWrapper
                                                .getGrapeService()
                                                .getSingleUserGrapes(username, "Black"); // Check user quantities

            float userGrapeInput = Float.parseFloat(grapeInput.getText());
            if(userGrapeInput > currentQuantity) { // If input grapes from the user is less than in warehouse
                Alert alert = new Alert(Alert.AlertType.WARNING, "You do not have that much stock in the warehouse, add more or reduce the quantity");
                alert.show();
                return; // stop
            }

            this.serviceWrapper
                    .getGrapeService()
                    .updateQuantity(-userGrapeInput, "black", username); // Delete the input quantity in database

            userGrapeInput /= attitudeWine; // We divide the input quantity on the basis
            String tempCurrentQuantity = String.valueOf(userGrapeInput); // Temp variable that takes the amount entered by the user
            wineInput.setText(tempCurrentQuantity); // Add the wine quantity to the textfield

            quantities = this.serviceWrapper
                    .getBottleService()
                    .fillTheBottles(userGrapeInput);

            if(quantities.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Not enough bottles ");
                alert.show();
            }else {
                fillBottles.getItems().clear();

                fillBottles.getItems().add(quantities.get(0) + " x  750ml");
                fillBottles.getItems().add(quantities.get(1) + " x  375ml");
                fillBottles.getItems().add(quantities.get(2) + " x  200ml");
                fillBottles.getItems().add(quantities.get(3) + " x  187ml");
            }

        }else if(attitudeWine <= 2 && grapeVariety.equals("Black")) {
            //Normal red wine
            typeWine = "Red";

            float currentQuantity = this.serviceWrapper
                    .getGrapeService()
                    .getSingleUserGrapes(username, "Black"); // Check user quantities

            float userGrapeInput = Float.parseFloat(grapeInput.getText());
            if(userGrapeInput > currentQuantity) { // If input grapes from the user is less than in warehouse
                Alert alert = new Alert(Alert.AlertType.WARNING, "You do not have that much stock in the warehouse, add more or reduce the quantity");
                alert.show();
                return; // stop
            }

            this.serviceWrapper
                    .getGrapeService()
                    .updateQuantity(-userGrapeInput, "black", username); // Delete the input quantity in database

            userGrapeInput /= attitudeWine; // We divide the input quantity on the basis
            String tempCurrentQuantity = String.valueOf(userGrapeInput); // Temp variable that takes the amount entered by the user
            wineInput.setText(tempCurrentQuantity); // Add the wine quantity to the textfield

            quantities = this.serviceWrapper
                    .getBottleService()
                    .fillTheBottles(userGrapeInput);

            if(quantities.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Not enough bottles ");
                alert.show();
            }else {
                fillBottles.getItems().clear();

                fillBottles.getItems().add(quantities.get(0) + " x  750ml");
                fillBottles.getItems().add(quantities.get(1) + " x  375ml");
                fillBottles.getItems().add(quantities.get(2) + " x  200ml");
                fillBottles.getItems().add(quantities.get(3) + " x  187ml");
            }

        }else if(grapeVariety.equals("White")) {
            //White wine
            typeWine = "White";

            float currentQuantity = this.serviceWrapper
                    .getGrapeService()
                    .getSingleUserGrapes(username, "White"); // Check user quantities

            float userGrapeInput = Float.parseFloat(grapeInput.getText());
            if(userGrapeInput > currentQuantity) { // If input grapes from the user is less than in warehouse
                Alert alert = new Alert(Alert.AlertType.WARNING, "You do not have that much stock in the warehouse, add more or reduce the quantity");
                alert.show();
                return; // stop
            }

            this.serviceWrapper
                    .getGrapeService()
                    .updateQuantity(-userGrapeInput, "white", username); // Delete the input quantity in database

            userGrapeInput /= attitudeWine; // We divide the input quantity on the basis
            String tempCurrentQuantity = String.valueOf(userGrapeInput); // Temp variable that takes the amount entered by the user
            wineInput.setText(tempCurrentQuantity); // Add the wine quantity to the textfield

            quantities = this.serviceWrapper
                                                    .getBottleService()
                                                    .fillTheBottles(userGrapeInput);

            if(quantities.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Not enough bottles ");
                alert.show();
            }else {
                fillBottles.getItems().clear();

                fillBottles.getItems().add(quantities.get(0) + " x  750ml");
                fillBottles.getItems().add(quantities.get(1) + " x  375ml");
                fillBottles.getItems().add(quantities.get(2) + " x  200ml");
                fillBottles.getItems().add(quantities.get(3) + " x  187ml");
            }

        }


    }

    @FXML
    void ShowAG(ActionEvent event) {
        //Add Grape to the warehouse scene
        warehouse.setVisible(false);
        bottleWine.setVisible(false);
        addGrapeToWh.setVisible(true);

        addGrapeInput.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE ||
               e.getCode() == KeyCode.PERIOD || e.getCode() == KeyCode.TAB) { }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "The quantity of the grapes can be only from digits");
                alert.show();
            }
        });

        addGrape.disableProperty().bind(addGrapeInput.textProperty().isEmpty()
                .or(addVarietyCombobox.valueProperty().isNull()));
    }

    @FXML
    void onAddGrapeToWH(ActionEvent event) {
        float quantity = Float.parseFloat(addGrapeInput.getText());
        String variety = addVarietyCombobox.getSelectionModel().getSelectedItem().toString();

        boolean isNotNull = this.serviceWrapper
                            .getGrapeService()
                            .updateQuantity(quantity, variety, username);
        if(isNotNull) {
            curentlyStoredGrape.getItems().clear();
            listGrapesOnCurrentUser(); // List stored grape for current user
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Grape is added to the system!");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something is wrong, try again..!");
            alert.show();
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


    void setUsername(String username) { this.username = username; }
}
