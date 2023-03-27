package com.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button exitButton;
    boolean clicked = false;

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
        String text = !clicked ? "Yay" : "Noooo";
        textBox.setText(text);
        clicked = !clicked;
        //textBox.setTranslateX(100); //<-- setTranslate to change pos
    }

    @FXML
    protected void onExitButtonClick(){
        exitButton.applyCss();
        //System.exit(0);
    }

    @FXML
    protected void onSaveButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void OnLoadButtonClick(ActionEvent actionEvent) {
    }
}
