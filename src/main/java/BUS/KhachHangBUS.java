package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBUS {
    private ArrayList<KhachHangDTO> ds;

    public KhachHangBUS() throws Exception {
        KhachHangDAO dao = new KhachHangDAO();
        ds = dao.docDSKhachHang();
    }

    public ArrayList<KhachHangDTO> getDSKhachHang() {
        return ds;
    }

    public void them(KhachHangDTO kh) throws Exception {
        ds.add(kh);
        new KhachHangDAO().them(kh);
    }

    public void sua(KhachHangDTO kh) throws Exception {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMaKhachHang() == kh.getMaKhachHang()) {
                ds.set(i, kh);
                break;
            }
        }
        new KhachHangDAO().sua(kh);
    }

    public void xoa(int ma) throws Exception {
        ds.removeIf(kh -> kh.getMaKhachHang() == ma);
        new KhachHangDAO().xoa(ma);
    }
}
