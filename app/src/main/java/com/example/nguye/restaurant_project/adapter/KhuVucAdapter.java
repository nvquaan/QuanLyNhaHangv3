package com.example.nguye.restaurant_project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.model.KhuVuc;

import java.util.ArrayList;

/**
 * Created by Hieu Nguyen on 11/23/2017.
 */

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.ViewHolder>  {
    ArrayList<KhuVuc> khuVucArrayList;
    Context context;
    OnItemClickListener clickListener;

    private int itemclickpos=-1;

    public ArrayList<KhuVuc> getKhuVucArrayList() {
        return khuVucArrayList;
    }

    public void setKhuVucArrayList(ArrayList<KhuVuc> khuVucArrayList) {
        this.khuVucArrayList = khuVucArrayList;
    }

    public KhuVucAdapter(ArrayList<KhuVuc> khuVucArrayList, Context context,OnItemClickListener clickListener) {
        this.khuVucArrayList = khuVucArrayList;
        this.context = context;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.items_khuvuc,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTenKhuVuc.setText(khuVucArrayList.get(position).getTenKhuVuc());
        if(itemclickpos==-1){
            position=0;
            itemclickpos=0;
        }

        if(itemclickpos==position){
            holder.linearLayoutKhuVuc.setBackground(holder.linearLayoutKhuVuc.getContext().getResources().getDrawable(R.drawable.dinhdang_click_khuvuc));
            holder.txtTenKhuVuc.setTextColor(Color.parseColor("#ffffff"));
        }
        else{
            holder.linearLayoutKhuVuc.setBackground(holder.linearLayoutKhuVuc.getContext().getResources().getDrawable(R.drawable.dinhdang_khuvuc));
            holder.txtTenKhuVuc.setTextColor(Color.parseColor("#303F9F"));
        }
    }


    @Override
    public int getItemCount() {
        return khuVucArrayList.size();
    }



    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener( OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenKhuVuc;
        LinearLayout  linearLayoutKhuVuc;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTenKhuVuc=(TextView)itemView.findViewById(R.id.txtTenKhuVuc);
            linearLayoutKhuVuc=(LinearLayout)itemView.findViewById(R.id.linearLayoutKhuVuc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener!=null){
                        itemclickpos=getAdapterPosition();
                        notifyDataSetChanged();
                        clickListener.onItemClick(view,getLayoutPosition());
                    }
                }
            });
        }

    }

}

