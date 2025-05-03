package DAO;

import DTO.KhachHangDTO;
import database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    Connection conn;

    public KhachHangDAO() throws Exception {
        conn = MySQLConnection.getConnection();
    }

    public ArrayList<KhachHangDTO> docDSKhachHang() throws Exception {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ds.add(new KhachHangDTO(
                rs.getInt("MaKhachHang"),
                rs.getString("TenKhachHang"),
                rs.getString("DiaChi"),
                rs.getString("SoDienThoai"),
                rs.getString("TrangThai")   // ✅ dòng mới
            ));
        }
        return ds;
    }

    public void them(KhachHangDTO kh) throws Exception {
        String sql = "INSERT INTO KhachHang (TenKhachHang, DiaChi, SoDienThoai, TrangThai) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kh.getTenKhachHang());
        ps.setString(2, kh.getDiaChi());
        ps.setString(3, kh.getSoDienThoai());
        ps.setString(4, "Hoạt động"); // ✅ luôn thêm mới ở trạng thái hoạt động
        ps.executeUpdate();
    }

    public int layMaKhachHangTiepTheo() throws Exception {
        String sql = "SELECT MAX(MaKhachHang) FROM KhachHang";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1) + 1;
        }
        return 1; // Nếu chưa có khách hàng nào
    }

    public void sua(KhachHangDTO kh) throws Exception {
        String sql = "UPDATE KhachHang SET TenKhachHang=?, DiaChi=?, SoDienThoai=? WHERE MaKhachHang=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kh.getTenKhachHang());
        ps.setString(2, kh.getDiaChi());
        ps.setString(3, kh.getSoDienThoai());
        ps.setInt(4, kh.getMaKhachHang());
        ps.executeUpdate();
    }

    public void xoa(int ma) throws Exception {
        String sql = "UPDATE KhachHang SET TrangThai='Đã xóa' WHERE MaKhachHang=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ma);
        ps.executeUpdate();
    }
    public void khoiPhuc(int ma) throws Exception {
        String sql = "UPDATE KhachHang SET TrangThai='Hoạt động' WHERE MaKhachHang=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ma);
        ps.executeUpdate();
    }

}
