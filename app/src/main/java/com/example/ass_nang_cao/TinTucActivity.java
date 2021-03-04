package com.example.ass_nang_cao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_nang_cao.Adapter.TinTuc_Adapter;
import com.example.ass_nang_cao.Models.Tin_tuc;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TinTucActivity extends AppCompatActivity {
    ListView lv_tin_tuc;
    String link_2 = "https://vtc.vn/rss/giao-duc.rss";
    List<Tin_tuc> tin_tucList;
    String[] the_loai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_tuc);
        setTitle("Tin Tức");

        lv_tin_tuc = findViewById(R.id.lv_tin_tuc);

        tin_tucList = new ArrayList<>();
        the_loai = getResources().getStringArray(R.array.the_loai);

        lv_tin_tuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String link = tin_tucList.get(i).getLink();
                startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(link)));
            }
        });
        show_tin_tuc(link_2 , "Giáo Dục");
    }

    public void test_data(String link){
        Log.e("---------------------" , "Kết nối đến link ");
        try {
//          kiểm tra link đúng hay sai
            URL url = new URL(link);
//          Mở kết nối
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();
//          lấy data vào qua inputStream ( một đối tượng cho phép đọc )
            InputStream inputStream = urlConnection.getInputStream();
//          Dùng các đối tượng sau để xử lí file hoặc dữ liệu kiểu xml
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);

            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");
//            lay su kien
            int event = xmlPullParser.getEventType();

            Tin_tuc tin_tuc = null;
            String text = null;

            Log.e("\n\tbắt đầu lấy data " , "step 1");

            while (event != XmlPullParser.END_DOCUMENT) {

                String tag = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if ( tag.equalsIgnoreCase("item") ){
                            tin_tuc = new Tin_tuc();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tin_tuc != null){
                            if (tag.equalsIgnoreCase("title")){
                                tin_tuc.setTitle(text);
                            }

                            if (tag.equalsIgnoreCase("description")){
                                tin_tuc.setDes(text);
                            }

                            if (tag.equalsIgnoreCase("link")){
                                tin_tuc.setLink(text);
                            }

                            if (tag.equalsIgnoreCase("pubDate")){
                                tin_tuc.setPubDate(text);
                            }

                            if (tag.equalsIgnoreCase("item")){
                                tin_tucList.add(tin_tuc);
                            }
                        }
                        break;
                }

                event = xmlPullParser.next();
            }
            Log.e("----------------------" , " " + tin_tucList.size() + "\t\n\t\t Finish");
        } catch (MalformedURLException e) {
            Log.e("Error 1 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error 2 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e("Error 3 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        }

    }
    public void load_data(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        NetworkInfo mobile_3g =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile_3g.isConnected() || wifi.isConnected()){

            Toast.makeText(getApplicationContext() , "Đã kết nối Wifi hoặc 3G/4G",
                    Toast.LENGTH_SHORT).show();
            show_tin_tuc(link_2 , "Giáo Dục");

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notifycation").setMessage("Vui lòng bật 3G/4G hoặc Wifi để xem được tin tức");
            builder.setCancelable(false);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void show_tin_tuc(String link , String rss_name){
        tin_tucList.clear();
        Log.e("-----------------------" , "vao luong phu 1111111111111");
        //      tạo luồng phụ xử lí
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.e("-----------------------" , "Bắt đầu xử lí xml");
                test_data(link);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
//                duoc goi khi luong ket thuc
//                khai bao adapter
//                Log.e("getId :  " + tin_tucList.get(0).getId() ,"getTitle :  " + tin_tucList.get(0).getTitle());
//                Log.e("\n getDes :  " + tin_tucList.get(0).getDes() , "\n getPubDate :  " + tin_tucList.get(0).getPubDate());
                TinTuc_Adapter adapter = new TinTuc_Adapter(TinTucActivity.this , tin_tucList , rss_name);
//                bo array vao adapter
//                set adapter cho listview
                adapter.notifyDataSetChanged();
                lv_tin_tuc.setAdapter(adapter);

            }
        };
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tin_tuc , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_load:
                load_data();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}