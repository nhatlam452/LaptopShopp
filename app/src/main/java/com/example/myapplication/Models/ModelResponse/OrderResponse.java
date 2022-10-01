package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("respone_code")
    private int responseCode;
    private String error;
    private String message;
    private List<Order> data;

    public OrderResponse() {
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }

    public OrderResponse(int responseCode, String error, String message, List<Order> data) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
        this.data = data;
    }
}
