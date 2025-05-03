package DAO;

import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private Connection connection;

    public KhachHangDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm mới một khách hàng
    public void save(KhachHangDTO khachHang) throws SQLException {
        String sql = "INSERT INTO KhachHang (MaKhachHang, TenKhachHang, DiaChi, SoDienThoai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, khachHang.getMaKhachHang());
            stmt.setString(2, khachHang.getTenKhachHang());
            stmt.setString(3, khachHang.getDiaChi());
            stmt.setString(4, khachHang.getSoDienThoai());
            stmt.executeUpdate();
        }
    }

    // Cập nhật thông tin khách hàng
    public void update(KhachHangDTO khachHang) throws SQLException {
        String sql = "UPDATE KhachHang SET TenKhachHang = ?, DiaChi = ?, SoDienThoai = ? WHERE MaKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, khachHang.getTenKhachHang());
            stmt.setString(2, khachHang.getDiaChi());
            stmt.setString(3, khachHang.getSoDienThoai());
            stmt.setInt(4, khachHang.getMaKhachHang());
            stmt.executeUpdate();
        }
    }

    // Xóa khách hàng
    public void delete(int maKhachHang) throws SQLException {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKhachHang);
            stmt.executeUpdate();
        }
    }

    // Tìm khách hàng theo mã
    public KhachHangDTO findByMaKhachHang(int maKhachHang) throws SQLException {
        String sql = "SELECT * FROM KhachHang WHERE MaKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKhachHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHangDTO(
                    rs.getInt("MaKhachHang"),
                    rs.getString("TenKhachHang"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai")
                );
            }
        }
        return null;
    }

    // Tìm khách hàng theo tên (gần đúng)
    public List<KhachHangDTO> findByTenKhachHang(String tenKhachHang) throws SQLException {
        List<KhachHangDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + tenKhachHang + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO(
                    rs.getInt("MaKhachHang"),
                    rs.getString("TenKhachHang"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai")
                );
                result.add(khachHang);
            }
        }
        return result;
    }

    // Tìm khách hàng theo số điện thoại
    public KhachHangDTO findBySoDienThoai(String soDienThoai) throws SQLException {
        String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHangDTO(
                    rs.getInt("MaKhachHang"),
                    rs.getString("TenKhachHang"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai")
                );
            }
        }
        return null;
    }

    // Lấy tất cả khách hàng
    public List<KhachHangDTO> findAll() throws SQLException {
        List<KhachHangDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO(
                    rs.getInt("MaKhachHang"),
                    rs.getString("TenKhachHang"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai")
                );
                result.add(khachHang);
            }
        }
        return result;
    }
}