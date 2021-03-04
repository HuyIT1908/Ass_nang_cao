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
    public static final String column_CA = "ca";
    public static final String column_maMon = "maMon";
    public static final String column_tenMOn = "tenMon";
    public static final String column_Lop = "lop";
    public static final String column_phong = "phong";
    public static final String column_ngay = "ngay";
    public static final String column_gio_bat_dau = "gioBatDau";
    public static final String column_gio_ket_thuc = "gioKetThuc";



    public static final String SQL_Lich_Hoc = "CREATE TABLE " + TABLE_NAME + " (" +
            column_CA + " text primary key," +
            column_maMon + " text," +
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

        values.put(column_CA, lichHoc.getCa() );
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

    public List<LichHoc> getAll_Lich_Hoc() throws ParseException {

        List<LichHoc> lichHocList = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {
            LichHoc lichHoc = new LichHoc();

            lichHoc.setCa(c.getString(0));
            lichHoc.setMaMon(c.getString(1));
            lichHoc.setTenMOn(c.getString(2));
            lichHoc.setLop(c.getString(3));
            lichHoc.setPhong(c.getString(4));
            lichHoc.setNgay( sdf.parse( c.getString(5)) );
            lichHoc.setGio_bat_dau(c.getString(6));
            lichHoc.setGio_ket_thuc(c.getString(7));

            lichHocList.add(lichHoc);
            Log.d(TAG + "//=========", lichHoc.toString());
            c.moveToNext();
        }
        c.close();
        return lichHocList;
    }

    public int update_Lich_Hoc(LichHoc lichHoc) {

        ContentValues values = new ContentValues();

        values.put(column_CA, lichHoc.getCa() );
        values.put(column_maMon, lichHoc.getMaMon() );
        values.put(column_tenMOn, lichHoc.getTenMOn());
        values.put(column_Lop, lichHoc.getLop());
        values.put(column_phong, lichHoc.getPhong());
        values.put(column_ngay, sdf.format(lichHoc.getNgay()) );
        values.put(column_gio_bat_dau, lichHoc.getGio_bat_dau() );
        values.put(column_gio_ket_thuc, lichHoc.getGio_ket_thuc() );

        int result = db.update(TABLE_NAME, values, column_CA + "=?",
                new String[]{ lichHoc.getCa() } );
        if (result == 0) {
//            Nếu cập nhật thất bại thì trả về -1
            return -1;
        }
//        Nếu cập nhật thành công thì trả về 1
        return 1;
    }

    public int delete_Lich_Hoc_By_ID(String ca) {
        int result = db.delete(TABLE_NAME, column_CA + "=?", new String[]{ ca });

        if (result == 0){
//            Nếu xóa THẤT BẠI thì trả về -1
            return -1;
        }
//        Nếu xóa THÀNH CÔNG thì trả về 1
        return 1;
    }

    public boolean validation_CA(LichHoc lichHoc) throws ParseException {
        int result = 0;
        List<LichHoc> lichHocList = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {

            String ca = c.getString(0);
            String maMonHoc = c.getString(1) ;
            String tenMonHoc = c.getString(2) ;
            String lop = c.getString(3) ;
            String phong = c.getString(4) ;
            Date ngay = sdf.parse( c.getString(5) ) ;
            String gio_bat_dau = c.getString(6) ;
            String gio_ket_thuc = c.getString(7) ;

            if ( lichHoc.getCa().equalsIgnoreCase(ca) ){
                c.close();
                Log.e("\t\t kiem tra roi nha  " , "------------------ \t\tda thanh cong roi");
                return true;
            }

            lichHocList.add(lichHoc);
            Log.d(TAG + "//\t\t========", lichHocList.toString());
            c.moveToNext();
        }
        c.close();

        if (result == 0){
            return false;
        }
        return true;
    }
}
