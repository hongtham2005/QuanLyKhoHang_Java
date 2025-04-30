package DAO;

import DTO.SanPhamDTO;
import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hong tham
 */
public class SanPhamDAO {

    public List<SanPhamDTO> layDanhSachSanPham() throws SQLException {
        List<SanPhamDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setMaSanPham(rs.getInt("MaSanPham"));
                sp.setTenSanPham(rs.getString("TenSanPham"));
                sp.setHanSuDung(rs.getDate("HanSuDung"));
                sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaXuat(rs.getBigDecimal("GiaXuat"));
                sp.setHinhAnh(rs.getString("HinhAnh"));
                sp.setMaLoaiHang(rs.getInt("MaLoaiHang"));
                danhSach.add(sp);
            }
        }
        return danhSach;
    }

    // New method to search products by name and category
    public List<SanPhamDTO> timKiemSanPham(String tenSanPham, Integer maLoaiHang) throws SQLException {
        List<SanPhamDTO> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1");
        
        if (tenSanPham != null && !tenSanPham.trim().isEmpty()) {
            sql.append(" AND TenSanPham LIKE ?");
        }
        if (maLoaiHang != null && maLoaiHang > 0) {
            sql.append(" AND MaLoaiHang = ?");
        }

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (tenSanPham != null && !tenSanPham.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + tenSanPham + "%");
            }
            if (maLoaiHang != null && maLoaiHang > 0) {
                stmt.setInt(paramIndex, maLoaiHang);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSanPham(rs.getInt("MaSanPham"));
                    sp.setTenSanPham(rs.getString("TenSanPham"));
                    sp.setHanSuDung(rs.getDate("HanSuDung"));
                    sp.setGiaNhap(rs.getBigDecimal("GiaNhap"));
                    sp.setGiaXuat(rs.getBigDecimal("GiaXuat"));
                    sp.setHinhAnh(rs.getString("HinhAnh"));
                    sp.setMaLoaiHang(rs.getInt("MaLoaiHang"));
                    danhSach.add(sp);
                }
            }
        }
        return danhSach;
    }

    public int themSanPham(SanPhamDTO sp) throws SQLException {
        String sql = "INSERT INTO SanPham (TenSanPham, HanSuDung, GiaNhap, GiaXuat, HinhAnh, MaLoaiHang) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sp.getTenSanPham());
            stmt.setDate(2, new java.sql.Date(sp.getHanSuDung().getTime()));
            stmt.setBigDecimal(3, sp.getGiaNhap());
            stmt.setBigDecimal(4, sp.getGiaXuat());
            stmt.setString(5, sp.getHinhAnh());
            stmt.setInt(6, sp.getMaLoaiHang());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public void suaSanPham(SanPhamDTO sp) throws SQLException {
        String sql = "UPDATE SanPham SET TenSanPham = ?, HanSuDung = ?, GiaNhap = ?, GiaXuat = ?, HinhAnh = ?, MaLoaiHang = ? WHERE MaSanPham = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sp.getTenSanPham());
            stmt.setDate(2, new java.sql.Date(sp.getHanSuDung().getTime()));
            stmt.setBigDecimal(3, sp.getGiaNhap());
            stmt.setBigDecimal(4, sp.getGiaXuat());
            stmt.setString(5, sp.getHinhAnh());
            stmt.setInt(6, sp.getMaLoaiHang());
            stmt.setInt(7, sp.getMaSanPham());
            stmt.executeUpdate();
        }
    }

    public void xoaSanPham(int maSanPham) throws SQLException {
        String sql = "DELETE FROM SanPham WHERE MaSanPham = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSanPham);
            stmt.executeUpdate();
        }
    }

    public String layHinhAnhSanPham(int maSanPham) throws SQLException {
        String sql = "SELECT HinhAnh FROM SanPham WHERE MaSanPham = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSanPham);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("HinhAnh");
                }
            }
        }
        return null;
    }

    public int layMaLoaiHangSanPham(int maSanPham) throws SQLException {
        String sql = "SELECT MaLoaiHang FROM SanPham WHERE MaSanPham = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maSanPham);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaLoaiHang");
                }
            }
        }
        return -1;
    }
}