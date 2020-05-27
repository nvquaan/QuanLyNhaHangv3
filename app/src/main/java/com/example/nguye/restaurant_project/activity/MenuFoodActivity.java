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
        xuLySaoChepCsdlTuAssetVaoHeThong();
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
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id");
        int id = bundle.getInt("idbundle");
        //bắt đầu select
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
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

    private void xuLySaoChepCsdlTuAssetVaoHeThong() {
        File file = getDatabasePath(DATABASE_NAME);
        if (!file.exists())
        {
            try
            {
                CoppyDatabaseFromAsset();
                Toast.makeText(MenuFoodActivity.this,"Database coppy complete",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(MenuFoodActivity.this,e.toString(),Toast.LENGTH_LONG).show();
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
