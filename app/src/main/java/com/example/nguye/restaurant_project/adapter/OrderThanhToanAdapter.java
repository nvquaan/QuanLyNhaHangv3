package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

/**
 * Created by Hieu Nguyen on 1/17/2018.
 */

public class OrderThanhToanAdapter extends ArrayAdapter<Order> {
    Context context;
    int resource;
    ArrayList<Order> orderArrayList;
    public OrderThanhToanAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Order> objects) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_order_thanhtoan,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtSoLuong=(TextView)convertView.findViewById(R.id.txtSoLuong);
            viewHolder.txtTenmon=(TextView)convertView.findViewById(R.id.txtTenMon);
            viewHolder.txtTongMon=(TextView)convertView.findViewById(R.id.txtTongMon);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Order order=orderArrayList.get(position);
        viewHolder.txtTenmon.setText(order.getTenMon());
        viewHolder.txtSoLuong.setText(Integer.toString((int) order.getSoLuong()));
        int tongMon=order.getSoLuong()*order.getDonGia();

        viewHolder.txtTongMon.setText(Integer.toString((int) tongMon));
        return convertView;
    }
    public class ViewHolder{
        TextView txtTenmon;
        TextView txtSoLuong;
        TextView txtTongMon;
    }
}
