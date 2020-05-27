package com.example.nguye.restaurant_project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChiTietMenu extends AppCompatActivity {
    TextView txtTenCT;
    TextView txtGiaCT;
    Button btnGotoDatBan;
    FloatingActionButton floatingActionButtonCT;
    ImageView imgCT;

    String sTenCT;
    int iGiaCT;
    byte[] anhCT;
    Bitmap bitmap;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_menu);
//        xuLySaoChepCsdlTuAssetVaoHeThong();
        addControls();
        addEvents();
    }

    public void addControls() {
        txtTenCT = (TextView) findViewById(R.id.txtTenCT);
        txtGiaCT = (TextView) findViewById(R.id.txtGiaCT);
        imgCT = (ImageView) findViewById(R.id.imgCT);
        floatingActionButtonCT = (FloatingActionButton) findViewById(R.id.floatingActionButtonCT);
        btnGotoDatBan = (Button) findViewById(R.id.btnGotoDatBan);
    }

    private void addEvents() {
        xuLyHienCT();
        xuLyClickFloatingButton();
        xuLyBtnGotoDatBan();
    }

    private void xuLyBtnGotoDatBan() {
        btnGotoDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ChiTietMenu.this,TableList.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyClickFloatingButton() {
        floatingActionButtonCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.trangThai==false)
                {
                    Toast.makeText(ChiTietMenu.this,"Bạn phải đặt bàn trước!",Toast.LENGTH_SHORT).show();
                    btnGotoDatBan.setVisibility(View.VISIBLE);

                }else
                {
                    Toast.makeText(ChiTietMenu.this,"Lấy được: "+sTenCT+" giá: "+ iGiaCT,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void xuLyHienCT() {
        // Nhận id món ăn từ activity MenuFoodActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundleIdMenuChiTiet");
        int idMenuChiTiet = bundle.getInt("idMenuChiTiet");
//        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        database=Database.initDatabase(this,DATABASE_NAME);
        String query = "select * from Food where IDFood = " + idMenuChiTiet;
        Cursor cursor = database.rawQuery(query,null);

        while (cursor.moveToNext())
        {
            sTenCT = cursor.getString(2);
            iGiaCT = cursor.getInt(3);
            anhCT = cursor.getBlob(4);
            bitmap = BitmapFactory.decodeByteArray(anhCT,0,anhCT.length);
        }
        cursor.close();
        // gán giá trị lấy được từ csdl ra các items.
        txtTenCT.setText(sTenCT.toString());
        txtGiaCT.setText(iGiaCT+"");
        imgCT.setImageBitmap(bitmap);
    }
}
