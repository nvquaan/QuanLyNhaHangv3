package com.example.nguye.restaurant_project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.Menu;

import java.util.List;

/**
 * Created by nguye on 11/7/2017.
 */

public class MenuAdapter extends ArrayAdapter<Menu> {

    Activity context;// màn hình sẽ sử dụng giao diện này
    int resource;// kiểu hiển thị, ở đây là items_menu
    List<Menu> objects;// nguồn dữ liệu cho listview

    public MenuAdapter(Activity context, int resource, List<Menu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(R.layout.items_menu,parent,false);
//        TextView txtIdGroupMenu = (TextView) row.findViewById(R.id.txtGroupMenu);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTenMonAn);
        ImageView imageView = (ImageView) row.findViewById(R.id.imgView);

        Menu menu = this.objects.get(position); // trả về menu hiện tại muốn vẽ
//        txtIdGroupMenu.setText(Integer.toString(menu.getTxtIdGroup()));
        imageView.setImageBitmap(menu.getBtnAnh());
        txtTen.setText(menu.getTxtTenMonAn());
        return row;
    }
}
