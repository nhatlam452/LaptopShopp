package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("orderid")
    private String orderID;
    @SerializedName("phonenumber")
    private String phoneNumber;
    private String state;
    private String total;
    @SerializedName("createdate")
    private String createDate;
    private String note;

    public Order() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order(String orderID, String phoneNumber, String state, String total, String createDate, String note) {
        this.orderID = orderID;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.total = total;
        this.createDate = createDate;
        this.note = note;
    }
}
