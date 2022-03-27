package controllers;

import project.Main;
import project.Product;
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
import javafx.stage.Stage;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingController implements Initializable {

    @FXML private Button buttMeal;
    @FXML private TableColumn<Product, String> colProd;
    @FXML private TableColumn<Product, Integer> colQty;
    @FXML private TableView<Product> tblProduct;


    @FXML
    void toFridge(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("FridgeScene");
    }

    @FXML
    void toInventory(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("InventoryScene.fxml");
    }

    @FXML
    void toRecipe(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("RecipeScene.fxml");
    }

    @FXML
    void toShoppingList(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("ShoppingScene.fxml");
    }


    public ShoppingController(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        String urlAPI = "https://studev.groept.be/api/a21ib2a01/readProducts";
        List<Object> products = db.parseJSONProducts(db.makeGETRequest(urlAPI));
        ObservableList<Product> OBList = FXCollections.observableArrayList();
        for (Object product:products) {
            OBList.add((Product) product);
        }

        this.colProd.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        tblProduct.setItems(OBList);
    }
}