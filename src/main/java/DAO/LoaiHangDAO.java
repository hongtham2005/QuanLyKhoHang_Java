package DAO;

import DTO.LoaiHangDTO;
import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hong tham
 */
public class LoaiHangDAO {

    // Lấy danh sách loại hàng
    public List<LoaiHangDTO> layDanhSachLoaiHang() throws SQLException {
        List<LoaiHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM LoaiHang";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LoaiHangDTO lh = new LoaiHangDTO();
                lh.setMaLoaiHang(rs.getInt("MaLoaiHang"));
                lh.setTenLoaiHang(rs.getString("TenLoaiHang"));
                danhSach.add(lh);
            }
        }
        return danhSach;
    }

    // Thêm loại hàng
    public void themLoaiHang(LoaiHangDTO lh) throws SQLException {
        String sql = "INSERT INTO LoaiHang (TenLoaiHang) VALUES (?)";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lh.getTenLoaiHang());
            stmt.executeUpdate();
        }
    }

    // Sửa loại hàng
    public void suaLoaiHang(LoaiHangDTO lh) throws SQLException {
        String sql = "UPDATE LoaiHang SET TenLoaiHang = ? WHERE MaLoaiHang = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lh.getTenLoaiHang());
            stmt.setInt(2, lh.getMaLoaiHang());
            stmt.executeUpdate();
        }
    }

    // Xóa loại hàng
    public void xoaLoaiHang(int maLoaiHang) throws SQLException {
        String sql = "DELETE FROM LoaiHang WHERE MaLoaiHang = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maLoaiHang);
            stmt.executeUpdate();
        }
    }
}