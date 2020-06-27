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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.MenuAdapter;
import com.example.nguye.restaurant_project.model.Menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    TextView txtIdGroupMenu;
    ListView lvMenu;
    ImageView imgMenu;
    ArrayList<Menu> dsContents;
    MenuAdapter menuAdapter;
    public static int idGroupFood;
    String DATABASE_NAME = "QLNhaHang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu2);
//        CoppyDatabaseFromAsset();
//        xuLySaoChepCsdlTuAssetVaoHeThong();
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyHienDsMenu();
        xuLyClickItems();
    }

    private void xuLyClickItems() {
        //Goi den menu chi tiet
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idGroupFood=dsContents.get(i).getTxtIdGroup();
//                txtIdGroupMenu = view.findViewById(R.id.txtGroupMenu);
//                int idGroup = Integer.parseInt(txtIdGroupMenu.getText().toString());
                Intent intent = new Intent(MenuActivity.this,MenuFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyHienDsMenu() {
//        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        database=Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.query("GroupMenu",null,null,null,null,null,null);
        dsContents.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String groupname = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
            Menu menu = new Menu(id,groupname,bitmap);
            dsContents.add(menu);
        }
        cursor.close();
        menuAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        lvMenu = (ListView) findViewById(R.id.lvMenuMonAn);
        imgMenu = (ImageView) findViewById(R.id.imgView);
        dsContents = new ArrayList<>();
        menuAdapter = new MenuAdapter(MenuActivity.this,R.layout.items_menu,dsContents);
        lvMenu.setAdapter(menuAdapter);

    }


}
