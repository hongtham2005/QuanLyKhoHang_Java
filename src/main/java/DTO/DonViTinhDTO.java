package DTO;

public class DonViTinhDTO {
    private int maDonViTinh;
    private String tenDonViTinh;

    public DonViTinhDTO(int ma, String ten) {
        this.maDonViTinh = ma;
        this.tenDonViTinh = ten;
    }

    public int getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(int maDonViTinh) {
        this.maDonViTinh = maDonViTinh;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
    }

    @Override
    public String toString() {
        return maDonViTinh + " - " + tenDonViTinh;
    }
}
