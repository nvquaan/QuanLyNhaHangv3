package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.Table;

import java.util.ArrayList;

/**
 * Created by Hieu Nguyen on 1/25/2018.
 */

public class frmOrderAdapter extends ArrayAdapter<Table> {
        Context context;
        int resource;
        ArrayList<Table> tableArrayList;

public frmOrderAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Table> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.tableArrayList=objects;
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
        convertView= LayoutInflater.from(context).inflate(R.layout.items_table,parent,false);
        viewHolder=new ViewHolder();
        viewHolder.txtTenBan=(TextView) convertView.findViewById(R.id.txtTenBan);
        viewHolder.itemBan=(LinearLayout)convertView.findViewById(R.id.linearLayoutBan);
//            viewHolder.txtIdTenBan = convertView.findViewById(R.id.txtIdTenBan);
//            viewHolder.itemBan=(LinearLayout)convertView.findViewById(R.id.itemBan);




        convertView.setTag(viewHolder);
        }else
        {
        viewHolder=(ViewHolder)convertView.getTag();
        }
        Table table=tableArrayList.get(position);
        viewHolder.txtTenBan.setText(table.getTenBan()+"\n"+table.getTenKhuVuc());
        if(table.isTrangThai()==true){
        viewHolder.itemBan.setBackground(viewHolder.itemBan.getContext().getResources().getDrawable(R.drawable.dinhdang_click_khuvuc));
        }
        else{
        viewHolder.itemBan.setBackground(viewHolder.itemBan.getContext().getResources().getDrawable(R.drawable.dinhdang_khuvuc));
        }
//        viewHolder.txtIdTenBan.setText(Integer.toString(table.getId()));
        return convertView;
        }

public class ViewHolder{
    //dinh nghia ra cac doi tuong view de gan voi monan_items layout
    TextView txtTenBan;
    //        TextView txtIdTenBan;
    LinearLayout itemBan;
}

}