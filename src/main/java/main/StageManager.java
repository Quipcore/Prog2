package main;

import java.io.IOException;

public interface StageManager {
    void resizeStage();

    void createPopup();
    void createPopup(String fxml) throws IOException;
}
