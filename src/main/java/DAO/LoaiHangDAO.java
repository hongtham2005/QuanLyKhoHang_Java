
package DAO;

import DTO.LoaiHangDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class LoaiHangDAO {
    private Connection conn;

    public LoaiHangDAO() throws SQLException {
        conn = MySQLConnection.getConnection();
    }

    public ArrayList<LoaiHangDTO> docDSLoaiHang() throws SQLException {
        ArrayList<LoaiHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM LoaiHang";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LoaiHangDTO lh = new LoaiHangDTO(
                    rs.getInt("MaLoaiHang"),
                    rs.getString("TenLoaiHang")
                );
                ds.add(lh);
            }
        }
        return ds;
    }

    // Sửa: Thêm phương thức themLoaiHang, trả về MaLoaiHang mới
    public void themLoaiHang(LoaiHangDTO lh) throws SQLException {
        String sql = "INSERT INTO LoaiHang (TenLoaiHang) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, lh.getTenLoaiHang());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    lh.setMaLoaiHang(rs.getInt(1));
                }
            }
        }
    }

    // Sửa: Thêm phương thức suaLoaiHang
    public void suaLoaiHang(LoaiHangDTO lh) throws SQLException {
        String sql = "UPDATE LoaiHang SET TenLoaiHang = ? WHERE MaLoaiHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lh.getTenLoaiHang());
            ps.setInt(2, lh.getMaLoaiHang());
            ps.executeUpdate();
        }
    }

    // Sửa: Thêm phương thức xoaLoaiHang, kiểm tra khóa ngoại
    public void xoaLoaiHang(int maLoaiHang) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaLoaiHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiHang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Không thể xóa loại hàng vì có sản phẩm đang sử dụng!");
                }
            }
        }
        sql = "DELETE FROM LoaiHang WHERE MaLoaiHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiHang);
            ps.executeUpdate();
        }
    }
}
