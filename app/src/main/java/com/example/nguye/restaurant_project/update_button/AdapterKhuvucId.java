package com.example.nguye.restaurant_project.update_button;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;

import java.util.List;


public class AdapterKhuvucId extends ArrayAdapter<ModelKhuvucId> {
    Activity context;
    int resource;
    List<ModelKhuvucId> objects;
    public AdapterKhuvucId(@NonNull Activity context, int resource, @NonNull List<ModelKhuvucId> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(resource,parent,false);
        TextView txt = row.findViewById(R.id.txtspinnerkhuvucid);
        ModelKhuvucId modelKhuvucId = this.objects.get(position);
        txt.setText(modelKhuvucId.getTenKhuVuc());

        return  row;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
