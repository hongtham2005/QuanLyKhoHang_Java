package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import java.util.ArrayList;
import java.util.List;

public class SanPhamBUS {
    private ArrayList<SanPhamDTO> ds;
    private SanPhamDAO dao;

    public SanPhamBUS() throws Exception {
        dao = new SanPhamDAO();
        ds = dao.docDSSanPham();
    }

    public ArrayList<SanPhamDTO> getDSSanPham() throws Exception {
        return dao.docDSSanPham(); // Lấy trực tiếp từ DAO để đảm bảo đồng bộ
    }

    public void them(SanPhamDTO sp) throws Exception {
        dao.them(sp);
        ds.add(sp);
    }

    public void sua(SanPhamDTO sp) throws Exception {
        dao.sua(sp);
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMaSanPham() == sp.getMaSanPham()) {
                ds.set(i, sp);
                break;
            }
        }
    }

    public void xoa(int maSP) throws Exception {
        dao.xoa(maSP);
        ds.removeIf(sp -> sp.getMaSanPham() == maSP);
    }

    public int layMaTiepTheo() throws Exception {
        return dao.layMaTiepTheo();
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String tenSanPham, Integer maLoaiHang) throws Exception {
        List<SanPhamDTO> ketQua = dao.timKiemSanPham(tenSanPham, maLoaiHang);
        return new ArrayList<>(ketQua);
    }

    public ArrayList<SanPhamDTO> layDanhSachSanPhamTheoPhieuXuat(int maPhieuXuat) throws Exception {
        return dao.layDanhSachSanPhamTheoPhieuXuat(maPhieuXuat); // Gọi phương thức từ DAO
    }
}