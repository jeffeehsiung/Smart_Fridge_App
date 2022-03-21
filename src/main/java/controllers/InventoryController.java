package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import project.Main;
import project.grocery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class InventoryController extends mysqlConnector implements Initializable {
        @FXML
        private TableView<grocery> inventoryTable;
        @FXML
        private TableColumn<grocery,String> id;
        @FXML
        private TableColumn<grocery,String> item;
        @FXML
        private TableColumn<grocery,String> category;
        @FXML
        private TableColumn<grocery, String> expiryDate;
        @FXML
        private TableColumn<grocery,String> qty;
        @FXML
        private TableColumn<grocery,String> comment;
        @FXML
        private Label searchItem;
        @FXML
        private TextField keyword;


        private ObservableList<grocery> data = FXCollections.observableArrayList();
        private String[] keys = new String[]{"id", "item","category", "expiry date", "qty", "comment"};
        private String serverName = "inventory";

        public InventoryController(){
                JSONArray dbJSON = parseIntoJSONarray(makeGETRequest(serverName));
                //iterate and compare to JSONarray
                for (int i = 0; i < dbJSON.length(); i++){
                        JSONObject curObject = dbJSON.getJSONObject(i);
                        String id = curObject.getString(keys[0]);
                        String item = curObject.getString(keys[1]);
                        String category = curObject.getString(keys[2]);
                        String expiryDate = null;
                        if (!(curObject).isNull(keys[3])){expiryDate = curObject.getString(keys[3]);}
                        String qty = curObject.getString(keys[4]);
                        String comment = null;
                        if (!(curObject).isNull(keys[5])){comment = curObject.getString(keys[5]);}

                        //Populate the ObservableList
                        data.add(new grocery(id,item,category,expiryDate,qty,comment));
                        System.out.println(data);
                }
                // propertyValueFatory corresponds to the new grocery fields
                // the table column is the one you annotate above
        }

        public void filter(){
                FilteredList<grocery> filteredData = new FilteredList<>(data, b -> true);
                keyword.textProperty().addListener((observable,oldValue,newValue)->{
                        filteredData.setPredicate(grocery -> {
                                //if no search value then display all records
                                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                                        return true;
                                }

                                String searchKeyword = newValue.toLowerCase();
                                if(grocery.getId_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in id
                                else if(grocery.getItem_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in item name//
                                else if(grocery.getCategory_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in category
                                else if(grocery.getQty_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in qty
                                /* else if(grocery.getExpiryDate_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in expiryDate */
                                /* else if(grocery.getComment_col().toLowerCase().indexOf(searchKeyword) > -1){
                                        return true;}//means we found a match in comment */
                                else
                                        return false;//no match found
                        });
                });
                SortedList<grocery> sortedList = new SortedList<>(filteredData);
                //bind sorted result with table view
                sortedList.comparatorProperty().bind(inventoryTable.comparatorProperty());
                //apply filtered and sorted data to the TableView
                inventoryTable.setItems(sortedList);
        }

        public void updateItem(ActionEvent event){
                serverName = "addToShoppingList";
                String toShoppingListItem = keyword.getText();
                String toShoppingListItemQty = "1";
                //get user input for qty
                if(!(keyword.getText().contains("key"))){
                        searchItem.setText("Enter "+ toShoppingListItem+ "qty: ");
                        keyword.setText("");
                        if(!(keyword.getText().isEmpty())){
                                toShoppingListItemQty = keyword.getText();
                        }
                        searchItem.setText("Search Item");  // Output user input
                }
                //Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                //String toShoppingListItemQty = myObj.nextLine();  // Read user input

                //extention url for inserting into shoppingList
                String urlExtention = serverName + "/" + toShoppingListItem + "/" + toShoppingListItemQty;
                JSONArray dbJSON = parseIntoJSONarray(makeGETRequest(urlExtention));

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

        public void initialize(URL url, ResourceBundle resourceBundle) {
                this.id.setCellValueFactory(new PropertyValueFactory<>("id_col"));
                this.item.setCellValueFactory(new PropertyValueFactory<>("item_col"));
                this.category.setCellValueFactory(new PropertyValueFactory<>("category_col"));
                this.expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate_col"));
                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty_col"));
                this.comment.setCellValueFactory(new PropertyValueFactory<>("comment_col"));

                inventoryTable.setItems(data);
                /* initial filter list */
                filter();


        }
}