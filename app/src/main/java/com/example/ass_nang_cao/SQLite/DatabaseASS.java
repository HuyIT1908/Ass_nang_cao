package com.example.ass_nang_cao.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.Models.KhoaHoc;

public class DatabaseASS extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbEducationManager";
    public static final int VERSION = 1;

    public DatabaseASS(@Nullable Context context) {
        super(context, DATABASE_NAME + ".db", null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(KhoaHocDAO.SQL_Khoa_Hoc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + KhoaHocDAO.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
