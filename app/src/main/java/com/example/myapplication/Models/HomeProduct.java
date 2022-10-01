package com.example.myapplication.Models;

import java.util.List;

public class HomeProduct {
    private String title;
    private List<Products> mList;

    public HomeProduct(String title, List<Products> mList) {
        this.title = title;
        this.mList = mList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Products> getmList() {
        return mList;
    }

    public void setmList(List<Products> mList) {
        this.mList = mList;
    }
}
