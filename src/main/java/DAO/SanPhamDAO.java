package DAO;

import DTO.SanPhamDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public void them(SanPhamDTO sp) throws SQLException {
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO SanPham (TenSanPham, XuatXu, HanSuDung, GiaNhap, GiaXuat, HinhAnh, MaLoaiHang) VALUES (?, ?, ?, ?, ?, ?, ?)",
                 PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getXuatXu());
            ps.setDate(3, sp.getHanSuDung() != null ? new java.sql.Date(sp.getHanSuDung().getTime()) : null);
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
        try (Connection conn = MySQLConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM SanPham")) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE SanPham SET TenSanPham=?, XuatXu=?, HanSuDung=?, GiaNhap=?, GiaXuat=?, HinhAnh=?, MaLoaiHang=? WHERE MaSanPham=?")) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM SanPham WHERE MaSanPham=?")) {
            ps.setInt(1, maSanPham);
            ps.executeUpdate();
        }
    }

    public int layMaTiepTheo() throws SQLException {
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAX(MaSanPham) FROM SanPham")) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT HinhAnh FROM SanPham WHERE MaSanPham = ?")) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MaLoaiHang FROM SanPham WHERE MaSanPham = ?")) {
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
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT XuatXu FROM SanPham WHERE MaSanPham = ?")) {
            ps.setInt(1, maSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("XuatXu") != null ? rs.getString("XuatXu") : "";
                }
            }
        }
        return "";
    }

    public ArrayList<SanPhamDTO> layDanhSachSanPhamTheoPhieuXuat(int maPhieuXuat) throws SQLException {
        ArrayList<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT sp.* FROM SanPham sp JOIN ChiTietPhieuXuat ctpx ON sp.MaSanPham = ctpx.MaSanPham WHERE ctpx.MaPhieuXuat = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhieuXuat);
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
}