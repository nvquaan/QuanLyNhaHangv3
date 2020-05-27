package com.example.nguye.restaurant_project.update_button;

import java.io.Serializable;

public class ModelKhuvucId implements Serializable {
    private int id;
    private String TenKhuVuc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhuVuc() {
        return TenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        TenKhuVuc = tenKhuVuc;
    }

    public ModelKhuvucId() {

    }

    public ModelKhuvucId(int id, String tenKhuVuc) {

        this.id = id;
        TenKhuVuc = tenKhuVuc;
    }
}
