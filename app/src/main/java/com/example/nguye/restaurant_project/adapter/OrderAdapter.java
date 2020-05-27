package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
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

/**
 * Created by Hieu Nguyen on 11/15/2017.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    int resource;
    ArrayList<Order> orderArrayList;

    OnItemClickListener clickListener;

    public OrderAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Order> objects,OnItemClickListener clickListener) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.orderArrayList=objects;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.items_order,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtTenmon=(TextView)convertView.findViewById(R.id.txtTenMon);
            viewHolder.btnSoluong=(Button)convertView.findViewById(R.id.btnSoLuong);
            viewHolder.btnCong=(Button)convertView.findViewById(R.id.btnCong);
            viewHolder.btnTru=(Button)convertView.findViewById(R.id.btnTru);
            viewHolder.txtTongMon=(TextView)convertView.findViewById(R.id.txtTongMon);
            convertView.setTag(viewHolder);
        }else
            {
                viewHolder=(ViewHolder)convertView.getTag();
            }
        final Order order=orderArrayList.get(position);
        viewHolder.txtTenmon.setText(order.getTenMon());
        viewHolder.btnSoluong.setText(Integer.toString((int) order.getSoLuong()));
        int tongMon=order.getSoLuong()*order.getDonGia();

        viewHolder.txtTongMon.setText(Integer.toString((int) tongMon));
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.btnCongonItemClick(view,position);



//                int maMon=order.getMaMon();
//                String tenMon=order.getTenMon();
//                int soluong=order.getSoLuong()+1;
//                int donGia=order.getDonGia();
//                orderArrayList.set(position, new Order(maMon,tenMon,donGia,soluong));
//                notifyDataSetChanged();



//                xuLyThayDoiSLTongTien(position);


            }
        });

        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.btnTruonItemClick(view,position);

//                int maMon=order.getMaMon();
//                String tenMon=order.getTenMon();
//                int soluong=order.getSoLuong()-1;
//                int donGia=order.getDonGia();
//                if(soluong==0){
//                    orderArrayList.remove(position);
//
//                }else {
//                    orderArrayList.set(position, new Order(maMon,tenMon,donGia,soluong));
//                }
//
//                notifyDataSetChanged();
//
//                xuLyThayDoiSLTongTien();

            }
        });

        viewHolder.btnSoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.btnSoLuongonItemClick(view,position);

//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.custom_dialog_sl);
//                dialog.setTitle("Nhập số lượng món ăn:");
//                final EditText etSoluong=(EditText)dialog.findViewById(R.id.etSoluong);
//                Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
//                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//                // if button is clicked, close the custom dialog
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Toast.makeText(context,"Dismissed..!!",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                btnOK.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int soLuong= Integer.parseInt(etSoluong.getText().toString());
////                        Toast.makeText(context,soLuong+"",Toast.LENGTH_LONG).show();
//                        orderArrayList.set(position, new Order(order.getMaMon(),order.getTenMon(),order.getDonGia(),soLuong));
//                        dialog.dismiss();
//                        notifyDataSetChanged();
//                    }
//                });
//                dialog.show();
            }
        });

        return convertView;
    }

    public interface OnItemClickListener {
        public void btnCongonItemClick(View view, int position);
        public void btnTruonItemClick(View view, int position);
        public void btnSoLuongonItemClick(View view, int position);
    }

//    private void xuLyThayDoiSLTongTien() {
//        int SL=0;
//        int tongTien=0;
////        Toast.makeText(context,position+"Haha",Toast.LENGTH_LONG).show();
//        for(int i=0;i<orderArrayList.size();i++){
//            SL= SL+ orderArrayList.get(i).getSoLuong();
//            tongTien= tongTien+ (orderArrayList.get(i).getSoLuong()*orderArrayList.get(i).getDonGia());
//        }
//        Toast.makeText(context,tongTien+"",Toast.LENGTH_LONG).show();
//        orderActivity.txtSL.setText(SL+"");
//        orderActivity.txttongTien.setText(tongTien+"");
//    }


    public class ViewHolder{
        TextView txtTenmon;
        Button btnSoluong;
        TextView txtTongMon;
        Button btnCong;
        Button btnTru;
    }
}
