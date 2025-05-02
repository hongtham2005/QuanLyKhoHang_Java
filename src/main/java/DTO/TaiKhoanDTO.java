package DTO;

public class TaiKhoanDTO {
    private int maTaiKhoan;
    private String matKhau;
    private String trangThai;
    private int maNhomQuyen;
    private String email;

    // Constructor đầy đủ 5 tham số
    public TaiKhoanDTO(int maTaiKhoan, String matKhau, String trangThai, int maNhomQuyen, String email) {
        this.maTaiKhoan = maTaiKhoan;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.maNhomQuyen = maNhomQuyen;
        this.email = email;
    }

    // Getter và Setter
    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
