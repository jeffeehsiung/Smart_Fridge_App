
package com.example.project;
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
    protected String userkey = "username";
    protected String passwordvalue = "password";
    protected boolean loginSucceed = false;
    protected boolean userExist = false;
    protected boolean pwdCorrect = false;

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

    public String[] readSensorValue(String jsonString){
        String[] sensorVal = null;
        String keyValue = "sensVal";
        String minorNotes = "sensUnit";

        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                sensorVal =new String[]{curObject.getString(keyValue),curObject.getString(minorNotes)};
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return sensorVal;
    }


    public boolean compareUserDB (String username, String password, String serverName){
        JSONArray dbJSON = parseIntoJSONarray(makeGETRequest(serverName));
        //iterate and compare to JSONarray
        try{
            for (int i = 0; i < dbJSON.length(); i++)
            {
                JSONObject curObject = dbJSON.getJSONObject(i);
                if (username.equals(curObject.getString(userkey))){
                    userExist = true; //userName incorrect, userExist remains false
                    if(password.equals(curObject.getString(passwordvalue))){
                        pwdCorrect = true; //password incorrect, pwdCorrect remains false
                        loginSucceed = true;
                    }
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return loginSucceed;
    }

    public JSONArray parseIntoJSONarray (String jsonString){
        JSONArray dataArray = new JSONArray();
        //parse all user into array
        try{
            dataArray = new JSONArray(jsonString);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return dataArray;
    }


    public static void main(String[] args) {
        mysqlConnector mysqlConnector = new mysqlConnector();
        String response = mysqlConnector.makeGETRequest("readUser");
        System.out.println(mysqlConnector.parseIntoJSONarray(response));
    }
}
