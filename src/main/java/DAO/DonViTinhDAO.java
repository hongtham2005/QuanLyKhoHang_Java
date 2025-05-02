package DAO;

import DTO.DonViTinhDTO;
import Database.MyConnect;

import java.sql.*;
import java.util.ArrayList;

public class DonViTinhDAO {
    Connection conn;

    public DonViTinhDAO() throws Exception {
        conn = MyConnect.getConnection();
    }

    public ArrayList<DonViTinhDTO> docDSDonViTinh() throws Exception {
        ArrayList<DonViTinhDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM DonViTinh";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            DonViTinhDTO dvt = new DonViTinhDTO(
                rs.getInt("MaDonViTinh"),
                rs.getString("TenDonViTinh")
            );
            ds.add(dvt);
        }

        return ds;
    }
}
