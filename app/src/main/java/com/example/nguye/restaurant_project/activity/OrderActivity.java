package com.example.nguye.restaurant_project.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.adapter.MenuChiTietMonAnAdapter;
import com.example.nguye.restaurant_project.adapter.OrderAdapter;
import com.example.nguye.restaurant_project.adapter.RecycleMenuOrderAdapter;
import com.example.nguye.restaurant_project.model.MenuChiTiet;
import com.example.nguye.restaurant_project.model.Order;
import com.example.nguye.restaurant_project.model.RecycleMenuOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity implements RecycleMenuOrderAdapter.OnItemClickListener,OrderAdapter.OnItemClickListener {
    int SL;
    public  int tongTien;
    TextView txtTenBanOrder;
    TextView txtSoPhieu;
    public TextView txtSL;
    public TextView txttongTien;

    ArrayList<RecycleMenuOrder> dsrecycleMenuOrders;
    RecycleMenuOrderAdapter recycleMenuOrderAdapter;
    RecyclerView recyclerViewMenuOrder;
    LinearLayoutManager layoutManager;

    ArrayList<MenuChiTiet> dsmenuChiTietMonAn;
    GridView gvFoodOrder;
    MenuChiTietMonAnAdapter menuChiTietMonAnAdapter;

    ArrayList<Order> dsOrderMonAn;
    ListView lvOrder;
    OrderAdapter orderAdapter;
    Order order;

    Button btnLuu;
    ImageButton btnTopDown;


    String DATABASE_NAME = "QLNhaHang.sqlite";
//    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database;

    int idban;
    int idKhuVuc;
    String tenBanOrder;
    int soPhieu;
    int tongtien;
    int soPhieuMax;

    int id=-1;
    int ma;
    int mafrm;

    int manv = 0;
    SharedPreferences sharedPreferences;

//    View view;
//    Database db;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view =inflater.inflate(R.layout.fragment_order,container,false);
//        idban=getArguments().getInt("idban");
//        idKhuVuc=getArguments().getInt("idKhuVuc");
//        addControls();
//        xuLyThongTinBan();
//        xuLyHienDsMenu();
//        xuLyHienMonAnBanDau();
//        addEvents();
//        return view;
//    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        xuLySaoChepCsdlTuAssetVaoHeThong();
            sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            manv = sharedPreferences.getInt("manv", 0);
        Intent intent = getIntent();
        soPhieuMax=intent.getIntExtra("soPhieuMax",0);
        idban = intent.getIntExtra("idban",0);
        idKhuVuc=intent.getIntExtra("idKhuVuc",0);
        soPhieu=intent.getIntExtra("soPhieu",0);
        tenBanOrder=intent.getStringExtra("BanOrder");
        tongtien= intent.getIntExtra("tongtien",0);
        ma=intent.getIntExtra("ma",0);
        mafrm=intent.getIntExtra("mafrm",0);
        addControls();
        xuLyThongTinBanSoPhieu();
        xuLyHienDsMenu();
        xuLyHienMonAnBanDau();
        addEvents();
            Toast.makeText(this,idban+"",Toast.LENGTH_LONG).show();
    }

    private void addEvents() {
        btnTopDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lvOrder.getVisibility()==View.GONE) {
                    lvOrder.setVisibility(View.VISIBLE);
                    btnTopDown.setImageResource(R.drawable.up_arrow);
                }else{
                    lvOrder.setVisibility(View.GONE);
                    btnTopDown.setImageResource(R.drawable.down_arrow);
                }
            }
        });


        gvFoodOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                xuLyClickItemGrid(position);
//                int id=dsmenuChiTietMonAn.get(i).getIdMonAn();
//                Toast.makeText(OrderActivity.this,id+"",Toast.LENGTH_LONG).show();
                xuLyThayDoiSLTongTien();



                dsmenuChiTietMonAn.set(position,new MenuChiTiet(dsmenuChiTietMonAn.get(position).getIdMonAn(),dsmenuChiTietMonAn.get(position).getIdGroup(),
                        dsmenuChiTietMonAn.get(position).getTenMon(),dsmenuChiTietMonAn.get(position).getDonGia(),dsmenuChiTietMonAn.get(position).getSoluong()+1));
                menuChiTietMonAnAdapter.notifyDataSetChanged();

