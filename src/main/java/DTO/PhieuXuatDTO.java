package DTO;

import java.util.Date;

public class PhieuXuatDTO {
    private int maPhieuXuat;
    private Date thoiGian;
    private String trangThai;
    private Integer nguoiTao;
    private int maKhachHang;

    public PhieuXuatDTO() {}

    public PhieuXuatDTO(int maPhieuXuat, Date thoiGian, String trangThai, Integer nguoiTao, int maKhachHang) {
        this.maPhieuXuat = maPhieuXuat;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.nguoiTao = nguoiTao;
        this.maKhachHang = maKhachHang;
    }

    // Getter - Setter
    public int getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(int maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(Integer nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}