package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    class Node{
        private int x;
        private int y;
        private int id;

        public Node(int x, int y, int id){
            this.x = x;
            this.y = y;
            this.id = id;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public int getID(){
            return id;
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

        for(int i = 0; i < 10; i++){
            Node node = new Node(i, i, i);
            graph.add(node);
            nodeList.add(node);
        }
        for(int i = 0; i < 10-1; i++){
            graph.connect(nodeList.get(i), nodeList.get(i+1), "Edge " + i, 1);
        }


        List<Edge<Node>> path = graph.getPath(nodeList.get(0), nodeList.get(3));
        System.out.println(path);
    }


}
