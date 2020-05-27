package com.example.nguye.restaurant_project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.frmThanhToanAdapter;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

public class ThongKeHoaDonActivity extends AppCompatActivity {
    ListView lvHoaDon;
    com.example.nguye.restaurant_project.adapter.frmThanhToanAdapter frmThanhToanAdapter;
    ArrayList<Order> orderArrayList;
    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;
    String ngayhientai="";
    int soPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_hoa_don);

        ngayhientai=getIntent().getStringExtra("ngayhientai");

        lvHoaDon = (ListView) findViewById(R.id.lvHoaDon);
        orderArrayList = new ArrayList<>();
        frmThanhToanAdapter = new frmThanhToanAdapter(ThongKeHoaDonActivity.this, R.layout.items_frmthanhtoan, orderArrayList);
        lvHoaDon.setAdapter(frmThanhToanAdapter);
        readData(ngayhientai);
    }

    private void readData(String ngayLap) {
        database= Database.initDatabase(this,DATABASE_NAME);
        String sql="select HoaDon.id, iTongTien, TenBan, TenKhuVuc, Ban.id, KhuVuc.id from (HoaDon inner join Ban on HoaDon.iMaBan=Ban.id) inner join KhuVuc on HoaDon.iKhuVuc=KhuVuc.id where bTrangThai=1 and dNgayLap='"+ngayLap+"'";
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
        Toast.makeText(ThongKeHoaDonActivity.this,soPhieu+"",Toast.LENGTH_LONG).show();
    }
}
