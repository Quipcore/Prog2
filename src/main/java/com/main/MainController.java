package com.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

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
}
