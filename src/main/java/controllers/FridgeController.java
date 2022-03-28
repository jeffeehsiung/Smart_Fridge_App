package controllers;

import project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FridgeController extends mysqlConnector implements Initializable {

        //pages
        @FXML
        private Button fridgeBut;
        @FXML
        private Button recipeBut;
        @FXML
        private Button inventoryBut;
        @FXML
        private Button shopListBut;
        @FXML
        private HBox sensorPage;

        //humidity
        @FXML
        private Label hum;
        @FXML
        private Label humSignal;
        @FXML
        private Label humVal;

        //ldr
        @FXML
        private Label ldr;
        @FXML
        private Label lightSignal;
        @FXML
        private Label lightVal;

        //temperature
        @FXML
        private Label temp;
        @FXML
        private Label tempSignal;
        @FXML
        private Label tempVal;

        //weight
        @FXML
        private Label weight;
        @FXML
        private Label weightVal;
        @FXML
        private Label weightSignal;

        private String[] sensorName = {"Light","Temp","Humid","Weight"};

        public String[] readSensorValue(String jsonString){
                String[] sensorVal = null;
                String keyValue = "sensVal";
                String minorNotes = "sensUnit";
                JSONArray array = new JSONArray(jsonString);
                for (int i = 0; i < array.length(); i++)
                {
                        JSONObject curObject = array.getJSONObject(i);
                        sensorVal =new String[]{String.valueOf(Math.round(curObject.getDouble(keyValue))),curObject.getString(minorNotes)};
                }
                return sensorVal;
        }
        public double getSensorValue(String sensorName){
                String response = makeGETRequest("readSensorValue",sensorName);
                Double sensorVal = Double.parseDouble(readSensorValue(response)[0]);
                return sensorVal;
        }
        public void setSensorVal() {
                Label[] labels = {lightVal,tempVal,humVal,weightVal};
                for (int i = 0; i<labels.length; i++){
                        String response = makeGETRequest("readSensorValue",sensorName[i]);
                        labels[i].setText(readSensorValue(response)[0]+" "+readSensorValue(response)[1]);
                }
        }
        public void setSensorSignal() {
                Label[] messages = {lightSignal,tempSignal,humSignal,weightSignal};
                for(int i = 0; i<messages.length; i++)
                if ( getSensorValue(sensorName[i]) > 200){
                        messages[i].setText("WARNING");
                }
                else{messages[i].setText("\t");}
        }
        @FXML
        void showSensorValue(ActionEvent event) {setSensorVal();setSensorSignal();}

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
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
