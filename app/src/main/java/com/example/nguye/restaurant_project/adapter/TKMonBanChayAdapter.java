package com.example.nguye.restaurant_project.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.TKMonBanChay;

import java.util.List;

/**
 * Created by nguye on 2/1/2018.
 */

public class TKMonBanChayAdapter extends ArrayAdapter<TKMonBanChay> {
    Activity context;
    int resource;
    List<TKMonBanChay> objects;

    public TKMonBanChayAdapter(@NonNull Activity context, int resource, @NonNull List<TKMonBanChay> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.items_tkmonbanchay,parent,false);
        TextView txtidMonBanChay = row.findViewById(R.id.txtTKIdMonBanChay);
        TextView txtTenMonBanChay = row.findViewById(R.id.txtTKTenMonBanChay);
        TextView txtslMonBanChay = row.findViewById(R.id.txtTKslMonBanChay);

        TKMonBanChay tkMonBanChay = this.objects.get(position);
        txtidMonBanChay.setText(Integer.toString(tkMonBanChay.getIdMonBanChay()));
        txtTenMonBanChay.setText(tkMonBanChay.getTenMonBanChay());
        txtslMonBanChay.setText(Integer.toString(tkMonBanChay.getGiaMonbanChay()));
        return row;
    }

}
