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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML    private AnchorPane background;
    @FXML    private ImageView wallpaper;
    @FXML    private ImageView ic1;
    @FXML    private ImageView ic2;
    @FXML    private ImageView ic3;
    @FXML    private Button buttRegisterClose;
    @FXML    private Button buttRegisterGo;
    @FXML    private Label lblTitel;
    @FXML    private TextField txtName;
    @FXML    private PasswordField txtPassword1;
    @FXML    private PasswordField txtPassword11;
    @FXML    private Label lblSucces;

    @Override
     public void initialize(URL url, ResourceBundle resourceBundle){
         File wall_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\Background_Register.jpg");
         File ic1_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\iconregister_1.jpg");
         File ic2_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\iconregister_2.png");

         Image wall_im = new Image(wall_f.toURI().toString());
         Image ic1_im= new Image(ic1_f.toURI().toString());
         Image ic2_im = new Image(ic2_f.toURI().toString());
         wallpaper.setImage(wall_im);
         ic1.setImage(ic1_im);
         ic2.setImage(ic2_im);
         ic3.setImage(ic2_im);
     }
    @FXML void closeRegister(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Stage window = (Stage) buttRegisterClose.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }
    @FXML void goRegister(ActionEvent event){
        String user = txtName.getText();
        String pass = txtPassword1.getText();
        String passConfirm = txtPassword11.getText();
        if (!user.equals("")){
            if(pass.equals(passConfirm)){
                lblSucces.setText("You successfully registered!");
                DBConnection dataBase = new DBConnection();
                String url = "https://studev.groept.be/api/a21ib2a01/register/%s/%s/%s".formatted(txtName.getText(), txtPassword1.getText(), "note");
                String data = dataBase.makeGETRequest(url);
            }else{
                lblSucces.setText("Your passwords didn't match");
            }
        }else {lblSucces.setText("Put in valid name");}

    }

}
