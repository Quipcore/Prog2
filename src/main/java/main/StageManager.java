package main;

import javafx.scene.Cursor;
import main.controllers.Controller;

import java.io.IOException;

public interface StageManager {
    void resizeStage();

    void setCursor(Cursor crosshair);

    void createPopup(String s, String name, Controller mainController) throws IOException;

    void close();
}
