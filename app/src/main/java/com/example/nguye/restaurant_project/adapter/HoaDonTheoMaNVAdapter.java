package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/23/2018.
 */

public class HoaDonTheoMaNVAdapter extends ArrayAdapter<Order> {
    Context context;
    int resource;
    ArrayList<Order> orderArrayList;
    public HoaDonTheoMaNVAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.orderArrayList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.items_danhsachhoadontheomanv,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.txttongtien=(TextView)convertView.findViewById(R.id.txttongTien);
            viewHolder.txtngaylap=(TextView)convertView.findViewById(R.id.txtNgayLap);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Order order=orderArrayList.get(position);
        viewHolder.txttongtien.setText(String.valueOf(order.getTongTien()));
        viewHolder.txtngaylap.setText(order.getNgayLap());
        return convertView;
    }
    public class ViewHolder{
        TextView txttongtien, txtngaylap;

    }
}
