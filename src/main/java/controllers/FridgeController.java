package controllers;

import javafx.scene.paint.Color;
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
import java.time.LocalDate;
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
        @FXML
        private Label humMax;
        @FXML
        private Label humMaxDate;
        @FXML
        private Label humMin;
        @FXML
        private Label humMinDate;

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
        @FXML
        private Label tempMax;
        @FXML
        private Label tempMaxDate;
        @FXML
        private Label tempMin;
        @FXML
        private Label tempMinDate;

        //weight
        @FXML
        private Label weight;
        @FXML
        private Label weightVal;
        @FXML
        private Label weightSignal;

        public JSONArray readResponseArray(){
                String response = makeGETRequest("sensorValue");
                JSONArray ResponseArray = parseIntoJSONarray(response);
                return ResponseArray;
        }
        public void readSetAllSensorInfo(JSONArray ResponseArray){
                String[] sensQty = {"Light","Temp","Humid","Weight"};
                Label[] sensID = {ldr,temp,hum,weight};
                Label[] values = {lightVal,tempVal,humVal,weightVal};
                Label[] messages = {lightSignal,tempSignal,humSignal,weightSignal};
                Label[] maxLabel = {tempMax,humMax};
                Label[] minLabel = {tempMin,humMin};
                Label[] maxDateLabel = {tempMaxDate,humMaxDate};
                Label[] minDateLabel= {tempMinDate,humMinDate};

                String[] sensorVal = null;
                String sensName = "sensQty";
                String sensVal = "sensVal";
                String sensUnit = "sensUnit";
                String notes = "notes";
                String max = "max";
                String maxDate = "maxDate";
                String min = "min";
                String minDate = "minDate";

                for (int i = 0; i < ResponseArray.length(); i++)
                {
                        JSONObject curObject = ResponseArray.getJSONObject(i);
                        sensID[i].setText(curObject.getString(sensName));
                        sensorVal = new String[]{String.valueOf(Math.round(curObject.getDouble(sensVal))),curObject.getString(sensUnit)};
                        values[i].setText(sensorVal[0]+" "+sensorVal[1]);
                        if(curObject.isNull(notes))
                        {
                                messages[i].setVisible(false);
                        }
                        else{
                                messages[i].setText(curObject.getString(notes));
                                double posX = messages[i].getLayoutX();
                                double originalWidth = messages[i].getWidth();
                                messages[i].setLayoutX(posX-(messages[i].getText().length()-originalWidth));
                                messages[i].setPrefWidth(messages[i].getText().length());
                                messages[i].setTextFill(Color.HOTPINK);
                                messages[i].setVisible(true);
                        }
                        if(0 < i && i < 3){
                                if(!(curObject.isNull(max))){
                                        System.out.println("max not null");
                                        maxLabel[i-1].setText("max:"+ curObject.getString(max));
                                        maxDateLabel[i-1].setText(curObject.getString(maxDate));
                                        maxLabel[i-1].setTextFill(Color.GOLD);
                                        maxDateLabel[i-1].setTextFill(Color.GOLD);
                                        maxLabel[i-1].setVisible(true);
                                        maxDateLabel[i-1].setVisible(true);                                }
                                else {System.out.println("max null");maxLabel[i - 1].setText(curObject.getString(sensVal));maxDateLabel[i-1].setText(LocalDate.now().toString());maxLabel[i-1].setVisible(false);maxDateLabel[i-1].setVisible(false);}
                                if(!(curObject.isNull(min))){
                                        System.out.println("min not null");
                                        minLabel[i-1].setText("min:"+ curObject.getString(min));
                                        minDateLabel[i-1].setText(curObject.getString(minDate));
                                        minLabel[i-1].setTextFill(Color.GOLD);
                                        minDateLabel[i-1].setTextFill(Color.GOLD);
                                        minLabel[i-1].setVisible(true);
                                        minDateLabel[i-1].setVisible(true);
                                }
                                else {System.out.println("min null");minLabel[i - 1].setText(curObject.getString(sensVal));minDateLabel[i-1].setText(LocalDate.now().toString());minLabel[i-1].setVisible(false);minDateLabel[i-1].setVisible(false);}
                        }
                }
        }

        public void registerMinMax(JSONArray ResponseArray){
                String serverName = "updateMinMax";
                String sensName = "sensQty";
                String sensVal = "sensVal";
                String max = "max";
                String maxDate = "maxDate";
                String min = "min";
                String minDate = "minDate";

                double maxVal = 0;
                double minVal = 0;
                LocalDate maxDateVal = LocalDate.now();
                LocalDate minDateVal = LocalDate.now();

                for (int i = 0; i < ResponseArray.length(); i++){
                        JSONObject curObject = ResponseArray.getJSONObject(i);
                        if(curObject.isNull(max)){
                                maxVal = Math.round(curObject.getDouble(sensVal));
                                maxDateVal = LocalDate.now();
                        }
                        else if(!curObject.isNull(max)){
                                if(curObject.getDouble(max) < curObject.getDouble(sensVal)){
                                        maxVal = Math.round(curObject.getDouble(sensVal));
                                        maxDateVal = LocalDate.now();

                                }
                                else if(curObject.getDouble(max) >= curObject.getDouble(sensVal)){
                                        maxVal = curObject.getDouble(max);
                                        maxDateVal = LocalDate.parse(curObject.getString(maxDate));
                                }
                        }
                        if(curObject.isNull(min)){
                                minVal = Math.round(curObject.getDouble(sensVal));
                                minDateVal = LocalDate.now();
                        }
                        else if(!curObject.isNull(min)){
                                if(curObject.getDouble(min) > curObject.getDouble(sensVal)){
                                        minVal = Math.round(curObject.getDouble(sensVal));
                                        minDateVal = LocalDate.now();
                                }
                                else if(curObject.getDouble(min) <= curObject.getDouble(sensVal)){
                                        minVal = curObject.getDouble(min);
                                        minDateVal = LocalDate.parse(curObject.getString(minDate));
                                }
                        }
                        String url = serverName + "/" + maxVal + "/" + minVal+ "/" + maxDateVal + "/" + minDateVal + "/" +curObject.getString(sensName);
                        System.out.println(url);
                        makeGETRequest(url);
                }
        }
        @FXML
        void showSensorValue(ActionEvent event) {
                JSONArray responseArray = readResponseArray();
                readSetAllSensorInfo(responseArray);
                registerMinMax(responseArray);

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
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
