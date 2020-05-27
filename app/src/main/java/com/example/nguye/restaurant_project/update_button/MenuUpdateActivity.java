package com.example.nguye.restaurant_project.update_button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.NhanVienActivity;

public class MenuUpdateActivity extends AppCompatActivity {
    Button btnAddGroupMenu, btnAddFood, btnAddTable, btnAddKhuvuc, btnUpdate, btnUpdatePictureFood, btnThongtinNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,UpdateActivity.class);
                startActivity(intent);
            }
        });
        btnAddGroupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,UpdateMenuGroup.class);
                startActivity(intent);
            }
        });
        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,InsertTable.class);
                startActivity(intent);
            }
        });
        btnAddKhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,InsertKhuvuc.class);
                startActivity(intent);
            }
        });
        btnUpdatePictureFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,UpdateFoodPhoto.class);
                startActivity(intent);
            }
        });
        btnThongtinNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUpdateActivity.this,NhanVienActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnAddGroupMenu = (Button) findViewById(R.id.btnAddGroupMenu);
        btnAddFood = (Button) findViewById(R.id.btnAddFood);
        btnAddTable = (Button) findViewById(R.id.btnAddTable);
        btnAddKhuvuc = (Button) findViewById(R.id.btnAddKhuVuc);
        btnUpdatePictureFood = (Button) findViewById(R.id.btnUpdatePictureFood);
        btnThongtinNV= (Button) findViewById(R.id.btnThongtinNV);
    }
}
