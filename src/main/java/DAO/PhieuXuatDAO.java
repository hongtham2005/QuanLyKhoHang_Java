package DAO;

import DTO.PhieuXuatDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class PhieuXuatDAO {
    Connection conn;

    public PhieuXuatDAO() throws Exception {
        conn = MyConnect.getConnection();
    }

    // Lấy danh sách tất cả phiếu xuất
    public ArrayList<PhieuXuatDTO> docDSPhieuXuat() throws Exception {
        ArrayList<PhieuXuatDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhieuXuat";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            PhieuXuatDTO px = new PhieuXuatDTO(
                rs.getInt("MaPhieuXuat"),
                rs.getTimestamp("ThoiGian"),
                rs.getString("TrangThai"),
                rs.getInt("NguoiTao"),
                rs.getInt("MaKhachHang")
            );
            ds.add(px);
        }

        return ds;
    }

    // Thêm phiếu xuất
    public void them(PhieuXuatDTO px) throws Exception {
        String sql = "INSERT INTO PhieuXuat (MaPhieuXuat, ThoiGian, TrangThai, NguoiTao, MaKhachHang) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, px.getMaPhieuXuat());
        ps.setTimestamp(2, new Timestamp(px.getThoiGian().getTime()));
        ps.setString(3, px.getTrangThai());
        ps.setInt(4, px.getNguoiTao());
        ps.setInt(5, px.getMaKhachHang());
        ps.executeUpdate();
    }

    // Xoá (hoặc cập nhật trạng thái nếu xóa mềm)
    public void xoa(int maPhieuXuat) throws Exception {
        String sql = "DELETE FROM PhieuXuat WHERE MaPhieuXuat=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maPhieuXuat);
        ps.executeUpdate();
    }

    // Lấy mã phiếu xuất tiếp theo
    public int layMaTiepTheo() throws Exception {
        String sql = "SELECT MAX(MaPhieuXuat) FROM PhieuXuat";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1) + 1;
        }
        return 1;
    }
}
