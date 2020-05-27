package com.example.nguye.restaurant_project.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.FragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Database extends SQLiteOpenHelper {
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase initDatabase(FragmentActivity activity, String databaseName){
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if(!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //Truy vấn không trả về kết quả: CREATE, INSERT, UPDATE, DELETE,...
    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Truy vấn có trả kết quả: SELECT
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    public boolean INSERTFOOD(Integer IDGroupMenu, String sTenMonAn, Integer iGiaTien, byte[] Picture)throws IOException
    {
        boolean Authen;
        try
        {
            SQLiteDatabase database = getWritableDatabase();
            String query = "insert into Food values (null,?,?,?,?)";
            //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
            SQLiteStatement statement = database.compileStatement(query);
            statement.clearBindings();
            statement.bindString(1,Integer.toString(IDGroupMenu));
            statement.bindString(2,sTenMonAn);
            statement.bindString(3,Integer.toString(iGiaTien));
            statement.bindBlob(4,Picture);
            statement.executeInsert();
            Authen = true;
        }catch(Exception e)
        {
            e.printStackTrace();
            Authen = false;
        }

        return Authen;
    }
    public boolean INSERTMENUFOOD(String GroupMenuName, byte[] Picture) throws IOException
    {
        boolean Authen;
        try
        {
            SQLiteDatabase database = getWritableDatabase();
            String query = "insert into GroupMenu values (null,?,?)";
            //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
            SQLiteStatement statement = database.compileStatement(query);
            statement.clearBindings();
            statement.bindString(1,GroupMenuName);
            statement.bindBlob(2,Picture);
            statement.executeInsert();
            Authen = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Authen = false;
        }
        return Authen;
    }

    public boolean InsertHoaDon(Integer id, String dNgayLap, Integer tongTien, boolean trangThai, Integer maBan, Integer maNV, Integer maKhuVuc )throws IOException
    {
        boolean Authen;
        try
        {
            SQLiteDatabase database = getWritableDatabase();
            String query = "insert into HoaDon values (null,?,?,null,?,?,?)";
            //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
            SQLiteStatement statement = database.compileStatement(query);
            statement.clearBindings();
            statement.bindString(1,Integer.toString(id));
            statement.bindString(2, String.valueOf(dNgayLap));
            statement.bindString(3,Integer.toString(tongTien));
            statement.bindString(4, String.valueOf(trangThai));
            statement.bindString(5,Integer.toString(maBan));
            statement.bindString(6,Integer.toString(maNV));
            statement.bindString(7,Integer.toString(maKhuVuc));
            statement.executeInsert();
            Authen = true;
        }catch(Exception e)
        {
            e.printStackTrace();
            Authen = false;
        }

        return Authen;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHoaDon(){

    }

}
