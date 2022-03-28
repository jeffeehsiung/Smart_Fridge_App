module com.example.fridge {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires java.net.http;


    opens project to javafx.fxml;
    exports project;
    exports controllers;
    opens controllers to javafx.fxml;
}