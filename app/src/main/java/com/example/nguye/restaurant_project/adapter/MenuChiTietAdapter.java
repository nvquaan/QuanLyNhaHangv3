package com.example.nguye.restaurant_project.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.MenuChiTiet;

import java.util.List;

/**
 * Created by nguye on 11/16/2017.
 */

public class MenuChiTietAdapter extends ArrayAdapter<MenuChiTiet> {

    Activity context;// màn hình sẽ sử dụng giao diện này
    int resource;// kiểu hiển thị, ở đây là items_menu
    List<MenuChiTiet> objects;// nguồn dữ liệu cho listview

    public MenuChiTietAdapter(Activity context, int resource, List<MenuChiTiet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(R.layout.items_menu_chitiet,parent,false);
        TextView txtTenChiTiet = (TextView) row.findViewById(R.id.txtTenChiTiet);
        ImageView imageView = (ImageView) row.findViewById(R.id.imgChiTiet);

        MenuChiTiet menuChiTiet = this.objects.get(position);
        txtTenChiTiet.setText(menuChiTiet.getTenMon());
        imageView.setImageBitmap(menuChiTiet.getAnh());

        return row;
    }
}
