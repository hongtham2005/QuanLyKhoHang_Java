/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hong tham
 */
import java.math.BigDecimal;
import java.util.Date;

public class SanPhamDTO {
    private int maSanPham;
    private String tenSanPham;
    private String xuatXu;
    private Date hanSuDung;
    private BigDecimal giaNhap;
    private BigDecimal giaXuat;
    private String hinhAnh;
    private int maDonViTinh;
    private int maLoaiHang;

    public SanPhamDTO() {
    }

    public SanPhamDTO(
            int maSanPham, 
            String tenSanPham, 
            String xuatXu, 
            Date hanSuDung,
            BigDecimal giaNhap, 
            BigDecimal giaXuat, 
            String hinhAnh,
            int maDonViTinh, 
            int maLoaiHang
    ) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.xuatXu = xuatXu;
        this.hanSuDung = hanSuDung;
        this.giaNhap = giaNhap;
        this.giaXuat = giaXuat;
        this.hinhAnh = hinhAnh;
        this.maDonViTinh = maDonViTinh;
        this.maLoaiHang = maLoaiHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(
            int maSanPham
    ) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(
            String tenSanPham
    ) {
        this.tenSanPham = tenSanPham;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(
            String xuatXu
    ) {
        this.xuatXu = xuatXu;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(
            Date hanSuDung
    ) {
        this.hanSuDung = hanSuDung;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(
            BigDecimal giaNhap
    ) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(
            BigDecimal giaXuat
    ) {
        this.giaXuat = giaXuat;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(
            String hinhAnh
    ) {
        this.hinhAnh = hinhAnh;
    }

    public int getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(
            int maDonViTinh
    ) {
        this.maDonViTinh = maDonViTinh;
    }

    public int getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(
            int maLoaiHang
    ) {
        this.maLoaiHang = maLoaiHang;
    }
}
