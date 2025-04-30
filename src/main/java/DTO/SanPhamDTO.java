package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class SanPhamDTO {
    private int maSanPham;
    private String tenSanPham;
    private Date hanSuDung;
    private BigDecimal giaNhap;
    private BigDecimal giaXuat;
    private String hinhAnh;
    private int maLoaiHang;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int maSanPham, String tenSanPham, Date hanSuDung, BigDecimal giaNhap, BigDecimal giaXuat, String hinhAnh, int maLoaiHang) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.hanSuDung = hanSuDung;
        this.giaNhap = giaNhap;
        this.giaXuat = giaXuat;
        this.hinhAnh = hinhAnh;
        this.maLoaiHang = maLoaiHang;
    }

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

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(BigDecimal giaXuat) {
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
