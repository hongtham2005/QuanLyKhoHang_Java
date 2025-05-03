package DAO;

import DTO.ChiTietQuyenDTO;
import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hong tham
 */
public class ChiTietQuyenDAO {

    // Lấy danh sách chi tiết quyền
    public List<ChiTietQuyenDTO> layDanhSachChiTietQuyen() throws SQLException {
        List<ChiTietQuyenDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietQuyen";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO();
                ctq.setMaNhomQuyen(rs.getInt("MaNhomQuyen"));
                ctq.setMaChucNang(rs.getInt("MaChucNang"));
                ctq.setHanhDong(rs.getString("HanhDong"));
                danhSach.add(ctq);
            }
        }
        return danhSach;
    }

    public boolean kiemTraQuyen(int maNhomQuyen, int maChucNang, String hanhDong) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ChiTietQuyen_HanhDong WHERE MaNhomQuyen = ? AND MaChucNang = ? AND HanhDong = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maNhomQuyen);
            stmt.setInt(2, maChucNang);
            stmt.setString(3, hanhDong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}