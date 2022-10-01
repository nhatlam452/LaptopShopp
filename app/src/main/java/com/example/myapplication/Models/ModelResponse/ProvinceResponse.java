package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.AddressDetail.Province;

import java.util.List;

public class ProvinceResponse {
    private String status;
    private String message;
    private List<Province> results;

    public ProvinceResponse() {
    }

    public ProvinceResponse(String status, String message, List<Province> results) {
        this.status = status;
        this.message = message;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Province> getResults() {
        return results;
    }

    public void setResults(List<Province> results) {
        this.results = results;
    }
}
