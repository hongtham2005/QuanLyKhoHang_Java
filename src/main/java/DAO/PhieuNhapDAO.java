package DAO;

import DTO.PhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private Connection connection;

    public PhieuNhapDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm mới phiếu nhập
    public void save(PhieuNhapDTO phieuNhap) throws SQLException {
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, ThoiGian, TrangThai, MaNhaCungCap, NguoiTao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, phieuNhap.getMaPhieuNhap());
            stmt.setTimestamp(2, phieuNhap.getThoiGian());
            stmt.setString(3, phieuNhap.getTrangThai());
            stmt.setInt(4, phieuNhap.getMaNhaCungCap());
            stmt.setInt(5, phieuNhap.getMaNguoiTao());
            stmt.executeUpdate();
        }
    }

    // Cập nhật phiếu nhập
    public void update(PhieuNhapDTO phieuNhap) throws SQLException {
        String sql = "UPDATE PhieuNhap SET ThoiGian = ?, TrangThai = ?, MaNhaCungCap = ?, NguoiTao = ? WHERE MaPhieuNhap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, phieuNhap.getThoiGian());
            stmt.setString(2, phieuNhap.getTrangThai());
            stmt.setInt(3, phieuNhap.getMaNhaCungCap());
            stmt.setInt(4, phieuNhap.getMaNguoiTao());
            stmt.setInt(5, phieuNhap.getMaPhieuNhap());
            stmt.executeUpdate();
        }
    }

    // Xóa phiếu nhập
    public void delete(int maPhieuNhap) throws SQLException {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuNhap);
            stmt.executeUpdate();
        }
    }

    // Tìm phiếu nhập theo mã
    public PhieuNhapDTO findByMaPhieuNhap(int maPhieuNhap) throws SQLException {
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuNhap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PhieuNhapDTO(
                    rs.getInt("MaNhaCungCap")
                );
            }
        }
        return null;
    }

    // Tìm phiếu nhập theo mã nhà cung cấp
    public List<PhieuNhapDTO> findByMaNhaCungCap(int maNhaCungCap) throws SQLException {
        List<PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap WHERE MaNhaCungCap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maNhaCungCap);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(
                    rs.getInt("MaNhaCungCap")
                );
                result.add(phieuNhap);
            }
        }
        return result;
    }

    // Tìm phiếu nhập theo trạng thái
    public List<PhieuNhapDTO> findByTrangThai(String trangThai) throws SQLException {
        List<PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap WHERE TrangThai = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(
                    rs.getInt("MaNhaCungCap"),
                    rs.getInt("MaPhieuNhap"),
                    rs.getTimestamp("ThoiGian"),
                    rs.getInt("NguoiTao"),
                    rs.getString("TrangThai")
                );
                result.add(phieuNhap);
            }
        }
        return result;
    }

    // Lấy tất cả phiếu nhập
    public List<PhieuNhapDTO> findAll() throws SQLException {
        List<PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(
                    rs.getInt("MaNhaCungCap"),
                    rs.getInt("MaPhieuNhap"),
                    rs.getTimestamp("ThoiGian"),
                    rs.getInt("NguoiTao"),
                    rs.getString("TrangThai")
                );
                result.add(phieuNhap);
            }
        }
        return result;
    }
}