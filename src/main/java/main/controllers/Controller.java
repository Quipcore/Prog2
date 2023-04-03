package main.controllers;

import main.Message;
import main.StageManager;

//Might want to split into controller and child controller interfaces.
public interface Controller {
    void setStageManager(StageManager manager);

    void setParentController(Controller parentController);

    void send(Message message);
}
