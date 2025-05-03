package DTO;

public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;

    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKhachHang, String tenKhachHang, String diaChi, String soDienThoai) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    public String getTenKhachHang() {
        return tenKhachHang;
    }
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

//    @Override
//    public String toString() {
//        return "khachHang{" + "maKhachHang=" + maKhachHang + ", hoTen=" + tenKhachHang + ", diaChi=" + diaChi; + ", soDienThoai=" + soDienThoai + '}';
//    }
}
