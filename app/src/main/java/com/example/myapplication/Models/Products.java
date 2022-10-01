package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("productid")
    private String productID;
    @SerializedName("productname")
    private String productName;
    private String price;
    private String state;
    @SerializedName("categoryname")
    private String brand;
    private String url;

    public Products() {
    }

    public Products(String productID, String productName, String price, String url) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.url = url;
    }

    public Products(String productID) {
        this.productID = productID;
    }

    public Products(String productID, String productName, String price, String  state, String brand, String url) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.state = state;
        this.brand = brand;
        this.url = url;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
