package com.example.myapplication.Models.AddressDetail;

public class Commune {
    private String code;
    private String name;
    private String district;
    private String province;

    public Commune() {
    }

    public Commune(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Commune(String code, String name, String district, String province) {
        this.code = code;
        this.name = name;
        this.district = district;
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
}
