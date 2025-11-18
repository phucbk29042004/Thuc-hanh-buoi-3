package com.example.projectlab8;

public class MonAn {
    private String tenMon;
    private int gia;
    private String moTa;
    private int hinhAnh;

    public MonAn(String tenMon, int gia, String moTa, int hinhAnh) {
        this.tenMon = tenMon;
        this.gia = gia;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }

    // Getter
    public String getTenMon() { return tenMon; }
    public int getGia() { return gia; }
    public String getMoTa() { return moTa; }
    public int getHinhAnh() { return hinhAnh; }

    // Setter (CẦN THIẾT CHO SỬA)
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }
    public void setGia(int gia) { this.gia = gia; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public void setHinhAnh(int hinhAnh) { this.hinhAnh = hinhAnh; }

    @Override
    public String toString() {
        return tenMon + " - " + gia + "đ";
    }
}