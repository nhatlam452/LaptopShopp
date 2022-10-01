package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.Images;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {
    @SerializedName("respone_code")
    private int responseCode;
    private String error;
    private String message;
    private List<Images> data;

    public ImagesResponse() {
    }

    public ImagesResponse(int responseCode, String error, String message, List<Images> data) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
        this.data = data;
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

    public List<Images> getData() {
        return data;
    }

    public void setData(List<Images> data) {
        this.data = data;
    }
}
