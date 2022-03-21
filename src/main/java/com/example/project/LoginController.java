package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private ImageView background;
    @FXML private ImageView icon;
    @FXML private Button buttLogin;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtUsername;
    @FXML private Label lblWrongLogin;
    @FXML private Button goRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File wall_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\Background_Register.jpg");
        File ic1_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\user.png");


        Image wall_im = new Image(wall_f.toURI().toString());
        Image ic1_im= new Image(ic1_f.toURI().toString());
        background.setImage(wall_im);
        icon.setImage(ic1_im);
    }

    @FXML
    void userLogin(ActionEvent event) throws IOException, InterruptedException {

        DBConnection db = new DBConnection();

        boolean userFound;
        boolean passFound;
        String url = "https://studev.groept.be/api/a21ib2a01/readUser";
        List<User> users = db.parseJSONUsers(db.makeGETRequest(url));

        for(User user: users){
            userFound = user.getName().equals(txtUsername.getText());
            passFound = user.getPass().equals(txtPassword.getText());
            if (userFound && passFound){
                lblWrongLogin.setText("Login successfully");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RecipeScene.fxml")));
                Stage window = (Stage) buttLogin.getScene().getWindow();
                window.setScene(new Scene(root, 600, 400));
                break;
            }
            lblWrongLogin.setText("Username and/or password or incorrect");

        }
    }
    @FXML
    void goRegister(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterScene.fxml")));
        Stage window = (Stage) buttLogin.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }
}
