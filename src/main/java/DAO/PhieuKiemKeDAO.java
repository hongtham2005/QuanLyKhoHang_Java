package DAO;

import DTO.PhieuKiemKeDTO;
import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuKiemKeDAO {

    // Create: Add a new PhieuKiemKe record to the database
    public boolean createPhieuKiemKe(PhieuKiemKeDTO phieu) throws SQLException {
        String sql = "INSERT INTO PhieuKiemKe (maPhieuKiemKe, nguoiTao, thoiGian) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieu.getMaPhieuKiemKe());
            stmt.setInt(2, phieu.getNguoiTao());
            stmt.setTimestamp(3, phieu.getThoiGian());
            return stmt.executeUpdate() > 0;
        }
    }

    // Read: Retrieve a PhieuKiemKe record by maPhieuKiemKe
    public PhieuKiemKeDTO getPhieuKiemKe(int maPhieuKiemKe) throws SQLException {
        String sql = "SELECT * FROM PhieuKiemKe WHERE maPhieuKiemKe = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuKiemKe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PhieuKiemKeDTO(
                        rs.getInt("maPhieuKiemKe"),
                        rs.getInt("nguoiTao")
                    );
                }
                return null;
            }
        }
    }

    // Read
    public List<PhieuKiemKeDTO> layDanhSachPhieuKiemKe() throws SQLException {
        List<PhieuKiemKeDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuKiemKe";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuKiemKeDTO phieu = new PhieuKiemKeDTO(
                    rs.getInt("maPhieuKiemKe"),
                    rs.getInt("nguoiTao")
                );
                danhSach.add(phieu);
            }
        }
        return danhSach;
    }

    // Update
    public boolean updatePhieuKiemKe(PhieuKiemKeDTO phieu) throws SQLException {
        String sql = "UPDATE PhieuKiemKe SET nguoiTao = ?, thoiGian = ? WHERE maPhieuKiemKe = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieu.getNguoiTao());
            stmt.setTimestamp(2, phieu.getThoiGian());
            stmt.setInt(3, phieu.getMaPhieuKiemKe());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletePhieuKiemKe(int maPhieuKiemKe) throws SQLException {
        String sql = "DELETE FROM PhieuKiemKe WHERE maPhieuKiemKe = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuKiemKe);
            return stmt.executeUpdate() > 0;
        }
    }
}