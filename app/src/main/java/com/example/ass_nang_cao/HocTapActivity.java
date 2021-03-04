package com.example.ass_nang_cao;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.Models.KhoaHoc;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HocTapActivity extends AppCompatActivity {
    TextInputLayout edt_ma_KHoc , edt_ten_KHoc , edt_ngay_bd , edt_ngay_kt;
    Button btn_bd , btn_kt , btn_add;
    KhoaHocDAO khoaHocDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_tap);
        setTitle("Học Tập");

        khoaHocDAO = new KhoaHocDAO(HocTapActivity.this);
    }

    public void dk_khoa_hoc(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.add_khoa_hoc , null);

        edt_ma_KHoc = layout.findViewById(R.id.edt__ma_khoa_hoc);
        edt_ten_KHoc = layout.findViewById(R.id.edt_ten_khoa_hoc);
        edt_ngay_bd = layout.findViewById(R.id.edt_ngay_bd);
        edt_ngay_kt = layout.findViewById(R.id.edt_ngay_kt);
        btn_bd = layout.findViewById(R.id.btn_bd);
        btn_kt = layout.findViewById(R.id.btn_kt);
        btn_add = layout.findViewById(R.id.btn_add);
        AlertDialog.Builder builder = new AlertDialog.Builder(HocTapActivity.this);
        builder.setView(layout);

        btn_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_ngay_bd);
            }
        });

        btn_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_time(edt_ngay_kt);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (
                            edt_ma_KHoc.getEditText().getText().toString().isEmpty()
                                    || edt_ten_KHoc.getEditText().getText().toString().isEmpty()
                                    || edt_ngay_bd.getEditText().getText().toString().isEmpty()
                                    || edt_ngay_kt.getEditText().getText().toString().isEmpty()
                    ){
                        thong_bao("Không được để trống. Phải nhập đầy đủ thông tin !" , HocTapActivity.this);
                    } else {
                        KhoaHoc khoaHoc = new KhoaHoc(
                                edt_ma_KHoc.getEditText().getText().toString(),
                                edt_ten_KHoc.getEditText().getText().toString(),
                                sdf.parse(edt_ngay_bd.getEditText().getText().toString()),
                                sdf.parse(edt_ngay_kt.getEditText().getText().toString())
                        );
                        boolean test = edt_ngay_bd.getEditText().getText().toString().equalsIgnoreCase(edt_ngay_kt.getEditText().getText().toString());
                        if (khoaHocDAO.validation_ma_khoa_hoc(khoaHoc)){
                            thong_bao("MÃ KHÓA HỌC đã tồn tại.\n\nVui lòng nhập lại !!!" ,
                                    HocTapActivity.this);
                        } else if ( test ){
                            thong_bao("Ngày BẮT ĐẦU và ngày KẾT THÚC không được trùng nhau.\n\nVui lòng kiểm tra lại !!!" ,
                                    HocTapActivity.this);
                        }
                        else if (khoaHocDAO.inser_Khoa_hoc(khoaHoc) > 0){
                            Toast.makeText(getApplicationContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HocTapActivity.this , ListKhoaHocActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Thêm thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception ex){
                    Log.e("--Error insert Database" , ex.toString());
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void get_time(TextInputLayout edt){
        DatePickerDialog datePickerDialog = new DatePickerDialog(HocTapActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                        edt.getEditText().setText(sdf.format(cal.getTime()));
                    }
                } , calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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

    public void ds_khoa_hoc(View view){
        Intent intent = new Intent(HocTapActivity.this , ListKhoaHocActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("makh" , edt_ma_KHoc.getEditText().getText().toString());
//        bundle.putString("tenkh" , edt_ten_KHoc.getEditText().getText().toString());
//        bundle.putString("ngaybd" , edt_ngay_bd.getEditText().getText().toString());
//        bundle.putString("ngaykt" , edt_ngay_kt.getEditText().getText().toString());
//        intent.putExtras(bundle);
        startActivity(intent);
    }
}