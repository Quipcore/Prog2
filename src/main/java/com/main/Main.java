package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 750, 500); //Setting scene w/h might be causing problems with images. We are loading image THEN resizing INSTEAD OF resizing THEN setting image
        stage.setTitle("Paths");
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(570);
        stage.show();
        fxmlLoader.<MainController>getController().resizeImage(null);
    }

    public static void main(String[] args) {
        launch();
    }
}
