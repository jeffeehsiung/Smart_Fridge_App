package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import project.Main;
import project.Product;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class RecipeController implements Initializable  {
    private Integer pointerDish = 0;
    private Integer recipeAmount;
    @FXML private Button buttD1;
    @FXML private Button buttD2;
    @FXML private Button buttD3;
    @FXML private Button buttMeal;
    @FXML private Button buttInv;
    @FXML private Button buttList;
    @FXML private ImageView imageMeal;
    @FXML private ImageView imageInv;
    @FXML private ImageView imageList;
    @FXML private Button buttSlideLeft;
    @FXML private Button buttSlideRight;
    @FXML private ImageView imageD1;
    @FXML private ImageView imageD2;
    @FXML private ImageView imageD3;
    @FXML private ImageView background_recipe;
    @FXML private Label lblInfD1;
    @FXML private Label lblInfD2;
    @FXML private Label lblInfD3;
    @FXML private Label lblTitD1;
    @FXML private Label lblTitD2;
    @FXML private Label lblTitD3;
    @FXML private Rating rateD1;
    @FXML private Rating rateD2;
    @FXML private Rating rateD3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML void goDish1(ActionEvent event) {
        System.out.println("D1");
    }

    public Integer getTableSize(){
        DBConnection db = new DBConnection();
        Integer size = null;

        try {
            String JSOData = db.makeGETRequest("https://studev.groept.be/api/a21ib2a01/getTableSize");
            JSONArray JSON = new JSONArray(JSOData);
            size = JSON.getJSONObject(0).getInt("size");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return size;
    }

    @FXML void goDish2(ActionEvent event) {
        System.out.println("D2");
    }
    @FXML void goDish3(ActionEvent event) {
        System.out.println("D3");
    }
    void loadImage(Integer pointer, ImageView imView){
        switch (pointer){
            case 0:
                File file_0 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\spaghetti-meatballs-500.jpg");
                Image image_0 = new Image(file_0.toURI().toString());
                imView.setImage(image_0);
                break;
            case 1:
                File file_1 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\81317-macaroni-met-kaas-en-ham.jpg");
                Image image_1 = new Image(file_1.toURI().toString());
                imView.setImage(image_1);
                break;
            case 2:
                File file_2 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\Dürüm-kip-kebab-3.jpg");
                Image image_2  = new Image(file_2.toURI().toString());
                imView.setImage(image_2 );
                break;
            case 3:
                File file_3 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\loempia_recept-scaled-1200x0-c-default-1024x0-c-default.jpg");
                Image image_3 = new Image(file_3.toURI().toString());
                imView.setImage(image_3);
                break;
            case 4:File file_4 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\klassieke-frieten-500.jpg");
                Image image_4 = new Image(file_4.toURI().toString());
                imView.setImage(image_4);
                break;
            case 5:
                File file_5 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\indische-kip-curry-met-appel-en-rijst_.jpg");
                Image image_5 = new Image(file_5.toURI().toString());
                imView.setImage(image_5);
                break;
            case 6:
                File file_6 = new File("C:\\Users\\aerts\\Downloads\\Fridge\\src\\main\\resources\\project\\picture\\arabische-soep-met-falafelspiesen-75a18e9d.jpg");
                Image image_6 = new Image(file_6.toURI().toString());
                imView.setImage(image_6);
                break;
        }

    }
    private Integer handlePointer(Integer pointer){
        pointer = pointer%getTableSize();
        if (pointer< 0) pointer = getTableSize();
        return pointer;
    }
    @FXML void slideLeft(ActionEvent event) {
          pointerDish--;





          loadImage(handlePointer(pointerDish), imageD1);
        DBConnection db = new DBConnection();
        JSONArray JSONData = new JSONArray(db.makeGETRequest("https://studev.groept.be/api/a21ib2a01/getRecipe"));
        JSONObject curObject = JSONData.getJSONObject(pointerDish);
        lblTitD1.setText(curObject.getString("name"));
        lblInfD1.setText(curObject.getString("disc"));
        System.out.println(handlePointer(pointerDish));
          loadImage(handlePointer(pointerDish+1), imageD2);
        curObject = JSONData.getJSONObject(pointerDish);
        lblTitD2.setText(curObject.getString("name"));
        lblInfD2.setText(curObject.getString("disc"));
        System.out.println(handlePointer(pointerDish+1));
          loadImage(handlePointer(pointerDish+2), imageD3);
        curObject = JSONData.getJSONObject(pointerDish);
        lblTitD3.setText(curObject.getString("name"));
        lblInfD3.setText(curObject.getString("disc"));
        System.out.println(handlePointer(pointerDish+2));

    }
    @FXML void slideRight(ActionEvent event) {
            pointerDish++;
        try {
            DBConnection db = new DBConnection();
            JSONArray JSONData = new JSONArray(db.makeGETRequest("https://studev.groept.be/api/a21ib2a01/getRecipe"));
            JSONObject curObject = JSONData.getJSONObject(handlePointer(pointerDish));
            lblTitD1.setText(curObject.getString("name"));
            lblTitD1.setText(curObject.getString("disc"));
            curObject = JSONData.getJSONObject(handlePointer(pointerDish+1));
            lblTitD2.setText(curObject.getString("name"));
            lblTitD2.setText(curObject.getString("disc"));
            curObject = JSONData.getJSONObject(handlePointer(pointerDish+2));
            lblTitD3.setText(curObject.getString("name"));
            lblTitD3.setText(curObject.getString("disc"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
            loadImage(handlePointer(pointerDish), imageD1);
        System.out.println(handlePointer(pointerDish));
            loadImage(handlePointer(pointerDish+1), imageD2);
        System.out.println(handlePointer(pointerDish+1));
            loadImage(handlePointer(pointerDish+2), imageD3);
        System.out.println(handlePointer(pointerDish+2));
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
}
