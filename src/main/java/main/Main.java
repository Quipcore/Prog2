package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.Controller;
import main.graph.Edge;
import main.graph.ListGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements StageManager{

    Stage stage;

    @Override
    public void resizeStage() {
        this.stage.sizeToScene();
    }


    @Override
    public void close() {
        stage.close();
    }

    @Override
    public void setCursor(Cursor crosshair) {
        stage.getScene().setCursor(crosshair);
    }

    class Node{
        private final int id;

        public Node(int id){
            this.id = id;
        }

        public int getID(){
            return id;
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
        this.stage.sizeToScene();
        this.stage.show();
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


        graph.connect(nodeList.get(0), nodeList.get(1), "Edge 1", 1);
        graph.connect(nodeList.get(1), nodeList.get(2), "Edge 2", 2);
        graph.connect(nodeList.get(2), nodeList.get(3), "Edge 3", 1);
        graph.connect(nodeList.get(2), nodeList.get(4), "Edge 4", 1);

        graph.connect(nodeList.get(5), nodeList.get(5), "Edge 5", 1);
        graph.connect(nodeList.get(5), nodeList.get(6), "Edge 6", 1);

        graph.connect(nodeList.get(0), nodeList.get(4), "Edge 7", 1);

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
