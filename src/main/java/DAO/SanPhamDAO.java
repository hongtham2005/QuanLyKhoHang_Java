package DAO;

import DTO.SanPhamDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private Connection conn;

    public SanPhamDAO() throws SQLException {
        this.conn = MySQLConnection.getConnection();
    }

    public boolean isConnectionValid() throws SQLException {
        return conn != null && !conn.isClosed();
    }

    public void them(SanPhamDTO sp) throws SQLException {
        if (!isConnectionValid()) {
            throw new SQLException("Kết nối cơ sở dữ liệu không khả dụng.");
        }
        String sql = "INSERT INTO SanPham (TenSanPham, XuatXu, HanSuDung, GiaNhap, GiaXuat, HinhAnh, MaLoaiHang) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getXuatXu());
            ps.setDate(3, new java.sql.Date(sp.getHanSuDung().getTime()));
            ps.setDouble(4, sp.getGiaNhap());
            ps.setDouble(5, sp.getGiaXuat());
            ps.setString(6, sp.getHinhAnh());
            ps.setInt(7, sp.getMaLoaiHang());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    sp.setMaSanPham(rs.getInt(1));
                }
            }
        }
    }

    public ArrayList<SanPhamDTO> docDSSanPham() throws SQLException {
        ArrayList<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ds.add(new SanPhamDTO(
                    rs.getInt("MaSanPham"),
                    rs.getString("TenSanPham"),
                    rs.getString("XuatXu"),
                    rs.getDate("HanSuDung"),
                    rs.getDouble("GiaNhap"),
                    rs.getDouble("GiaXuat"),
                    rs.getString("HinhAnh"),
                    rs.getInt("MaLoaiHang")
                ));
            }
        }
        return ds;
    }

    public void sua(SanPhamDTO sp) throws SQLException {
        String sql = "UPDATE SanPham SET TenSanPham=?, XuatXu=?, HanSuDung=?, GiaNhap=?, GiaXuat=?, HinhAnh=?, MaLoaiHang=? " +
                     "WHERE MaSanPham=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getXuatXu());
            ps.setDate(3, sp.getHanSuDung() != null ? new java.sql.Date(sp.getHanSuDung().getTime()) : null);
            ps.setDouble(4, sp.getGiaNhap());
            ps.setDouble(5, sp.getGiaXuat());
            ps.setString(6, sp.getHinhAnh());
            ps.setInt(7, sp.getMaLoaiHang());
            ps.setInt(8, sp.getMaSanPham());
            ps.executeUpdate();
        }
    }

    public void xoa(int maSanPham) throws SQLException {
        String sql = "DELETE FROM SanPham WHERE MaSanPham=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSanPham);
            ps.executeUpdate();
        }
    }

    public int layMaTiepTheo() throws SQLException {
        String sql = "SELECT MAX(MaSanPham) FROM SanPham";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;
        }
    }

    public List<SanPhamDTO> timKiemSanPham(String tenSanPham, Integer maLoaiHang) throws SQLException {
        List<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TenSanPham LIKE ?" +
                     (maLoaiHang != null ? " AND MaLoaiHang = ?" : "");
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenSanPham + "%");
            if (maLoaiHang != null) {
                ps.setInt(2, maLoaiHang);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(new SanPhamDTO(
                        rs.getInt("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getString("XuatXu"),
                        rs.getDate("HanSuDung"),
                        rs.getDouble("GiaNhap"),
                        rs.getDouble("GiaXuat"),
                        rs.getString("HinhAnh"),
                        rs.getInt("MaLoaiHang")
                    ));
                }
            }
        }
        return ds;
    }

    public String layHinhAnhSanPham(int maSanPham) throws SQLException {
        String sql = "SELECT HinhAnh FROM SanPham WHERE MaSanPham = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("HinhAnh");
                }
            }
        }
        return null;
    }

    public int layMaLoaiHangSanPham(int maSanPham) throws SQLException {
        String sql = "SELECT MaLoaiHang FROM SanPham WHERE MaSanPham = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaLoaiHang");
                }
            }
        }
        return -1;
    }

    public String getXuatXu(int maSanPham) throws SQLException {
        if (!isConnectionValid()) {
            throw new SQLException("Kết nối cơ sở dữ liệu không khả dụng.");
        }
        String sql = "SELECT XuatXu FROM SanPham WHERE MaSanPham = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("XuatXu") != null ? rs.getString("XuatXu") : "";
                } else {
                    return "";
                }
            }
        }
    }
}