package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.AddressDetail.District;

import java.util.List;

public class DistrictResponse {
    private String status;
    private String message;
    private List<District> results;

    public DistrictResponse() {
    }

    public DistrictResponse(String status, String message, List<District> results) {
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

    public List<District> getResults() {
        return results;
    }

    public void setResults(List<District> results) {
        this.results = results;
    }
}
