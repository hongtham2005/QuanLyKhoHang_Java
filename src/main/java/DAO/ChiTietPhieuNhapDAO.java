package DAO;

import DTO.ChiTietPhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapDAO {
    private Connection connection;

    public ChiTietPhieuNhapDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm mới chi tiết phiếu nhập
    public void save(ChiTietPhieuNhapDTO chiTiet) throws SQLException {
        String sql = "INSERT INTO ChiTietPhieuNhap (MaPhieuNhap, MaSanPham, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getMaPhieuNhap());
            stmt.setInt(2, chiTiet.getMaSanPham());
            stmt.setInt(3, chiTiet.getSoLuong());
            stmt.setInt(4, chiTiet.getDonGia());
            stmt.executeUpdate();
        }
    }

    // Cập nhật chi tiết phiếu nhập
    public void update(ChiTietPhieuNhapDTO chiTiet) throws SQLException {
        String sql = "UPDATE ChiTietPhieuNhap SET SoLuong = ?, DonGia = ? WHERE MaPhieuNhap = ? AND MaSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getSoLuong());
            stmt.setInt(2, chiTiet.getDonGia());
            stmt.setInt(3, chiTiet.getMaPhieuNhap());
            stmt.setInt(4, chiTiet.getMaSanPham());
            stmt.executeUpdate();
        }
    }

    // Xóa chi tiết phiếu nhập
    public void delete(int maPhieuNhap, int maSanPham) throws SQLException {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPhieuNhap = ? AND MaSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuNhap);
            stmt.setInt(2, maSanPham);
            stmt.executeUpdate();
        }
    }

    // Tìm chi tiết phiếu nhập theo mã phiếu
    public List<ChiTietPhieuNhapDTO> findByMaPhieuNhap(int maPhieuNhap) throws SQLException {
        List<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPhieuNhap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuNhap);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(
                    rs.getInt("MaPhieuNhap"),
                    rs.getInt("MaSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getInt("DonGia"), maPhieuNhap
                );
                result.add(chiTiet);
            }
        }
        return result;
    }

    // Tìm chi tiết phiếu nhập theo mã sản phẩm
    public List<ChiTietPhieuNhapDTO> findByMaSanPham(int maSanPham) throws SQLException {
        List<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(
                    rs.getInt("MaPhieuNhap"),
                    rs.getInt("MaSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getInt("DonGia"), maSanPham
                );
                result.add(chiTiet);
            }
        }
        return result;
    }

    // Tìm một chi tiết phiếu nhập theo khóa chính
    public ChiTietPhieuNhapDTO findById(int maPhieuNhap, int maSanPham) throws SQLException {
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPhieuNhap = ? AND MaSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuNhap);
            stmt.setInt(2, maSanPham);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ChiTietPhieuNhapDTO(
                    rs.getInt("MaPhieuNhap"),
                    rs.getInt("MaSanPham"),
                    rs.getInt("SoLuong"),
                    rs.getInt("DonGia"), maSanPham
                );
            }
        }
        return null;
    }
}