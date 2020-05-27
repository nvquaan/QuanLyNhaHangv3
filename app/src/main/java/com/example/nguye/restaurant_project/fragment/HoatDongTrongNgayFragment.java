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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.Database;
import com.example.nguye.restaurant_project.activity.ThongKeHoaDonActivity;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 4/22/2018.
 */

public class HoatDongTrongNgayFragment extends Fragment {
    TextView txtNgayHienTai, txtDoanhthu;
    Button btnXemPhieu;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frm_hdtrongngay,container,false);

        txtNgayHienTai=(TextView)view.findViewById(R.id.txtNgayhientai);
        txtDoanhthu=(TextView)view.findViewById(R.id.txtDoanhthu);
        btnXemPhieu=(Button)view.findViewById(R.id.btnXemPhieu);

        layNgayHientai();
        txtDoanhthu.setText(layTongTien(txtNgayHienTai.getText().toString())+"");
        btnXemPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ThongKeHoaDonActivity.class);
                intent.putExtra("ngayhientai",txtNgayHienTai.getText());
                startActivity(intent);
            }
        });
        return view;
    }

    private void layNgayHientai(){
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);
        String ngayHienTai = iNgay + "/" + (iThang+1) + "/" + iNam;
        txtNgayHienTai.setText(ngayHienTai);
    }

    private int layTongTien(String ngayLap) {
        int tongtien=0;
        database= Database.initDatabase(getActivity(),DATABASE_NAME);
        String sql="select HoaDon.id, iTongTien, TenBan, TenKhuVuc, Ban.id, KhuVuc.id from (HoaDon inner join Ban on HoaDon.iMaBan=Ban.id) inner join KhuVuc on HoaDon.iKhuVuc=KhuVuc.id where bTrangThai=1 and dNgayLap='"+ngayLap+"'";
        Cursor cursor=database.rawQuery(sql,null);
        for (int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            tongtien=tongtien+ cursor.getInt(1);
        }
        return tongtien;
    }
}
