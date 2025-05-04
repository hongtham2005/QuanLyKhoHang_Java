package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SanPhamBUS {
    private ArrayList<SanPhamDTO> dsSanPham;

    public SanPhamBUS() throws SQLException {
        dsSanPham = new SanPhamDAO().docDSSanPham();
    }

    public ArrayList<SanPhamDTO> getDSSanPham() {
        return dsSanPham;
    }

    public void them(SanPhamDTO sp) throws SQLException {
        SanPhamDAO dao = new SanPhamDAO();
        dao.them(sp);
        dsSanPham.add(sp);
    }

    public void sua(SanPhamDTO sp) throws SQLException {
        SanPhamDAO dao = new SanPhamDAO();
        dao.sua(sp);
        for (int i = 0; i < dsSanPham.size(); i++) {
            if (dsSanPham.get(i).getMaSanPham() == sp.getMaSanPham()) {
                dsSanPham.set(i, sp);
                break;
            }
        }
    }

    public void xoa(int maSanPham) throws SQLException {
        SanPhamDAO dao = new SanPhamDAO();
        dao.xoa(maSanPham);
        dsSanPham.removeIf(sp -> sp.getMaSanPham() == maSanPham);
    }

    public int layMaTiepTheo() throws SQLException {
        return new SanPhamDAO().layMaTiepTheo();
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String keyword, Integer maLoaiHang) throws SQLException {
        return (ArrayList<SanPhamDTO>) new SanPhamDAO().timKiemSanPham(keyword, maLoaiHang);
    }

    public SanPhamDTO timKiemSanPhamTheoMa(int maSanPham) throws SQLException {
        return new SanPhamDAO().timKiemSanPhamTheoMa(maSanPham);
    }
}