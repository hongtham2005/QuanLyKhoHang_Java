package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import java.util.List;
import java.util.ArrayList;

public class SanPhamBUS {
    private ArrayList<SanPhamDTO> ds;

    public SanPhamBUS() throws Exception {
        SanPhamDAO dao = new SanPhamDAO();
        ds = dao.docDSSanPham();
    }

    public ArrayList<SanPhamDTO> getDSSanPham() {
        return ds;
    }

    public void them(SanPhamDTO sp) throws Exception {
        new SanPhamDAO().them(sp);
        ds.add(sp);
    }

    public void sua(SanPhamDTO sp) throws Exception {
        new SanPhamDAO().sua(sp);
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMaSanPham() == sp.getMaSanPham()) {
                ds.set(i, sp);
                break;
            }
        }
    }

    public void xoa(int maSP) throws Exception {
        new SanPhamDAO().xoa(maSP);
        ds.removeIf(sp -> sp.getMaSanPham() == maSP);
    }

    public int layMaTiepTheo() throws Exception {
        return new SanPhamDAO().layMaTiepTheo();
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String tenSanPham, Integer maLoaiHang) throws Exception {
        SanPhamDAO dao = new SanPhamDAO();
        List<SanPhamDTO> ketQua = dao.timKiemSanPham(tenSanPham, maLoaiHang); // Lấy List từ DAO
        return new ArrayList<>(ketQua); // Chuyển đổi List thành ArrayList
    }
}