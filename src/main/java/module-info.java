module com.main {
    requires javafx.controls;
    requires javafx.fxml;

/*
    opens com.example.prog2 to javafx.fxml;
    exports com.example.prog2;
*/
    opens main to javafx.fxml;
    exports main;
    exports main.Controllers;
    opens main.Controllers to javafx.fxml;
}
