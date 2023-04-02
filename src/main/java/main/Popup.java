package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.Controller;

import java.io.IOException;

public class Popup implements StageManager{

    static FXMLLoader fxmlLoader;
    public Popup(String fxml){
        fxmlLoader = new FXMLLoader(Popup.class.getResource(fxml));
    }

    public void display() throws IOException {
        Stage popupWindow = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.setStageManager(this);
        popupWindow.setTitle("New Place");
        popupWindow.setScene(scene);
        popupWindow.show();
    }

    @Override
    public void resizeStage() {
    }

    @Override
    public void createPopup() {

    }

    @Override
    public void createPopup(String fxml) throws IOException {

    }
}
