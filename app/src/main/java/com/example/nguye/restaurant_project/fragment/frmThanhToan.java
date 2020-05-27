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
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.Database;
import com.example.nguye.restaurant_project.activity.ThanhToanActivity;
import com.example.nguye.restaurant_project.adapter.frmThanhToanAdapter;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

public class frmThanhToan extends Fragment {
    ListView lvHoaDon;
    frmThanhToanAdapter frmThanhToanAdapter;
    ArrayList<Order> orderArrayList;

    final String DATABASE_NAME="QLNhaHang.sqlite";
    public static SQLiteDatabase database;

    int soPhieu;
    int mafrm=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pay,container,false);
        lvHoaDon=(ListView)view.findViewById(R.id.lvHoaDon);
        orderArrayList=new ArrayList<>();
        frmThanhToanAdapter= new frmThanhToanAdapter(getContext(),R.layout.items_frmthanhtoan,orderArrayList);
        lvHoaDon.setAdapter(frmThanhToanAdapter);
        readData();
        getEvents();
        return view;
    }

    private void readData() {
        database= Database.initDatabase(getActivity(),DATABASE_NAME);
        String sql="select HoaDon.id, iTongTien, TenBan, TenKhuVuc, Ban.id, KhuVuc.id from (HoaDon inner join Ban on HoaDon.iMaBan=Ban.id) inner join KhuVuc on HoaDon.iKhuVuc=KhuVuc.id where bTrangThai=0";
        Cursor cursor=database.rawQuery(sql,null);
        orderArrayList.clear();
        for (int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            soPhieu=cursor.getInt(0);
            int tongTien=cursor.getInt(1);
            String tenBan=cursor.getString(2);
            String tenKhuVuc=cursor.getString(3);
            int maBan=cursor.getInt(4);
            int maKhuVuc=cursor.getInt(5);
//            Toast.makeText(getContext(),soPhieu,Toast.LENGTH_LONG).show();
            orderArrayList.add(new Order(soPhieu,tongTien,tenBan,tenKhuVuc,maBan, maKhuVuc));
        }
        frmThanhToanAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(),soPhieu+"",Toast.LENGTH_LONG).show();
    }

    private void getEvents() {
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idban=orderArrayList.get(i).getMaBan();
                int idKhuVuc=orderArrayList.get(i).getMaKhuVuc();
                Intent intent=new Intent(getContext(),ThanhToanActivity.class);
                intent.putExtra("idban",idban);
                intent.putExtra("idKhuVuc",idKhuVuc);
                intent.putExtra("mafrm", mafrm);
                startActivity(intent);

            }
        });

    }
}
