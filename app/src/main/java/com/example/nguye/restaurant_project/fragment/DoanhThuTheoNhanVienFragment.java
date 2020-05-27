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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.DanhSachHoaDonTheoMaNVActivity;
import com.example.nguye.restaurant_project.activity.Database;
import com.example.nguye.restaurant_project.adapter.frmDoanhThuNVAdapter;
import com.example.nguye.restaurant_project.adapter.frmThanhToanAdapter;
import com.example.nguye.restaurant_project.model.DoanhThuNhanVien;
import com.example.nguye.restaurant_project.model.Order;

import java.util.ArrayList;

/**
 * Created by Admin on 4/22/2018.
 */

public class DoanhThuTheoNhanVienFragment extends Fragment {
    ListView lvDoanhthuNV;
    frmDoanhThuNVAdapter doanhThuNVAdapter;
    ArrayList<DoanhThuNhanVien> doanhThuNhanVienArrayList;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_doanhthutheonv, container, false);
        lvDoanhthuNV = (ListView) view.findViewById(R.id.lvDoanhthuNV);

        doanhThuNhanVienArrayList=new ArrayList<>();
        doanhThuNVAdapter= new frmDoanhThuNVAdapter(getContext(),R.layout.items_doanhthunv,doanhThuNhanVienArrayList);
        lvDoanhthuNV.setAdapter(doanhThuNVAdapter);
        readData();
        lvDoanhthuNV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int manv=doanhThuNhanVienArrayList.get(position).getManv();
                Intent intent=new Intent(getContext(), DanhSachHoaDonTheoMaNVActivity.class);
                intent.putExtra("manv",manv);
                startActivity(intent);
            }
        });
        return view;
    }
    private void readData() {
        database= Database.initDatabase(getActivity(),DATABASE_NAME);
        String sql="SELECT NhanVien.id,tenDN , sum(iTongTien) as [TongTien] FROM HoaDon inner join NhanVien on iMaNV=NhanVien.id\n" +
                "group by tenDN,NhanVien.id  \n" +
                "order by  sum(iTongTien) DESC";
        Cursor cursor=database.rawQuery(sql,null);
        doanhThuNhanVienArrayList.clear();
        for (int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            int manv=cursor.getInt(0);
            String ten=cursor.getString(1);
            int tongTien=cursor.getInt(2);
            doanhThuNhanVienArrayList.add(new DoanhThuNhanVien(manv,ten,tongTien));
        }
        doanhThuNVAdapter.notifyDataSetChanged();
//        Toast.makeText(getContext(),soPhieu+"",Toast.LENGTH_LONG).show();
    }
}
