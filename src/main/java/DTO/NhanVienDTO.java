package DTO;

import java.util.Date;

public class NhanVienDTO {
    private int maNhanVien;
    private String hoTen;
    private String gioiTinh; 
    private String soDienThoai;
    private Date ngaySinh;

    public NhanVienDTO() {
    }

    public NhanVienDTO(int maNhanVien, String hoTen, String gioiTinh, String soDienThoai, Date ngaySinh) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" +
               "maNhanVien=" + maNhanVien +
               ", hoTen='" + hoTen + '\'' +
               ", gioiTinh='" + gioiTinh + '\'' +
               ", soDienThoai='" + soDienThoai + '\'' +
               ", ngaySinh=" + ngaySinh +
               '}';
    }
}