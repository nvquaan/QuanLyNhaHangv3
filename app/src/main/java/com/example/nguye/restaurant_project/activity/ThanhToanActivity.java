package com.example.nguye.restaurant_project.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.OrderThanhToanAdapter;
import com.example.nguye.restaurant_project.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThanhToanActivity extends AppCompatActivity {
    TextView txtBan;
    TextView txtSoLuong;
    TextView txtSoPhieu;
    TextView txtTongTien;
    Button btnThanhToan;

    LinearLayout linearLayoutButton;
    LinearLayout linearLayoutSua;
    ImageButton btnQuaylai;
    Button btnThaydoiSL;
    Button btnHuymon;
    Button btnThem;

    ArrayList<Order> orderArrayList;
    ListView lvOrderThanhToan;
    OrderThanhToanAdapter orderThanhToanAdapter;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;

    int idBan;
    int idKhuVuc;
    int soPhieu ;
    int maMon;
    int soluongtungmon;
    int vitri;

    int mafrm;
    int SL, tongTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Intent intent=getIntent();
        idBan=intent.getIntExtra("idban",0);
        idKhuVuc=intent.getIntExtra("idKhuVuc",0);
        mafrm=intent.getIntExtra("mafrm",mafrm);
        addControls();

        addEvents();


    }

    private void addEvents() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrangThaiNgayLapHoaDon();
                updateTrangThaiBan();
//                finish();
            }
        });

        lvOrderThanhToan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
                maMon=orderArrayList.get(i).getMaMon();
                soluongtungmon=orderArrayList.get(i).getSoLuong();
                linearLayoutButton.setVisibility(View.GONE);
                linearLayoutSua.setVisibility(View.VISIBLE);
            }
        });

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutSua.setVisibility(View.GONE);
                linearLayoutButton.setVisibility(View.VISIBLE);

            }
        });
        btnThaydoiSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ThanhToanActivity.this);
                dialog.setContentView(R.layout.custom_dialog_sl);
                dialog.setTitle("Nhập số lượng món ăn:");
                final EditText etSoluong=(EditText)dialog.findViewById(R.id.etSoluong);
                Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                // if button is clicked, close the custom dialog
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(ThanhToanActivity.this,"Không thay đổi",Toast.LENGTH_SHORT).show();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int soLuong= Integer.parseInt(etSoluong.getText().toString());
//                        Toast.makeText(context,soLuong+"",Toast.LENGTH_LONG).show();
                        orderArrayList.set(vitri, new Order(orderArrayList.get(vitri).getMaMon(),
                                orderArrayList.get(vitri).getTenMon(),orderArrayList.get(vitri).getDonGia(),soLuong));
                        dialog.dismiss();
                        orderThanhToanAdapter.notifyDataSetChanged();
                        updateMonAnCTHD(soLuong);
                        xuLyThayDoiSLTongTien();
                        updateHoaDon();
                    }

                });
                dialog.show();
            }
        });

        btnHuymon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ThanhToanActivity.this,vitri+""+maMon,Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder=new AlertDialog.Builder(ThanhToanActivity.this);
                builder.setTitle("Bạn muốn hủy "+orderArrayList.get(vitri).getTenMon()+"?");
                builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xoaMonAn();  //xoa mon an trong bảng ChiTietHoaDon
                        orderArrayList.remove(vitri); //xoa mon an trong listview giao dien

                        xuLyThayDoiSLTongTien();
                        updateHoaDon();
                        orderThanhToanAdapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ThanhToanActivity.this, OrderActivity.class);
                intent.putExtra("mafrm",mafrm);
                intent.putExtra("soPhieu",soPhieu);
                intent.putExtra("BanOrder",txtBan.getText());
                intent.putExtra("tongtien",Integer.parseInt(txtTongTien.getText().toString()));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
