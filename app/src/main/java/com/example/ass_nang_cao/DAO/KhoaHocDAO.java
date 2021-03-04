package com.example.ass_nang_cao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ass_nang_cao.Models.KhoaHoc;
import com.example.ass_nang_cao.SQLite.DatabaseASS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhoaHocDAO {
    private SQLiteDatabase db;
    private DatabaseASS dbHelper;

    public static final String TABLE_NAME = "KhoaHoc";
    public static final String column_maKH = "maKhoaHoc";
    public static final String column_tenKH = "tenKhoaHoc";
    public static final String column_ngay_bat_dau = "ngayBatDau";
    public static final String column_ngay_ket_thuc = "ngayKetThuc";


    public static final String SQL_Khoa_Hoc = "CREATE TABLE KhoaHoc (" +
            column_maKH + " text primary key," +
            column_tenKH + " text," +
            column_ngay_bat_dau + " date," +
            column_ngay_ket_thuc + " date);";

    public static final String TAG = "KhoaHocDAO";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public KhoaHocDAO(Context context) {
        dbHelper = new DatabaseASS(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inser_Khoa_hoc(KhoaHoc kh) {

        ContentValues values = new ContentValues();

        values.put(column_maKH, kh.getMaKH());
        values.put(column_tenKH, kh.getTenKH());
        values.put(column_ngay_bat_dau, sdf.format(kh.getNgayBatDau()) );
        values.put(column_ngay_ket_thuc , sdf.format(kh.getNgayKetThuc()) );
        try {
            if ( db.insert(TABLE_NAME , null , values) == -1 ) {
//                Nếu thêm THẤT BẠI thì trả về -1
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG + " ------ Error", ex.toString());
        }
//        Nếu thêm THÀNH CÔNG thì trả về 1
        return 1;
    }

    public List<KhoaHoc> getAll_Khoa_Hoc() throws ParseException {

        List<KhoaHoc> dsKhoaHoc = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {
            KhoaHoc khoaHoc = new KhoaHoc();
            khoaHoc.setMaKH(c.getString(0));
            khoaHoc.setTenKH( c.getString(1) );
            khoaHoc.setNgayBatDau( sdf.parse( c.getString(2)) );
            khoaHoc.setNgayKetThuc( sdf.parse( c.getString(3)) );

            dsKhoaHoc.add(khoaHoc);
            Log.d(TAG + "//========", khoaHoc.toString());
            c.moveToNext();
        }
        c.close();
        return dsKhoaHoc;
    }

    public int update_khoa_hoc(KhoaHoc khoaHoc) {

        ContentValues values = new ContentValues();
        values.put(column_maKH, khoaHoc.getMaKH());
        values.put(column_tenKH, khoaHoc.getTenKH());
        values.put(column_ngay_bat_dau, sdf.format(khoaHoc.getNgayBatDau()) );
        values.put(column_ngay_ket_thuc , sdf.format(khoaHoc.getNgayKetThuc()) );

        int result = db.update(TABLE_NAME, values, column_maKH + "=?",
                new String[]{khoaHoc.getMaKH()} );
        if (result == 0) {
//            Nếu cập nhật thất bại thì trả về -1
            return -1;
        }
//        Nếu cập nhật thành công thì trả về 1
        return 1;
    }

    public int delete_khoa_hoc_By_ID(String ma_Khoa_Hoc) {
        int result = db.delete(TABLE_NAME, column_maKH + "=?", new String[]{ma_Khoa_Hoc});

        if (result == 0){
//            Nếu xóa THẤT BẠI thì trả về -1
            return -1;
        }
//        Nếu xóa THÀNH CÔNG thì trả về 1
        return 1;
    }

    public boolean validation_ma_khoa_hoc(KhoaHoc khoaHoc) throws ParseException {
        int result = 0;
        List<KhoaHoc> dsKhoaHoc = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {

            String maKH = c.getString(0);
            String tenKH = c.getString(1) ;
            Date ngayBD = sdf.parse( c.getString(2)) ;
            Date ngayKT = sdf.parse( c.getString(3)) ;

            if (khoaHoc.getMaKH().equalsIgnoreCase(maKH) ){
                c.close();
                Log.e("kiem tra roi nha" , "------------------da thanh cong roi");
                return true;
            }

            dsKhoaHoc.add(khoaHoc);
            Log.d(TAG + "//========", khoaHoc.toString());
            c.moveToNext();
        }
        c.close();

        if (result == 0){
            return false;
        }
        return true;
    }
}
