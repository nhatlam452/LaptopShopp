package com.example.myapplication.Models;

public class ProductDetail extends Products{
    private String cpu;
    private String gpu;
    private String ram;
    private String screen;
    private String disk;
    private String system;
    private String battery;
    private String weight;

    public String getWeight() {
        return weight;
    }

    public ProductDetail() {
    }
    public ProductDetail(String productID){
        super(productID);
    }
    public ProductDetail(String productID, String productName, String price, String state, String brand, String url, String cpu, String gpu, String ram, String screen, String disk, String system, String battery,String weight) {
        super(productID, productName, price, state, brand, url);
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.screen = screen;
        this.disk = disk;
        this.system = system;
        this.battery = battery;
        this.weight = weight;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public String getRam() {
        return ram;
    }

    public String getScreen() {
        return screen;
    }

    public String getDisk() {
        return disk;
    }

    public String getSystem() {
        return system;
    }

    public String getBattery() {
        return battery;
    }
}
