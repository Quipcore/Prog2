package main;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        //Resize image to fit the window
        DoubleBinding newWidth = hBox.widthProperty().subtract(0);
        DoubleBinding newHeight = vBox.heightProperty().subtract(hBox.getHeight() + menuBar.getHeight());
        imageView.fitWidthProperty().bind(newWidth);
        imageView.fitHeightProperty().bind(newHeight);
    }

    public void initialize(){

    }

    public void printImageSize(ActionEvent actionEvent) {
        System.out.println("Image size: " + imageView.getFitWidth() + ", " + imageView.getFitHeight());
    }

    public void printWindowSize(ActionEvent actionEvent) {
        System.out.println("Window size: " + vBox.getWidth() + ", " + vBox.getHeight());
    }
}
