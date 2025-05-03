package DTO;

public class ChiTietKiemKeDTO {
    private int maPhieuKiemKe;
    private int maSanPham;
    private int soLuong;
    private int chenhLech;
    private String ghiChu;

    public ChiTietKiemKeDTO() {}

    public ChiTietKiemKeDTO(int maPhieuKiemKe, int maSanPham, int soLuong, int chenhLech, String ghiChu) {
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.chenhLech = chenhLech;
        this.ghiChu = ghiChu;
    }
    public int getMaPhieuKiemKe() {
        return maPhieuKiemKe;
    }
    public void setMaPhieuKiemKe(int maPhieuKiemKe) {
        this.maPhieuKiemKe = maPhieuKiemKe;
    }
    public int getMaSanPham() {
        return maSanPham;
    }
    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getChenhLech() {
        return chenhLech;
    }
    public void setChenhLech(int chenhLech) {
        this.chenhLech = chenhLech;
    }
    public String getGhiChu() {
        return ghiChu;
    }
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ChiTietKiemKeDTO{" + "maPhieuKiemKe=" + maPhieuKiemKe + ", maSanPham=" + maSanPham + ", soLuong=" + soLuong + ", chenhLech=" + chenhLech + ", ghiChu=" + ghiChu + '}';
    }
}
