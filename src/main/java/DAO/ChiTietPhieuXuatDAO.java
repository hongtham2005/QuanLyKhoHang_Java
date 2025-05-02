package DAO;

import DTO.ChiTietPhieuXuatDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuXuatDAO {
    Connection conn;

    public ChiTietPhieuXuatDAO() throws Exception {
        conn = MyConnect.getConnection();
    }

    // Lấy danh sách chi tiết theo mã phiếu xuất
    public ArrayList<ChiTietPhieuXuatDTO> docChiTietTheoPhieu(int maPhieuXuat) throws Exception {
        ArrayList<ChiTietPhieuXuatDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuXuat WHERE MaPhieuXuat=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maPhieuXuat);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ChiTietPhieuXuatDTO ct = new ChiTietPhieuXuatDTO(
                rs.getInt("MaPhieuXuat"),
                rs.getInt("MaSanPham"),
                rs.getInt("SoLuong"),
                rs.getDouble("DonGia")
            );
            ds.add(ct);
        }

        return ds;
    }

    // Thêm một chi tiết vào phiếu
    public void them(ChiTietPhieuXuatDTO ct) throws Exception {
        String sql = "INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaSanPham, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ct.getMaPhieuXuat());
        ps.setInt(2, ct.getMaSanPham());
        ps.setInt(3, ct.getSoLuong());
        ps.setDouble(4, ct.getDonGia());
        ps.executeUpdate();
    }

    // Xoá một dòng sản phẩm khỏi phiếu xuất
    public void xoa(int maPhieuXuat, int maSanPham) throws Exception {
        String sql = "DELETE FROM ChiTietPhieuXuat WHERE MaPhieuXuat=? AND MaSanPham=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maPhieuXuat);
        ps.setInt(2, maSanPham);
        ps.executeUpdate();
    }
    public void sua(ChiTietPhieuXuatDTO ct) throws Exception {
        String sql = "UPDATE chitietphieuxuat SET SoLuong = ?, DonGia = ? WHERE MaPhieuXuat = ? AND MaSanPham = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ct.getSoLuong());
        ps.setDouble(2, ct.getDonGia());
        ps.setInt(3, ct.getMaPhieuXuat());
        ps.setInt(4, ct.getMaSanPham());
        ps.executeUpdate();
    }

}
