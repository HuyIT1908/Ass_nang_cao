package com.example.ass_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ass_nang_cao.Adapter.LvKhoaHocAdapter;
import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.Models.KhoaHoc;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListKhoaHocActivity extends AppCompatActivity {
    ListView lv_khoa_hoc;
    KhoaHocDAO khoaHocDAO;
    List<KhoaHoc> khoaHocList;
    TextInputLayout edt_ma_KHoc , edt_ten_KHoc , edt_ngay_bd , edt_ngay_kt;
    Button btn_bd , btn_kt , btn_add;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();
    LvKhoaHocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khoa_hoc);
        setTitle("Danh sách khóa học");

        lv_khoa_hoc = findViewById(R.id.lv_khoa_hoc);

        khoaHocDAO = new KhoaHocDAO(this);
        try {
            khoaHocList = khoaHocDAO.getAll_Khoa_Hoc();
        } catch (ParseException e) {
            Log.e("Error ds khoa hoc ---" , e.toString());
        }
        adapter = new LvKhoaHocAdapter(ListKhoaHocActivity.this , khoaHocList);
        lv_khoa_hoc.setAdapter(adapter);
        lv_khoa_hoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                update_khoa_hoc(i);
                lv_update();
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void update_khoa_hoc(int i){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.add_khoa_hoc , null);

        edt_ma_KHoc = layout.findViewById(R.id.edt__ma_khoa_hoc);
        edt_ten_KHoc = layout.findViewById(R.id.edt_ten_khoa_hoc);
        edt_ngay_bd = layout.findViewById(R.id.edt_ngay_bd);
        edt_ngay_kt = layout.findViewById(R.id.edt_ngay_kt);
        btn_bd = layout.findViewById(R.id.btn_bd);
        btn_kt = layout.findViewById(R.id.btn_kt);
        btn_add = layout.findViewById(R.id.btn_add);

        AlertDialog.Builder builder = new AlertDialog.Builder(ListKhoaHocActivity.this);
        builder.setView(layout);
        khoaHocList.clear();
        try {
            khoaHocList.clear();
            khoaHocList = khoaHocDAO.getAll_Khoa_Hoc();
        } catch (ParseException e) {
            Log.e("Error get ALL database " , e.toString());
        }
        edt_ma_KHoc.getEditText().setText( khoaHocList.get(i).getMaKH() );
        edt_ma_KHoc.getEditText().setEnabled(false);
        edt_ten_KHoc.getEditText().setText( khoaHocList.get(i).getTenKH() );
        edt_ngay_bd.getEditText().setText(sdf.format( khoaHocList.get(i).getNgayBatDau() ) );
        edt_ngay_kt.getEditText().setText(sdf.format( khoaHocList.get(i).getNgayKetThuc() ) );
        btn_add.setText("Cập Nhật");

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

        AlertDialog dialog = builder.create();
        dialog.show();
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
                        thong_bao("Không được để trống. Phải nhập đầy đủ thông tin !" , ListKhoaHocActivity.this);
                    } else {
                        KhoaHoc khoaHoc = new KhoaHoc(
                                edt_ma_KHoc.getEditText().getText().toString(),
                                edt_ten_KHoc.getEditText().getText().toString(),
                                sdf.parse(edt_ngay_bd.getEditText().getText().toString()),
                                sdf.parse(edt_ngay_kt.getEditText().getText().toString())
                        );
                        boolean test = edt_ngay_bd.getEditText().getText().toString().equalsIgnoreCase(edt_ngay_kt.getEditText().getText().toString());

                        if ( test ){
                            thong_bao("Ngày BẮT ĐẦU và ngày KẾT THÚC không được trùng nhau.\n\nVui lòng kiểm tra lại !!!" ,
                                    ListKhoaHocActivity.this);
                        }
                        else if (khoaHocDAO.update_khoa_hoc(khoaHoc) > 0){
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công",
                                    Toast.LENGTH_SHORT).show();
                            lv_update();
                            dialog.dismiss();
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
    }

    private void get_time(TextInputLayout edt){
        DatePickerDialog datePickerDialog = new DatePickerDialog(ListKhoaHocActivity.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.khoa_hoc, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_add_kh:
                dk_khoa_hoc();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dk_khoa_hoc(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.add_khoa_hoc , null);

        edt_ma_KHoc = layout.findViewById(R.id.edt__ma_khoa_hoc);
        edt_ten_KHoc = layout.findViewById(R.id.edt_ten_khoa_hoc);
        edt_ngay_bd = layout.findViewById(R.id.edt_ngay_bd);
        edt_ngay_kt = layout.findViewById(R.id.edt_ngay_kt);
        btn_bd = layout.findViewById(R.id.btn_bd);
        btn_kt = layout.findViewById(R.id.btn_kt);
        btn_add = layout.findViewById(R.id.btn_add);
        AlertDialog.Builder builder = new AlertDialog.Builder(ListKhoaHocActivity.this);
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
        AlertDialog dialog = builder.create();
        dialog.show();
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
                        thong_bao("Không được để trống. Phải nhập đầy đủ thông tin !" , ListKhoaHocActivity.this);
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
                                    ListKhoaHocActivity.this);
                        } else if ( test ){
                            thong_bao("Ngày BẮT ĐẦU và ngày KẾT THÚC không được trùng nhau.\n\nVui lòng kiểm tra lại !!!" ,
                                    ListKhoaHocActivity.this);
                        }
                        else if (khoaHocDAO.inser_Khoa_hoc(khoaHoc) > 0){
                            Toast.makeText(getApplicationContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            lv_update();
                            dialog.dismiss();
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

    }

    private void lv_update(){
        khoaHocDAO = new KhoaHocDAO(this);
        try {
            khoaHocList.clear();
            khoaHocList = khoaHocDAO.getAll_Khoa_Hoc();
        } catch (ParseException e) {
            Log.e("Error ds khoa hoc ---" , e.toString());
        }
        adapter = new LvKhoaHocAdapter(ListKhoaHocActivity.this , khoaHocList);
        lv_khoa_hoc.setAdapter(adapter);
        lv_khoa_hoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                update_khoa_hoc(i);
            }
        });
        adapter.notifyDataSetChanged();
    }
}