/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hong tham
 */
public class ChiTietQuyenDTO {
    private int maNhomQuyen;
    private int maChucNang;
    private String hanhDong;

    public ChiTietQuyenDTO() {
    }

    public ChiTietQuyenDTO(
            int maNhomQuyen, 
            int maChucNang, 
            String hanhDong
    ) {
        this.maNhomQuyen = maNhomQuyen;
        this.maChucNang = maChucNang;
        this.hanhDong = hanhDong;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(
            int maNhomQuyen
    ) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(
            int maChucNang
    ) {
        this.maChucNang = maChucNang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(
            String hanhDong
    ) {
        this.hanhDong = hanhDong;
    }
}