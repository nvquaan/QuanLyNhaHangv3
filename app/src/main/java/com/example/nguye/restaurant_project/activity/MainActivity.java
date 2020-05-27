package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

        //Đọc quyền từ file xml
        sharedPreferences =getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen=sharedPreferences.getInt("maquyen",0);


        getControls();
        addEvents();
        if(maquyen!=1){
            btnReport.setEnabled(false);
            btnSetting.setEnabled(false);
        }
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
                Intent intent=new Intent(MainActivity.this,TabbleOrderActivity.class);
                startActivity(intent);
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ThongKeActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MenuUpdateActivity.class);
                startActivity(intent);
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

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(maquyen==1){
            MenuItem menuItem=menu.add(1,R.menu.main,1,"Cài đặt");
        menuItem.setIcon(R.drawable.settingmain);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_menu) {
            Toast.makeText(MainActivity.this,"Bạn chọn" + item,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_table) {

            Intent intent=new Intent(MainActivity.this, TableList.class);
            startActivity(intent);

        } else if (id == R.id.nav_location) {

        } else if (id == R.id.nav_social) {

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    private void xuLySaoChepCsdlTuAssetVaoHeThong() {
//        File file = getDatabasePath(DATABASE_NAME);
//        if (!file.exists())
//        {
//            try
//            {
//                CoppyDatabaseFromAsset();
//                Toast.makeText(MainActivity.this,"Database coppy complete",Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception e)
//            {
//                Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
//            }
//
//        }
//    }
//
//    private void CoppyDatabaseFromAsset() {
//        try
//        {
//            InputStream inputStream = getAssets().open(DATABASE_NAME);
//            String outFileName = getDatabasePath();
//            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
//            if (!f.exists())
//            {
//                f.mkdir();
//            }
//            OutputStream outputStream = new FileOutputStream(outFileName);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer))>0)
//            {
//                outputStream.write(buffer,0,length);
//            }
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//        }
//        catch (IOException e)
//        {
//
//            Log.e("LOI_SaoChep",e.toString());
//        }
//    }
//
//    private String getDatabasePath(){
//        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
//
//    }
}
