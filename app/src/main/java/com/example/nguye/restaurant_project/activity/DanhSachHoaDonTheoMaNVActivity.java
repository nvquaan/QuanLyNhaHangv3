package com.example.nguye.restaurant_project.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.HoaDonTheoMaNVAdapter;
import com.example.nguye.restaurant_project.adapter.KhuVucAdapter;
import com.example.nguye.restaurant_project.adapter.OrderAdapter;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

public class DanhSachHoaDonTheoMaNVActivity extends AppCompatActivity {
    ArrayList<Order> orderArrayList;
    ListView lvHoaDonNV;
    HoaDonTheoMaNVAdapter hoaDonTheoMaNVAdapter;
    int maNV;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_hoa_don_theo_ma_nv);

        maNV=getIntent().getIntExtra("manv",0);

        lvHoaDonNV= (ListView) findViewById(R.id.lvhoaDonNV);
        orderArrayList=new ArrayList<>();
        //khuVucAdapter=new KhuVucAdapter(khuVucArrayList,getApplicationContext());
        hoaDonTheoMaNVAdapter= new HoaDonTheoMaNVAdapter(DanhSachHoaDonTheoMaNVActivity.this,R.layout.items_danhsachhoadontheomanv,orderArrayList);
        lvHoaDonNV.setAdapter(hoaDonTheoMaNVAdapter);
        readData(maNV);
    }

    private void readData(int manv) {
        database= Database.initDatabase(this,DATABASE_NAME);
        String sql="SELECT dNgayLap,iMaNV, iTongTien from HoaDon where iMaNV="+manv;
        Cursor cursor=database.rawQuery(sql,null);
        orderArrayList.clear();
        for (int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            String dNgayLap=cursor.getString(0);
            int manhanvien=cursor.getInt(1);
            int tongtien=cursor.getInt(2);
//            Toast.makeText(getContext(),soPhieu,Toast.LENGTH_LONG).show();
            orderArrayList.add(new Order(dNgayLap,manhanvien,tongtien));
        }
        hoaDonTheoMaNVAdapter.notifyDataSetChanged();
    }
}
