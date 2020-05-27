package com.example.nguye.restaurant_project.update_button;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.Database;

public class InsertKhuvuc extends AppCompatActivity {
    EditText edtTenKhuVucInsert;
    Button btnTenKhuVucInsert;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_khuvuc);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTenKhuVucInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTenKhuVucInsert.getText().toString().isEmpty())
                {
                    Toast.makeText(InsertKhuvuc.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    xuLyInsert2Db();
                }
            }
        });
    }

    private void xuLyInsert2Db() {
        database= Database.initDatabase(this,DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenKhuVuc",edtTenKhuVucInsert.getText().toString());
        database.insert("KhuVuc",null,contentValues);
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();

    }

    private void addControls() {
        edtTenKhuVucInsert = (EditText) findViewById(R.id.edtTenKhuVucInsert);
        btnTenKhuVucInsert = (Button) findViewById(R.id.btnTenKhuVucInsert);
    }
}
