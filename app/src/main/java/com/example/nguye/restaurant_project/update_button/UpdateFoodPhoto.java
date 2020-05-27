package com.example.nguye.restaurant_project.update_button;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.activity.MenuFoodActivity;
import com.example.nguye.restaurant_project.adapter.MenuChiTietAdapter;
import com.example.nguye.restaurant_project.adapter.UpdateFoodPhotoAdapter;
import com.example.nguye.restaurant_project.model.MenuChiTiet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateFoodPhoto extends AppCompatActivity {

    Button btnPickNewFoodPhoto, btnOkNewFoodPhoto;
    ImageView imgNewFoodPhoto;

    Bitmap bitmap;
    int selectedItems = -1;
    private int PICK_IMAGE_REQUEST = 1;

    ListView listView;
    ArrayList<MenuChiTiet> dsChiTiet;
    UpdateFoodPhotoAdapter updateFoodPhotoAdapter;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food_photo);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xyLyHienDsFood2Update();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnPickNewFoodPhoto.setVisibility(View.VISIBLE);
                btnOkNewFoodPhoto.setVisibility(View.VISIBLE);
                imgNewFoodPhoto.setVisibility(View.VISIBLE);

                database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
                selectedItems = dsChiTiet.get(i).getIdMonAn();
                String query = "select * from Food where IDFood ="+selectedItems;
                Cursor cursor = database.rawQuery(query,null);
                while (cursor.moveToNext())
                {
                    byte[] anh = cursor.getBlob(4);
                    bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
                }
                imgNewFoodPhoto.setImageBitmap(bitmap);
            }
        });
        btnPickNewFoodPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn ảnh "),PICK_IMAGE_REQUEST);
            }
        });
        btnOkNewFoodPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgNewFoodPhoto.getDrawable() == null)
                {
                    Toast.makeText(UpdateFoodPhoto.this, "Vui lòng chọn ảnh cho món ăn!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    xuLyCapNhatAnhMonAn();
                }
            }
        });
    }

    private void xuLyCapNhatAnhMonAn() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgNewFoodPhoto.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        // bat dau update
        ContentValues contentValues = new ContentValues();
        contentValues.put("Picture",b);
        database.update("Food",contentValues,"IDFood=?",new String[] {selectedItems+""});
        Toast.makeText(this, "Cập nhật thành công ảnh cho món "+dsChiTiet.get(selectedItems-1).getTenMon(), Toast.LENGTH_SHORT).show();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.lvUpdateFoodPhoto);
        dsChiTiet = new ArrayList<>();
        updateFoodPhotoAdapter = new UpdateFoodPhotoAdapter(UpdateFoodPhoto.this,R.layout.items_updatefoodphoto,dsChiTiet);
        listView.setAdapter(updateFoodPhotoAdapter);

        btnPickNewFoodPhoto = (Button) findViewById(R.id.btnPickNewFoodPhoto);
        btnOkNewFoodPhoto = (Button) findViewById(R.id.btnOkNewFoodPhoto);
        imgNewFoodPhoto = (ImageView) findViewById(R.id.imgNewFoodPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imgNewFoodPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void xyLyHienDsFood2Update() {
        //bắt đầu select
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String query = "select * from Food";
        Cursor cursor = database.rawQuery(query,null);
        dsChiTiet.clear();
        while (cursor.moveToNext())
        {
            int idChiTiet = cursor.getInt(0);
            String sTenMon = cursor.getString(2);
            byte[] anh = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
            MenuChiTiet menuChiTiet = new MenuChiTiet(idChiTiet, sTenMon,bitmap);
            dsChiTiet.add(menuChiTiet);
        }
        cursor.close();
        updateFoodPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.updatephotofood_search,menu);
        MenuItem menuItem = menu.findItem(R.id.updatefoodphotomenusearch);
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query = "select * from Food where sTenMonAn LIKE N'%" + s + "%'";

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String query = "select * from Food where sTenMonAn LIKE '%" + s + "%'";
                Cursor cursor = database.rawQuery(query,null);
                dsChiTiet.clear();
                while (cursor.moveToNext())
                {
                    int idChiTiet = cursor.getInt(0);
                    String sTenMon = cursor.getString(2);
                    byte[] anh = cursor.getBlob(4);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
                    MenuChiTiet menuChiTiet = new MenuChiTiet(idChiTiet, sTenMon,bitmap);
                    dsChiTiet.add(menuChiTiet);
                }
                cursor.close();
                updateFoodPhotoAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
