package com.example.nguye.restaurant_project.update_button;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.Database;

import java.util.ArrayList;

public class InsertTable extends AppCompatActivity {
    Button btnTableNameInsert;
    EditText edtTableNameInsert;

    // try spinner (su dung spinner hien thi ds cac khu vuc - tranh nhap sai du lieu)
    ArrayList<ModelKhuvucId> dsContents;
    AdapterKhuvucId adapter;
    Spinner spinnerKhuvuc;
    int lastSelectedItem = -1; // dung de luu vi tri cua item duoc chon de lay ra gia tri string

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_table);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTableNameInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTableNameInsert.getText().toString().isEmpty())
                {
                    Toast.makeText(InsertTable.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    XuLyInsert2Db();
                }
            }
        });
        xuLyLoadData2Spinner();
        spinnerKhuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lastSelectedItem = dsContents.get(i).getId();
                Toast.makeText(InsertTable.this, "Bạn chọn "+ dsContents.get(i).getTenKhuVuc(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void xuLyLoadData2Spinner() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("KhuVuc",null,null,null,null,null,null);
        dsContents.clear();
        while (cursor.moveToNext())
        {
            int idKhuvuc     = cursor.getInt(0);
            String tenkhuvuc = cursor.getString(1);
            ModelKhuvucId modelKhuvucId = new ModelKhuvucId(idKhuvuc,tenkhuvuc);
            dsContents.add(modelKhuvucId);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void XuLyInsert2Db() {
        database= Database.initDatabase(this,DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenBan",edtTableNameInsert.getText().toString());
        contentValues.put("TrangThai",0);
        contentValues.put("KhuVuc",lastSelectedItem);
        database.insert("Ban",null,contentValues);
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
    }

    private void addControls() {
        btnTableNameInsert = (Button) findViewById(R.id.btnTableNameInsert);
        edtTableNameInsert = (EditText) findViewById(R.id.edtTableNameInsert);

        spinnerKhuvuc = (Spinner) findViewById(R.id.spTableNameInsert);
        dsContents = new ArrayList<>();
        adapter = new AdapterKhuvucId(InsertTable.this,R.layout.items_spkhuvuc,dsContents);
        spinnerKhuvuc.setAdapter(adapter);
    }
}
