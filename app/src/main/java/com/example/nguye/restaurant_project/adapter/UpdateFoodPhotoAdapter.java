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
 * Created by nguye on 1/26/2018.
 */

public class UpdateFoodPhotoAdapter extends ArrayAdapter<MenuChiTiet> {

    Activity context;// màn hình sẽ sử dụng giao diện này
    int resource;// kiểu hiển thị, ở đây là items_menu
    List<MenuChiTiet> objects;// nguồn dữ liệu cho listview

    public UpdateFoodPhotoAdapter(Activity context, int resource, List<MenuChiTiet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(R.layout.items_updatefoodphoto,parent,false);
        TextView txtTenChiTiet = (TextView) row.findViewById(R.id.txtTenPhotoUpdate);
        TextView txtIdPhotoUpdate = row.findViewById(R.id.txtIdPhotoUpdate);
        ImageView imageView = (ImageView) row.findViewById(R.id.imgUpdatePhoto);

        MenuChiTiet menuChiTiet = this.objects.get(position);
        txtTenChiTiet.setText(menuChiTiet.getTenMon());
        txtIdPhotoUpdate.setText(Integer.toString(menuChiTiet.getIdMonAn()));
        imageView.setImageBitmap(menuChiTiet.getAnh());

        return row;
    }
}