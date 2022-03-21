package controllers;

import com.example.fridge.Main;
import controllers.mysqlConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class login extends mysqlConnector implements Initializable {

    @FXML
    private Button login;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    private String userDBserver = "readUser";

    public login (){}

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException{
        Main m = new Main();
        //mysqlConnector mysqlConnector = new mysqlConnector();
        compareUserDB(username.getText().toString(),password.getText().toString(),userDBserver);
        if(loginSucceed){
            wronglogin.setText("Success!");
            m.changeScene("fridge.fxml");
        }
        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
        }
        else if(userExist && !pwdCorrect){
            wronglogin.setText("user exist, password incorrect");
        }
        else{
            wronglogin.setText("no user found");
        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
