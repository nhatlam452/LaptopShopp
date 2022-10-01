package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Cart  extends Products{
    @SerializedName("cartid")
    private String cartID;
    @SerializedName("phonenumber")
    private String phoneNumber;
    private String quantity;
    private float totalPrice;


    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart(String cartID, String quantity) {
        this.cartID = cartID;
        this.quantity = quantity;
    }

    public Cart(String productID, String cartID, String phoneNumber) {
        super(productID);
        this.cartID = cartID;
        this.phoneNumber = phoneNumber;
    }

    public Cart(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public Cart(String productID, String productName, String price, String url, String cartID, String phoneNumber, String quantity) {
        super(productID, productName, price, url);
        this.cartID = cartID;
        this.phoneNumber = phoneNumber;
        this.quantity = quantity;
    }

    public Cart(String productID, String cartID, String phoneNumber, String quantity) {
        super(productID);
        this.cartID = cartID;
        this.phoneNumber = phoneNumber;
        this.quantity = quantity;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
