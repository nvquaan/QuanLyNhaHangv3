package com.example.nguye.restaurant_project.model;

import java.io.Serializable;


public class TKMonBanChay implements Serializable {
    private int idMonBanChay;
    private String tenMonBanChay;
    private int slMonBanChay;

    public int getIdMonBanChay() {
        return idMonBanChay;
    }

    public void setIdMonBanChay(int idMonBanChay) {
        this.idMonBanChay = idMonBanChay;
    }

    public String getTenMonBanChay() {
        return tenMonBanChay;
    }

    public void setTenMonBanChay(String tenMonBanChay) {
        this.tenMonBanChay = tenMonBanChay;
    }

    public int getGiaMonbanChay() {
        return slMonBanChay;
    }

    public void setGiaMonbanChay(int giaMonbanChay) {
        this.slMonBanChay = giaMonbanChay;
    }

    public TKMonBanChay() {

    }

    public TKMonBanChay(int idMonBanChay, String tenMonBanChay, int giaMonbanChay) {

        this.idMonBanChay = idMonBanChay;
        this.tenMonBanChay = tenMonBanChay;
        this.slMonBanChay = giaMonbanChay;
    }
}
