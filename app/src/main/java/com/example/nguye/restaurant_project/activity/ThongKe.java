package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.TKMonBanChayAdapter;
import com.example.nguye.restaurant_project.model.TKMonBanChay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ThongKe extends AppCompatActivity {

    Button btnTK,btnTKThang;

    ListView lvMonBanChay;
    ArrayList<TKMonBanChay> dsContents;
    TKMonBanChayAdapter monBanChayAdapter;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        xuLySaoChepCsdlTuAssetVaoHeThong();

        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==1){

        }
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvMonBanChay.setVisibility(view.VISIBLE);
                xuLyMonBanChay();
            }
        });
        btnTKThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKe.this, TKThang.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyMonBanChay() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String query = "SELECT  iMaMon, Food.sTenMonAn,sum(iSoLuong)  from ChiTietHoaDon join Food where ChiTietHoaDon.iMaMon = Food.IDFood group by iMaMon order by sum(iSoLuong) desc";
        Cursor cursor = database.rawQuery(query,null);
        dsContents.clear();
        while (cursor.moveToNext())
        {
            int idMon = cursor.getInt(0);
            String sTenMon = cursor.getString(1);
            int slMon = cursor.getInt(2);
            TKMonBanChay tkMonBanChay = new TKMonBanChay(idMon,sTenMon,slMon);
            dsContents.add(tkMonBanChay);
        }
        cursor.close();
        monBanChayAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btnTK = (Button) findViewById(R.id.btnTK);
        btnTKThang = (Button) findViewById(R.id.btnTKDoanhThuThang);
        lvMonBanChay = (ListView) findViewById(R.id.lvTK);
        dsContents = new ArrayList<>();
        monBanChayAdapter = new TKMonBanChayAdapter(ThongKe.this,R.layout.items_tkmonbanchay,dsContents);
        lvMonBanChay.setAdapter(monBanChayAdapter);

    }
    private void xuLySaoChepCsdlTuAssetVaoHeThong() {
        File file = getDatabasePath(DATABASE_NAME);
        if (!file.exists())
        {
            try
            {
                CoppyDatabaseFromAsset();
                Toast.makeText(ThongKe.this,"Database coppy complete",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(ThongKe.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CoppyDatabaseFromAsset() {
        try
        {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if (!f.exists())
            {
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer))>0)
            {
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e)
        {

            Log.e("LOI_SaoChep",e.toString());
        }
    }

    private String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;

    }
}
