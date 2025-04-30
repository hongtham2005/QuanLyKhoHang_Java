package DAO;

import DTO.NhomQuyenDTO;
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
public class NhomQuyenDAO {

    // Lấy danh sách nhóm quyền
    public List<NhomQuyenDTO> layDanhSachNhomQuyen() throws SQLException {
        List<NhomQuyenDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM NhomQuyen";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NhomQuyenDTO nq = new NhomQuyenDTO();
                nq.setMaNhomQuyen(rs.getInt("MaNhomQuyen"));
                nq.setTenNhomQuyen(rs.getString("TenNhomQuyen"));
                danhSach.add(nq);
            }
        }
        return danhSach;
    }
}