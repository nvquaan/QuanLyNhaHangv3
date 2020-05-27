package com.example.nguye.restaurant_project.update_button;

import java.io.Serializable;


public class ModelGroupId implements Serializable {
    private int IdGroupMenu;
    private String GroupName;

    public int getIdGroupMenu() {
        return IdGroupMenu;
    }

    public void setIdGroupMenu(int idGroupMenu) {
        IdGroupMenu = idGroupMenu;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public ModelGroupId() {

    }

    public ModelGroupId(int idGroupMenu, String groupName) {

        IdGroupMenu = idGroupMenu;
        GroupName = groupName;
    }
}
