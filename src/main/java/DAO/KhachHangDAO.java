package DAO;

import DTO.KhachHangDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    private Connection conn;

    public KhachHangDAO() throws Exception {
        conn = MySQLConnection.getConnection();
    }

    public ArrayList<KhachHangDTO> docDSKhachHang() throws SQLException {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ds.add(new KhachHangDTO(
                    rs.getInt("MaKhachHang"),
                    rs.getString("TenKhachHang"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("TrangThai"),
                    rs.getString("Email")
                ));
            }
        }
        return ds;
    }

    public void them(KhachHangDTO kh) throws SQLException {
        String sql = "INSERT INTO KhachHang (TenKhachHang, DiaChi, SoDienThoai, TrangThai, Email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKhachHang());
            ps.setString(2, kh.getDiaChi());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getTrangThai());
            ps.setString(5, kh.getEmail());
            ps.executeUpdate();
        }
    }

    public int layMaKhachHangTiepTheo() throws SQLException {
        String sql = "SELECT MAX(MaKhachHang) FROM KhachHang";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1; // Nếu chưa có khách hàng nào
    }

    public void sua(KhachHangDTO kh) throws SQLException {
        String sql = "UPDATE KhachHang SET TenKhachHang=?, DiaChi=?, SoDienThoai=?, Email=? WHERE MaKhachHang=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKhachHang());
            ps.setString(2, kh.getDiaChi());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getEmail());
            ps.setInt(5, kh.getMaKhachHang());
            ps.executeUpdate();
        }
    }

    public void xoa(int ma) throws SQLException {
        String sql = "UPDATE KhachHang SET TrangThai='Đã xóa' WHERE MaKhachHang=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ma);
            ps.executeUpdate();
        }
    }

    public void khoiPhuc(int ma) throws SQLException {
        String sql = "UPDATE KhachHang SET TrangThai='Hoạt động' WHERE MaKhachHang=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ma);
            ps.executeUpdate();
        }
    }

    public KhachHangDTO timKiemTheoMa(int ma) throws SQLException {
        String sql = "SELECT * FROM KhachHang WHERE MaKhachHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ma);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHangDTO(
                        rs.getInt("MaKhachHang"),
                        rs.getString("TenKhachHang"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("TrangThai"),
                        rs.getString("Email")
                    );
                }
            }
        }
        return null;
    }

    public int layMaKhachHangTheoEmail(String email) throws SQLException {
        String sql = "SELECT k.MaKhachHang FROM KhachHang k " +
                     "JOIN TaiKhoan t ON k.MaKhachHang = t.MaTaiKhoan " +
                     "WHERE t.Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaKhachHang");
                }
            }
        }
        throw new SQLException("Không tìm thấy khách hàng với email: " + email);
    }

    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}