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

    private Stage stage;

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
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller mainController = fxmlLoader.getController();
        mainController.setStageManager(this);

        this.stage.setTitle("Paths");
        this.stage.setScene(scene);
        this.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            mainController.close();
        });
        this.stage.sizeToScene();
        this.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
