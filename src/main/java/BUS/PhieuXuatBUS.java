package BUS;

import DAO.PhieuXuatDAO;
import DAO.ChiTietPhieuXuatDAO;
import DTO.PhieuXuatDTO;
import DTO.ChiTietPhieuXuatDTO;
import java.util.ArrayList;

public class PhieuXuatBUS {
    private ArrayList<PhieuXuatDTO> dsPhieuXuat;

    public PhieuXuatBUS() throws Exception {
        dsPhieuXuat = new PhieuXuatDAO().docDSPhieuXuat();
    }

    public ArrayList<PhieuXuatDTO> getDSPhieuXuat() {
        return dsPhieuXuat;
    }

    public void them(PhieuXuatDTO px) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        dao.them(px);
        dsPhieuXuat.add(px);
    }

    public void sua(PhieuXuatDTO px) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        dao.sua(px);
        for (int i = 0; i < dsPhieuXuat.size(); i++) {
            if (dsPhieuXuat.get(i).getMaPhieuXuat() == px.getMaPhieuXuat()) {
                dsPhieuXuat.set(i, px);
                break;
            }
        }
    }

    public void xoa(int maPhieuXuat) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        dao.xoa(maPhieuXuat);
        dsPhieuXuat.removeIf(px -> px.getMaPhieuXuat() == maPhieuXuat);
    }

    public int layMaTiepTheo() throws Exception {
        return new PhieuXuatDAO().layMaTiepTheo();
    }

    public ArrayList<ChiTietPhieuXuatDTO> layDanhSachSanPhamTheoPhieuXuat(int maPhieuXuat) throws Exception {
        return new ChiTietPhieuXuatDAO().layDanhSachSanPhamTheoPhieuXuat(maPhieuXuat);
    }
}