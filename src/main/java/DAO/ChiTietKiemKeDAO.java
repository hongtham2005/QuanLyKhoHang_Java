package DAO;

import DTO.ChiTietKiemKeDTO;
import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietKiemKeDAO {
     // Lấy toàn bộ danh sách ChiTietKiemKe
    public List<ChiTietKiemKeDTO> layDanhSachChiTietKiemKe() throws SQLException {
        List<ChiTietKiemKeDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietKiemKe";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChiTietKiemKeDTO ctkk = new ChiTietKiemKeDTO();
                ctkk.setMaPhieuKiemKe(rs.getInt("maPhieuKiemKe"));
                ctkk.setMaSanPham(rs.getInt("maSanPham"));
                ctkk.setSoLuong(rs.getInt("soLuong"));
                ctkk.setChenhLech(rs.getInt("chenhLech"));
                ctkk.setGhiChu(rs.getString("ghiChu"));
                danhSach.add(ctkk);
            }
        }
        return danhSach;
    }
     // Lấy danh sách ChiTietKiemKe theo maPhieuKiemKe
    public List<ChiTietKiemKeDTO> layDanhSachKiemKeTheoPhieu(int maPhieuKiemKe) throws SQLException {
        List<ChiTietKiemKeDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietKiemKe WHERE maPhieuKiemKe = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPhieuKiemKe);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietKiemKeDTO ctkk = new ChiTietKiemKeDTO();
                    ctkk.setMaPhieuKiemKe(rs.getInt("maPhieuKiemKe"));
                    ctkk.setMaSanPham(rs.getInt("maSanPham"));
                    ctkk.setSoLuong(rs.getInt("soLuong"));
                    ctkk.setChenhLech(rs.getInt("chenhLech"));
                    ctkk.setGhiChu(rs.getString("ghiChu"));
                    danhSach.add(ctkk);
                }
            }
        }
        return danhSach;
    }
}