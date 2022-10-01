package com.example.myapplication.Models.ModelResponse;

import com.example.myapplication.Models.AddressDetail.Commune;

import java.util.List;

public class CommuneResponse {
    private String status;
    private String message;
    private List<Commune> results;

    public CommuneResponse() {
    }

    public CommuneResponse(String status, String message, List<Commune> results) {
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

    public List<Commune> getResults() {
        return results;
    }

    public void setResults(List<Commune> results) {
        this.results = results;
    }
}
