package com.example.nguye.restaurant_project.model;

import java.io.Serializable;

public class KhuVuc implements Serializable {
    private int id;
    private String tenKhuVuc;

    public KhuVuc() {
    }

    public KhuVuc(int id, String tenKhuVuc) {
        this.id = id;
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }
}
