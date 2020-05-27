package com.example.nguye.restaurant_project.update_button;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;

import java.util.List;


public class AdapterGroupId extends ArrayAdapter<ModelGroupId> {
    Activity context;
    int resource;
    List<ModelGroupId> objects;
    public AdapterGroupId(Activity context, int resource, List<ModelGroupId> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(resource,parent,false);
        TextView txtGroupMenu = row.findViewById(R.id.txtspGroupMenuId);
        ModelGroupId modelGroupId = this.objects.get(position);
        txtGroupMenu.setText(modelGroupId.getGroupName());

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
