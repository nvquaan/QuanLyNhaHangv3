package com.example.nguye.restaurant_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.KhuVucAdapter;
import com.example.nguye.restaurant_project.adapter.TableAdapter;
import com.example.nguye.restaurant_project.model.KhuVuc;
import com.example.nguye.restaurant_project.model.Table;

import java.util.ArrayList;
//implements KhuVucAdapter.OncardClickListener
public class TableList extends AppCompatActivity implements KhuVucAdapter.OnItemClickListener{
    ArrayList<KhuVuc> khuVucArrayList;
    KhuVucAdapter khuVucAdapter;
    RecyclerView recyclerView; //hien thi list khu vuc
    LinearLayoutManager layoutManager;

    GridView grvTable; // hien thi ban trong khu vuc
    ArrayList<Table> tableArrayList;
    TableAdapter tableAdapter;

    TextView txtIdKhuVuc;
    TextView txtTenBan;
    TextView txtIdTenBan;

    int soPhieuMax;

    final String DATABASE_NAME="QLNhaHang.sqlite";
    public static SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        addControls();
        readData();
        addEvents();
    }



    private void addControls() {
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        khuVucArrayList=new ArrayList<>();
        //khuVucAdapter=new KhuVucAdapter(khuVucArrayList,getApplicationContext());
        khuVucAdapter=new KhuVucAdapter(khuVucArrayList,this,this);
        recyclerView.setAdapter(khuVucAdapter);
//        khuVucAdapter.setOncardClickListener(this);

        grvTable= (GridView) findViewById(R.id.grvTable);
        txtTenBan = (TextView) findViewById(R.id.txtTenBan);
        tableArrayList=new ArrayList<>();
        tableAdapter=new TableAdapter(this,R.layout.items_table,tableArrayList);
        grvTable.setAdapter(tableAdapter);
    }

    private void readData() {
        database=Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM KhuVuc",null);
        khuVucArrayList.clear();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            String ten=cursor.getString(1);
            khuVucArrayList.add(new KhuVuc(id,ten));
        }
        khuVucAdapter.notifyDataSetChanged();


        int idKhuVuc=khuVucArrayList.get(0).getId();
        Cursor cursor1=database.rawQuery("SELECT * FROM Ban WHERE KhuVuc = "+idKhuVuc,null);
        tableArrayList.clear();
        for (int i=0;i<cursor1.getCount();i++){
            cursor1.moveToPosition(i);
            int id=cursor1.getInt(0);
            String ten=cursor1.getString(1);
            boolean trangThai=cursor1.getInt(2)>0;
            int maKhuVuc=cursor1.getInt(3);
            tableArrayList.add(new Table(id,ten,trangThai,maKhuVuc));
        }
        tableAdapter.notifyDataSetChanged();

    }


    private void addEvents() {
        grvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final int idban = tableArrayList.get(i).getId();
                if(tableArrayList.get(i).isTrangThai()){
                    final AlertDialog.Builder builder=new AlertDialog.Builder(TableList.this);
                    builder.setTitle("Chuyển bàn về trạng thái trống?");
                    builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String sql = "Update BAN set TrangThai = 0 where id = " + idban;
                            database.execSQL(sql);
                            tableArrayList.get(i).setTrangThai(false);
                            tableAdapter.notifyDataSetChanged();

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
                }else{
                    final AlertDialog.Builder builder=new AlertDialog.Builder(TableList.this);
                    builder.setTitle("Bạn có muốn đặt bàn này không?");
                    builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String sql = "Update BAN set TrangThai = 1 where id = " + idban;
                            database.execSQL(sql);
                            tableArrayList.get(i).setTrangThai(true);
                            tableAdapter.notifyDataSetChanged();

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
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            setResult(RESULT_CANCELED);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onItemClick(View view, int position) {
        int idKhuVuc =khuVucArrayList.get(position).getId();
         //truy vấn và khai thác dữ liệu
        Cursor cursor1=database.rawQuery("SELECT * FROM Ban where KhuVuc = "+idKhuVuc,null);
        tableArrayList.clear();
        for (int i=0;i<cursor1.getCount();i++){
            cursor1.moveToPosition(i);
            int id=cursor1.getInt(0);
            String ten=cursor1.getString(1);
            boolean trangThai=cursor1.getInt(2)>0;
            int maKhuVuc=cursor1.getInt(3);
            tableArrayList.add(new Table(id,ten,trangThai,maKhuVuc));
        }
        tableAdapter.notifyDataSetChanged();
    }
}
