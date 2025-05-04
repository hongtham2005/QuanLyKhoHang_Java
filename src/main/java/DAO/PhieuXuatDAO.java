package DAO;

import DTO.PhieuXuatDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class PhieuXuatDAO {

    public ArrayList<PhieuXuatDTO> docDSPhieuXuat() throws SQLException {
        ArrayList<PhieuXuatDTO> ds = new ArrayList<>();
        Connection conn = MySQLConnection.getConnection();
        try {
            String sql = "SELECT * FROM PhieuXuat";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Integer nguoiTao = rs.getInt("NguoiTao");
                    if (rs.wasNull()) nguoiTao = null;
                    PhieuXuatDTO px = new PhieuXuatDTO(
                        rs.getInt("MaPhieuXuat"),
                        rs.getTimestamp("ThoiGian"),
                        rs.getString("TrangThai"),
                        nguoiTao,
                        rs.getInt("MaKhachHang")
                    );
                    ds.add(px);
                }
            }
        } finally {
            if (conn != null) conn.close();
        }
        return ds;
    }

    public void them(PhieuXuatDTO px) throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        try {
            String sql = "INSERT INTO PhieuXuat (MaPhieuXuat, ThoiGian, TrangThai, NguoiTao, MaKhachHang) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, px.getMaPhieuXuat());
                ps.setTimestamp(2, new Timestamp(px.getThoiGian().getTime()));
                ps.setString(3, px.getTrangThai());
                if (px.getNguoiTao() != null) {
                    ps.setInt(4, px.getNguoiTao());
                } else {
                    ps.setNull(4, Types.INTEGER);
                }
                ps.setInt(5, px.getMaKhachHang());
                ps.executeUpdate();
            }
        } finally {
            if (conn != null) conn.close();
        }
    }

    public void sua(PhieuXuatDTO px) throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        try {
            String sql = "UPDATE PhieuXuat SET ThoiGian = ?, TrangThai = ?, NguoiTao = ?, MaKhachHang = ? WHERE MaPhieuXuat = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setTimestamp(1, new Timestamp(px.getThoiGian().getTime()));
                ps.setString(2, px.getTrangThai());
                if (px.getNguoiTao() != null) {
                    ps.setInt(3, px.getNguoiTao());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }
                ps.setInt(4, px.getMaKhachHang());
                ps.setInt(5, px.getMaPhieuXuat());
                ps.executeUpdate();
            }
        } finally {
            if (conn != null) conn.close();
        }
    }

    public void xoa(int maPhieuXuat) throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        try {
            String sql = "DELETE FROM PhieuXuat WHERE MaPhieuXuat = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, maPhieuXuat);
                ps.executeUpdate();
            }
        } finally {
            if (conn != null) conn.close();
        }
    }

    public int layMaTiepTheo() throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        try {
            String sql = "SELECT COALESCE(MAX(MaPhieuXuat), 0) FROM PhieuXuat";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt(1) + 1;
                }
            }
        } finally {
            if (conn != null) conn.close();
        }
        return 1;
    }
}