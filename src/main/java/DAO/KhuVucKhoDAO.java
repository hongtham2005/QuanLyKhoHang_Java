package DAO;

import DTO.KhuVucKhoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuVucKhoDAO {
    private Connection connection;

    public KhuVucKhoDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm mới khu vực kho
    public void save(KhuVucKhoDTO khuVucKho) throws SQLException {
        String sql = "INSERT INTO KhuVucKho (MaKhuVucKho, TenKhuVucKho) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, khuVucKho.getMaKhuVucKho());
            stmt.setString(2, khuVucKho.getTenKhuVucKho());
            stmt.executeUpdate();
        }
    }

    // Cập nhật khu vực kho
    public void update(KhuVucKhoDTO khuVucKho) throws SQLException {
        String sql = "UPDATE KhuVucKho SET TenKhuVucKho = ? WHERE MaKhuVucKho = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, khuVucKho.getTenKhuVucKho());
            stmt.setInt(2, khuVucKho.getMaKhuVucKho());
            stmt.executeUpdate();
        }
    }

    // Xóa khu vực kho
    public void delete(int maKhuVucKho) throws SQLException {
        String sql = "DELETE FROM KhuVucKho WHERE MaKhuVucKho = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKhuVucKho);
            stmt.executeUpdate();
        }
    }

    // Tìm khu vực kho theo mã
    public KhuVucKhoDTO findByMaKhuVucKho(int maKhuVucKho) throws SQLException {
        String sql = "SELECT * FROM KhuVucKho WHERE MaKhuVucKho = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKhuVucKho);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhuVucKhoDTO(
                    rs.getInt("MaKhuVucKho"),
                    rs.getString("TenKhuVucKho")
                );
            }
        }
        return null;
    }

    // Tìm khu vực kho theo tên (gần đúng)
    public List<KhuVucKhoDTO> findByTenKhuVucKho(String tenKhuVucKho) throws SQLException {
        List<KhuVucKhoDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KhuVucKho WHERE TenKhuVucKho LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + tenKhuVucKho + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhuVucKhoDTO khuVucKho = new KhuVucKhoDTO(
                    rs.getInt("MaKhuVucKho"),
                    rs.getString("TenKhuVucKho")
                );
                result.add(khuVucKho);
            }
        }
        return result;
    }

    // Lấy tất cả khu vực kho
    public List<KhuVucKhoDTO> findAll() throws SQLException {
        List<KhuVucKhoDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KhuVucKho";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KhuVucKhoDTO khuVucKho = new KhuVucKhoDTO(
                    rs.getInt("MaKhuVucKho"),
                    rs.getString("TenKhuVucKho")
                );
                result.add(khuVucKho);
            }
        }
        return result;
    }
}