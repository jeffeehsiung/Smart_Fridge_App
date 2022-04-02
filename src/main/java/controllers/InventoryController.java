package controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.json.JSONArray;
import org.json.JSONObject;
import project.Main;
import project.grocery;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        private TableColumn<grocery,String> toCart;
        @FXML
        private Label searchItem;
        @FXML
        private TextField keyword;
        @FXML
        private Button addToCart;



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

        public void addToCart(ActionEvent event){
                serverName = "addToShoppingList";
                String urlExtention;
                String toShoppingListItem = keyword.getText();
                String toShoppingListItemQty = "1";
                //get user input for qty
                if(!(keyword.getText().contains("key")) && !(keyword.getText().isEmpty())){
                        searchItem.setText("Enter "+ toShoppingListItem+ "qty: ");
                        keyword.clear();
                        if(!(keyword.getText().isEmpty())){
                                toShoppingListItemQty = keyword.getText();
                        }
                        searchItem.setText("Search Item");  // Output user input
                }
                //extention url for inserting into shoppingList
                urlExtention = serverName + "/" + toShoppingListItem + "/" + toShoppingListItemQty;
                parseIntoJSONarray(makeGETRequest(urlExtention));

                //search checkBox item to put into shopping cart
                System.out.println("check selected");
                for(grocery grocery: data){
                        if(grocery.getToCart().isSelected()){
                                urlExtention = serverName + "/" + grocery.getItem_col() + "/" + toShoppingListItemQty;
                                makeGETRequest(urlExtention);

                        }
                }
        }

        public void addToInventory(ActionEvent event){
                serverName = "addToInventory";
                final String[] newGrocery = {"itemName","category","comment"};
                final LocalDate[] date = {LocalDate.now().plusDays(10)}; //default as 10 days
                final Integer[] qty = {1}; //default, unless specified

                Paint color = searchItem.getTextFill();
                Double position = keyword.getLayoutX();
                //action event
                EventHandler<ActionEvent> dateNamed = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent dateNamed) {
                                if((keyword.getText().trim().length()!=0) && !(keyword.getText().contains("-"))){
                                        searchItem.setText("Re-enter"); keyword.clear(); return;
                                }
                                else if ((keyword.getText().length()==0)||(keyword.getText().contains("YYYY"))){
                                        date[0] = LocalDate.now().plusDays(10);
                                }
                                else{ date[0] = LocalDate.parse(keyword.getText().trim().replaceAll("\\s",""));}
                                keyword.clear();
                                keyword.setLayoutX(position);
                                searchItem.setVisible(false);
                                searchItem.setText("Search Item");
                                searchItem.setLayoutX(keyword.getLayoutX()- searchItem.getText().length() - searchItem.getWidth() - 5);
                                searchItem.setTextFill(color);
                                searchItem.setVisible(true);
                                keyword.setVisible(true);
                                String urlExtention = serverName + "/" + newGrocery[0] + "/" + newGrocery[1]+ "/" + date[0] + "/" + qty[0] + "/" +newGrocery[2];
                                System.out.println(urlExtention);
                                makeGETRequest(urlExtention);
                        }
                };

                EventHandler<ActionEvent> qtyNamed = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent qtyNamed) {
                                if(keyword.getText().getClass().equals(qty[0].getClass())){
                                        searchItem.setText("Re-enter"); keyword.clear(); return;
                                }
                                else if (keyword.getText().isEmpty()){
                                        qty[0] = 1;
                                }
                                else{ qty[0] = Integer.parseInt(keyword.getText().trim());}
                                keyword.clear();
                                keyword.setVisible(false);
                                keyword.setStyle("-fx-text-fill: grey");
                                keyword.setText("default: 10 days. form YYYY-MM-DD");
                                //keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-keyword.getText().length());
                                keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-2);
                                searchItem.setVisible(false);
                                searchItem.setText("Enter expiry date: ");
                                searchItem.setLayoutX(keyword.getLayoutX()- searchItem.getText().length() - searchItem.getWidth() - 7);
                                searchItem.setTextFill(Color.CORAL);
                                searchItem.setVisible(true);
                                keyword.setVisible(true);
                                keyword.setOnAction(dateNamed);//enter hit
                        }
                };

                EventHandler<ActionEvent> commentNamed = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent commentNamed) {
                                System.out.println(keyword.getText().getClass());
                                if(keyword.getText().getClass().equals( "".getClass())){
                                        newGrocery[2] = keyword.getText().toLowerCase().trim().replaceAll("\\s","+");

                                }
                                else if (keyword.getText().length()==0){
                                        newGrocery[2]="null";
                                }
                                else{searchItem.setText("Re-enter"); keyword.clear(); return;}
                                keyword.clear();
                                keyword.setVisible(false);
                                keyword.setStyle("-fx-text-fill: grey");
                                keyword.setText(" default: 1. hit enter");
                                //keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-keyword.getText().length());
                                keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-2);
                                searchItem.setVisible(false);
                                searchItem.setText("Enter qty: ");
                                searchItem.setLayoutX(keyword.getLayoutX()- searchItem.getText().length() - searchItem.getWidth() - 5);
                                searchItem.setTextFill(Color.CORAL);
                                searchItem.setVisible(true);
                                keyword.setVisible(true);
                                keyword.setOnAction(qtyNamed); //enter hit

                        }
                };
                EventHandler<ActionEvent> categoryNamed = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent categoryNamed) {
                                if(keyword.getText().getClass().equals("".getClass()) && (keyword.getText().length()!=0)){
                                        newGrocery[1] = keyword.getText().toLowerCase().trim().replaceAll("\\s","+");
                                }else if (keyword.getText().length()==0){
                                        newGrocery[1]=("to be named").trim().replaceAll("\\s","+");
                                }else{searchItem.setText("Re-enter"); keyword.clear(); return;}
                                keyword.clear();
                                keyword.setVisible(false);
                                keyword.setStyle("-fx-text-fill: grey");
                                keyword.setText(" default: none. hit enter");
                                //keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-keyword.getText().length());
                                keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-2);
                                searchItem.setVisible(false);
                                searchItem.setText("Enter comment: ");
                                searchItem.setLayoutX(keyword.getLayoutX()- searchItem.getText().length() - searchItem.getWidth() - 5);
                                searchItem.setTextFill(Color.CORAL);
                                searchItem.setVisible(true);
                                keyword.setVisible(true);
                                keyword.setOnAction(commentNamed); // enter hit
                        }
                };
                EventHandler<ActionEvent> itemNamed = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent itemNamed) {
                                if(keyword.getText().getClass().equals("".getClass())){
                                        newGrocery[0] = keyword.getText().toLowerCase().trim().replaceAll("\\s","+");
                                }else{searchItem.setText("Re-enter"); keyword.clear(); return;}
                                keyword.clear();
                                keyword.setVisible(false);
                                keyword.setStyle("-fx-text-fill: grey");
                                keyword.setText("enter and hit enter");
                                //keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-keyword.getText().length());
                                keyword.setLayoutX(addToCart.getLayoutX()-keyword.getWidth()-2);
                                searchItem.setVisible(false);
                                searchItem.setText("Enter category: ");
                                searchItem.setLayoutX(keyword.getLayoutX()- searchItem.getText().length() - searchItem.getWidth() - 5);
                                searchItem.setTextFill(Color.CORAL);
                                searchItem.setVisible(true);
                                keyword.setVisible(true);
                                keyword.setOnAction(categoryNamed); //enter hit
                        }
                };

                //when enter is parsed
                keyword.setOnAction(itemNamed);
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
                this.toCart.setCellValueFactory(new PropertyValueFactory<>("toCart"));

                inventoryTable.setItems(data);
                /* initial filter list */
                filter();


        }
}