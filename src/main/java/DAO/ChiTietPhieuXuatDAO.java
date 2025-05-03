package DAO;

import DTO.ChiTietPhieuXuatDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuXuatDAO {
    Connection conn;

    public ChiTietPhieuXuatDAO() throws SQLException {
        this.conn = MySQLConnection.getConnection();
    }

    public ArrayList<ChiTietPhieuXuatDTO> layDanhSachSanPhamTheoPhieuXuat(int maPhieuXuat) throws SQLException {
        ArrayList<ChiTietPhieuXuatDTO> ds = new ArrayList<>();
        String sql = "SELECT ctp.*, sp.TenSanPham FROM ChiTietPhieuXuat ctp " +
                     "JOIN SanPham sp ON ctp.MaSanPham = sp.MaSanPham " +
                     "WHERE ctp.MaPhieuXuat = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhieuXuat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuXuatDTO ct = new ChiTietPhieuXuatDTO(
                        rs.getInt("MaPhieuXuat"),
                        rs.getInt("MaSanPham"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("DonGia"),
                        rs.getString("TenSanPham")
                    );
                    ds.add(ct);
                }
            }
        }
        return ds;
    }

    // Thêm một chi tiết vào phiếu
    public void them(ChiTietPhieuXuatDTO ct) throws SQLException {
        String sql = "INSERT INTO ChiTietPhieuXuat (MaPhieuXuat, MaSanPham, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ct.getMaPhieuXuat());
            ps.setInt(2, ct.getMaSanPham());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            ps.executeUpdate();
        }
    }

    // Xoá một dòng sản phẩm khỏi phiếu xuất
    public void xoa(int maPhieuXuat, int maSanPham) throws SQLException {
        String sql = "DELETE FROM ChiTietPhieuXuat WHERE MaPhieuXuat = ? AND MaSanPham = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhieuXuat);
            ps.setInt(2, maSanPham);
            ps.executeUpdate();
        }
    }

    // Cập nhật chi tiết
    public void sua(ChiTietPhieuXuatDTO ct) throws SQLException {
        String sql = "UPDATE ChiTietPhieuXuat SET SoLuong = ?, DonGia = ? WHERE MaPhieuXuat = ? AND MaSanPham = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getDonGia());
            ps.setInt(3, ct.getMaPhieuXuat());
            ps.setInt(4, ct.getMaSanPham());
            ps.executeUpdate();
        }
    }

    // Đóng kết nối
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}