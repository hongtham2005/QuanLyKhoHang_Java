package BUS;

import DAO.PhieuXuatDAO;
import DTO.PhieuXuatDTO;

import java.util.ArrayList;

public class PhieuXuatBUS {
    private ArrayList<PhieuXuatDTO> ds;

    public PhieuXuatBUS() throws Exception {
        ds = new PhieuXuatDAO().docDSPhieuXuat();
    }

    public ArrayList<PhieuXuatDTO> getDSPhieuXuat() {
        return ds;
    }

    public void them(PhieuXuatDTO px) throws Exception {
        new PhieuXuatDAO().them(px);
        ds.add(px);
    }

    public void xoa(int maPhieuXuat) throws Exception {
        new PhieuXuatDAO().xoa(maPhieuXuat);
        ds.removeIf(px -> px.getMaPhieuXuat() == maPhieuXuat);
    }

    public int layMaTiepTheo() throws Exception {
        return new PhieuXuatDAO().layMaTiepTheo();
    }
}
