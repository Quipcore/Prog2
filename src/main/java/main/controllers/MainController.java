package main.controllers;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import main.Pin;
import main.Popup;
import main.StageManager;
import main.graph.Edge;
import main.graph.ListGraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController implements Controller {

    //-------------------- Application fields -----------------------------------------------
    private ListGraph<Pin> graph = new ListGraph<>();
    private StageManager stageManager;

    private boolean isSaved;

    //-------------------- FXML components --------------------------------------------------

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

    //-------------------- Menu functions -----------------------------------------------

    @FXML
    protected void onMenuNewMapClick() {
        if(!isSaved){
            if(Popup.unsavedChanges()){
                return;
            }
        }
        InputStream mapStream = Objects.requireNonNull(getClass().getResourceAsStream("images/map.PNG"));
        mapImage = new Image(mapStream);
        createNewMap(mapImage);
        isSaved = false;
    }

    //----------------------------------------------------------------------------------------

    private void createNewMap(Image mapImage){
        outputArea.getChildren().clear();
        imageView.setImage(mapImage);
        outputArea.getChildren().add(imageView);
        stageManager.resizeStage();

        //Set amount of clicked pins to 0
        if(graph != null) {
            graph.getNodes().forEach(pin -> {
                if (pin.isClicked()) {
                    pin.click();
                }
            });
        }
        graph = new ListGraph<>();
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onMenuOpenFileClick() throws IOException {
        if(!isSaved){
            if(Popup.unsavedChanges()){
                return;
            }
        }
        //Get list of all line in file
        Path mapFilePath = Path.of("src/main/java/main/graph/saved/.graph");
        String[] lines = Files.readAllLines(mapFilePath).toArray(new String[0]);

        //Get image and set image
        String[] pathSplit = lines[0].split(":");
        mapImage = new Image(new FileInputStream(pathSplit[1].trim()));
        createNewMap(mapImage);

        //Get pins and add them to map
        List<Pin> pins = new ArrayList<>();
        String[] nodes = lines[1].split(";");
        for(int i = 0; i < nodes.length - 2; i += 3) {
            double xPos = Double.parseDouble(nodes[i + 1]);
            double yPos = Double.parseDouble(nodes[i + 2]);
            String name = nodes[i];

            Pin pin = new Pin(xPos, yPos, name);
            addPinToMap(pin);
            pins.add(pin);
        }

        //Get connections and add them to map
        for(int i = 2; i < lines.length;i++){
            String[] connection = lines[i].split(";");

            Pin from = getPin(pins, connection[0]);
            Pin to = getPin(pins, connection[1]);

            String name = connection[2];
            int weight = Integer.parseInt(connection[3]);
            try {
                addEdgeToMap(from, to, name, weight);
            }catch (NoSuchElementException e){
                System.out.println("Node not found");
            } catch (IllegalStateException e){
                //Expected exception because every connection is added twice
                System.out.println("Connection already exists");
            }
        }

        isSaved = true;

    }

    //----------------------------------------------------------------------------------------

    private static Pin getPin(List<Pin> pins, String connection) {
        return pins.stream()
                    .filter(pin -> pin.toString().equals(connection))
                    .findFirst()
                    .orElse(null);
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onMenuSaveFileClick() throws IOException {
        /*  Save current display data to .graph file
            1. Create new .graph file or open old file
            2. Write one row with URL to output file [file:URL]
            3. Write one row with information about the nodes [name1;x1;y1;name2;x2;y2;...;nameN;xN;yN]
            4. Create a new row for every connection [fromName;toName;connectionName;connectionWeight]
            5. Close file
         */
        String resourcePath = "src/main/java/main/graph/saved/";
        Path filePath = Path.of(resourcePath + ".graph");

        if(!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        StringBuilder output = new StringBuilder();
        String imageFilePath = "file:src/main/resources/main/controllers/images/map.PNG";
        output.append(imageFilePath).append("\n");
        output.append(graph.getNodes().stream()
                                        .map(Pin::getLongString)
                                        .collect(Collectors.joining(";")))
                                        .append("\n");

        for(Pin node : graph.getNodes()) {
            for(Edge<Pin> edge : graph.getEdgeFrom(node)) {
                output.append(edge.toString()).append("\n");
            }
        }

        Files.writeString(filePath, output.toString());
        try (Stream<String> stream = Files.lines(filePath)) {
            stream.forEach(System.out::println);
        }

        isSaved = true;

    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onMenuSaveImageClick() throws IOException {

        // Save screenshot of current windowPane to resource folder with name "capture.PNG"
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onExitButtonClick() {
        // Create popup to ask about unsaved changes
        if(!isSaved){
            if(Popup.unsavedChanges()){
                return;
            }
        }

        stageManager.close(isSaved);
    }

    @Override
    public void close(){
        onExitButtonClick();
    }

    //-------------------- Buttons functions -------------------------------------------------

    private Pin[] getSortedClickedPins(){
        Pin[] pins = graph.getNodes().parallelStream().filter(Pin::isClicked).toArray(Pin[]::new);
        if(pins.length != 2){
            Popup.error("Two places must be selected");
            return null;
        }
        //If p0 is newer than p1 then swap
        if (pins[0].getTimestamp() > pins[1].getTimestamp()) {
            Pin temp = pins[0];
            pins[0] = pins[1];
            pins[1] = temp;
            return pins;
        }

        return pins;
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onFindPathButtonClick() {
        Pin[] pins = getSortedClickedPins();
        if(pins == null){
            return;
        }
        Pin p0 = pins[0];
        Pin p1 = pins[1];

        List<Edge<Pin>> path = graph.getPath(p0,p1);

        if(path == null){
            Popup.findPath(p0,p1,"No path found");
            return;
        }

        StringBuilder pathString = new StringBuilder();
        int accumulatedWeight = 0;
        for (Edge<Pin> edge: path) {
            System.out.println(edge);
            pathString.append("to ")
                    .append(edge.getDestination())
                    .append(" by ")
                    .append(edge.getName())
                    .append(" takes ")
                    .append(edge.getWeight())
                    .append("\n");

            accumulatedWeight += edge.getWeight();
        }
        System.out.println("Accumulated weight: " + accumulatedWeight);
        pathString.append("Total: ").append(accumulatedWeight);

        Popup.findPath(p0,p1,pathString.toString());

    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onShowConnectionButtonClick() {
        Pin[] pins = getSortedClickedPins();
        if(pins == null){
            return;
        }
        Pin p0 = pins[0];
        Pin p1 = pins[1];

        Edge<Pin> edge = graph.getEdgeBetween(p0,p1);
        if(edge == null){
            Popup.error("No connection found!");
            return;
        }
        Popup.showConnection(p0,p1,edge);
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onNewPlaceButtonClick() {
        stageManager.setCursor(Cursor.CROSSHAIR);
        btnNewPlace.setDisable(true);
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onNewConnectionButtonClick() {
        Pin[] pins = getSortedClickedPins();
        if(pins == null){
            return;
        }
        Pin p0 = pins[0];
        Pin p1 = pins[1];

        if(graph.getEdgeBetween(p0,p1) != null){
            Popup.error("Connection already exists!");
            return;
        }

        Pair<String, Integer> result = Popup.newConnection(p0, p1);
        if(result != null) {
            if(result.getValue() < 0){return;}
            addEdgeToMap(p0, p1, result.getKey(), result.getValue());
            isSaved = false;
            return;
        }

        Popup.error("Result error");
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void onChangeConnectionButtonClick() {
        isSaved = false;

        Pin[] pins = getSortedClickedPins();
        if(pins == null){
            return;
        }
        Pin p0 = pins[0];
        Pin p1 = pins[1];

        Edge<Pin> edge = graph.getEdgeBetween(p0,p1);
        String result = Popup.changeConnection(p0,p1,edge);
        if(result == null || result.isEmpty()){return;}

        graph.setConnectionWeight(p0, p1, Integer.parseInt(result));
    }

    //-------------------- OutputArea Functions -----------------------------------------------

    @FXML
    protected void onOutPutAreaMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getX() > mapImage.getWidth() || mouseEvent.getY() > mapImage.getHeight()) {return;}

        Pin pin = getPinOnMousePos(mouseEvent);
        if (pin != null) {
            pin.click();
            return;
        }

        if (btnNewPlace.isDisable()) {
            String name = Popup.newPlace();
            if(name != null && !name.isEmpty())
                addPinToMap(new Pin(mouseEvent.getX(), mouseEvent.getY(), name));
            stageManager.setCursor(Cursor.DEFAULT);
            btnNewPlace.setDisable(false);
            isSaved = false;
            return;
        }
    }

    //----------------------------------------------------------------------------------------

    private Pin getPinOnMousePos(MouseEvent mouseEvent) {
        return graph.getNodes().parallelStream().filter(pin -> {
            double x1 = pin.getCircle().getCenterX();
            double y1 = pin.getCircle().getCenterY();

            double x2 = mouseEvent.getX();
            double y2 = mouseEvent.getY();

            double distanceBetweenPoints = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));

            return pin.getCircle().getRadius() >= distanceBetweenPoints;
        }).findAny().orElse(null);
    }

    //----------------------------------------------------------------------------------------

    private void addPinToMap(Pin pin) {
        outputArea.getChildren().add(pin.getCircle());
        graph.add(pin);
    }

    //----------------------------------------------------------------------------------------

    private void addEdgeToMap(Pin p0, Pin p1, String name, int weight) {
        graph.connect(p0, p1, name, weight);

        Line line = new Line();
        line.setStartX(p0.getCircle().getCenterX());
        line.setStartY(p0.getCircle().getCenterY());
        line.setEndX(p1.getCircle().getCenterX());
        line.setEndY(p1.getCircle().getCenterY());
        outputArea.getChildren().add(line);
    }

    //-------------------- Developer Functions -----------------------------------------------

    @FXML
    protected void bindImage() {
        //Resize image to fit the window
        DoubleBinding newWidth = outputArea.widthProperty().subtract(0);
        DoubleBinding newHeight = vBox.heightProperty().subtract(hBox.getHeight() + menu.getHeight());
        imageView.fitWidthProperty().bind(newWidth);
        imageView.fitHeightProperty().bind(newHeight);
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void printImageSize() {
        System.out.println("Image size: " + imageView.getFitWidth() + ", " + imageView.getFitHeight());
    }

    //----------------------------------------------------------------------------------------

    @FXML
    protected void printWindowSize() {
        System.out.println("Window size: " + vBox.getWidth() + ", " + vBox.getHeight());
    }

    //-------------------- Init Functions ----------------------------------------------------

    public void initialize() {
        isSaved = true;
    }

    //----------------------------------------------------------------------------------------

    @Override
    public void setStageManager(StageManager manager) {
        this.stageManager = manager;
    }
}
