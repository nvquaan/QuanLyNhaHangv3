package com.example.nguye.restaurant_project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    GridView grvTable;
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


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_ban, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int pos=info.position;
//        Table table=tableArrayList.get(pos);
//
//        int id=item.getItemId();
//        switch (id){
//            case R.id.menuThanhToan:
//                //Thanh toán hóa đơn
//                ThanhToanHoaDon(table);
//                break;
//            case  R.id.menuChinhSua:
//                //Chỉnh sửa hóa đơn
//                ChinhSuaHoaDon(table);
//                break;
//        }
//
//        return super.onContextItemSelected(item);
//    }

//    private void ChinhSuaHoaDon(Table table) {
//        int idban=table.getId();
//        int idKhuVuc=table.getKhuVuc();
//        Intent intent=new Intent(TableList.this,OrderActivity.class);
//        intent.putExtra("idban",idban);
//        intent.putExtra("idKhuVuc",idKhuVuc);
//        startActivity(intent);
//    }
//
//    private void ThanhToanHoaDon(Table table) {
//        int idban=table.getId();
//        int idKhuVuc=table.getKhuVuc();
//        Intent intent=new Intent(TableList.this,ThanhToanActivity.class);
//        intent.putExtra("idban",idban);
//        intent.putExtra("idKhuVuc",idKhuVuc);
//        startActivity(intent);
//    }

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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idban=tableArrayList.get(i).getId();
                int idKhuVuc=tableArrayList.get(i).getKhuVuc();
                if(tableArrayList.get(i).isTrangThai()){
                    Intent intent=new Intent(TableList.this,ThanhToanActivity.class);
                    intent.putExtra("idban",idban);
                    intent.putExtra("idKhuVuc",idKhuVuc);
                    startActivity(intent);

                }else{
                    laySoPhieu();
                    soPhieuMax=soPhieuMax+1;
                    Intent intent=new Intent(TableList.this,OrderActivity.class);
                    intent.putExtra("soPhieuMax",soPhieuMax);
                    intent.putExtra("idban",idban);
                    intent.putExtra("idKhuVuc",idKhuVuc);
                    startActivity(intent);
                }


//                Bundle bundle=new Bundle();
//                bundle.putInt("idKhuVuc",idKhuVuc);
//                bundle.putInt("idban",idban);
//                OrderActivity tabOrder=new OrderActivity();
//                tabOrder.setArguments(bundle);


            }
        });

    }

    private void laySoPhieu() {
        database=Database.initDatabase(this,DATABASE_NAME);
        String sql="select max(id) from HoaDon";
        Cursor cursor=database.rawQuery(sql,null);
        cursor.moveToFirst();
        soPhieuMax=cursor.getInt(0);

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

//        view.setBackgroundColor(Color.BLUE);
    }
}
