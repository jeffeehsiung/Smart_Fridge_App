module com.example.fridge {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.sql;
    requires java.desktop;


    opens com.example.fridge to javafx.fxml;
    exports com.example.fridge;
    exports controllers;
    opens controllers to javafx.fxml;
}