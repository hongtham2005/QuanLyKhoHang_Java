/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hong tham
 */
public class NhomQuyenDTO {
    private int maNhomQuyen;
    private String tenNhomQuyen;

    public NhomQuyenDTO(){
    
    }
    public NhomQuyenDTO(int maNhomQuyen, String tenNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public int getMaNhomQuyen(){ 
        return maNhomQuyen; 
    }
    public void setMaNhomQuyen(int maNhomQuyen){ 
        this.maNhomQuyen = maNhomQuyen; 
    }
    public String getTenNhomQuyen(){
        return tenNhomQuyen; 
    }
    public void setTenNhomQuyen(String tenNhomQuyen){
        this.tenNhomQuyen = tenNhomQuyen; 
    }
}