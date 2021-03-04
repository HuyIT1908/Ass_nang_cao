package com.example.ass_nang_cao.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_nang_cao.DAO.KhoaHocDAO;
import com.example.ass_nang_cao.Models.KhoaHoc;
import com.example.ass_nang_cao.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LvKhoaHocAdapter extends BaseAdapter {
    Context context;
    List<KhoaHoc> khoaHocList = new ArrayList<>();
    KhoaHocDAO khoaHocDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public LvKhoaHocAdapter(Context context, List<KhoaHoc> khoaHocList) {
        this.context = context;
        this.khoaHocList = khoaHocList;
        khoaHocDAO = new KhoaHocDAO(context);
    }

    @Override
    public int getCount() {
        return khoaHocList.size();
    }

    @Override
    public Object getItem(int i) {
        return khoaHocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DsKhoaHoc dsKhoaHoc;
        if (view == null){
            dsKhoaHoc = new DsKhoaHoc();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ds_khoa_hoc , null);

            dsKhoaHoc.maKH = view.findViewById(R.id.tv_ma_kh);
            dsKhoaHoc.tenKH = view.findViewById(R.id.tv_ten_kh);
            dsKhoaHoc.ngayBD = view.findViewById(R.id.tv_ngay_bd);
            dsKhoaHoc.ngayKT = view.findViewById(R.id.tv_ngay_kt);
            dsKhoaHoc.img_delete = view.findViewById(R.id.ivDelete_khoa_hoc);

            dsKhoaHoc.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông Báo").setMessage("Bạn có chắc chắn muốn xóa không ?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            khoaHocDAO.delete_khoa_hoc_By_ID(khoaHocList.get(i).getMaKH());
                            khoaHocList.remove(i);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa thành công !!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            view.setTag(dsKhoaHoc);
        } else {
            dsKhoaHoc = (DsKhoaHoc) view.getTag();
        }

        dsKhoaHoc.maKH.setText("Mã khóa học :  " + khoaHocList.get(i).getMaKH() );
        dsKhoaHoc.tenKH.setText("Tên khóa học :  " + khoaHocList.get(i).getTenKH() );
        dsKhoaHoc.ngayBD.setText("Ngày bắt đầu :  " + sdf.format(khoaHocList.get(i).getNgayBatDau()) );
        dsKhoaHoc.ngayKT.setText("Ngày kết thúc :  " + sdf.format(khoaHocList.get(i).getNgayKetThuc()) );
        return view;
    }

    public static class DsKhoaHoc{
        TextView maKH;
        TextView tenKH;
        TextView ngayBD;
        TextView ngayKT;
        ImageView img_delete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
