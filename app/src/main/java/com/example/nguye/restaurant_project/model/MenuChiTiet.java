package com.example.nguye.restaurant_project.model;

import android.graphics.Bitmap;

import java.io.Serializable;


public class MenuChiTiet implements Serializable {
    public int idMonAn;
    private int idGroup;
    private String tenMon;
    private int donGia;
    private Bitmap anh;
    private int soluong;



    public MenuChiTiet() {
    }

    public MenuChiTiet(int idMonAn, int idGroup, String tenMon, int donGia, int soluong) {
        this.idMonAn = idMonAn;
        this.idGroup = idGroup;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.soluong = soluong;
    }

    public MenuChiTiet(int idMonAn, String tenMon, Bitmap anh){
        this.idMonAn = idMonAn;
        this.tenMon = tenMon;
        this.anh = anh;
    }

    public MenuChiTiet(int idMonAn, int idGroup, String tenMon, int donGia, Bitmap anh) {
        this.idMonAn = idMonAn;
        this.idGroup = idGroup;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.anh = anh;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public Bitmap getAnh() {
        return anh;
    }

    public void setAnh(Bitmap anh) {
        this.anh = anh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
