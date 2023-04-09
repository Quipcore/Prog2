package main.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.StageManager;

public class newPlaceController implements Controller {

    public TextField textfield;
    public Button btnEnter;
    StageManager stageManager;
    Controller parentController;
    @Override
    public void setStageManager(StageManager manager) {
        this.stageManager = manager;
    }

    @Override
    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }

    public void initialize(StageManager manager, Controller parentController){
        setStageManager(manager);
        setParentController(parentController);
    }
}
