package main.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Message;
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

    @Override
    public void send(Message message) {
        throw new UnsupportedOperationException();
    }

    public void initialize(StageManager manager, Controller parentController){
        setStageManager(manager);
        setParentController(parentController);
    }

    public void onEnterButtonClick(ActionEvent actionEvent) {
        parentController.send(new Message(textfield.getText()));
        stageManager.close();
    }


}
