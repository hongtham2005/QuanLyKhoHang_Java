package DTO;

public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;
    private String trangThai;

    // Constructors
 // Constructor KHÔNG có mã (dùng khi thêm mới)
    public KhachHangDTO(String ten, String diaChi, String soDienThoai) {
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.trangThai = "Hoạt động";
    }

    // Constructor CÓ mã (dùng khi sửa)
    public KhachHangDTO(int ma, String ten, String diaChi, String soDienThoai) {
        this.maKhachHang = ma;
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public KhachHangDTO() {}
    public KhachHangDTO(int ma, String ten, String diaChi, String sdt, String trangThai) {
        this.maKhachHang = ma;
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = sdt;
        this.trangThai = trangThai;
    }

    // Getters + Setters
    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int ma) { this.maKhachHang = ma; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String ten) { this.tenKhachHang = ten; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String sdt) { this.soDienThoai = sdt; }
    
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
