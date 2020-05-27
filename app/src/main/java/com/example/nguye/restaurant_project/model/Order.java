package com.example.nguye.restaurant_project.model;

import java.io.Serializable;


public class Order implements Serializable {
    private int id;
    private String ngayLap;
    private int tongTien;
    private boolean trangThai;
    private int maBan;
    private String tenBan;
    private int maNV;
    private String tenNV;
    private int maMon;
    private String tenMon;
    private int donGia;
    private int SoLuong;
    private  int maKhuVuc;
    private String tenKhuVuc;

    public Order() {
    }

    public Order(int id, int tongTien, String tenBan, String tenKhuVuc, int maBan, int maKhuVuc){
        this.id=id;
        this.tongTien=tongTien;
        this.tenBan=tenBan;
        this.tenKhuVuc=tenKhuVuc;
        this.maBan=maBan;
        this.maKhuVuc=maKhuVuc;
    }

    public Order(int maMon,String tenMon,int donGia,int soLuong) {
        this.maMon=maMon;
        this.tenMon = tenMon;
        this.donGia=donGia;
        SoLuong = soLuong;
    }

    public Order(int id, String ngayLap, int tongTien, boolean trangThai, int maBan, int maNV, String tenNV, int maMon, String tenMon,int donGia, int soLuong) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.maBan = maBan;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.donGia=donGia;
        SoLuong = soLuong;
    }
    public Order(String ngayLap,int manv, int tongTien){
        this.ngayLap=ngayLap;
        this.maNV=manv;
        this.tongTien=tongTien;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
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

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(int maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }
}

