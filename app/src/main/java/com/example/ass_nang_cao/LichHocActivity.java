package com.example.ass_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.DAO.LichHocDAO;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LichHocActivity extends AppCompatActivity {
    ListView lv_lich_hoc;
    LichHocDAO lichHocDAO;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    TextInputLayout edt_CA , edt_maMon ,edt_tenMon , edt_LOP , edt_PHONG , edt_Ngay , edt_gioBD , edt_gioKT;
    Button btn_ngay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hoc);
        setTitle("Lịch Học");

        lv_lich_hoc = findViewById(R.id.lv_lich_hoc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lich_hoc , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_lich_hoc:
                add_lich_hoc();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void add_lich_hoc() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.add_lich_hoc , null);

        edt_CA = layout.findViewById(R.id.edt_CA);
        edt_maMon = layout.findViewById(R.id.edt_maMon);
        edt_tenMon = layout.findViewById(R.id.edt_tenMon);
        edt_LOP = layout.findViewById(R.id.edt_LOP);
        edt_PHONG = layout.findViewById(R.id.edt_PHONG);
        edt_Ngay = layout.findViewById(R.id.edt_Ngay);
        edt_gioBD = layout.findViewById(R.id.edt_gioBD);
        edt_gioKT = layout.findViewById(R.id.edt_gioKT);
        btn_ngay = layout.findViewById(R.id.btn_chon_ngay);

        AlertDialog.Builder builder = new AlertDialog.Builder(LichHocActivity.this);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_gioBD);
            }
        });
    }

    private void thong_bao(String tb , Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông Báo").setMessage(tb);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void get_DAY(TextInputLayout edt){
        DatePickerDialog datePickerDialog = new DatePickerDialog(LichHocActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                        edt.getEditText().setText(sdf.format(cal.getTime()));
                    }
                } , calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void get_time(TextInputLayout edt){
        TimePickerDialog timePickerDialog = new TimePickerDialog(LichHocActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                edt.getEditText().setText(hour + " : " + minute );
            }
        },
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(LichHocActivity.this) );
        timePickerDialog.show();
    }
}