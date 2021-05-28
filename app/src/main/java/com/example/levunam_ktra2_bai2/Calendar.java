package com.example.levunam_ktra2_bai2;

public class Calendar {
    private int id;
    private String tenMon;
    private String ngayThi;
    private String gioBatDau;
    private String kieuThi;

    public Calendar() {
    }

    public Calendar(int id, String tenMon, String ngayThi, String gioBatDau, String kieuThi) {
        this.id = id;
        this.tenMon = tenMon;
        this.ngayThi = ngayThi;
        this.gioBatDau = gioBatDau;
        this.kieuThi = kieuThi;
    }

    public Calendar(String tenMon, String ngayThi, String gioBatDau, String kieuThi) {
        this.tenMon = tenMon;
        this.ngayThi = ngayThi;
        this.gioBatDau = gioBatDau;
        this.kieuThi = kieuThi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getKieuThi() {
        return kieuThi;
    }

    public void setKieuThi(String kieuThi) {
        this.kieuThi = kieuThi;
    }
}
