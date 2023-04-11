package main;

import javafx.scene.Cursor;

import java.awt.*;

public interface StageManager {
    void resizeStage();

    void setCursor(Cursor crosshair);

    void close(boolean isSaved);

    Dimension getDimensions();

    Point getPos();
}
