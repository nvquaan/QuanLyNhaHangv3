package com.example.nguye.restaurant_project.model;

import java.io.Serializable;


public class DoanhThuNhanVien implements Serializable {
    int manv;
    String tenNV;
    int tongTien;

    public DoanhThuNhanVien() {
    }

    public DoanhThuNhanVien(int manv,String tenNV, int tongTien) {
        this.manv=manv;
        this.tenNV = tenNV;
        this.tongTien = tongTien;
    }

    public String getTenNV() {
        return tenNV;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
