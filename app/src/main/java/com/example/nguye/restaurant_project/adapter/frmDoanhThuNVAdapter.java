package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.DoanhThuNhanVien;
import com.example.nguye.restaurant_project.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/22/2018.
 */

public class frmDoanhThuNVAdapter extends ArrayAdapter<DoanhThuNhanVien> {
    Context context;
    int resource;
    ArrayList<DoanhThuNhanVien> doanhThuNhanVienArrayList;
    public frmDoanhThuNVAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DoanhThuNhanVien> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.doanhThuNhanVienArrayList=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.items_doanhthunv,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtTenNV=(TextView) convertView.findViewById(R.id.txtTenNV);
            viewHolder.txtTongTien=(TextView)convertView.findViewById(R.id.txtTongtien);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        DoanhThuNhanVien doanhThuNhanVien=doanhThuNhanVienArrayList.get(position);
        viewHolder.txtTenNV.setText(doanhThuNhanVien.getTenNV());
        viewHolder.txtTongTien.setText(Integer.toString(doanhThuNhanVien.getTongTien()));

        return convertView;
    }

    public class ViewHolder{
        //dinh nghia ra cac doi tuong view de gan voi monan_items layout
        TextView txtTenNV,txtTongTien;
        //        TextView txtIdTenBan;
//        LinearLayout itemBan;
    }
}
