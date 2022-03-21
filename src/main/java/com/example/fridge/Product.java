package com.example.fridge;

public class Product {
    public String name;
    public String qty;

    public Product(String name, String qty){
        this.name = name;
        this.qty = qty;
    }
    public String getName(){
        return this.name;
    }
    public String getQty(){
        return this.qty;
    }
}