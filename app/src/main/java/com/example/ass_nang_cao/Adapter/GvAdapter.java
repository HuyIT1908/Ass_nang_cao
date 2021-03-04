package com.example.ass_nang_cao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ass_nang_cao.Models.CustomGV;
import com.example.ass_nang_cao.R;

import java.util.ArrayList;
import java.util.List;

public class GvAdapter extends BaseAdapter {
    Context context;
    List<CustomGV> gvList = new ArrayList<>();

    public GvAdapter(Context context, List<CustomGV> gvList) {
        this.context = context;
        this.gvList = gvList;
    }

    @Override
    public int getCount() {
        return gvList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IconShow iconShow;
        if (view == null){
            iconShow = new IconShow();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gv_icon , null);

            iconShow.img_icon = view.findViewById(R.id.imv_icon_gv);
            iconShow.name_icon = view.findViewById(R.id.tv_icon_gv);

            view.setTag(iconShow);
        } else {
            iconShow = (IconShow) view.getTag();
        }

        iconShow.name_icon.setText(gvList.get(i).getName());
        iconShow.img_icon.setImageResource(gvList.get(i).getImg());
        return view;
    }

    public static class IconShow{
        ImageView img_icon;
        TextView name_icon;
    }
}
