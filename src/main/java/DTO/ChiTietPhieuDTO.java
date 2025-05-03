package DTO;

public class ChiTietPhieuDTO {
    private int maPhieuNhap;
    private int soLuong;
    private int maSanPham;
    private int donGia;

    public ChiTietPhieuDTO() {

    }
    public ChiTietPhieuDTO(int maPhieuNhap, int soLuong, int maSanPham, int donGia) {
        this.maPhieuNhap = maPhieuNhap;
        this.soLuong = soLuong;
        this.maSanPham = maSanPham;
        this.donGia = donGia;
    }
    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }
    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getMaSanPham() {
        return maSanPham;
    }
    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }
    public int getDonGia() {
        return donGia;
    }
    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
    @Override
    public String toString() {
        return "ChiTietPhieuDTO{" + "maPhieuNhap=" + maPhieuNhap + ", soLuong=" + soLuong + ", maSanPham=" + maSanPham + ", donGia=" + donGia + '}';
    }
}
