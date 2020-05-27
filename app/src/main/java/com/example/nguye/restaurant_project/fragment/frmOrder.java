package com.example.nguye.restaurant_project.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.Database;
import com.example.nguye.restaurant_project.activity.OrderActivity;
import com.example.nguye.restaurant_project.activity.TabbleOrderActivity;
import com.example.nguye.restaurant_project.activity.TableList;
import com.example.nguye.restaurant_project.adapter.frmOrderAdapter;
import com.example.nguye.restaurant_project.model.Table;

import java.util.ArrayList;

/**
 * Created by Hieu Nguyen on 1/25/2018.
 */

public class frmOrder extends Fragment {
    GridView grvTable;
    ArrayList<Table> tableArrayList;
    frmOrderAdapter tableAdapter;
    View view;

    int soPhieuMax;

    final String DATABASE_NAME="QLNhaHang.sqlite";
    public static SQLiteDatabase database;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_order,container,false);
        addControls();
        readData();
        addEvents();
        return view;
    }

    private void addEvents() {
        grvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                laySoPhieu();
                soPhieuMax=soPhieuMax+1;
                int ma=1;
                int idban=tableArrayList.get(i).getId();
                int idKhuVuc=tableArrayList.get(i).getKhuVuc();
                Intent intent=new Intent(getContext(),OrderActivity.class);
                intent.putExtra("ma",ma);
                intent.putExtra("soPhieuMax",soPhieuMax);
                intent.putExtra("idban",idban);
                intent.putExtra("idKhuVuc",idKhuVuc);
                startActivity(intent);
            }
        });
    }

    private void laySoPhieu() {
        database=Database.initDatabase(getActivity(),DATABASE_NAME);
        String sql="select max(id) from HoaDon";
        Cursor cursor=database.rawQuery(sql,null);
        cursor.moveToFirst();
        soPhieuMax=cursor.getInt(0);

    }

    private void readData() {
        database= Database.initDatabase(getActivity(),DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT Ban.id, TenBan, TrangThai, KhuVuc, TenKhuVuc FROM Ban inner join KhuVuc on Ban.KhuVuc=KhuVuc.id where TrangThai=0",null);
        tableArrayList.clear();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            String tenBan=cursor.getString(1);
            boolean trangThai=cursor.getInt(2)>0;
            int khuVuc=cursor.getInt(3);
            String tenKhuVuc=cursor.getString(4);
            tableArrayList.add(new Table(id,tenBan,trangThai,khuVuc,tenKhuVuc));
        }
        tableAdapter.notifyDataSetChanged();
//        Toast.makeText(getActivity(),tableArrayList.size()+"",Toast.LENGTH_LONG).show();
    }

    private void addControls() {
        grvTable=(GridView)view.findViewById(R.id.grvTable);
        tableArrayList=new ArrayList<>();
        tableAdapter=new frmOrderAdapter(getContext(),R.layout.items_order,tableArrayList);
        grvTable.setAdapter(tableAdapter);
    }
}
