package DAO;

import DTO.SanPhamDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    Connection conn;

    public SanPhamDAO() throws Exception {
        conn = MyConnect.getConnection();
    }

    // Đọc danh sách sản phẩm
    public ArrayList<SanPhamDTO> docDSSanPham() throws Exception {
        ArrayList<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            ds.add(new SanPhamDTO(
                rs.getInt("MaSanPham"),
                rs.getString("TenSanPham"),
                rs.getString("XuatXu"),
                rs.getDate("HanSuDung"),
                rs.getDouble("GiaNhap"),
                rs.getDouble("GiaXuat"),
                rs.getString("HinhAnh"),
                rs.getInt("MaDonViTinh"),
                rs.getInt("MaLoaiHang")
            ));
        }
        return ds;
    }

    // Thêm sản phẩm
    public void them(SanPhamDTO sp) throws Exception {
        String sql = "INSERT INTO SanPham (MaSanPham, TenSanPham, XuatXu, HanSuDung, GiaNhap, GiaXuat, HinhAnh, MaDonViTinh, MaLoaiHang) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, sp.getMaSanPham());
        ps.setString(2, sp.getTenSanPham());
        ps.setString(3, sp.getXuatXu());
        ps.setDate(4, new java.sql.Date(sp.getHanSuDung().getTime()));
        ps.setDouble(5, sp.getGiaNhap());
        ps.setDouble(6, sp.getGiaXuat());
        ps.setString(7, sp.getHinhAnh());
        ps.setInt(8, sp.getMaDonViTinh());
        ps.setInt(9, sp.getMaLoaiHang());
        ps.executeUpdate();
    }

    // Sửa sản phẩm
    public void sua(SanPhamDTO sp) throws Exception {
        String sql = "UPDATE SanPham SET TenSanPham=?, XuatXu=?, HanSuDung=?, GiaNhap=?, GiaXuat=?, HinhAnh=?, MaDonViTinh=?, MaLoaiHang=? " +
                     "WHERE MaSanPham=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sp.getTenSanPham());
        ps.setString(2, sp.getXuatXu());
        ps.setDate(3, new java.sql.Date(sp.getHanSuDung().getTime()));
        ps.setDouble(4, sp.getGiaNhap());
        ps.setDouble(5, sp.getGiaXuat());
        ps.setString(6, sp.getHinhAnh());
        ps.setInt(7, sp.getMaDonViTinh());
        ps.setInt(8, sp.getMaLoaiHang());
        ps.setInt(9, sp.getMaSanPham());
        ps.executeUpdate();
    }

    // Xóa sản phẩm (có thể sửa để xóa mềm nếu cần)
    public void xoa(int maSanPham) throws Exception {
        String sql = "DELETE FROM SanPham WHERE MaSanPham=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maSanPham);
        ps.executeUpdate();
    }

    // Lấy mã sản phẩm tiếp theo
    public int layMaTiepTheo() throws Exception {
        String sql = "SELECT MAX(MaSanPham) FROM SanPham";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1) + 1;
        }
        return 1;
    }
}
