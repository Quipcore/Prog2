package com.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class MainController {

    @FXML
    private Button newConnectionButton;
    @FXML
    private Button findPathButton;
    @FXML
    private Button newPlaceButton;
    @FXML
    private Button showConnectionButton;
    @FXML
    private Label welcomeText;

    @FXML
    private Label textBox;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onClickMeButtonClick(){
        String text = "adbababng";
    }


    @FXML
    protected void onNewPlaceButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onShowConnectionButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onFindPathButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onNewConnectionButtonClick(ActionEvent actionEvent) {
    }

    public void handleKeyInput(KeyEvent keyEvent) {
    }

    public void handleAboutAction(ActionEvent actionEvent) {
    }
}
