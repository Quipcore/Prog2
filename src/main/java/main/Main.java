package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.controllers.Controller;
import main.graph.Edge;
import main.graph.ListGraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements StageManager{

    Stage stage;

    @Override
    public void resizeStage() {
        this.stage.sizeToScene();
    }


    @Override
    public void close(boolean isSaved) {
        stage.close();
    }

    @Override
    public void setCursor(Cursor cursor) {
        stage.getScene().setCursor(cursor);
    }

    @Override
    public Dimension getDimensions() {
        return new Dimension((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight());
    }

    @Override
    public Point getPos() {
        return new Point((int) stage.getX(), (int) stage.getY());
    }

    static class Node{
        private final int id;

        public Node(int id){
            this.id = id;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller mainController = fxmlLoader.getController();
        mainController.setStageManager(this);

        this.stage.setTitle("Paths");
        this.stage.setScene(scene);
//        this.stage.getScene().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
//            mainController.close();
//        });
        this.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            mainController.close();
        });
        this.stage.sizeToScene();
        this.stage.show();

        System.out.println(this.stage.getX());
        System.out.println(this.stage.getY());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.runSearch();

        launch();
    }

    private void runSearch() {
        ListGraph<Node> graph = new ListGraph<>();
        List<Node> nodeList = new ArrayList<>();

        int numOfNodes = 6;
        for(int i = 0; i < numOfNodes + 1; i++){
            nodeList.add(new Node(i));
            graph.add(nodeList.get(i));
        }


        graph.connect(nodeList.get(0), nodeList.get(1), "Edge A", 1);
        graph.connect(nodeList.get(1), nodeList.get(2), "Edge B", 2);
        graph.connect(nodeList.get(2), nodeList.get(3), "Edge C", 1);
        graph.connect(nodeList.get(2), nodeList.get(4), "Edge D", 1);

        graph.connect(nodeList.get(5), nodeList.get(5), "Edge E", 1);
        graph.connect(nodeList.get(5), nodeList.get(6), "Edge F", 1);

        graph.connect(nodeList.get(0), nodeList.get(4), "Edge G", 1);

        graph.disconnect(nodeList.get(0), nodeList.get(4));
        graph.connect(nodeList.get(0), nodeList.get(4), "Edge H", 1);

        nodeList.add(new Node(7));
        graph.add(nodeList.get(7));
        graph.remove(nodeList.get(7));

        System.out.println(graph);
        System.out.println(graph.pathExists(nodeList.get(0), nodeList.get(2)));

        /*

        0 - 1 - 2 - 3
         \      |
          '- - -4

        5 - 6
       */

        List<Edge<Node>> path = graph.getPath(nodeList.get(0), nodeList.get(2));
        if(path == null){
            System.out.println("No path found");

        }else {
            int accumulatedWeight = 0;
            for (Edge<Node> edge: path) {
                System.out.println(edge);
                accumulatedWeight += edge.getWeight();
            }
            System.out.println("Accumulated weight: " + accumulatedWeight);
        }

    }


}