//            Intent intent=new Intent(ThanhToanActivity.this, TableList.class);

            if(orderArrayList.size()==0){
                final AlertDialog.Builder builder=new AlertDialog.Builder(ThanhToanActivity.this);
                builder.setTitle("Bạn muốn hủy hóa đơn?");
                builder.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        huyHoaDon();
                        updateTrangThaiBan();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void huyHoaDon() {
        database=Database.initDatabase(this,DATABASE_NAME);
        database.delete("HoaDon","id=?",new String[]{soPhieu+""});

    }

    private void updateMonAnCTHD(int soLuong) {
        database=Database.initDatabase(this,DATABASE_NAME);
        ContentValues row=new ContentValues();
        row.put("iSoLuong",soLuong);
        database.update("ChiTietHoaDon",row,"id=? and iMaMon=?",new String[]{soPhieu+"",maMon+""});
    }


    private void xuLyThayDoiSLTongTien() {
        SL=0;
        tongTien=0;
        for(int i=0;i<orderArrayList.size();i++){
            SL = SL+ orderArrayList.get(i).getSoLuong();
            tongTien= tongTien+(orderArrayList.get(i).getSoLuong()*orderArrayList.get(i).getDonGia());

        }
        txtSoLuong.setText(SL+"");
        txtTongTien.setText(tongTien+"");
    }


    private void updateHoaDon() {
        database=Database.initDatabase(this,DATABASE_NAME);
        ContentValues row=new ContentValues();
        row.put("iTongTien",tongTien);
        database.update("HoaDon",row,"id=?",new String[]{soPhieu+""});


    }

    private void xoaMonAn() {
        database=Database.initDatabase(this,DATABASE_NAME);
        database.delete("ChiTietHoaDon","iMaMon=? and id=?",new String[]{maMon+"",soPhieu+""});


    }

    private void updateTrangThaiBan() {
        database=Database.initDatabase(this,DATABASE_NAME);
        boolean trangThai=false;
        ContentValues row=new ContentValues();
        row.put("TrangThai",trangThai);
        database.update("Ban",row,"id=?",new String[]{idBan+""});
        if(mafrm==0){
            Intent intent=new Intent(ThanhToanActivity.this,TableList.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(ThanhToanActivity.this,TabbleOrderActivity.class);
            startActivity(intent);
        }

    }

    private void updateTrangThaiNgayLapHoaDon() {
        database=Database.initDatabase(this,DATABASE_NAME);
        boolean trangThai=true;
        Calendar calendar=Calendar.getInstance();
//       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);
        String ngayLap = iNgay + "/" + (iThang+1) + "/" + iNam;
        ContentValues row=new ContentValues();
        row.put("dNgayLap",ngayLap);
        row.put("bTrangThai",trangThai);
        database.update("HoaDon",row,"id=?",new String[]{soPhieu+""});
//        Toast.makeText(this,""+soPhieu,Toast.LENGTH_LONG).show();
    }

    private void addControls() {
        txtBan= (TextView) findViewById(R.id.txtTenBanOrder);
        txtSoLuong= (TextView) findViewById(R.id.txtSL);
        txtSoPhieu= (TextView) findViewById(R.id.txtSoPhieu);
        txtTongTien= (TextView) findViewById(R.id.txttongTien);
        btnThanhToan= (Button) findViewById(R.id.btnThanhtoan);
        btnThem= (Button) findViewById(R.id.btnThem);
        btnQuaylai= (ImageButton) findViewById(R.id.btnQuaylai);
        btnThaydoiSL= (Button) findViewById(R.id.btnThaydoiSL);
        btnHuymon= (Button) findViewById(R.id.btnHuymon);
        linearLayoutButton= (LinearLayout) findViewById(R.id.linearButton);
        linearLayoutSua= (LinearLayout) findViewById(R.id.linearSua);

        lvOrderThanhToan= (ListView) findViewById(R.id.lvOrder);
        orderArrayList=new ArrayList<>();
        orderThanhToanAdapter=new OrderThanhToanAdapter(this,R.layout.item_order_thanhtoan,orderArrayList);
        lvOrderThanhToan.setAdapter(orderThanhToanAdapter);

        xuLyHienDanhSachOrder();
        xuLyThongTinBan();
    }

    private void xuLyHienDanhSachOrder() {
        int tongTien = 0, tongSoLuong=0;
        database=Database.initDatabase(this,DATABASE_NAME);
        boolean trangThai=false;
        String sql="select HoaDon.id, iTongTien, iMaBan, iKhuVuc, iMaMon, sTenMonAn, iSoLuong, iGiaTien " +
                "from (HoaDon inner join ChiTietHoaDon on HoaDon.id=ChiTietHoaDon.id) " +
                "inner join Food on Food.IDFood=ChiTietHoaDon.iMaMon where iMaBan="+idBan+" and bTrangThai= 0";
        Cursor cursor=database.rawQuery(sql,null);
        orderArrayList.clear();
        while (cursor.moveToNext()){
            soPhieu=cursor.getInt(0);
            tongTien=cursor.getInt(1);
            int maBan=cursor.getInt(2);
            int khuVuc=cursor.getInt(3);
            int maMon=cursor.getInt(4);
            String tenMon=cursor.getString(5);
            int soLuong=cursor.getInt(6);
            int donGia=cursor.getInt(7);
            orderArrayList.add(new Order(maMon,tenMon,donGia,soLuong));
            tongSoLuong=tongSoLuong+soLuong;
        }
        cursor.close();
        orderThanhToanAdapter.notifyDataSetChanged();

        txtSoPhieu.setText(soPhieu+"");
        txtTongTien.setText(tongTien+"");
        txtSoLuong.setText(tongSoLuong+"");
//        Toast.makeText(this,tongSoLuong+"",Toast.LENGTH_LONG).show();
    }

    private void xuLyThongTinBan() {
        String tenKhuVuc="";
        String tenBan="";
        database=Database.initDatabase(this,DATABASE_NAME);

        Cursor cursor=database.rawQuery("SELECT * FROM KhuVuc where id="+idKhuVuc,null);
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            tenKhuVuc=cursor.getString(1);
        }
        Cursor cursor1=database.rawQuery("SELECT * FROM Ban WHERE id = "+idBan,null);
        for (int i=0;i<cursor1.getCount();i++) {
            cursor1.moveToPosition(i);
            int id = cursor1.getInt(0);
            tenBan = cursor1.getString(1);
        }

        txtBan.setText(tenBan+"-"+tenKhuVuc);
    }
}
