package com.example.ass_nang_cao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ass_nang_cao.Models.KhoaHoc;
import com.example.ass_nang_cao.Models.LichHoc;
import com.example.ass_nang_cao.SQLite.DatabaseASS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LichHocDAO {
    private SQLiteDatabase db;
    private DatabaseASS dbHelper;

    public static final String TABLE_NAME = "LichHoc";
    public static final String column_maMon = "maMon";
    public static final String column_tenMOn = "tenMon";
    public static final String column_Lop = "lop";
    public static final String column_phong = "phong";
    public static final String column_ngay = "ngay";
    public static final String column_gio_bat_dau = "gioBatDau";
    public static final String column_gio_ket_thuc = "gioKetThuc";



    public static final String SQL_Lich_Hoc = "CREATE TABLE " + TABLE_NAME + " (" +
            column_maMon + " text primary key," +
            column_tenMOn + " text," +
            column_Lop + " text," +
            column_phong + " text," +
            column_ngay + " date," +
            column_gio_bat_dau + " text," +
            column_gio_ket_thuc + " text);";

    public static final String TAG = "LichHocDAO";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public LichHocDAO(Context context) {
        dbHelper = new DatabaseASS(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inser_Lich_Hoc(LichHoc lichHoc) {

        ContentValues values = new ContentValues();

        values.put(column_maMon, lichHoc.getMaMon() );
        values.put(column_tenMOn, lichHoc.getTenMOn());
        values.put(column_Lop, lichHoc.getLop());
        values.put(column_phong, lichHoc.getPhong());
        values.put(column_ngay, sdf.format(lichHoc.getNgay()) );
        values.put(column_gio_bat_dau, lichHoc.getGio_bat_dau() );
        values.put(column_gio_ket_thuc, lichHoc.getGio_ket_thuc() );

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

//    public List<KhoaHoc> getAll_Khoa_Hoc() throws ParseException {
//
//        List<KhoaHoc> dsKhoaHoc = new ArrayList<>();
//
//        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
//        c.moveToFirst();
//
//        while (c.isAfterLast() == false) {
//            KhoaHoc khoaHoc = new KhoaHoc();
//            khoaHoc.setMaKH(c.getString(0));
//            khoaHoc.setTenKH( c.getString(1) );
//            khoaHoc.setNgayBatDau( sdf.parse( c.getString(2)) );
//            khoaHoc.setNgayKetThuc( sdf.parse( c.getString(3)) );
//
//            dsKhoaHoc.add(khoaHoc);
//            Log.d(TAG + "//========", khoaHoc.toString());
//            c.moveToNext();
//        }
//        c.close();
//        return dsKhoaHoc;
//    }
//
//    public int update_khoa_hoc(KhoaHoc khoaHoc) {
//
//        ContentValues values = new ContentValues();
//        values.put(column_maKH, khoaHoc.getMaKH());
//        values.put(column_tenKH, khoaHoc.getTenKH());
//        values.put(column_ngay_bat_dau, sdf.format(khoaHoc.getNgayBatDau()) );
//        values.put(column_ngay_ket_thuc , sdf.format(khoaHoc.getNgayKetThuc()) );
//
//        int result = db.update(TABLE_NAME, values, column_maKH + "=?",
//                new String[]{khoaHoc.getMaKH()} );
//        if (result == 0) {
////            Nếu cập nhật thất bại thì trả về -1
//            return -1;
//        }
////        Nếu cập nhật thành công thì trả về 1
//        return 1;
//    }
//
//    public int delete_khoa_hoc_By_ID(String ma_Khoa_Hoc) {
//        int result = db.delete(TABLE_NAME, column_maKH + "=?", new String[]{ma_Khoa_Hoc});
//
//        if (result == 0){
////            Nếu xóa THẤT BẠI thì trả về -1
//            return -1;
//        }
////        Nếu xóa THÀNH CÔNG thì trả về 1
//        return 1;
//    }
//
//    public boolean validation_ma_khoa_hoc(KhoaHoc khoaHoc) throws ParseException {
//        int result = 0;
//        List<KhoaHoc> dsKhoaHoc = new ArrayList<>();
//
//        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
//        c.moveToFirst();
//
//        while (c.isAfterLast() == false) {
//
//            String maKH = c.getString(0);
//            String tenKH = c.getString(1) ;
//            Date ngayBD = sdf.parse( c.getString(2)) ;
//            Date ngayKT = sdf.parse( c.getString(3)) ;
//
//            if (khoaHoc.getMaKH().equalsIgnoreCase(maKH) ){
//                c.close();
//                Log.e("kiem tra roi nha" , "------------------da thanh cong roi");
//                return true;
//            }
//
//            dsKhoaHoc.add(khoaHoc);
//            Log.d(TAG + "//========", khoaHoc.toString());
//            c.moveToNext();
//        }
//        c.close();
//
//        if (result == 0){
//            return false;
//        }
//        return true;
//    }
}
