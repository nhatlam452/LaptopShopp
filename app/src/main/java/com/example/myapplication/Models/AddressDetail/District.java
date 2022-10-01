package com.example.myapplication.Models.AddressDetail;

public class District {
    private String code;
    private String name;
    private String province;

    public District() {
    }

    public District(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public District(String code, String name, String province) {
        this.code = code;
        this.name = name;
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
