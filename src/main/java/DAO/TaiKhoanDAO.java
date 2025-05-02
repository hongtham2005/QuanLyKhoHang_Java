package DAO;

import DTO.TaiKhoanDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class TaiKhoanDAO {
    Connection conn;

    public TaiKhoanDAO() throws Exception {
        conn = MyConnect.getConnection(); // Kết nối CSDL
    }

    // Lấy toàn bộ danh sách tài khoản
    public ArrayList<TaiKhoanDTO> docDSTaiKhoan() throws Exception {
        ArrayList<TaiKhoanDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ds.add(new TaiKhoanDTO(
                rs.getInt("MaTaiKhoan"),
                rs.getString("MatKhau"),
                rs.getString("TrangThai"),
                rs.getInt("MaNhomQuyen"),
                rs.getString("Email")
            ));
        }
        return ds;
    }

    // Thêm mới tài khoản
    public void them(TaiKhoanDTO tk) throws Exception {
        String sql = "INSERT INTO TaiKhoan (MaTaiKhoan, MatKhau, TrangThai, MaNhomQuyen, Email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tk.getMaTaiKhoan());
        ps.setString(2, tk.getMatKhau());
        ps.setString(3, tk.getTrangThai());
        ps.setInt(4, tk.getMaNhomQuyen());
        ps.setString(5, tk.getEmail());
        ps.executeUpdate();
    }

    // Sửa thông tin tài khoản
    public void sua(TaiKhoanDTO tk) throws Exception {
        String sql = "UPDATE TaiKhoan SET MatKhau=?, TrangThai=?, MaNhomQuyen=?, Email=? WHERE MaTaiKhoan=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tk.getMatKhau());
        ps.setString(2, tk.getTrangThai());
        ps.setInt(3, tk.getMaNhomQuyen());
        ps.setString(4, tk.getEmail());
        ps.setInt(5, tk.getMaTaiKhoan());
        ps.executeUpdate();
    }

    // Xoá mềm tài khoản (cập nhật trạng thái)
    public void xoa(int maTK) throws Exception {
        String sql = "UPDATE TaiKhoan SET TrangThai='Đã xóa' WHERE MaTaiKhoan=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maTK);
        ps.executeUpdate();
    }

    // Kiểm tra đăng nhập (tìm theo email + mật khẩu)
    public TaiKhoanDTO timTheoEmailVaMatKhau(String email, String matKhau) throws Exception {
        String sql = "SELECT * FROM TaiKhoan WHERE Email=? AND MatKhau=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, matKhau);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new TaiKhoanDTO(
                rs.getInt("MaTaiKhoan"),
                rs.getString("MatKhau"),
                rs.getString("TrangThai"),
                rs.getInt("MaNhomQuyen"),
                rs.getString("Email")
            );
        }
        return null;
    }
}
