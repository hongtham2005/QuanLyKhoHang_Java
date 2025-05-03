package BUS;

import DAO.PhieuXuatDAO;
import DAO.ChiTietPhieuXuatDAO;
import DTO.PhieuXuatDTO;
import DTO.ChiTietPhieuXuatDTO;
import java.util.ArrayList;

public class PhieuXuatBUS {
    private ArrayList<PhieuXuatDTO> dsPhieuXuat;

    public PhieuXuatBUS() throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        try {
            dsPhieuXuat = dao.docDSPhieuXuat();
        } finally {
            dao.closeConnection();
        }
    }

    public ArrayList<PhieuXuatDTO> getDSPhieuXuat() {
        return dsPhieuXuat;
    }

    public void them(PhieuXuatDTO px) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        try {
            dao.them(px);
            dsPhieuXuat.add(px);
        } finally {
            dao.closeConnection();
        }
    }

    public void sua(PhieuXuatDTO px) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        try {
            dao.sua(px);
            for (int i = 0; i < dsPhieuXuat.size(); i++) {
                if (dsPhieuXuat.get(i).getMaPhieuXuat() == px.getMaPhieuXuat()) {
                    dsPhieuXuat.set(i, px);
                    break;
                }
            }
        } finally {
            dao.closeConnection();
        }
    }

    public void xoa(int maPhieuXuat) throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        try {
            dao.xoa(maPhieuXuat);
            dsPhieuXuat.removeIf(px -> px.getMaPhieuXuat() == maPhieuXuat);
        } finally {
            dao.closeConnection();
        }
    }

    public int layMaTiepTheo() throws Exception {
        PhieuXuatDAO dao = new PhieuXuatDAO();
        try {
            return dao.layMaTiepTheo();
        } finally {
            dao.closeConnection();
        }
    }

    public ArrayList<ChiTietPhieuXuatDTO> layDanhSachSanPhamTheoPhieuXuat(int maPhieuXuat) throws Exception {
        ChiTietPhieuXuatDAO dao = new ChiTietPhieuXuatDAO();
        try {
            return dao.layDanhSachSanPhamTheoPhieuXuat(maPhieuXuat);
        } finally {
            dao.closeConnection();
        }
    }
}