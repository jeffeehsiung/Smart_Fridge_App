package project;


import java.awt.*;
import javafx.scene.control.CheckBox;

public class grocery {
    private String id_col,item_col,category_col,expiryDate_col,qty_col,comment_col;
    private CheckBox toCart_col;


    public grocery(String id, String item, String category, String expiryDate, String qty, String comment) {
        this.id_col = id;
        this.item_col = item;
        this.category_col = category;
        this.expiryDate_col = expiryDate;
        this.qty_col = qty;
        this.comment_col = comment;
        this.toCart_col = new CheckBox();

    }

    public String getId_col() {
        return id_col;
    }

    public void setId_col(String id_col) {
        this.id_col = id_col;
    }

    public String getItem_col() {
        return item_col;
    }

    public void setItem_col(String item_col) {
        this.item_col = item_col;
    }

    public String getCategory_col() {
        return category_col;
    }

    public void setCategory_col(String category_col) {
        this.category_col = category_col;
    }

    public String getExpiryDate_col() {
        return expiryDate_col;
    }

    public void setExpiryDate_col(String expiryDate_col) {
        this.expiryDate_col = expiryDate_col;
    }

    public String getQty_col() {
        return qty_col;
    }

    public void setQty_col(String qty_col) {
        this.qty_col = qty_col;
    }

    public String getComment_col() {
        return comment_col;
    }

    public void setComment_col(String comment_col) {
        this.comment_col = comment_col;
    }

    public CheckBox getToCart() {return toCart_col;}

    public void setToCart(CheckBox toCart_col) {this.toCart_col = toCart_col;}
}
