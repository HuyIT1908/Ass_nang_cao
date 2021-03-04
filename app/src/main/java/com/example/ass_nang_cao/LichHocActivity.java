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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ass_nang_cao.Adapter.LvLichHocAdapter;
import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.DAO.LichHocDAO;
import com.example.ass_nang_cao.Models.LichHoc;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LichHocActivity extends AppCompatActivity {
    ListView lv_lich_hoc;
    LichHocDAO lichHocDAO;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    TextInputLayout edt_CA , edt_maMon ,edt_tenMon , edt_LOP , edt_PHONG , edt_Ngay , edt_gioBD , edt_gioKT;
    Button btn_ngay , btn_chon_gio_bd , btn_chon_gio_kt , btn_xoa_trang , btn_add_lich_hoc;
    Context context = LichHocActivity.this;
    List<LichHoc> lichHocList;
    LvLichHocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hoc);
        setTitle("Lịch Học");

        lv_lich_hoc = findViewById(R.id.lv_lich_hoc);
        lichHocDAO = new LichHocDAO(context);
        lichHocList = new ArrayList<>();
        lv_show();
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
        btn_chon_gio_bd = layout.findViewById(R.id.btn_chon_gio_bd);
        btn_chon_gio_kt = layout.findViewById(R.id.btn_chon_gio_kt);
        btn_xoa_trang = layout.findViewById(R.id.btn_xoa_trang);
        btn_add_lich_hoc = layout.findViewById(R.id.btn_add_lich_hoc);

        AlertDialog.Builder builder = new AlertDialog.Builder(LichHocActivity.this);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        btn_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_DAY(edt_Ngay);
            }
        });
        btn_chon_gio_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_gioBD);
            }
        });
        btn_chon_gio_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_gioKT);
            }
        });
        btn_xoa_trang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_CA.getEditText().setText("");
                edt_maMon.getEditText().setText("");
                edt_tenMon.getEditText().setText("");
                edt_LOP.getEditText().setText("");
                edt_PHONG.getEditText().setText("");
                edt_Ngay.getEditText().setText("");
                edt_gioBD.getEditText().setText("");
                edt_gioKT.getEditText().setText("");
            }
        });
        btn_add_lich_hoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (
                            edt_CA.getEditText().getText().toString().isEmpty()
                                    || edt_maMon.getEditText().getText().toString().isEmpty()
                                    || edt_tenMon.getEditText().getText().toString().isEmpty()
                                    || edt_LOP.getEditText().getText().toString().isEmpty()
                                    || edt_PHONG.getEditText().getText().toString().isEmpty()
                                    || edt_Ngay.getEditText().getText().toString().isEmpty()
                                    || edt_gioBD.getEditText().getText().toString().isEmpty()
                                    || edt_gioKT.getEditText().getText().toString().isEmpty()
                    ){
                      thong_bao("Phải nhập đầy đủ thông tin !!!" , context);
                    } else {
                        LichHoc lichHoc = new LichHoc(
                                edt_CA.getEditText().getText().toString(),
                                edt_maMon.getEditText().getText().toString(),
                                edt_tenMon.getEditText().getText().toString(),
                                edt_LOP.getEditText().getText().toString(),
                                edt_PHONG.getEditText().getText().toString(),
                                sdf.parse(edt_Ngay.getEditText().getText().toString()),
                                edt_gioBD.getEditText().getText().toString(),
                                edt_gioKT.getEditText().getText().toString()
                        );

                        boolean test = lichHoc.getGio_bat_dau().equalsIgnoreCase(lichHoc.getGio_ket_thuc());

                        if ( test ){
                            thong_bao("giờ BẮT ĐẦU và giờ KẾT THÚC không được trùng nhau" , context);
                        } else if ( lichHocDAO.validation_CA(lichHoc) ){
                          thong_bao("Ca này đã tồn tại.\n\nBạn nên chọn ca khác !!!" , context);
                        } else if (lichHocDAO.inser_Lich_Hoc(lichHoc) > 0){

                            Toast.makeText(context , "Thêm thành công",Toast.LENGTH_SHORT).show();
                            lv_show();
                            dialog.dismiss();

                        } else {

                            Toast.makeText(context , "Thêm thất bại",Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception ex){
                    Log.e("\t----------------------" ,"lich hoc activity ===---  " + ex.toString() );
                }
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
                edt.getEditText().setText(hour + " h : " + minute + " m");
            }
        },
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(LichHocActivity.this) );
        timePickerDialog.show();
    }

    private void lv_show(){
        lichHocDAO = new LichHocDAO(context);
        try {
            lichHocList.clear();
            lichHocList = lichHocDAO.getAll_Lich_Hoc();
        } catch (ParseException e) {
            Log.e("\t\t------------------" , e.toString());
        }
        adapter = new LvLichHocAdapter(context , lichHocList);
        lv_lich_hoc.setAdapter(adapter);
        lv_lich_hoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                update_lich_hoc(i);
                lv_show();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void update_lich_hoc(int i) {
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
        btn_chon_gio_bd = layout.findViewById(R.id.btn_chon_gio_bd);
        btn_chon_gio_kt = layout.findViewById(R.id.btn_chon_gio_kt);
        btn_xoa_trang = layout.findViewById(R.id.btn_xoa_trang);
        btn_add_lich_hoc = layout.findViewById(R.id.btn_add_lich_hoc);

        lichHocList.clear();
        try {
            lichHocList = lichHocDAO.getAll_Lich_Hoc();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        edt_CA.getEditText().setText( lichHocList.get(i).getCa() );
        edt_CA.getEditText().setEnabled(false);
        edt_maMon.getEditText().setText( lichHocList.get(i).getMaMon() );
        edt_tenMon.getEditText().setText( lichHocList.get(i).getTenMOn() );
        edt_LOP.getEditText().setText( lichHocList.get(i).getLop() );
        edt_PHONG.getEditText().setText( lichHocList.get(i).getPhong() );
        edt_Ngay.getEditText().setText( sdf.format( lichHocList.get(i).getNgay() ) );
        edt_gioBD.getEditText().setText( lichHocList.get(i).getGio_bat_dau() );
        edt_gioKT.getEditText().setText( lichHocList.get(i).getGio_ket_thuc() );
        btn_add_lich_hoc.setText("Cập Nhật");

        AlertDialog.Builder builder = new AlertDialog.Builder(LichHocActivity.this);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        btn_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_DAY(edt_Ngay);
            }
        });
        btn_chon_gio_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_gioBD);
            }
        });
        btn_chon_gio_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_gioKT);
            }
        });
        btn_xoa_trang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_CA.getEditText().setText("");
                edt_maMon.getEditText().setText("");
                edt_tenMon.getEditText().setText("");
                edt_LOP.getEditText().setText("");
                edt_PHONG.getEditText().setText("");
                edt_Ngay.getEditText().setText("");
                edt_gioBD.getEditText().setText("");
                edt_gioKT.getEditText().setText("");
            }
        });
        btn_add_lich_hoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (
                            edt_CA.getEditText().getText().toString().isEmpty()
                                    || edt_maMon.getEditText().getText().toString().isEmpty()
                                    || edt_tenMon.getEditText().getText().toString().isEmpty()
                                    || edt_LOP.getEditText().getText().toString().isEmpty()
                                    || edt_PHONG.getEditText().getText().toString().isEmpty()
                                    || edt_Ngay.getEditText().getText().toString().isEmpty()
                                    || edt_gioBD.getEditText().getText().toString().isEmpty()
                                    || edt_gioKT.getEditText().getText().toString().isEmpty()
                    ){
                        thong_bao("Phải nhập đầy đủ thông tin !!!" , context);
                    } else {
                        LichHoc lichHoc = new LichHoc(
                                edt_CA.getEditText().getText().toString(),
                                edt_maMon.getEditText().getText().toString(),
                                edt_tenMon.getEditText().getText().toString(),
                                edt_LOP.getEditText().getText().toString(),
                                edt_PHONG.getEditText().getText().toString(),
                                sdf.parse(edt_Ngay.getEditText().getText().toString()),
                                edt_gioBD.getEditText().getText().toString(),
                                edt_gioKT.getEditText().getText().toString()
                        );

                        boolean test = lichHoc.getGio_bat_dau().equalsIgnoreCase(lichHoc.getGio_ket_thuc());

                        if ( test ){
                            thong_bao("giờ BẮT ĐẦU và giờ KẾT THÚC không được trùng nhau" , context);
                        } else if (lichHocDAO.update_Lich_Hoc(lichHoc) > 0){

                            Toast.makeText(context , "Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            lv_show();

                        } else {

                            Toast.makeText(context , "Cập nhật thất bại",Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception ex){
                    Log.e("\t----------------------" ,"lich hoc activity ===---  " + ex.toString() );
                }
            }
        });
    }
}