package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import project.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML    private AnchorPane background;
    @FXML    private ImageView wallpaper;
    @FXML    private ImageView ic1;
    @FXML    private ImageView ic2;
    @FXML    private ImageView ic3;
    @FXML    private Button buttRegisterClose;
    @FXML    private Button buttRegisterGo;
    @FXML    private Label lblTitle;
    @FXML    private TextField txtName;
    @FXML    private PasswordField txtPassword1;
    @FXML    private PasswordField txtPassword11;
    @FXML    private Label lblSuccess;

    @Override
     public void initialize(URL url, ResourceBundle resourceBundle){}

    @FXML void closeRegister(ActionEvent event) throws Exception{
        Main m = new Main();
        m.changeScene("LoginScene.fxml");
    }
    @FXML void goRegister(ActionEvent event){
        String user = txtName.getText();
        String pass = txtPassword1.getText();
        String passConfirm = txtPassword11.getText();
        if (!user.equals("")){
            if(pass.equals(passConfirm)){
                lblSuccess.setText("You've successfully registered!");
                DBConnection dataBase = new DBConnection();
                String url = "https://studev.groept.be/api/a21ib2a01/register/%s/%s/%s".formatted(txtName.getText(), txtPassword1.getText(), "note");
                String data = dataBase.makeGETRequest(url);
            }else{
                lblSuccess.setText("Your passwords didn't match");
            }
        }else {lblSuccess.setText("Put in valid name");}

    }

}
