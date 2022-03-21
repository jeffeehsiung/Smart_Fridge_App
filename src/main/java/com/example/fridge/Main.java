package com.example.fridge;

import controllers.mysqlConnector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.w3c.dom.Text;

import java.io.IOException;

public class Main extends Application {

    double x, y;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("JJKL");
        stage.setScene(scene);
        stage.show();

        //move around
        scene.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            this.stage.setX(mouseEvent.getScreenX() - x);
            this.stage.setY(mouseEvent.getScreenY() - y);
        });

    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        this.stage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}