//                Toast.makeText(OrderActivity.this,dsmenuChiTietMonAn.get(position).getSoluong()+"",Toast.LENGTH_LONG).show();

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dsOrderMonAn.size()==0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(OrderActivity.this);
                    builder.setTitle("Bạn chưa chọn món!");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
//                    Intent intent=new Intent(OrderActivity.this,TableList.class);
//                    startActivity(intent);
                }else {
                    if(idban!=0){
                        addHoaDon();
                        for(int i=0;i<dsOrderMonAn.size();i++){
                            addChiTietHoaDon(i);
                        }

                        updateTrangThaiBan();
                        finish();
                    }else {
                        for(int i=0;i<dsOrderMonAn.size();i++){

                            themChiTietHoaDon(i);
                        }
                        tongtien=tongtien+Integer.parseInt(txttongTien.getText().toString());
                        updateHoaDon(tongtien);
                        finish();
//                        Toast.makeText(OrderActivity.this,tongtien+"",Toast.LENGTH_LONG).show();

                    }


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


    private void updateHoaDon(int tongtien) {
        database=Database.initDatabase(this,DATABASE_NAME);
        ContentValues row=new ContentValues();
        row.put("iTongTien",tongtien);
        database.update("HoaDon",row,"id=?",new String[]{soPhieu+""});
        if(mafrm==0){
            Intent intent=new Intent(this,TableList.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(this,TabbleOrderActivity.class);
            startActivity(intent);
        }


    }
    private void themChiTietHoaDon(int i) {
        database=Database.initDatabase(this,DATABASE_NAME);
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",soPhieu);
        contentValues.put("iMaMon",dsOrderMonAn.get(i).getMaMon());
        contentValues.put("iSoLuong",dsOrderMonAn.get(i).getSoLuong());
        database.insert("ChiTietHoaDon",null,contentValues);


    }

    private void updateTrangThaiBan() {
//        database=getWritableDatabase();
        database=Database.initDatabase(this,DATABASE_NAME);
        boolean trangThai=true;
//        String sql ="UPDATE Ban set TrangThai="+trangThai+"WHERE id="+idban;
//        try{
//            MainActivity.database.QueryData(sql);
//            Toast.makeText(this,"Thành công",Toast.LENGTH_LONG).show();
//            Intent intent=new Intent(OrderActivity.this,TableList.class);
//            startActivity(intent);
//
//        }catch (Exception e){
//            Toast.makeText(this,"Lỗi!!!",Toast.LENGTH_LONG).show();
//        }

        ContentValues row=new ContentValues();
        row.put("TrangThai",trangThai);
        database.update("Ban",row,"id=?",new String[]{idban+""});
        if(ma==0){
            Intent intent=new Intent(this,TableList.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this,TabbleOrderActivity.class);
            startActivity(intent);
        }

    }

    private void addChiTietHoaDon(int i) {
        database=Database.initDatabase(this,DATABASE_NAME);
        boolean kq;
//        String sqlMax="SELECT MAX(id) FROM HoaDon";
//        Cursor cursor = database.rawQuery(sqlMax,null);
//        cursor.moveToFirst();
//        int id=cursor.getInt(0);
        int id=soPhieuMax;
        int maMon=dsOrderMonAn.get(i).getMaMon();
        int soLuong=dsOrderMonAn.get(i).getSoLuong();

        ContentValues row=new ContentValues();
        row.put("id",id);
        row.put("iMaMon",maMon);
        row.put( "iSoLuong" ,soLuong);
        database.insert("ChiTietHoaDon",null,row);
//        Toast.makeText(OrderActivity.this,"Bạn vừa mới thêm"+r,Toast.LENGTH_SHORT).show();
    }

    private void addHoaDon() {
        database=Database.initDatabase(this,DATABASE_NAME);
        Calendar calendar=Calendar.getInstance();
//       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);
        String ngayLap = iNgay + "/" + (iThang+1) + "/" + iNam;
//        String ngayLap=sdf.format(now.getTime());
        Toast.makeText(this,ngayLap+"",Toast.LENGTH_LONG).show();
        int tongTien=Integer.parseInt(txttongTien.getText().toString());
//        Intent intent = getIntent();
//        int idban = intent.getIntExtra("idban",0);
//        int idKhuVuc=intent.getIntExtra("idKhuVuc",0);
        int maNV=manv;
//        String sql="insert into HoaDon values(null,'"+ngayLap+"',"+tongTien+",null,"+idban+","+maNV+","+idKhuVuc+")";
//        String sql="INSERT INTO HoaDon(dNgayLap,iTongTien,iMaBan,iMaNV,idKhuVuc) VALUES(datetime('now', 'localtime'),"+tongTien+","+idban+","+maNV+","+idKhuVuc+")";
//        try{
//            MainActivity.database.QueryData(sql);
//            Toast.makeText(this,"Thành công!",Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            Toast.makeText(this,"Lỗi!",Toast.LENGTH_SHORT).show();
//        }

//        try {
//            if(MainActivity.database.InsertHoaDon(id,ngayLap,tongTien,false,idban,maNV,idKhuVuc))
//            {
//                Toast.makeText(this,"Thành công!",Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(this,"Lỗi rồi",Toast.LENGTH_SHORT).show();Toast.makeText(this,"Lỗi rồi",Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ContentValues row=new ContentValues();
        row.put("dNgayLap",ngayLap);
        row.put("iTongTien",tongTien);
        row.put( "iMaBan" ,idban);
        row.put("iMaNV",maNV);
        row.put("iKhuVuc",idKhuVuc);
        database.insert("HoaDon",null,row);
//        Toast.makeText(OrderActivity.this,"Bạn vừa mới thêm"+r,Toast.LENGTH_SHORT).show();
    }

    private void xuLyThayDoiSLTongTien() {
        SL=0;
        tongTien=0;
        for(int i=0;i<dsOrderMonAn.size();i++){
            SL = SL+ dsOrderMonAn.get(i).getSoLuong();
            tongTien= tongTien+(dsOrderMonAn.get(i).getSoLuong()*dsOrderMonAn.get(i).getDonGia());

        }
        txtSL.setText(SL+"");
        txttongTien.setText(tongTien+"");
    }



    private void xuLyClickItemGrid(int position) {
        String tenMon = dsmenuChiTietMonAn.get(position).getTenMon();
        int donGia = dsmenuChiTietMonAn.get(position).getDonGia();
        int maMon = dsmenuChiTietMonAn.get(position).getIdMonAn();

//        Toast.makeText(OrderActivity.this, dsOrderMonAn.size() + "", Toast.LENGTH_LONG).show();
        if (dsOrderMonAn.size() == 0) {
            dsOrderMonAn.add(new Order(maMon, tenMon, donGia, 1));
            orderAdapter.notifyDataSetChanged();

        } else {
            int i = 0;
            while (i < dsOrderMonAn.size()) {
//                Toast.makeText(OrderActivity.this, dsOrderMonAn.get(i).getMaMon() + "", Toast.LENGTH_LONG).show();
                if (dsOrderMonAn.get(i).getMaMon() == maMon) {
                    int sl=dsOrderMonAn.get(i).getSoLuong()+1;
                    order = new Order(maMon, tenMon, donGia, sl);
                    dsOrderMonAn.set(i, order);
                    orderAdapter.notifyDataSetChanged();
                    break;
                }else{
                    i++;
                    if(i>=dsOrderMonAn.size()){
                        dsOrderMonAn.add(new Order(maMon, tenMon, donGia, 0));
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }

        }
    }

    private void xuLyHienMonAnBanDau() {
        int id=dsrecycleMenuOrders.get(0).getIdRecycleMenuOrder();
        hienMonAnTheoId(id);
    }

    private void hienMonAnTheoId(int id) {
        String query = "select * from Food where IDGroupMenu = " + id;
        Cursor cursor = database.rawQuery(query,null);
        dsmenuChiTietMonAn.clear();
        while (cursor.moveToNext())
        {
            int idChiTietOrder = cursor.getInt(0);
            int idGroup=cursor.getInt(1);
            String sTenMonOrder = cursor.getString(2);
            int giaChiTietOrder = cursor.getInt(3);

            MenuChiTiet menuChiTietOrder = new MenuChiTiet(idChiTietOrder,idGroup,sTenMonOrder,giaChiTietOrder,0);
            dsmenuChiTietMonAn.add(menuChiTietOrder);
        }
        cursor.close();
        menuChiTietMonAnAdapter.notifyDataSetChanged();
    }


    private void xuLyHienDsMenu() {
//        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        database=Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.query("GroupMenu",null,null,null,null,null,null);
        dsrecycleMenuOrders.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String groupname = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
            RecycleMenuOrder recycleMenuOrder = new RecycleMenuOrder(id,groupname);
            dsrecycleMenuOrders.add(recycleMenuOrder);
        }
        cursor.close();
        recycleMenuOrderAdapter.notifyDataSetChanged();
    }

    private void xuLyThongTinBanSoPhieu() {
//        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        if(idban==0){

            txtTenBanOrder.setText(tenBanOrder);
            txtSoPhieu.setText(soPhieu+"");
        }
        else {
            String tenKhuVuc="";
            String tenBan="";
            database=Database.initDatabase(this,DATABASE_NAME);
//        Intent intent = getIntent();
//        idban = intent.getIntExtra("idban",0);
//        idKhuVuc=intent.getIntExtra("idKhuVuc",0);

            Cursor cursor=database.rawQuery("SELECT * FROM KhuVuc where id="+idKhuVuc,null);
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                int id=cursor.getInt(0);
                tenKhuVuc=cursor.getString(1);
            }
            Cursor cursor1=database.rawQuery("SELECT * FROM Ban WHERE id = "+idban,null);
            for (int i=0;i<cursor1.getCount();i++) {
                cursor1.moveToPosition(i);
                int id = cursor1.getInt(0);
                tenBan = cursor1.getString(1);

            }
            txtSoPhieu.setText(soPhieuMax+"");
            txtTenBanOrder.setText(tenBan+"-"+tenKhuVuc);
        }

    }

    private void addControls() {
        // xy ly recycle view hien thi danh sach menu
        txtTenBanOrder = (TextView) findViewById(R.id.txtTenBanOrder);
        txtSL= (TextView) findViewById(R.id.txtSL);
        txttongTien= (TextView) findViewById(R.id.txttongTien);
        txtSoPhieu= (TextView) findViewById(R.id.txtSoPhieu);

        btnLuu= (Button) findViewById(R.id.btnLuu);
        btnTopDown= (ImageButton) findViewById(R.id.btnTopDown);

        lvOrder= (ListView) findViewById(R.id.lvOrder);
        dsOrderMonAn=new ArrayList<>();
        orderAdapter=new OrderAdapter(this,R.layout.items_order,dsOrderMonAn,this);
        lvOrder.setAdapter(orderAdapter);


        recyclerViewMenuOrder = (RecyclerView) findViewById(R.id.recycler_view_Order);
        recyclerViewMenuOrder.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewMenuOrder.setLayoutManager(layoutManager);

        dsrecycleMenuOrders = new ArrayList<>();
        recycleMenuOrderAdapter = new RecycleMenuOrderAdapter(dsrecycleMenuOrders,this,this);
        recyclerViewMenuOrder.setAdapter(recycleMenuOrderAdapter);

        //// xu ly gridview hien thi danh sach cac mon an (food)
        gvFoodOrder = (GridView) findViewById(R.id.gvFoodOrder);
        dsmenuChiTietMonAn = new ArrayList<>();
        menuChiTietMonAnAdapter = new MenuChiTietMonAnAdapter(this,R.layout.items_gv_monan,dsmenuChiTietMonAn);
        gvFoodOrder.setAdapter(menuChiTietMonAnAdapter);
    }



    @Override
    public void onItemClick(View view, int position) {
        int id=dsrecycleMenuOrders.get(position).getIdRecycleMenuOrder();
        xuLyHienGridViewFood(id,position);
    }

    private void xuLyHienGridViewFood( int id,int position) {
        database=Database.initDatabase(this,DATABASE_NAME);
        hienMonAnTheoId(id);

        luuSoLuongTungMon();

    }

    private void luuSoLuongTungMon() {
        for(int i=0;i<dsOrderMonAn.size();i++){
            for(int j=0;j<dsmenuChiTietMonAn.size();j++){
                if(dsOrderMonAn.get(i).getMaMon()==dsmenuChiTietMonAn.get(j).getIdMonAn()) {
                    dsmenuChiTietMonAn.set(j, new MenuChiTiet(dsmenuChiTietMonAn.get(j).getIdMonAn(), dsmenuChiTietMonAn.get(j).getIdGroup(),
                            dsmenuChiTietMonAn.get(j).getTenMon(), dsmenuChiTietMonAn.get(j).getDonGia(), dsOrderMonAn.get(i).getSoLuong()));

                }
            }
        }
        menuChiTietMonAnAdapter.notifyDataSetChanged();
    }

    @Override
    public void btnCongonItemClick(View view, int position) {
        String tenMon = dsOrderMonAn.get(position).getTenMon();
        int donGia = dsOrderMonAn.get(position).getDonGia();
        int maMon = dsOrderMonAn.get(position).getMaMon();
        int sl=dsOrderMonAn.get(position).getSoLuong()+1;
        order = new Order(maMon, tenMon, donGia, sl);
        dsOrderMonAn.set(position, order);
        orderAdapter.notifyDataSetChanged();

        xuLyThayDoiSLTongTien();

        thayDoiSoLuongTungMonAn(position,sl);



    }


    @Override
    public void btnTruonItemClick(View view, int position) {
        String tenMon = dsOrderMonAn.get(position).getTenMon();
        int donGia = dsOrderMonAn.get(position).getDonGia();
        int maMon = dsOrderMonAn.get(position).getMaMon();
        int sl=dsOrderMonAn.get(position).getSoLuong()-1;
        if(sl==0){
            thayDoiSoLuongTungMonAn(position,sl);
            dsOrderMonAn.remove(position);
        }else {
            order = new Order(maMon, tenMon, donGia, sl);
            dsOrderMonAn.set(position, order);
            thayDoiSoLuongTungMonAn(position,sl);
        }

        orderAdapter.notifyDataSetChanged();

        xuLyThayDoiSLTongTien();


    }

    private void thayDoiSoLuongTungMonAn(int position,int soluong) {
        for(int i=0;i<dsmenuChiTietMonAn.size();i++){
            if (dsOrderMonAn.get(position).getMaMon()==dsmenuChiTietMonAn.get(i).getIdMonAn()){
                dsmenuChiTietMonAn.set(i, new MenuChiTiet(dsmenuChiTietMonAn.get(i).getIdMonAn(), dsmenuChiTietMonAn.get(i).getIdGroup(),
                        dsmenuChiTietMonAn.get(i).getTenMon(), dsmenuChiTietMonAn.get(i).getDonGia(), soluong));
            }
        }

        menuChiTietMonAnAdapter.notifyDataSetChanged();
    }

    @Override
    public void btnSoLuongonItemClick(View view, final int position) {
        final Dialog dialog = new Dialog(this);
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
                Toast.makeText(OrderActivity.this,"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong= Integer.parseInt(etSoluong.getText().toString());
//                        Toast.makeText(context,soLuong+"",Toast.LENGTH_LONG).show();
                dsOrderMonAn.set(position, new Order(dsOrderMonAn.get(position).getMaMon(),dsOrderMonAn.get(position).getTenMon(),dsOrderMonAn.get(position).getDonGia(),soLuong));
                dialog.dismiss();
                orderAdapter.notifyDataSetChanged();
                xuLyThayDoiSLTongTien();
            }
        });
        dialog.show();
    }


}
