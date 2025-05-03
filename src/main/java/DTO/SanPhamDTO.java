package DTO;

import java.util.Date;

public class SanPhamDTO {
    private int maSanPham;
    private String tenSanPham;
    private String xuatXu;
    private Date hanSuDung;
    private double giaNhap;
    private double giaXuat;
    private String hinhAnh;
    private int maLoaiHang;

    // Constructor mặc định
    public SanPhamDTO() {
        this.maSanPham = 0;
        this.tenSanPham = "";
        this.xuatXu = "";
        this.hanSuDung = new Date();
        this.giaNhap = 0.0;
        this.giaXuat = 0.0;
        this.hinhAnh = "";
        this.maLoaiHang = 0;
    }

    public SanPhamDTO(int maSanPham, String tenSanPham, String xuatXu, Date hanSuDung,
                      double giaNhap, double giaXuat, String hinhAnh,
                      int maLoaiHang) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.xuatXu = xuatXu;
        this.hanSuDung = hanSuDung;
        this.giaNhap = giaNhap;
        this.giaXuat = giaXuat;
        this.hinhAnh = hinhAnh;
        this.maLoaiHang = maLoaiHang;
    }

    // Getter & Setter
    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(double giaXuat) {
        this.giaXuat = giaXuat;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(int maLoaiHang) {
        this.maLoaiHang = maLoaiHang;
    }
}