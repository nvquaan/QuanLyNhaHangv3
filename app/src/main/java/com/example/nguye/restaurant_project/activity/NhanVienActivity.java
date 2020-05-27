package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.NhanVienAdapter;
import com.example.nguye.restaurant_project.model.NhanVien;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {

    ListView lvNhanVien;
    ArrayList<NhanVien> nhanVienArrayList;
    NhanVienAdapter nhanVienAdapter;
    NhanVien nhanVien;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        HienThiNhanVien();
        loadNhanVien();

        //Đọc quyền từ file xml
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if (maquyen == 1) {
            registerForContextMenu(lvNhanVien);
        }

    }

    private void HienThiNhanVien() {
        lvNhanVien = (ListView) findViewById(R.id.lvNhanVien);
        nhanVienArrayList = new ArrayList<>();
        nhanVienAdapter = new NhanVienAdapter(NhanVienActivity.this, R.layout.items_nhanvien, nhanVienArrayList);
        lvNhanVien.setAdapter(nhanVienAdapter);
    }


    private void loadNhanVien() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        nhanVienArrayList.clear();
//        Toast.makeText(NhanVienActivity.this,""+cursor.getCount(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String matkhau = cursor.getString(2);
            String gt = cursor.getString(3);
            String ngaySinh = cursor.getString(4);
            int cmnd = Integer.parseInt(cursor.getString(5));
            int maQuyen = cursor.getInt(6);
            nhanVienArrayList.add(new NhanVien(id, cmnd, maQuyen, ten, matkhau, gt, ngaySinh));
        }
        nhanVienAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNhanVien();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manv = nhanVienArrayList.get(vitri).getMaNV();
        switch (id) {
            case R.id.sua:
                Intent intent = new Intent(NhanVienActivity.this, DangKyActivity.class);
                intent.putExtra("manv", manv);
                startActivity(intent);
                break;
            case R.id.xoa:
                if (XoaNhanVien(manv)) {
                    Toast.makeText(NhanVienActivity.this, getResources().getString(R.string.xoathanhcong) + "", Toast.LENGTH_SHORT).show();
                    HienThiNhanVien();
                } else
                    Toast.makeText(NhanVienActivity.this, getResources().getString(R.string.xoakothanhcong) + "", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private boolean XoaNhanVien(int maNV) {
        long xoa = database.delete("NhanVien", "id= " + maNV, null);
        if (xoa != 0) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
        if(maquyen==1){
            MenuItem menuItem = menu.add(1, R.id.themNV, 1, "Thêm nhân viên");
            menuItem.setIcon(R.drawable.employee);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.themNV:
                Intent intent = new Intent(NhanVienActivity.this, DangKyActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
