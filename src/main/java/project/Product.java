package project;

import javafx.scene.control.CheckBox;

public class Product {
    public String name;
    public String qty;
    public CheckBox itemBox;

    public Product(String name, String qty) {
        this.name = name;
        this.qty = qty;
        this.itemBox = new CheckBox();
    }

    public String getName() {
        return this.name;
    }

    public String getQty() {
        return this.qty;
    }

    public CheckBox getItemBox() {
        return itemBox;
    }

    public void setItemBox(CheckBox itemBox) {
        this.itemBox = itemBox;
    }
}