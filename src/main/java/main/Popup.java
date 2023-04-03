package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.Controller;

import java.io.IOException;

public class Popup implements StageManager {

    FXMLLoader fxmlLoader;
    String name;

    Controller parentController;

    Stage popupWindow;

    public Popup(String fxml, String name) {
        fxmlLoader = new FXMLLoader(Popup.class.getResource(fxml));
        this.name = name;
    }

    public Popup(String fxml, String name, Controller parentController) throws IOException {
        this(fxml, name);
        this.parentController = parentController;
        this.popupWindow = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.setStageManager(this);
        controller.setParentController(parentController);
        this.popupWindow.setTitle(name);
        this.popupWindow.setScene(scene);
    }

    public void display() throws IOException {
        popupWindow.show();
        popupWindow.showAndWait();
    }

    @Override
    public void resizeStage() {
        this.popupWindow.sizeToScene();
    }

    @Override
    public void setCursor(Cursor cursor) {
        popupWindow.getScene().setCursor(cursor);
    }

    @Override
    public void createPopup(String fxml, String name, Controller parentController) throws IOException {
        Popup popup = new Popup(fxml, name, parentController);
        popup.display();
    }

    @Override
    public void close() {
        popupWindow.close();
    }
}
