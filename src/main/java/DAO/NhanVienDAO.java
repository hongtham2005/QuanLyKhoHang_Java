package DAO;

import DTO.NhanVienDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private Connection connection;

    public NhanVienDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm mới nhân viên
    public void save(NhanVienDTO nhanVien) throws SQLException {
        String sql = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, GioiTinh, NgaySinh, SoDienThoai) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nhanVien.getMaNhanVien());
            stmt.setString(2, nhanVien.getHoTen());
            stmt.setString(3, nhanVien.getGioiTinh());
            stmt.setDate(4, nhanVien.getNgaySinh() != null ? new java.sql.Date(nhanVien.getNgaySinh().getTime()) : null);
            stmt.setString(5, nhanVien.getSoDienThoai());
            stmt.executeUpdate();
        }
    }

    // Cập nhật thông tin nhân viên
    public void update(NhanVienDTO nhanVien) throws SQLException {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, GioiTinh = ?, NgaySinh = ?, SoDienThoai = ? WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nhanVien.getHoTen());
            stmt.setString(2, nhanVien.getGioiTinh());
            stmt.setDate(3, nhanVien.getNgaySinh() != null ? new java.sql.Date(nhanVien.getNgaySinh().getTime()) : null);
            stmt.setString(4, nhanVien.getSoDienThoai());
            stmt.setInt(5, nhanVien.getMaNhanVien());
            stmt.executeUpdate();
        }
    }

    // Xóa nhân viên
    public void delete(int maNhanVien) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maNhanVien);
            stmt.executeUpdate();
        }
    }

    // Tìm nhân viên theo mã
    public NhanVienDTO findByMaNhanVien(int maNhanVien) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhanVienDTO(
                    rs.getInt("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getDate("NgaySinh")
                );
            }
        }
        return null;
    }

    // Tìm nhân viên theo tên (gần đúng)
    public List<NhanVienDTO> findByHoTen(String hoTen) throws SQLException {
        List<NhanVienDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TenNhanVien LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + hoTen + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVienDTO nhanVien = new NhanVienDTO(
                    rs.getInt("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getDate("NgaySinh")
                );
                result.add(nhanVien);
            }
        }
        return result;
    }

    // Tìm nhân viên theo số điện thoại
    public NhanVienDTO findBySoDienThoai(String soDienThoai) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE SoDienThoai = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhanVienDTO(
                    rs.getInt("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getDate("NgaySinh")
                );
            }
        }
        return null;
    }

    // Lấy tất cả nhân viên
    public List<NhanVienDTO> findAll() throws SQLException {
        List<NhanVienDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVienDTO nhanVien = new NhanVienDTO(
                    rs.getInt("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getDate("NgaySinh")
                );
                result.add(nhanVien);
            }
        }
        return result;
    }
}