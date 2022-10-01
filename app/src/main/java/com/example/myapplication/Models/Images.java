package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("imageid")
    private String imageID;
    @SerializedName("productid")
    private String productID;
    private String url;

    public Images() {
    }

    public Images(String imageID, String productID, String url) {
        this.imageID = imageID;
        this.productID = productID;
        this.url = url;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
