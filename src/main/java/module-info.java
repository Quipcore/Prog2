module com.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
    exports main;
    exports main.controllers;
    opens main.controllers to javafx.fxml;
    exports main.graph;
    opens main.graph to javafx.fxml;
}