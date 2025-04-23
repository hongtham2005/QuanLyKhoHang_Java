/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hong tham
 */
public class ChucNangDTO {
    private int maChucNang;
    private String tenChucNang;

    public ChucNangDTO() {
    }

    public ChucNangDTO(
            int maChucNang, 
            String tenChucNang
    ) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
    }

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(
            int maChucNang
    ) {
        this.maChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(
            String tenChucNang
    ) {
        this.tenChucNang = tenChucNang;
    }
}
