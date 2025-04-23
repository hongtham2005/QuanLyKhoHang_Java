/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hong tham
 */
public class DonViTinhDTO {
    private int maDonViTinh;
    private String tenDonViTinh;

    public DonViTinhDTO() {
    }

    public DonViTinhDTO(
            int maDonViTinh, 
            String tenDonViTinh
    ) {
        this.maDonViTinh = maDonViTinh;
        this.tenDonViTinh = tenDonViTinh;
    }

    public int getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(
            int maDonViTinh
    ) {
        this.maDonViTinh = maDonViTinh;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh){
        this.tenDonViTinh = tenDonViTinh;
    }
}
