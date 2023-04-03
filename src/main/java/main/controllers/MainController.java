package main.controllers;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import main.Message;
import main.Popup;
import main.StageManager;
import main.graph.Edge;
import main.graph.ListGraph;

import java.io.IOException;
import java.io.InputStream;
import java.security.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Controller {

    private static class Pin {

        private static int currentClicked = 0;

        private boolean isClicked;

        private long timestamp;
        private final Circle circle;

        private final Color offColor = Color.BLUE;
        private final Color onColor = Color.RED;

        private String name;

        public Pin(double radius, double xPos, double yPos) {
            circle = new Circle();
            circle.setRadius(radius);
            circle.setCenterX(xPos);
            circle.setCenterY(yPos);
            circle.setFill(offColor);
            this.name = "";
            this.timestamp = -1;
        }

        public Pin(double xPos, double yPos) {
            this(10, xPos, yPos);
        }

        public Pin(double xPos, double yPos, String name) {
            this(xPos, yPos);
            this.name = name;
        }

        public Circle getCircle() {
            return circle;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void click() {
            if (isClicked) {
                if (currentClicked > 0) {
                    currentClicked--;
                }
                isClicked = false;
                circle.setFill(offColor);
                timestamp = -1;
            } else if (!isClicked && currentClicked < 2) {
                currentClicked++;
                isClicked = true;
                circle.setFill(onColor);
                timestamp = System.nanoTime();
            }
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    //-------------------- Application fields ------------------
    private ListGraph<Pin> graph = new ListGraph<>();
    private StageManager stageManager;

    private Message message = new Message(Message.Status.EMPTY);

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
        if (stageManager != null) {
            stageManager.resizeStage();
        }
    }

    @FXML
    protected void onMenuOpenFileClick(ActionEvent actionEvent) {
        //Open and dislay data in selected .graph file
        // 1. Popup window to ask to continue and override unsaved changes
        // 2. Remove current data
        // 3. Display .graph data
    }

    @FXML
    protected void onMenuSaveFileClick(ActionEvent actionEvent) {
        /*  Save current display data to .graph file
            1. Create new .graph file or open old file
            2. Write one row with URL to output file [file:URL]
            3. Write one row with information about the nodes [name1;x1;y1;name2;x2;y2;...;nameN;xN;yN]
            4. Create a new row for every connection [fromName;toName;connectionName;connectionWeight]
            5. Close file
         */
    }

    @FXML
    protected void onMenuSaveImageClick(ActionEvent actionEvent) {
        /*
        Save screenshot of current windowPane to resource folder with name "capture.PNG"
         */
    }

    @FXML
    protected void onExitButtonClick(ActionEvent actionEvent) {
        /*
        Create popup to ask about unsaved changes
         */
        System.exit(0);
    }

    //-------------------- Buttons functions ---------------

    @FXML
    protected void onFindPathButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    protected void onShowConnectionButtonClick(ActionEvent actionEvent) {
        List<Pin> pinList = new ArrayList<>(graph.getNodes().parallelStream().filter(pin -> pin.isClicked).toList());

        //If p0 is newer than p1 then swap
        if (pinList.get(0).getTimestamp() > pinList.get(1).getTimestamp()) {
            Collections.swap(pinList, 0, 1);
        }

        Pin p0 = pinList.get(0);
        Pin p1 = pinList.get(1);
        Edge<Pin> edge = graph.getEdgeBetween(p0,p1);

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText(String.format("Connection from %s to %s", p0, p1));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setText(edge.getName());
        nameField.setEditable(false);

        TextField timeField = new TextField();
        timeField.setText(String.valueOf(edge.getWeight()));
        timeField.setEditable(false);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(timeField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> new Pair<>(nameField.getText(), timeField.getText()));
        dialog.showAndWait();
    }

    @FXML
    protected void onNewPlaceButtonClick(ActionEvent actionEvent) throws IOException {
        stageManager.setCursor(Cursor.CROSSHAIR);
        btnNewPlace.setDisable(true);
    }

    @FXML
    protected void onNewConnectionButtonClick(ActionEvent actionEvent) throws IOException {
        List<Pin> pinList = new ArrayList<>(graph.getNodes().parallelStream().filter(pin -> pin.isClicked).toList());

        //If p0 is newer than p1 then swap
        if (pinList.get(0).getTimestamp() > pinList.get(1).getTimestamp()) {
            Collections.swap(pinList, 0, 1);
        }

        Pin p0 = pinList.get(0);
        Pin p1 = pinList.get(1);

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText(String.format("Connection from %s to %s", p0, p1));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField timeField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(timeField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> new Pair<>(nameField.getText(), timeField.getText()));
        dialog.showAndWait().ifPresent(result -> graph.connect(p0, p1, result.getKey(), Integer.parseInt(result.getValue())));
    }

    @FXML
    protected void onChangeConnectionButtonClick(ActionEvent actionEvent) {
    }

    //-------------------- OutputArea Functions -------------

    @FXML
    protected void onOutPutAreaMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getX() > mapImage.getWidth() || mouseEvent.getY() > mapImage.getHeight()) {
            return;
        }

        Pin pin = getPinOnMousePos(mouseEvent);
        if (pin != null) {
            pin.click();
            return;
        }

        if (btnNewPlace.isDisable()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Name");
            dialog.setHeaderText("");
            dialog.setContentText("Name of place:");
            dialog.showAndWait().ifPresent(name -> addPinToMap(new Pin(mouseEvent.getX(), mouseEvent.getY(), name)));

            stageManager.setCursor(Cursor.DEFAULT);
            btnNewPlace.setDisable(false);
            return;
        }


    }

    private Pin getPinOnMousePos(MouseEvent mouseEvent) {
//        for (Pin pin : pinList) {
//            double x1 = pin.getCircle().getCenterX();
//            double y1 = pin.getCircle().getCenterY();
//
//            double x2 = mouseEvent.getX();
//            double y2 = mouseEvent.getY();
//
//            double distanceBetweenPoints = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
//            double pinRadius = pin.getCircle().getRadius();
//
//            if (pinRadius >= distanceBetweenPoints) {
//                return pin;
//            }
//        }
        return graph.getNodes().parallelStream().filter(pin -> {
            double x1 = pin.getCircle().getCenterX();
            double y1 = pin.getCircle().getCenterY();

            double x2 = mouseEvent.getX();
            double y2 = mouseEvent.getY();

            double distanceBetweenPoints = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
            double pinRadius = pin.getCircle().getRadius();

            return pinRadius >= distanceBetweenPoints;
        }).findAny().orElse(null);
    }

    private void addPinToMap(Pin pin) {
        outputArea.getChildren().add(pin.getCircle());
        graph.add(pin);
    }

    //-------------------- Utility Functions -------------

    @FXML
    protected void bindImage(ActionEvent actionEvent) {
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

    public void initialize(StageManager manager, Controller parentController) {
        setStageManager(manager);
        setParentController(parentController);
    }

    public void setStageManager(StageManager manager) {
        this.stageManager = manager;
    }

    @Override
    public void setParentController(Controller parentController) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(Message message) {
        this.message = message;
        message.status = Message.Status.RECIVED;

        String messageText = message.get();
        System.out.println(messageText);
    }
}
