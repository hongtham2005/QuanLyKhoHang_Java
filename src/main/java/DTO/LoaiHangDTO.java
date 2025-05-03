
package DTO;

public class LoaiHangDTO {
    private int maLoaiHang;
    private String tenLoaiHang;

    public LoaiHangDTO() {
        // Sửa: Thêm constructor mặc định
    }

    public LoaiHangDTO(int ma, String ten) {
        this.maLoaiHang = ma;
        this.tenLoaiHang = ten;
    }

    public int getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(int maLoaiHang) {
        this.maLoaiHang = maLoaiHang;
    }

    public String getTenLoaiHang() {
        return tenLoaiHang;
    }

    public void setTenLoaiHang(String tenLoaiHang) {
        this.tenLoaiHang = tenLoaiHang;
    }

    @Override
    public String toString() {
        return maLoaiHang + " - " + tenLoaiHang;
    }
}
