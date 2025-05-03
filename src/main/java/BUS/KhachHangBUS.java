package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBUS {
    private ArrayList<KhachHangDTO> ds;

    public KhachHangBUS() throws Exception {
        taiDuLieuMoi();
    }

    private void taiDuLieuMoi() throws Exception {
        KhachHangDAO dao = new KhachHangDAO();
        ds = dao.docDSKhachHang();
    }

    public ArrayList<KhachHangDTO> getDSKhachHang() {
        return ds;
    }

    public void them(KhachHangDTO kh) throws Exception {
        ds.add(kh);
        KhachHangDAO dao = new KhachHangDAO();
        dao.them(kh);
        taiDuLieuMoi(); // Làm mới danh sách
    }

    public void sua(KhachHangDTO kh) throws Exception {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMaKhachHang() == kh.getMaKhachHang()) {
                ds.set(i, kh);
                break;
            }
        }
        KhachHangDAO dao = new KhachHangDAO();
        dao.sua(kh);
        taiDuLieuMoi(); // Làm mới danh sách
    }

    public void xoa(int ma) throws Exception {
        ds.removeIf(kh -> kh.getMaKhachHang() == ma);
        KhachHangDAO dao = new KhachHangDAO();
        dao.xoa(ma);
        taiDuLieuMoi(); // Làm mới danh sách
    }

    public KhachHangDTO timKiemTheoMa(int ma) throws Exception {
        KhachHangDAO dao = new KhachHangDAO();
        return dao.timKiemTheoMa(ma);
    }
}