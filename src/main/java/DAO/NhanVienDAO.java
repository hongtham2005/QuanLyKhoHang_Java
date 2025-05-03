package DAO;

import database.MySQLConnection;
import DTO.NhanVienDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    // Lấy mã nhân viên dựa trên email (liên kết qua bảng TaiKhoan)
    public Integer layMaNhanVienTheoEmail(String email) throws SQLException {
        String sql = "SELECT nv.MaNhanVien FROM NhanVien nv " +
                     "JOIN TaiKhoan tk ON nv.MaNhanVien = tk.MaTaiKhoan " +
                     "WHERE tk.Email = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaNhanVien");
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Lấy danh sách tất cả nhân viên
    public List<NhanVienDTO> getAllNhanVien() throws SQLException {
        List<NhanVienDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                    rs.getInt("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getString("SoDienThoai")
                );
                danhSach.add(nv);
            }
        }
        return danhSach;
    }

    // Thêm nhân viên mới
    public boolean themNhanVien(NhanVienDTO nv) throws SQLException {
        String sql = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, GioiTinh, NgaySinh, SoDienThoai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nv.getMaNhanVien());
            ps.setString(2, nv.getTenNhanVien());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(5, nv.getSoDienThoai());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Cập nhật thông tin nhân viên
    public boolean suaNhanVien(NhanVienDTO nv) throws SQLException {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, GioiTinh = ?, NgaySinh = ?, SoDienThoai = ? WHERE MaNhanVien = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNhanVien());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(4, nv.getSoDienThoai());
            ps.setInt(5, nv.getMaNhanVien());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Xóa nhân viên
    public boolean xoaNhanVien(int maNhanVien) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Lấy thông tin nhân viên dựa trên mã
    public NhanVienDTO getNhanVienById(int maNhanVien) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new NhanVienDTO(
                        rs.getInt("MaNhanVien"),
                        rs.getString("TenNhanVien"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh"),
                        rs.getString("SoDienThoai")
                    );
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
}