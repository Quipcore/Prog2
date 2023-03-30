package main;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainController {
    private class Pin /*Might want to extend circle*/ {
        private static boolean isClicked;
        private static Circle circle;

        public Pin(double radius, double xPos, double yPos, boolean b) {
            circle = new Circle();
            circle.setRadius(radius);
            circle.setCenterX(xPos);
            circle.setCenterY(yPos);
            setClicked(b);
        }

        private Pin(double xPos, double yPos, boolean b) {
            this(10, xPos, yPos, b);

        }

        public Pin(double xPos, double yPos) {
            this(xPos, yPos, false);
        }


        public Circle getCircle() {
            return circle;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean b) {
            isClicked = b;
            circle.setFill(b ? Color.RED : Color.BLUE);
        }
    }

    //-------------------- Application fields ------------------

    private List<Pin> pinList = new LinkedList<>();
    private StageManager stageManager;

    //-------------------- FXML components ------------------

    @FXML
    private Pane windowPane;

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menu;
    @FXML
    private Menu menuFile;
    @FXML
    private MenuItem menuNewMap;
    @FXML
    private MenuItem menuOpenFile;
    @FXML
    private MenuItem menuSaveFile;
    @FXML
    private MenuItem menuSaveImage;
    @FXML
    private MenuItem menuExit;

    @FXML
    private Button btnChangeConnection;
    @FXML
    private Button btnNewConnection;
    @FXML
    private Button btnShowConnection;
    @FXML
    private Button btnFindPath;

    @FXML
    private Button btnNewPlace;
    @FXML
    private Pane outputArea;
    @FXML
    private HBox hBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Image mapImage;

    //-------------------- Menu functions ------------------

    @FXML
    protected void onMenuNewMapClick(ActionEvent actionEvent) {
        InputStream mapPath = Objects.requireNonNull(getClass().getResourceAsStream("images/map.PNG"));
        mapImage = new Image(mapPath);
        imageView.setImage(mapImage);
        if(stageManager != null){
            stageManager.resizeScene();
        }
    }

    @FXML
    protected void onMenuOpenFileClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onMenuSaveFileClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onMenuSaveImageClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onExitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    //-------------------- Buttons functions ---------------

    @FXML
    protected void onFindPathButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onShowConnectionButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onNewPlaceButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onNewConnectionButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    protected void onChangeConnectionButtonClick(ActionEvent actionEvent) {
    }

    //-------------------- OutputArea Functions -------------

    @FXML
    protected void onOutPutAreaMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getX() > mapImage.getWidth() || mouseEvent.getY() > mapImage.getHeight()){
            return;
        }

        Pin pin = getPinOnMousePos(mouseEvent);
        if (pin != null) {
            pin.setClicked(true);
        } else {
            pin = new Pin(mouseEvent.getX(), mouseEvent.getY());
            addPinToMap(pin);
        }
    }

    private Pin getPinOnMousePos(MouseEvent mouseEvent) {
        for (Pin pin : pinList) {
            double x1 = pin.getCircle().getCenterX();
            double y1 = pin.getCircle().getCenterY();

            double x2 = mouseEvent.getX();
            double y2 = mouseEvent.getY();

            double distanceBetweenPoints = (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1);
            double sq_pinRadius = pin.getCircle().getRadius() * pin.getCircle().getRadius();

            if (sq_pinRadius >= distanceBetweenPoints) {
                return pin;
            }
        }
        return null;
    }

    private void addPinToMap(Pin pin) {
        outputArea.getChildren().add(pin.getCircle());
        pinList.add(pin);
    }

    //-------------------- Utility Functions -------------

    @FXML
    protected void resizeImage(ActionEvent actionEvent) {
        //Resize image to fit the window
        DoubleBinding newWidth = outputArea.widthProperty().subtract(0);
        DoubleBinding newHeight = vBox.heightProperty().subtract(hBox.getHeight() + menu.getHeight());
        //newHeight = pane.heightProperty().subtract(0);
        imageView.fitWidthProperty().bind(newWidth);
        imageView.fitHeightProperty().bind(newHeight);
    }

    @FXML
    protected void printImageSize(ActionEvent actionEvent) {
        System.out.println("Image size: " + imageView.getFitWidth() + ", " + imageView.getFitHeight());
    }

    @FXML
    protected void printWindowSize(ActionEvent actionEvent) {
        System.out.println("Window size: " + vBox.getWidth() + ", " + vBox.getHeight());
    }


    //-------------------- Init Functions ---------------------

    public void initialize() {
    }

    public void setStageManager(StageManager manager) {
        this.stageManager = manager;
    }
}
