module com.example.prog2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prog2 to javafx.fxml;
    exports com.example.prog2;
}