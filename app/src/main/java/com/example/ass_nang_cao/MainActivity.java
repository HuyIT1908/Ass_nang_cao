package com.example.ass_nang_cao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ass_nang_cao.Adapter.GvAdapter;
import com.example.ass_nang_cao.Models.CustomGV;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gv_list;
    List<CustomGV> gvList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv_list = findViewById(R.id.gv_list);
        gvList.add(new CustomGV("Học Tập", R.drawable.hoc_tap));
        gvList.add(new CustomGV("Bản Đồ", R.drawable.map));
        gvList.add(new CustomGV("Tin Tức", R.drawable.tin_tuc));
        gvList.add(new CustomGV("Đăng Nhập Facebook", R.drawable.dang_nhap_facebook));

        GvAdapter gvAdapter = new GvAdapter(MainActivity.this, gvList);
        gv_list.setAdapter(gvAdapter);

        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (gvList.get(i).getName().equalsIgnoreCase("học tập")) {

                    startActivity(new Intent(MainActivity.this, HocTapActivity.class));

                } else if (gvList.get(i).getName().equalsIgnoreCase("bản đồ")) {

                    startActivity(new Intent(MainActivity.this , MapsActivity.class));

                } else if (gvList.get(i).getName().equalsIgnoreCase("tin tức")) {

                    startActivity(new Intent(MainActivity.this , TinTucActivity.class));

                } else if (gvList.get(i).getName().equalsIgnoreCase("đăng nhập facebook")) {

                    startActivity(new Intent(MainActivity.this , LoginFacebookActivity.class));
                }
            }
        });
    }
}