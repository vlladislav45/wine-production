package com.uni.wine.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class OperatorController {
    @FXML
    public StackPane container;

    private HBox productsWrapper;
    private VBox productsInnerLayout;
    private VBox verticalLayout;
    private ListView listView;

    private Text attitudeGrapeWineText;
    private TextField attitudeGrapeWine;
    private Text varietyText;
    private ComboBox varietyCombobox;
    private Text grapeText;
    private TextField grapeInput;
    private Text wineText;
    private TextField wineInput;
    private Button convertGrape;
    private Button convertWine;

    private Button menuToggle;
    private VBox menu;



    public void initUI() {
        initLayout();

        initTopUI();

        initMenu();
    }

    private void initMenu() {
        menu = new VBox(10);

        menu.minHeightProperty().bind(container.heightProperty());
        menu.setPrefWidth(200);
        menu.getChildren().addAll(new TextField("Veto"));
        menuToggle = new Button("<");
        menuToggle.setPrefWidth(50);

            TranslateTransition openNav=new TranslateTransition(new Duration(350), menu);
            openNav.setToX(0);
            TranslateTransition closeNav=new TranslateTransition(new Duration(350), menu);
            menuToggle.setOnMouseClicked((MouseEvent evt)->{
                  if(container.getTranslateX() != 0){
                    openNav.setToX(+(container.getWidth()));
                    openNav.play();
                }else{
                    //closeNav.setToX(-(container.getWidth()));
                      closeNav.setToX(-250);
                    closeNav.play();
                }
            });



        productsWrapper.getChildren().addAll(menuToggle, menu);
    }

    private void initTopUI() {
        attitudeGrapeWineText = new Text("1:n");
        attitudeGrapeWine = new TextField();
        varietyText = new Text("Variety:");
        varietyCombobox = new ComboBox();
        grapeText = new Text("Quantity:");
        grapeInput = new TextField();
        wineText = new Text("Quantity:");
        wineInput = new TextField();
        convertGrape = new Button("Convert Grape");
        convertWine = new Button("Bottle Up");

        attitudeGrapeWine.setPromptText("1:?");
        attitudeGrapeWine.setOnKeyPressed((KeyEvent e) -> {
            if((e.getCode().isDigitKey() && attitudeGrapeWine.getText().length() < 1) ||
               e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.TAB) {

            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Only digits");
                alert.show();
            }
        });

        varietyCombobox.setPrefWidth(130);
        varietyCombobox.getItems().addAll(
                "black",
                "white"
        );

        grapeInput.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE ||
               e.getCode() == KeyCode.PERIOD || e.getCode() == KeyCode.TAB) {

            }else {
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

        HBox grapeTextRow = new HBox(125);
        grapeTextRow.getChildren().addAll(attitudeGrapeWineText, varietyText, grapeText);
        HBox grapeRow = new HBox(5);
        grapeRow.getChildren().addAll(attitudeGrapeWine, varietyCombobox, grapeInput, convertGrape);
        HBox wineRow = new HBox(5);
        wineRow.getChildren().addAll(wineText, wineInput, convertWine);

        productsInnerLayout.getChildren().addAll(grapeTextRow, grapeRow, wineRow);

        container.getChildren().add(verticalLayout);
    }

    private void initLayout() {
        verticalLayout = new VBox(25);
        productsInnerLayout = new VBox(15);
        productsWrapper = new HBox(10);
 //       listView = new ListView();
        productsWrapper.getChildren().add(productsInnerLayout);
        verticalLayout.getChildren().add(productsWrapper);
   //     verticalLayout.getChildren().add(listView);
    }
}
