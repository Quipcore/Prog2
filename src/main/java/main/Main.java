package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    class Node{
        private int id;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 750, 500);
        stage.setTitle("Paths");
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(570);
        stage.show();
        fxmlLoader.<MainController>getController().resizeImage(null); //This call only works after stage.show. WHY?!?!?!?!
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
        graph.connect(nodeList.get(1), nodeList.get(2), "Edge 2", 1);
        graph.connect(nodeList.get(2), nodeList.get(3), "Edge 3", 1);
        graph.connect(nodeList.get(2), nodeList.get(4), "Edge 4", 1);

        graph.connect(nodeList.get(5), nodeList.get(5), "Edge 5", 1);
        graph.connect(nodeList.get(5), nodeList.get(6), "Edge 6", 1);

        graph.connect(nodeList.get(0), nodeList.get(4), "Edge 7", 1);



        List<Edge<Node>> path = graph.getPath(nodeList.get(0), nodeList.get(4));
        if(path == null){
            System.out.println("No path found");

        }else {
            System.out.println(path);
        }

    }


}
