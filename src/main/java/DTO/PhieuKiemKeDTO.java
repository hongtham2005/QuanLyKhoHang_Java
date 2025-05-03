package DTO;

public class PhieuKiemKeDTO {
    private int maPhieuKiemKe;
    private int nguoiTao;
    private java.sql.Timestamp thoiGian;

    public PhieuKiemKeDTO(int maPhieuKiemKe, int nguoiTao) {
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.nguoiTao = nguoiTao;
    }

    public PhieuKiemKeDTO(int maPhieuKiemKe, int nguoiTao, java.sql.Timestamp thoiGian) {
        this.maPhieuKiemKe = maPhieuKiemKe;
        this.nguoiTao = nguoiTao;
        this.thoiGian = thoiGian;
    }

    public int getMaPhieuKiemKe() {
        return maPhieuKiemKe;
    }
    public void setMaPhieuKiemKe(int maPhieuKiemKe) {
        this.maPhieuKiemKe = maPhieuKiemKe;
    }
    public int getNguoiTao() {
        return nguoiTao;
    }
    public void setNguoiTao(int nguoiTao) {
        this.nguoiTao = nguoiTao;
    }
    public java.sql.Timestamp getThoiGian() {
        return thoiGian;
    }
    public void setThoiGian(java.sql.Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }
    @Override
    public String toString() {
        return "PhieuKiemKeDTO{" + "maPhieuKiemKe=" + maPhieuKiemKe + ", nguoiTao=" + nguoiTao + ", thoiGian=" + thoiGian + '}';
    }
}
