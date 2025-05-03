package BUS;

import DAO.ChiTietPhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;

import java.util.ArrayList;

public class ChiTietPhieuXuatBUS {
    public ArrayList<ChiTietPhieuXuatDTO> docTheoPhieu(int maPhieuXuat) throws Exception {
        return new ChiTietPhieuXuatDAO().layDanhSachSanPhamTheoPhieuXuat(maPhieuXuat);
    }

    public void them(ChiTietPhieuXuatDTO ct) throws Exception {
        new ChiTietPhieuXuatDAO().them(ct);
    }

    public void xoa(int maPhieuXuat, int maSanPham) throws Exception {
        new ChiTietPhieuXuatDAO().xoa(maPhieuXuat, maSanPham);
    }

    public void sua(ChiTietPhieuXuatDTO ct) throws Exception {
        new ChiTietPhieuXuatDAO().sua(ct);
    }
}