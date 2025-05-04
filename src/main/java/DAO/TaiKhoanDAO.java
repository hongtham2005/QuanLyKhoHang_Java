package DAO;

import DTO.TaiKhoanDTO;
import database.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO {

    public TaiKhoanDTO kiemTraDangNhap(String email, String matKhau) throws SQLException {
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM TaiKhoan WHERE Email = ? AND MatKhau = ?")) {
            ps.setString(1, email);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanDTO(
                        rs.getInt("MaTaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getString("TrangThai"),
                        rs.getInt("MaNhomQuyen"),
                        rs.getString("Email")
                    );
                }
            }
        }
        return null;
    }
}