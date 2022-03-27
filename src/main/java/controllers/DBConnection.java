package controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import project.Product;
import project.User;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

//To lecturer: comments are for team members, in case we have to make something similar in the second semester

//References: for a minor error I've made, I made a Stackoverflow-thread and implemented the answer into the program
// https://stackoverflow.com/questions/70306313/how-to-properly-add-data-from-string-to-arrayliststring/70307438#70307438


public class DBConnection {


    public List<User> dataListU = new ArrayList<>();
    public List<Object> dataListP = new ArrayList<>();
    public String makeGETRequest(String urlName){                       //URL handling, explained in theory video
        BufferedReader rd;
        StringBuilder sb;
        String line;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public List<Object> parseJSONProducts(String jsonString){
        try {
            dataListP = new ArrayList<>();
            JSONArray array = new JSONArray(jsonString);            //every time button is pressed create read new data (obvious)
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                if(!(curObject).isNull("Product"))
                {
                    Product product = new Product(curObject.getString("Product"), curObject.getString("Quantity"));
                    dataListP.add(product);
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return dataListP;
    }
    public List<User> parseJSONUsers(String jsonString){
        try {
            JSONArray array = new JSONArray(jsonString);            //every time button is pressed create read new data (obvious)
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                User user = new User(curObject.getString("username"), curObject.getString("password"), curObject.getString("notes"));
                dataListU.add(user);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return dataListU;
    }
}
//not really a 2D arraylist, but an arraylist with arrays in it. Which elements are strings, but the idea is the same