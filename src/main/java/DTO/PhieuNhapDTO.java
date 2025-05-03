package DTO;

import java.security.Timestamp;

public class PhieuNhapDTO extends PhieuDTO {
    private int maNhaCungCap;

    public PhieuNhapDTO(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public PhieuNhapDTO(int maNhaCungCap, int maPhieuNhap, Timestamp thoiGian, int maNguoiTao, int trangThai) {
        super();
        this.maNhaCungCap = maNhaCungCap;
    }
    public PhieuNhapDTO(int int1, int int2, java.sql.Timestamp timestamp, int int3, String string) {
        //TODO Auto-generated constructor stub
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }
    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" + "maNhaCungCap=" + maNhaCungCap + '}';
    }
}
