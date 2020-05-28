package com.example.nguye.restaurant_project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.ContentsAdapter;
import com.example.nguye.restaurant_project.model.Contents;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity{
    ListView lvIcon;
    ArrayList<Contents> dsContents;
    ContentsAdapter contentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        addControls();
        addEvents();
    }
    private void addEvents() {

    }

    private void addControls() {
        lvIcon = (ListView) findViewById(R.id.lv_about_us);
        dsContents = new ArrayList<>();

        dsContents.add(new Contents("Ứng dụng quản lý nhà hàng","By CNTT1 - K58",R.drawable.ic_menu_home));
        dsContents.add(new Contents("Giáo viên hướng dẫn","Trần Vũ Hiếu",R.drawable.ic_face));
        dsContents.add(new Contents("Copyright","Copyright © 2020 QuanNV - HoaiPV. All rights reserved",R.drawable.ic_menu_about));


        contentsAdapter = new ContentsAdapter(AboutActivity.this,R.layout.items,dsContents);
        lvIcon.setAdapter(contentsAdapter);

        lvIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });


    }

}
