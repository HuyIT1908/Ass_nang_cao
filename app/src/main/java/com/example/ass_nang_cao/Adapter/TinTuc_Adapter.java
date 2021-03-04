package com.example.ass_nang_cao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ass_nang_cao.Models.Tin_tuc;
import com.example.ass_nang_cao.R;

import java.util.List;

public class TinTuc_Adapter extends BaseAdapter {
    Context context;
    List<Tin_tuc> tin_tucList;
    String rss_name;

    public TinTuc_Adapter(Context context, List<Tin_tuc> tin_tucList, String rss_name) {
        this.context = context;
        this.tin_tucList = tin_tucList;
        this.rss_name = rss_name;
    }

    @Override
    public int getCount() {
        return tin_tucList.size();
    }

    @Override
    public Object getItem(int i) {
        return tin_tucList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Show_tin show_tin;
        if (convertView == null){
            show_tin = new Show_tin();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.show_tin_tuc , null);

            show_tin.tv_link = convertView.findViewById(R.id.tv_id);
            show_tin.tv_title = convertView.findViewById(R.id.tv_title);
            show_tin.tv_des = convertView.findViewById(R.id.tv_des);
            show_tin.tv_pubDate = convertView.findViewById(R.id.tv_pubDate);

            convertView.setTag(show_tin);
        }else {
            show_tin = (Show_tin) convertView.getTag();
        }
//        show_tin.tv_link.setText(tin_tucList.get(position).getLink());
        show_tin.tv_link.setText(rss_name);

        show_tin.tv_title.setText(tin_tucList.get(position).getTitle());

//        show_tin.tv_des.setText(tin_tucList.get(position).getDes());
        show_tin.tv_des.setText("");

        show_tin.tv_pubDate.setText(tin_tucList.get(position).getPubDate());

        return convertView;
    }

    private static class Show_tin{
        TextView tv_link;
        TextView tv_title;
        TextView tv_des;
        TextView tv_pubDate;
        ImageView imv_icon;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
