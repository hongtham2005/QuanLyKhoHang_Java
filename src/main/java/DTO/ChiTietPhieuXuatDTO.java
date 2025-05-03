package DTO;

public class ChiTietPhieuXuatDTO {
    private int maPhieuXuat;
    private int maSanPham;
    private int soLuong;
    private double donGia;
    private String tenSanPham; // Thêm trường này

    public ChiTietPhieuXuatDTO() {}

    // Constructor với 4 tham số (không bao gồm tenSanPham)
    public ChiTietPhieuXuatDTO(int maPhieuXuat, int maSanPham, int soLuong, double donGia) {
        this.maPhieuXuat = maPhieuXuat;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tenSanPham = null; // Có thể lấy sau từ SanPhamDAO nếu cần
    }

    // Constructor với 5 tham số (bao gồm tenSanPham)
    public ChiTietPhieuXuatDTO(int maPhieuXuat, int maSanPham, int soLuong, double donGia, String tenSanPham) {
        this.maPhieuXuat = maPhieuXuat;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tenSanPham = tenSanPham;
    }

    // Getter và Setter
    public int getMaPhieuXuat() { return maPhieuXuat; }
    public void setMaPhieuXuat(int maPhieuXuat) { this.maPhieuXuat = maPhieuXuat; }
    public int getMaSanPham() { return maSanPham; }
    public void setMaSanPham(int maSanPham) { this.maSanPham = maSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    @Override
    public String toString() {
        return "ChiTietPhieuXuatDTO{" +
                "maPhieuXuat=" + maPhieuXuat +
                ", maSanPham=" + maSanPham +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", tenSanPham='" + tenSanPham + '\'' +
                '}';
    }
}