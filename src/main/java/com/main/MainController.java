package com.main;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private HBox hBox;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vBox;
    @FXML
    private Image mapImage;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button newConnectionButton;
    @FXML
    private Button findPathButton;
    @FXML
    private Button newPlaceButton;
    @FXML
    private Button showConnectionButton;

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

    public void resizeImage(ActionEvent actionEvent) {

        System.out.println(vBox.heightProperty());
        System.out.println(vBox.widthProperty());

        ReadOnlyDoubleProperty boundingImageHeight = vBox.heightProperty();
        boundingImageHeight.subtract(menuBar.heightProperty());
        boundingImageHeight.subtract(hBox.heightProperty());

        imageView.setPreserveRatio(false);
        imageView.fitHeightProperty().bind(vBox.heightProperty());
        imageView.fitWidthProperty().bind(vBox.widthProperty());
//
//        imageView.setFitHeight(vBox.getHeight());
//        imageView.setFitWidth(vBox.getWidth());
    }
}
