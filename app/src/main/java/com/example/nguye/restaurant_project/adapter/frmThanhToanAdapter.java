package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.DoanhThuNhanVien;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

/**
 * Created by Admin on 1/30/2018.
 */

public class frmThanhToanAdapter extends ArrayAdapter<Order> {
    Context context;
    int resource;
    ArrayList<Order> orderArrayList;

    public frmThanhToanAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.items_frmthanhtoan,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.txttongTien=(TextView) convertView.findViewById(R.id.txttongTien);
            viewHolder.txttenBan=(TextView)convertView.findViewById(R.id.txttenBan);
            viewHolder.txtsoPhieu=(TextView)convertView.findViewById(R.id.txtsoPhieu);

            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Order order=orderArrayList.get(position);
        viewHolder.txttongTien.setText(Integer.toString(order.getTongTien()));
        viewHolder.txttenBan.setText(order.getTenBan()+"-"+order.getTenKhuVuc());
        viewHolder.txtsoPhieu.setText(Integer.toString(order.getId()));

        return  convertView;
    }

    public  class ViewHolder{
        TextView txttongTien, txttenBan, txtsoPhieu;
    }
}
