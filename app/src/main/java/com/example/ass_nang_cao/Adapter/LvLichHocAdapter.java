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
import com.example.ass_nang_cao.DAO.LichHocDAO;
import com.example.ass_nang_cao.Models.KhoaHoc;
import com.example.ass_nang_cao.Models.LichHoc;
import com.example.ass_nang_cao.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LvLichHocAdapter extends BaseAdapter {
    Context context;
    List<LichHoc> lichHocList = new ArrayList<>();
    LichHocDAO lichHocDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public LvLichHocAdapter(Context context, List<LichHoc> lichHocList) {
        this.context = context;
        this.lichHocList = lichHocList;
        lichHocDAO = new LichHocDAO(context);
    }

    @Override
    public int getCount() {
        return lichHocList.size();
    }

    @Override
    public Object getItem(int i) {
        return lichHocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DsLichHoc dsLichHoc;
        if (view == null){
            dsLichHoc = new DsLichHoc();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.show_lich_hoc , null);

            dsLichHoc.ca = view.findViewById(R.id.tv_ca);
            dsLichHoc.maMon = view.findViewById(R.id.tv_ma_mon);
            dsLichHoc.tenMOn = view.findViewById(R.id.tv_ten_mon);
            dsLichHoc.lop = view.findViewById(R.id.tv_lop);
            dsLichHoc.phong = view.findViewById(R.id.tv_phong);
            dsLichHoc.ngay = view.findViewById(R.id.tv_ngay);
            dsLichHoc.gio_bat_dau = view.findViewById(R.id.tv_gio_bd);
            dsLichHoc.gio_ket_thuc = view.findViewById(R.id.tv_gio_kt);

            dsLichHoc.img_delete = view.findViewById(R.id.imv_delete_lich_hoc);

            dsLichHoc.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông Báo").setMessage("Bạn có chắc chắn muốn xóa không ?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lichHocDAO.delete_Lich_Hoc_By_ID(lichHocList.get(i).getCa());
                            lichHocList.remove(i);
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
            view.setTag(dsLichHoc);
        } else {
            dsLichHoc = (DsLichHoc) view.getTag();
        }

        dsLichHoc.ca.setText("CA :  " + lichHocList.get(i).getCa());
        dsLichHoc.maMon.setText("Mã môn :  " + lichHocList.get(i).getMaMon());
        dsLichHoc.tenMOn.setText("Tên môn :  " + lichHocList.get(i).getTenMOn());
        dsLichHoc.lop.setText("Lớp :  " + lichHocList.get(i).getLop());
        dsLichHoc.phong.setText("Phòng :  " + lichHocList.get(i).getPhong());

        dsLichHoc.ngay.setText("Ngày :  " + sdf.format( lichHocList.get(i).getNgay() ) );

        dsLichHoc.gio_bat_dau.setText("Giờ bắt đầu  " + lichHocList.get(i).getGio_bat_dau());
        dsLichHoc.gio_ket_thuc.setText("Giờ kết thúc  " + lichHocList.get(i).getGio_ket_thuc());
        return view;
    }

    public static class DsLichHoc{
        TextView ca;
        TextView maMon;
        TextView tenMOn;
        TextView lop ;
        TextView phong;
        TextView ngay ;
        TextView gio_bat_dau;
        TextView gio_ket_thuc;
        ImageView img_delete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
