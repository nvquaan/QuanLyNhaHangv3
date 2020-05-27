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
import com.example.nguye.restaurant_project.model.NhanVien;
import com.example.nguye.restaurant_project.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/22/2018.
 */

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    Context context;
    int resource;
    ArrayList<NhanVien> nhanVienArrayList;

    public NhanVienAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NhanVien> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.nhanVienArrayList=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.items_nhanvien,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtTenNV=(TextView) convertView.findViewById(R.id.txtTenNV);
            viewHolder.txtCMND=(TextView) convertView.findViewById(R.id.txtCMND);
//            viewHolder.txtIdTenBan = convertView.findViewById(R.id.txtIdTenBan);
//            viewHolder.itemBan=(LinearLayout)convertView.findViewById(R.id.itemBan);




            convertView.setTag(viewHolder);
        }else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        NhanVien nhanVien=nhanVienArrayList.get(position);
        viewHolder.txtTenNV.setText(nhanVien.getTenDN());
        viewHolder.txtCMND.setText(String.valueOf(nhanVien.getCMND()));
//        viewHolder.txtIdTenBan.setText(Integer.toString(table.getId()));
        return convertView;
    }

    public class ViewHolder{
        //dinh nghia ra cac doi tuong view de gan voi monan_items layout
        TextView txtTenNV, txtCMND;
        //        TextView txtIdTenBan;

    }
}
