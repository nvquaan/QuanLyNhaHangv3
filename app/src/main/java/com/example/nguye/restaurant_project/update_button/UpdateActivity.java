package com.example.nguye.restaurant_project.update_button;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nguye.restaurant_project.activity.MainActivity;
import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.MenuAdapter;
import com.example.nguye.restaurant_project.model.Menu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.content.Context.MODE_PRIVATE;

public class UpdateActivity extends AppCompatActivity {
    Button btnOkUpdate, btnShowPhoto;
    EditText edtsTenMon,edtGiaTien;
    ImageView imgShowPhoto;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    // try spinner (su dung spinner hien thi ds cac nhom mon an - tranh nhap sai du lieu)
    ArrayList<ModelGroupId> dsContents;
    AdapterGroupId adapter;
    Spinner spinnerMenu;
    int lastSelectedItem = -1; // dung de luu vi tri cua item duoc chon de lay ra gia tri string

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addControls();
        addEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imgShowPhoto);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEvents() {
        // button chọn ảnh để thêm vào csdl
        btnShowPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn ảnh "),PICK_IMAGE_REQUEST);
            }
        });
        // xu ly khi bam nút đồng ý thêm vào csdl
        btnOkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShowPhoto.getDrawable();
                if (edtsTenMon.getText().toString().isEmpty() || edtGiaTien.getText().toString().isEmpty()|| bitmapDrawable == null)
                {
                    Toast.makeText(UpdateActivity.this, "Vui lòng nhập đầy đủ thông tin trước khi thêm!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    xyLyInsertData2Db();
                }
            }
        });
        xuLyLoadData2Spinner();
        // hien thi ds cac items theo lua chon
        spinnerMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lastSelectedItem = dsContents.get(i).getIdGroupMenu();
                Toast.makeText(UpdateActivity.this, "Bạn chọn " + dsContents.get(i).getGroupName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void xuLyLoadData2Spinner() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("GroupMenu",null,null,null,null,null,null);
        dsContents.clear();
        while (cursor.moveToNext())
        {
            int idGroupMenu = cursor.getInt(0);
            String groupname = cursor.getString(1);
            ModelGroupId modelGroupId = new ModelGroupId(idGroupMenu,groupname);
            dsContents.add(modelGroupId);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void xyLyInsertData2Db() {
        Integer giatien = Integer.parseInt(edtGiaTien.getText().toString());
        String tenmon = edtsTenMon.getText().toString();
        // convert imageview to byte;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShowPhoto.getDrawable();
        Bitmap bitmap2 = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        try {
            if(MainActivity.database.INSERTFOOD(lastSelectedItem,tenmon,giatien,b))
            {
                Toast.makeText(this,"Thành công!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Lỗi rồi",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addControls() {
        btnShowPhoto = (Button) findViewById(R.id.btnPickPhoto);
        btnOkUpdate = (Button) findViewById(R.id.btnUpdateOk);
        edtsTenMon = (EditText) findViewById(R.id.edtsTenMon);
        edtGiaTien = (EditText) findViewById(R.id.edtiGiaTien);
        imgShowPhoto = (ImageView) findViewById(R.id.imgShowPhoto);

        spinnerMenu = (Spinner) findViewById(R.id.spinnerMenu);
        dsContents = new ArrayList<>();
        adapter = new AdapterGroupId(UpdateActivity.this,R.layout.items_spgroupmenu,dsContents);
        spinnerMenu.setAdapter(adapter);
    }
}
