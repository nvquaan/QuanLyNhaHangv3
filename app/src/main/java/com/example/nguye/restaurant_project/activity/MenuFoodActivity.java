package com.example.nguye.restaurant_project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.MenuChiTietAdapter;
import com.example.nguye.restaurant_project.model.MenuChiTiet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MenuFoodActivity extends AppCompatActivity {
    GridView gvMenuChiTiet;
    TextView txtIdMenuChiTiet;
    ArrayList<MenuChiTiet> dsChiTiet;
    MenuChiTietAdapter menuChiTietAdapter;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xyLyHienDsMenuChiTiet();
        xuLyClickItems();
    }

    private void xuLyClickItems() {
        gvMenuChiTiet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idMenuChiTiet = dsChiTiet.get(i).getIdMonAn();
                Intent intent = new Intent(MenuFoodActivity.this,ChiTietMenu.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idMenuChiTiet",idMenuChiTiet);
                intent.putExtra("bundleIdMenuChiTiet",bundle);
                startActivity(intent);
            }
        });
    }

    private void xyLyHienDsMenuChiTiet() {
        //Nhận id group menu từ activity MenuActivity để select ra món ăn theo nhóm
        int id = MenuActivity.idGroupFood;
        //bắt đầu select
        database=Database.initDatabase(this,DATABASE_NAME);
        String query = "select * from Food where IDGroupMenu = " + id;
        Cursor cursor = database.rawQuery(query,null);
        dsChiTiet.clear();
        while (cursor.moveToNext())
        {
            int idChiTiet = cursor.getInt(0);
            String sTenMon = cursor.getString(2);
            byte[] anh = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
            MenuChiTiet menuChiTiet = new MenuChiTiet(idChiTiet, sTenMon,bitmap);
            dsChiTiet.add(menuChiTiet);
        }
        cursor.close();
        menuChiTietAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        gvMenuChiTiet = (GridView) findViewById(R.id.gvFood);
        dsChiTiet = new ArrayList<>();
        menuChiTietAdapter = new MenuChiTietAdapter(MenuFoodActivity.this,R.layout.items_menu_chitiet,dsChiTiet);
        gvMenuChiTiet.setAdapter(menuChiTietAdapter);
    }


}
