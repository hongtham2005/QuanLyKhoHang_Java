package DAO;

import DTO.ChucNangDTO;
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
public class ChucNangDAO {

    // Lấy danh sách chức năng
    public List<ChucNangDTO> layDanhSachChucNang() throws SQLException {
        List<ChucNangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ChucNang";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChucNangDTO cn = new ChucNangDTO();
                cn.setMaChucNang(rs.getInt("MaChucNang"));
                cn.setTenChucNang(rs.getString("TenChucNang"));
                danhSach.add(cn);
            }
        }
        return danhSach;
    }
}