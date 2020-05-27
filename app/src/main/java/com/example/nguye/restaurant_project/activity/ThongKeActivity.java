package com.example.nguye.restaurant_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.fragment.DoanhThuTheoNhanVienFragment;
import com.example.nguye.restaurant_project.fragment.HoatDongTrongNgayFragment;

public class ThongKeActivity extends AppCompatActivity{

    Toolbar toolbar;
    FragmentManager fragmentManager;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke2);
        addControls();



    }

    @SuppressLint("RestrictedApi")
    private void addControls() {

        toolbar= (Toolbar) findViewById(R.id.toolbar_tk);


        fragmentManager=getSupportFragmentManager();

        FragmentTransaction transactionHDtrongngay=fragmentManager.beginTransaction();
        HoatDongTrongNgayFragment hoatDongTrongNgayFragment=new HoatDongTrongNgayFragment();
        transactionHDtrongngay.replace(R.id.contentTK,hoatDongTrongNgayFragment);
        transactionHDtrongngay.commit();
    }

}
