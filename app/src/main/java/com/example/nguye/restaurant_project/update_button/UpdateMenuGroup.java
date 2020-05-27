package com.example.nguye.restaurant_project.update_button;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateMenuGroup extends AppCompatActivity {
    EditText edtGroupNameInsert;
    Button btnPickGroupPhoto, btnGroupInsert;
    ImageView imgShowGroupPhoto;

    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_group);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnPickGroupPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn ảnh "),PICK_IMAGE_REQUEST);
            }
        });
        /// button xac nhan dong y them vao csdl
        btnGroupInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShowGroupPhoto.getDrawable();
                if(edtGroupNameInsert.getText().toString().isEmpty() || bitmapDrawable == null)
                {
                    Toast.makeText(UpdateMenuGroup.this, "Vui lòng nhập đủ thông tin tên và ảnh!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    xyLyInsertData2Db();
                }
            }
        });
    }

    private void xyLyInsertData2Db() {
        String groupmenu = edtGroupNameInsert.getText().toString();
        // convert imageview to byte;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShowGroupPhoto.getDrawable();
        Bitmap bitmap2 = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        try {
            if(MainActivity.database.INSERTMENUFOOD(groupmenu,b))
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imgShowGroupPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addControls() {
        edtGroupNameInsert = (EditText) findViewById(R.id.edtGroupNameInsert);
        btnGroupInsert = (Button) findViewById(R.id.btnGroupInsert);
        btnPickGroupPhoto = (Button) findViewById(R.id.btnPickGroupPhoto);
        imgShowGroupPhoto = (ImageView) findViewById(R.id.imgShowGroupPhoto);
    }
}
