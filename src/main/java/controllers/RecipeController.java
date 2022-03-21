package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RecipeController implements Initializable  {

    @FXML private Button buttD1;
    @FXML private Button buttD2;
    @FXML private Button buttD3;
    @FXML private Button buttMeal;
    @FXML private Button buttInv;
    @FXML private Button buttList;
    @FXML private ImageView imageMeal;
    @FXML private ImageView imageInv;
    @FXML private ImageView imageList;
    @FXML private Button buttSlideLeft;
    @FXML private Button buttSlideRight;
    @FXML private ImageView imageD1;
    @FXML private ImageView imageD2;
    @FXML private ImageView imageD3;
    @FXML private ImageView background_recipe;
    @FXML private Label lblInfD1;
    @FXML private Label lblInfD2;
    @FXML private Label lblInfD3;
    @FXML private Label lblTitD1;
    @FXML private Label lblTitD2;
    @FXML private Label lblTitD3;
    @FXML private Rating rateD1;
    @FXML private Rating rateD2;
    @FXML private Rating rateD3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File background_f = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\background_recipe.jpg");
        File dish1_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\dish1.jpg");
        File dish2_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\dish2.jpg");
        File dish3_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\dish3.jpg");
        File butMeal_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\butMeal.png");
        File butInv_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\butInv.png");
        File butList_f  = new File("C:\\Users\\aerts\\Desktop\\Project\\src\\main\\resources\\Images\\butList.png");

        Image background_im = new Image(background_f.toURI().toString());
        Image dish1_im= new Image(dish1_f.toURI().toString());
        Image dish2_im = new Image(dish2_f.toURI().toString());
        Image dish3_im= new Image(dish3_f.toURI().toString());
        Image butMeal_im = new Image(butMeal_f.toURI().toString());
        Image butInv_im= new Image(butInv_f.toURI().toString());
        Image butList_im = new Image(butList_f.toURI().toString());
        background_recipe.setImage(background_im);
        imageD1.setImage(dish1_im);
        imageD2.setImage(dish2_im);
        imageD3.setImage(dish3_im);
        imageMeal.setImage(butMeal_im);
        imageInv.setImage(butInv_im);
        imageList.setImage(butList_im);

    }

    @FXML void goDish1(ActionEvent event) {
        System.out.println("D1");
    }
    @FXML void goDish2(ActionEvent event) {
        System.out.println("D2");
    }
    @FXML void goDish3(ActionEvent event) {
        System.out.println("D3");
    }

    @FXML void slideLeft(ActionEvent event) {
          System.out.println("slL");
    }

    @FXML void slideRight(ActionEvent event) {
        System.out.println("slR");
    }
    @FXML void goMenuMeal(ActionEvent event) {
        System.out.println("meal");
    }
    @FXML void goMenuInv(ActionEvent event) {
        System.out.println("inv");
    }

    @FXML void goMenuList(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ShoppingScene.fxml"));
        Stage window = (Stage) buttMeal.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }
}
