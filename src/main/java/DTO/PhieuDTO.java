package DTO;


public class PhieuDTO {
    private int maPhieuNhap;
    private java.sql.Timestamp thoiGian;
    private String trangThai;
    private int maNguoiTao;

    public PhieuDTO() {

    }

    public PhieuDTO(int maPhieuNhap, java.sql.Timestamp thoiGian, int maNguoiTao, String trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.thoiGian = thoiGian;
        this.maNguoiTao = maNguoiTao;
        this.trangThai = trangThai;
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }
    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    public java.sql.Timestamp getThoiGian() {
        return thoiGian;
    }
    public void setThoiGian(java.sql.Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public int getMaNguoiTao() {
        return maNguoiTao;
    }
    public void setMaNguoiTao(int nguoiTao) {
        this.maNguoiTao = nguoiTao;
    }

    @Override
    public String toString() {
        return "PhieuDTO{" + "maPhieuNhap=" + maPhieuNhap + "thoiGian=" + thoiGian + "trangThai=" + trangThai + ", maNguoiTao=" + maNguoiTao + '}';
    }
}
