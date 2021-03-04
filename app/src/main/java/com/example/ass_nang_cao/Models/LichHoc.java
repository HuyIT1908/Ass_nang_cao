package com.example.ass_nang_cao.Models;

import java.util.Date;

public class LichHoc {
    String maMon;
    String tenMOn; 
    String Lop ;
    String phong; 
    Date ngay ;
    String gio_bat_dau; 
    String gio_ket_thuc;

    public LichHoc() {
    }

    public LichHoc(String maMon, String tenMOn, String lop, String phong, Date ngay, String gio_bat_dau, String gio_ket_thuc) {
        this.maMon = maMon;
        this.tenMOn = tenMOn;
        Lop = lop;
        this.phong = phong;
        this.ngay = ngay;
        this.gio_bat_dau = gio_bat_dau;
        this.gio_ket_thuc = gio_ket_thuc;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMOn() {
        return tenMOn;
    }

    public void setTenMOn(String tenMOn) {
        this.tenMOn = tenMOn;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getGio_bat_dau() {
        return gio_bat_dau;
    }

    public void setGio_bat_dau(String gio_bat_dau) {
        this.gio_bat_dau = gio_bat_dau;
    }

    public String getGio_ket_thuc() {
        return gio_ket_thuc;
    }

    public void setGio_ket_thuc(String gio_ket_thuc) {
        this.gio_ket_thuc = gio_ket_thuc;
    }

    @Override
    public String toString() {
        return "LichHoc{" +
                "maMon='" + maMon + '\'' +
                ", tenMOn='" + tenMOn + '\'' +
                ", Lop='" + Lop + '\'' +
                ", phong='" + phong + '\'' +
                ", ngay=" + ngay +
                ", gio_bat_dau='" + gio_bat_dau + '\'' +
                ", gio_ket_thuc='" + gio_ket_thuc + '\'' +
                '}';
    }
}
