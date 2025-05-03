package DTO;

public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;
    private String trangThai;
    private String email;

    // Constructor không có mã (dùng khi thêm mới)
    public KhachHangDTO(String ten, String diaChi, String soDienThoai) {
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.trangThai = "Hoạt động";
    }

    // Constructor có mã (dùng khi sửa)
    public KhachHangDTO(int ma, String ten, String diaChi, String soDienThoai) {
        this.maKhachHang = ma;
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    // Constructor đầy đủ (bao gồm TrangThai và Email)
    public KhachHangDTO(int ma, String ten, String diaChi, String sdt, String trangThai, String email) {
        this.maKhachHang = ma;
        this.tenKhachHang = ten;
        this.diaChi = diaChi;
        this.soDienThoai = sdt;
        this.trangThai = trangThai;
        this.email = email;
    }

    // Constructor mặc định
    public KhachHangDTO() {}

    // Getters + Setters
    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int ma) { this.maKhachHang = ma; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String ten) { this.tenKhachHang = ten; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String sdt) { this.soDienThoai = sdt; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}