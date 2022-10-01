package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {
    @SerializedName("respone_code")
    private int responseCode;
    private String error;
    private String message;
    private List<Cart> data;

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

    public List<Cart> getData() {
        return data;
    }

    public void setData(List<Cart> data) {
        this.data = data;
    }

    public CartResponse(int responseCode, String error, String message, List<Cart> data) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public CartResponse() {
    }
}
