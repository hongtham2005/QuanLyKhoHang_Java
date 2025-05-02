package DAO;

import DTO.LoaiHangDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class LoaiHangDAO {
    Connection conn;

    public LoaiHangDAO() throws Exception {
        conn = MyConnect.getConnection();
    }

    public ArrayList<LoaiHangDTO> docDSLoaiHang() throws Exception {
        ArrayList<LoaiHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM LoaiHang";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            LoaiHangDTO lh = new LoaiHangDTO(
                rs.getInt("MaLoaiHang"),
                rs.getString("TenLoaiHang")
            );
            ds.add(lh);
        }

        return ds;
    }
}
