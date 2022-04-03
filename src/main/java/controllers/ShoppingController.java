package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.Main;
import project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingController extends mysqlConnector implements Initializable {

    @FXML private Button buttMeal;
    @FXML private TableColumn<Product, String> colProd;
    @FXML private TableColumn<Product, Integer> colQty;
    @FXML private TableColumn<Product, String> itemBox;
    @FXML private TableView<Product> tblProduct;

    private ObservableList<Product> OBList = FXCollections.observableArrayList();

    public void autoCheckOut(){
        OBList.forEach((product -> {
            if(product.getItemBox().isSelected()){
                goToStoreAndShop();
            };
        }));
    }

    @FXML
    void toFridge(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("FridgeScene.fxml");
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

    public void deletItem(ActionEvent actionEvent){
        String serverName = "removeFromShoppingList";
        String urlExtention;
        System.out.println("check selected");
        for(Product product: OBList){
            if(product.getItemBox().isSelected()){
                urlExtention = serverName + "/" + product.getName();
                makeGETRequest(urlExtention);
            }
        }
    }

    public ShoppingController(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection db = new DBConnection();
        String urlAPI = "https://studev.groept.be/api/a21ib2a01/readProducts";
        List<Object> products = db.parseJSONProducts(db.makeGETRequest(urlAPI));
        for (Object product:products) {
            OBList.add((Product) product);
        }

        this.colProd.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        this.itemBox.setCellValueFactory(new PropertyValueFactory<>("itemBox"));

        tblProduct.setItems(OBList);
    }
}