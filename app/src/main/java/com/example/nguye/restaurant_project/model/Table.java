package com.example.nguye.restaurant_project.model;

import java.io.Serializable;


public class Table implements Serializable {
    private int id;
    private String tenBan;
    private Boolean trangThai;
    private int khuVuc;
    private String tenKhuVuc;

    public Table() {
    }

    public Table(int id, String tenBan, boolean trangThai, int khuVuc) {
        this.id = id;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.khuVuc = khuVuc;
    }

    public Table(int id, String tenBan, Boolean trangThai, int khuVuc, String tenKhuVuc) {
        this.id = id;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.khuVuc = khuVuc;
        this.tenKhuVuc = tenKhuVuc;
    }


    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(int khuVuc) {
        this.khuVuc = khuVuc;
    }
}
