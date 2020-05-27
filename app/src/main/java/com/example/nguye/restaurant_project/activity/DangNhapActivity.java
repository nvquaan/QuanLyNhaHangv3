package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;

public class DangNhapActivity extends AppCompatActivity {

    Button btnDongyDN, btnDangkyDN;
    EditText edTenDN, edMatKhauDN;
    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        database=Database.initDatabase(this,DATABASE_NAME);
        message = getIntent().getStringExtra("message");
        if(message != null) {
            Toast.makeText(DangNhapActivity.this ,message, Toast.LENGTH_SHORT).show();
        }

        addControls();

        HienThiButton();

        addEvents();
    }

    private void HienThiButton() {
        btnDongyDN.setVisibility(View.VISIBLE);
        btnDangkyDN.setVisibility(View.VISIBLE);
//        if(KiemTraNhanVien()){
//            btnDangkyDN.setVisibility(View.GONE);
//            btnDongyDN.setVisibility(View.VISIBLE);
//        }else{
//            btnDangkyDN.setVisibility(View.VISIBLE);
//            btnDongyDN.setVisibility(View.GONE);
//        }

    }

    private boolean KiemTraNhanVien() {
        database=Database.initDatabase(this,DATABASE_NAME);
        String query="SELECT *FROM NhanVien";
        Cursor cursor=database.rawQuery(query,null);
        if(cursor.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }

    private void addControls() {
        btnDongyDN= (Button) findViewById(R.id.btnDongyDN);
        btnDangkyDN= (Button) findViewById(R.id.btnDangkyDN);
        edTenDN= (EditText) findViewById(R.id.edTenDangNhap);
        edMatKhauDN= (EditText) findViewById(R.id.edMatKhauDN);
    }

    private void addEvents() {
        btnDongyDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDN=edTenDN.getText().toString();
                String matKhau=edMatKhauDN.getText().toString();
                if(tenDN==null || tenDN.equals("") || matKhau==null || matKhau.equals("")){
                    Toast.makeText(DangNhapActivity.this,getResources().getString(R.string.tendangnhapormatkhaukodung)+"",Toast.LENGTH_SHORT).show();
                }else{
                    int manv=KiemTraDangNhap(tenDN,matKhau);
                    int maquyen=layQuyenNhanVien(manv);
                    if(manv!=0){
                        //Lưu mã quyền trong file xml
                        SharedPreferences sharedPreferences=getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("maquyen",maquyen);
                        editor.putInt("manv",manv);
                        editor.commit();

                        Intent intent=new Intent(DangNhapActivity.this,MainActivity.class);
                        intent.putExtra("tendn",tenDN);
                        intent.putExtra("makhau",matKhau);
                        startActivity(intent);
                    }else {
                        Toast.makeText(DangNhapActivity.this,getResources().getString(R.string.dulieudaydu)+"",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnDangkyDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DangNhapActivity.this,DangKyActivity.class);
                intent.putExtra("landautien", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButton();
    }
    private int layQuyenNhanVien(int manv){
        int maquyen=0;
        String query="SELECT *FROM NhanVien where id= "+manv;
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            maquyen=cursor.getInt(6);
            cursor.moveToNext();
        }
        return maquyen;
    }

    private int KiemTraDangNhap(String tenDN, String matKhau) {
        int manv=0;
        String query="SELECT *FROM NhanVien where tenDN= '"+tenDN+"'and matKhau='"+matKhau+"'";
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            manv =cursor.getInt(0);
            cursor.moveToNext();
        }
        return  manv;
    }
}
