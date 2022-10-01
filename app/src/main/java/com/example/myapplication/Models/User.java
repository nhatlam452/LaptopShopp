package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class User {
    //phonenumber làm khóa chính
    @SerializedName("phonenumber")
    public String phoneNumber;
    @SerializedName("fullname")
    public String fullName;
    public String password;
    public String dob;
    @SerializedName("fbid")
    public String fbId;
    public String email;
    public int role;
    public int state;

    public User() {
    }

    public User(String phoneNumber, String fullName, String password, String dob, String fbId, String email, int role, int state) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.password = password;
        this.dob = dob;
        this.fbId = fbId;
        this.email = email;
        this.role = role;
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
