<<<<<<< HEAD
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
=======
module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.json;
    opens com.example.project to javafx.fxml;
    exports com.example.project;


>>>>>>> ffd93b0 (jorie project)
}