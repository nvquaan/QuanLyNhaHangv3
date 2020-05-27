package com.example.nguye.restaurant_project.activity;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.restaurant_project.R;
import com.example.nguye.restaurant_project.fragment.DatePickerFragment;
import com.example.nguye.restaurant_project.model.KhuVuc;
import com.example.nguye.restaurant_project.model.NhanVien;
import com.example.nguye.restaurant_project.model.Quyen;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText edTenDN, edMatKhau, edNgaySinh, edCMND;
    RadioButton rdNam, rdNu;
    RadioGroup groupGT;
    Button btnDongy, btnThoat;
    TextView txtTieude;
    String giotinh;
    Spinner spQuyen;
    Quyen quyen;
    List<Quyen> quyenList;
    List<String> stringAdapter;

    int landautien = 0;
    int manv = 0;

    String DATABASE_NAME = "QLNhaHang.sqlite";
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        landautien = getIntent().getIntExtra("landautien", 0);
        Toast.makeText(DangKyActivity.this, "" + landautien, Toast.LENGTH_SHORT).show();
        manv = getIntent().getIntExtra("manv", 0);
//        Toast.makeText(DangKyActivity.this, manv+"", Toast.LENGTH_LONG).show();
        addControls();
        if (manv != 0) {
            txtTieude.setText(getResources().getString(R.string.capnhatnhanvien) + "");
//            txtTieude.setText("Cập nhật nhân viên");
            NhanVien nhanVien = layNhanVienTheoMa(manv);
            edTenDN.setText(nhanVien.getTenDN());
            edMatKhau.setText(nhanVien.getMatKhau());
            edNgaySinh.setText(nhanVien.getNgaySinh());
            edCMND.setText(String.valueOf(nhanVien.getCMND()));
            String giotinh = nhanVien.getGioiTinh();
//            Toast.makeText(DangKyActivity.this, giotinh+"", Toast.LENGTH_LONG).show();
            if (giotinh.equals("Nam")) {
                rdNam.setChecked(true);
            } else if (giotinh.equals("Nữ")) {
                rdNu.setChecked(true);
            }
            int maquyen = nhanVien.getMaQuyen();
        }

        addEvent();


    }

    private NhanVien layNhanVienTheoMa(int manv) {
        NhanVien nhanvien = new NhanVien();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien where id=" + manv, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nhanvien.setMaNV(cursor.getInt(0));
            nhanvien.setTenDN(cursor.getString(1));
            nhanvien.setMatKhau(cursor.getString(2));
            nhanvien.setGioiTinh(cursor.getString(3));
            nhanvien.setNgaySinh(cursor.getString(4));
            nhanvien.setCMND(Integer.parseInt(cursor.getString(5)));
            nhanvien.setMaQuyen(cursor.getInt(6));
            cursor.moveToNext();

        }
        return nhanvien;
    }

    private void addControls() {
        txtTieude = (TextView) findViewById(R.id.txtTieuDe);
        edTenDN = (EditText) findViewById(R.id.edTenDN);
        edMatKhau = (EditText) findViewById(R.id.edMatKhau);
        edNgaySinh = (EditText) findViewById(R.id.edNgaySinh);
        edCMND = (EditText) findViewById(R.id.edCMND);
        rdNu = (RadioButton) findViewById(R.id.rdNu);
        rdNam = (RadioButton) findViewById(R.id.rdNam);
        groupGT = (RadioGroup) findViewById(R.id.groupGT);
        btnDongy = (Button) findViewById(R.id.btnDongy);
        btnThoat = (Button) findViewById(R.id.btnThoat);
        spQuyen = (Spinner) findViewById(R.id.spQuyen);

        quyen = new Quyen();
        stringAdapter = new ArrayList<String>();
        quyenList = selectQuyen();

        for (int i = 0; i < quyenList.size(); i++) {
            String tenQuyen = quyenList.get(i).getTenQuyen();
            stringAdapter.add(tenQuyen);
        }
        if (landautien == 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringAdapter);
            spQuyen.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } else
            spQuyen.setVisibility(View.GONE);


    }

    private List<Quyen> selectQuyen() {
        List<Quyen> quyen = new ArrayList<Quyen>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Quyen", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String tenQuyen = cursor.getString(1);
            quyen.add(new Quyen(id, tenQuyen));
        }
        return quyen;
    }

    private void addEvent() {
        edNgaySinh.setOnFocusChangeListener(this);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDN = edTenDN.getText().toString();
                String matKhau = edMatKhau.getText().toString();
                switch (groupGT.getCheckedRadioButtonId()) {
                    case R.id.rdNam:
                        giotinh = "Nam";
                        break;
                    case R.id.rdNu:
                        giotinh = "Nữ";
                        break;
                }
                String ngaySinh = edNgaySinh.getText().toString();
                int CMND = Integer.parseInt(edCMND.getText().toString());
                if (edTenDN == null && edTenDN.equals("")) {
                    Toast.makeText(DangKyActivity.this, getResources().getString(R.string.nhaptendangnhap) + "", Toast.LENGTH_LONG).show();
                } else if (edMatKhau == null && edMatKhau.equals("")) {
                    Toast.makeText(DangKyActivity.this, getResources().getString(R.string.nhapmatkhau) + "", Toast.LENGTH_LONG).show();
                }
                int maQuyen;
                if (landautien == 1) {
                    maQuyen = 1;
                } else {
                    int vitri = spQuyen.getSelectedItemPosition();
                    maQuyen = quyenList.get(vitri).getMaQuyen();
                }
                if (manv != 0) {
                    SuaNhanVien(CMND, maQuyen, tenDN, matKhau, giotinh, ngaySinh);
                } else
                    ThemNhanVien(CMND, maQuyen, tenDN, matKhau, giotinh, ngaySinh);

            }
        });

    }
//    private int layTenQuyen( int maquyen){
//        String query="SELECT *FROM Quyen where id= "+maquyen;
//        Cursor cursor = database.rawQuery(query, null);
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//
//        }
//        return quyen;
//    }

    private void ThemNhanVien(int CMND, int maQuyen, String tenDN, String matKhau, String gioiTinh, String ngaySinh) {
        database = Database.initDatabase(this, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDN", tenDN);
        contentValues.put("matKhau", matKhau);
        contentValues.put("gioiTinh", gioiTinh);
        contentValues.put("ngaySinh", ngaySinh);
        contentValues.put("CMND", CMND);
        contentValues.put("maQuyen", maQuyen);
        long r = database.insert("NhanVien", null, contentValues);
        Toast.makeText(this, "Them" + r, Toast.LENGTH_SHORT).show();
    }

    private void SuaNhanVien(int CMND, int maQuyen, String tenDN, String matKhau, String gioiTinh, String ngaySinh) {
        database = Database.initDatabase(this, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDN", tenDN);
        contentValues.put("matKhau", matKhau);
        contentValues.put("gioiTinh", gioiTinh);
        contentValues.put("ngaySinh", ngaySinh);
        contentValues.put("CMND", CMND);
        contentValues.put("maQuyen", maQuyen);
        long r = database.update("NhanVien", contentValues, "id=?" ,new String[]{manv+""});
        Toast.makeText(this, "Sửa " + r, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id) {
            case R.id.edNgaySinh:
                if (b) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(), "Ngày sinh");
                }

                break;
        }
    }
}
