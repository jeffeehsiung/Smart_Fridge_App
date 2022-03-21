package controllers;

import com.example.fridge.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    @FXML private ImageView background_recipe;
    @FXML private Button buttInv;
    @FXML private Button buttList;
    @FXML private Button buttMeal;
    @FXML private TableColumn<Product, String> colProd;
    @FXML private TableColumn<Product, Integer> colQty;
    @FXML private ImageView imageInv;
    @FXML private ImageView imageList;
    @FXML private ImageView imageMeal;
    @FXML private TableView<Product> tblProduct;


    @FXML void goMenuInv(ActionEvent event) {

    }


    public ShoppingController(){
    }

    @FXML void goMenuMeal(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RecipeScene.fxml"));
        Stage window = (Stage) buttMeal.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        String urlAPI = "https://studev.groept.be/api/a21ib2a01/readProducts";
        List<Object> products = db.parseJSONProducts(db.makeGETRequest(urlAPI));
        ObservableList<Product> OBList = FXCollections.observableArrayList();
        for (Object product:products) {
            //String nameProd = product.getName();
            //String qtyProd =product.getQty();
            //System.out.print(nameProd +" -- "+ qtyProd+ "\n");
            OBList.add((Product) product);
            System.out.println(OBList);
        }

        this.colProd.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        tblProduct.setItems(OBList);
    }
}