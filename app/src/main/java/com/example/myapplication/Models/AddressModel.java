package com.example.myapplication.Models;

public class AddressModel {
    private String addressid;
    private String location;
    private String commune;
    private String district;
    private String province;
    private String phonenumber;
    private String addressdefault;

    public AddressModel() {
    }

    public AddressModel(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public AddressModel(String addressid, String location, String commune, String district, String province, String phonenumber, String addressdefault) {
        this.addressid = addressid;
        this.location = location;
        this.commune = commune;
        this.district = district;
        this.province = province;
        this.phonenumber = phonenumber;
        this.addressdefault = addressdefault;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddressdefault() {
        return addressdefault;
    }

    public void setAddressdefault(String addressdefault) {
        this.addressdefault = addressdefault;
    }
}
