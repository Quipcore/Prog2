package main;

import javafx.scene.Cursor;
import main.controllers.Controller;

import java.io.File;
import java.io.IOException;

public interface StageManager {
    void resizeStage();

    void setCursor(Cursor crosshair);

    void close();
}
