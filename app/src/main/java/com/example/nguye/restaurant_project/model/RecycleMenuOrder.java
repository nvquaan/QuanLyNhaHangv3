package com.example.nguye.restaurant_project.model;

import java.io.Serializable;


public class RecycleMenuOrder implements Serializable {
    int idRecycleMenuOrder;
    String tenRecycleMenuOrder;

    public int getIdRecycleMenuOrder() {
        return idRecycleMenuOrder;
    }

    public void setIdRecycleMenuOrder(int idRecycleMenuOrder) {
        this.idRecycleMenuOrder = idRecycleMenuOrder;
    }

    public String getTenRecycleMenuOrder() {
        return tenRecycleMenuOrder;
    }

    public void setTenRecycleMenuOrder(String tenRecycleMenuOrder) {
        this.tenRecycleMenuOrder = tenRecycleMenuOrder;
    }

    public RecycleMenuOrder() {

    }

    public RecycleMenuOrder(int idRecycleMenuOrder, String tenRecycleMenuOrder) {

        this.idRecycleMenuOrder = idRecycleMenuOrder;
        this.tenRecycleMenuOrder = tenRecycleMenuOrder;
    }
}
