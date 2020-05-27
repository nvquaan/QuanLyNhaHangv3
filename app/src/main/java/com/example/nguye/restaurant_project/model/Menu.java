package com.example.nguye.restaurant_project.model;

import android.graphics.Bitmap;

import java.io.Serializable;


public class Menu implements Serializable {
    public int txtIdGroup;
    private String txtTenMonAn;
    private Bitmap btnAnh;

    public int getTxtIdGroup() {
        return txtIdGroup;
    }

    public void setTxtIdGroup(int txtIdGroup) {
        this.txtIdGroup = txtIdGroup;
    }

    public String getTxtTenMonAn() {
        return txtTenMonAn;
    }

    public void setTxtTenMonAn(String txtTenMonAn) {
        this.txtTenMonAn = txtTenMonAn;
    }

    public Bitmap getBtnAnh() {
        return btnAnh;
    }

    public void setBtnAnh(Bitmap btnAnh) {
        this.btnAnh = btnAnh;
    }

    public Menu() {

    }

    public Menu(int txtIdGroup, String txtTenMonAn) {
        this.txtIdGroup = txtIdGroup;
        this.txtTenMonAn = txtTenMonAn;
    }

    public Menu(int txtIdGroup, String txtTenMonAn, Bitmap btnAnh) {

        this.txtIdGroup = txtIdGroup;
        this.txtTenMonAn = txtTenMonAn;
        this.btnAnh = btnAnh;
    }
}
