/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hong tham
 */
public class LoaiHangDTO {
    private int maLoaiHang;
    private String tenLoaiHang;

    public LoaiHangDTO() {
    }

    public LoaiHangDTO(
            int maLoaiHang, 
            String tenLoaiHang
    ) {
        this.maLoaiHang = maLoaiHang;
        this.tenLoaiHang = tenLoaiHang;
    }

    public int getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(
            int maLoaiHang
    ) {
        this.maLoaiHang = maLoaiHang;
    }

    public String getTenLoaiHang() {
        return tenLoaiHang;
    }

    public void setTenLoaiHang(
            String tenLoaiHang
    ) {
        this.tenLoaiHang = tenLoaiHang;
    }
}