package com.example.nguye.restaurant_project.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.MenuChiTiet;

import java.util.List;

/**
 * Created by nguye on 12/8/2017.
 */

public class MenuChiTietMonAnAdapter extends ArrayAdapter<MenuChiTiet> {
    Activity context;// màn hình sẽ sử dụng giao diện này
    int resource;// kiểu hiển thị, ở đây là items_menu
    List<MenuChiTiet> objects;// nguồn dữ liệu cho listview


    public MenuChiTietMonAnAdapter(Activity context, int resource, List<MenuChiTiet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(R.layout.items_gv_monan,parent,false);
        TextView txtTenChiTietOrder = (TextView) row.findViewById(R.id.txtTenChiTietOrder);
        TextView txtGiaChiTietOrder = (TextView) row.findViewById(R.id.txtGiaChiTietOrder);
        TextView txtSoLuong=(TextView) row.findViewById(R.id.txtSoLuong);

        MenuChiTiet menuChiTietOrder = this.objects.get(position);
        txtTenChiTietOrder.setText(menuChiTietOrder.getTenMon());
        txtGiaChiTietOrder.setText(Integer.toString((int) menuChiTietOrder.getDonGia()));
        txtSoLuong.setText("x"+ menuChiTietOrder.getSoluong());

        if(menuChiTietOrder.getSoluong()!=0){
            txtSoLuong.setVisibility(View.VISIBLE);
        }
        else{
            txtSoLuong.setVisibility(View.GONE);
        }

        return row;
    }

}
