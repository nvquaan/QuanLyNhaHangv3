package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.RecycleMenuOrder;

import java.util.ArrayList;

/**
 * Created by nguye on 12/8/2017.
 */

public class RecycleMenuOrderAdapter extends RecyclerView.Adapter<RecycleMenuOrderAdapter.ViewHolder> {
    ArrayList<RecycleMenuOrder> ArrRecycleMenuOrders;
    Context context;
    OnItemClickListener clickListener;

    private int itemclickpos=-1;

    public ArrayList<RecycleMenuOrder> getArrRecycleMenuOrders() {
        return ArrRecycleMenuOrders;
    }

    public void setArrRecycleMenuOrders(ArrayList<RecycleMenuOrder> ArrRecycleMenuOrders) {
        this.ArrRecycleMenuOrders = ArrRecycleMenuOrders;
    }

    public RecycleMenuOrderAdapter(ArrayList<RecycleMenuOrder> ArrRecycleMenuOrders, Context context,OnItemClickListener clickListener) {
        this.ArrRecycleMenuOrders = ArrRecycleMenuOrders;
        this.context = context;
        this.clickListener=clickListener;
    }

    @Override
    public RecycleMenuOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.items_recycle_menu_order,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecycleMenuOrderAdapter.ViewHolder holder, int position) {
        holder.txtTenRecycleMenuOrder.setText(ArrRecycleMenuOrders.get(position).getTenRecycleMenuOrder());
//        holder.txtIdRecycleMenuOrder.setText(Integer.toString(ArrRecycleMenuOrders.get(position).getIdRecycleMenuOrder()));
        if(itemclickpos==-1){
            position=0;
            itemclickpos=0;
        }

        if(itemclickpos==position){
            holder.linearLayoutRecycleMenuOrder.setBackground(holder.linearLayoutRecycleMenuOrder.getContext().getResources().getDrawable(R.drawable.dinhdang_click_khuvuc));
        }
        else{
            holder.linearLayoutRecycleMenuOrder.setBackground(holder.linearLayoutRecycleMenuOrder.getContext().getResources().getDrawable(R.drawable.dinhdang_khuvuc));
        }
    }

    @Override
    public int getItemCount() {
        return ArrRecycleMenuOrders.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenRecycleMenuOrder;
        LinearLayout linearLayoutRecycleMenuOrder;
        TextView txtIdRecycleMenuOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenRecycleMenuOrder = (TextView) itemView.findViewById(R.id.txtTenRecycleMenuOrder);
            linearLayoutRecycleMenuOrder = (LinearLayout) itemView.findViewById(R.id.linearLayoutRecycleMenuOrder);
//            txtIdRecycleMenuOrder = itemView.findViewById(R.id.txtIdRecycleMenuOrder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        itemclickpos = getAdapterPosition();
                        notifyDataSetChanged();
                        clickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }
}