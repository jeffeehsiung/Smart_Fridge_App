package controllers;

import com.example.fridge.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class fridgeInfo implements Initializable {

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

        @FXML
        void showSensorValue(ActionEvent event) {
                setSensorVal();
                setSensorSignal();
        }

        public void toInventory(ActionEvent event) throws IOException {
                Main m = new Main();
                m.changeScene("inventory.fxml");
        }

        private mysqlConnector sql = new mysqlConnector();
        private String[] sensorName = {"Light","Temp","Humid","Weight"};

        public double getSensorValue(String sensorName){
                String response = sql.makeGETRequest("sensorValue",sensorName);
                Double sensorVal = Double.parseDouble(sql.readSensorValue(response)[0]);
                return sensorVal;
        }

        public void setSensorVal() {
                Label[] labels = {lightVal,tempVal,humVal,weightVal};
                for (int i = 0; i<labels.length; i++){
                        String response = sql.makeGETRequest("sensorValue",sensorName[i]);
                        labels[i].setText(sql.readSensorValue(response)[0]+"\t"+sql.readSensorValue(response)[1]);
                }
        }
        public void setSensorSignal() {
                Label[] messages = {lightSignal,tempSignal,humSignal,weightSignal};
                for(int i = 0; i<messages.length; i++)
                if ( getSensorValue(sensorName[i]) > 10){
                        messages[i].setText("WARNING");
                }
                else{messages[i].setText("\t");}
        }


        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
