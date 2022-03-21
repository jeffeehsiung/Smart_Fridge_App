package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

public class mysqlConnector {

    protected String serverURL = "https://studev.groept.be/api/a21ib2a01";
    protected String serverName;
    protected  String searchName;


    public String makeGETRequest(String serverName){
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        this.serverName = serverName;
        String line;
        try {
            URL url = new URL(serverURL+"/"+serverName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            conn.disconnect();
            return stringBuilder.toString();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "pageCannotBeFound";

    }
    public String makeGETRequest(String serverName, String searchName){
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        this.serverName = serverName;
        this.searchName = searchName;
        String line;
        try {
            URL url = new URL(serverURL+"/"+serverName+"/"+searchName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            conn.disconnect();
            return stringBuilder.toString();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "pageCannotBeFound";

    }

    public JSONArray parseIntoJSONarray (String jsonString){
        JSONArray dataArrary = new JSONArray();
        //parse all user into array
        try{
            dataArrary = new JSONArray(jsonString);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return dataArrary;
    }


    public static void main(String[] args) {
        mysqlConnector mysqlConnector = new mysqlConnector();
        String response = mysqlConnector.makeGETRequest("inventory");
        System.out.println(mysqlConnector.parseIntoJSONarray(response));
    }
}
