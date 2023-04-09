package main.controllers;

import main.StageManager;

//Might want to split into controller and child controller interfaces.
public interface Controller {
    void setStageManager(StageManager manager);
}
