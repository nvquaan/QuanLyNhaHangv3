package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.update_button.MenuUpdateActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean trangThai = false;
    Button btnMenu, btnBan, btnSetting, btnAbout, btnOrder, btnReport;
    public static Database database;

    int maquyen=0;
    SharedPreferences sharedPreferences;

//    String DATABASE_NAME = "QLNhaHang.sqlite";
//    String DB_PATH_SUFFIX = "/databases/";
//    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //xuLySaoChepCsdlTuAssetVaoHeThong();

        sharedPreferences =getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen=sharedPreferences.getInt("maquyen",0);

        getControls();
        addEvents();
    }

    private void getControls() {
        btnMenu= (Button) findViewById(R.id.btnMenu);
        btnBan= (Button) findViewById(R.id.btnBan);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnOrder= (Button) findViewById(R.id.btnOrder);
        btnReport= (Button) findViewById(R.id.btnReport);
        database = new Database(this,"QLNhaHang.sqlite",null,1);
    }

    private void addEvents() {
        btnBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TableList.class);
                startActivity(intent);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_LONG).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_LONG).show();
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maquyen == 1) {
                    Intent intent = new Intent(MainActivity.this, MenuUpdateActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Don't have permission!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_menu) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            if(maquyen == 1) {
                Intent intent=new Intent(MainActivity.this, MenuUpdateActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Don't have permission!", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_table) {
            Intent intent = new Intent(MainActivity.this, TableList.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bạn có muốn thoát không?");
                builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
