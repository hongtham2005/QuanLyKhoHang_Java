package GUI;

import BUS.ChiTietPhieuXuatBUS;
import DAO.SanPhamDAO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.SanPhamDTO;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChiTietPhieuXuatGUI extends Frame {
    TextField tfMaPhieuXuat, tfSoLuong, tfDonGia;
    Choice cboSanPham;
    TextArea taDanhSach;

    ChiTietPhieuXuatBUS bus;
    ArrayList<SanPhamDTO> dsSP;

    public ChiTietPhieuXuatGUI(int maPhieuXuat) {
        setTitle("Chi tiết Phiếu Xuất #" + maPhieuXuat);
        setSize(700, 500);
        setLayout(new FlowLayout());

        tfMaPhieuXuat = new TextField(5); tfMaPhieuXuat.setEditable(false);
        tfSoLuong = new TextField(5);
        tfDonGia = new TextField(8);
        cboSanPham = new Choice();
        taDanhSach = new TextArea(15, 70); taDanhSach.setEditable(false);

        add(new Label("Mã PX:")); add(tfMaPhieuXuat);
        add(new Label("Sản phẩm:")); add(cboSanPham);
        add(new Label("Số lượng:")); add(tfSoLuong);
        add(new Label("Đơn giá:")); add(tfDonGia);

        Button btnThem = new Button("Thêm SP");
        Button btnTai = new Button("Tải DS");
        Button btnIn = new Button("In ra file");

        add(btnThem); add(btnTai); add(btnIn);
        add(taDanhSach);

        try {
            bus = new ChiTietPhieuXuatBUS();
            dsSP = new SanPhamDAO().docDSSanPham();

            for (SanPhamDTO sp : dsSP) {
                cboSanPham.add(sp.getMaSanPham() + " - " + sp.getTenSanPham());
            }

            tfMaPhieuXuat.setText(String.valueOf(maPhieuXuat));
            hienThiDanhSach(maPhieuXuat);
        } catch (Exception e) {
            taDanhSach.setText("Lỗi khởi tạo: " + e.getMessage());
        }

        btnThem.addActionListener(e -> {
            try {
                int maPX = Integer.parseInt(tfMaPhieuXuat.getText());
                int maSP = dsSP.get(cboSanPham.getSelectedIndex()).getMaSanPham();
                int soLuongMoi = Integer.parseInt(tfSoLuong.getText());
                double donGiaMoi = Double.parseDouble(tfDonGia.getText());

                ArrayList<ChiTietPhieuXuatDTO> ds = bus.docTheoPhieu(maPX);
                boolean daTonTai = false;

                for (ChiTietPhieuXuatDTO ct : ds) {
                    if (ct.getMaSanPham() == maSP) {
                        // Nếu đã tồn tại sản phẩm, cập nhật số lượng và đơn giá
                        int tongSoLuong = ct.getSoLuong() + soLuongMoi;
                        ChiTietPhieuXuatDTO capNhat = new ChiTietPhieuXuatDTO(maPX, maSP, tongSoLuong, donGiaMoi);
                        bus.sua(capNhat);
                        daTonTai = true;
                        break;
                    }
                }

                if (!daTonTai) {
                    ChiTietPhieuXuatDTO ct = new ChiTietPhieuXuatDTO(maPX, maSP, soLuongMoi, donGiaMoi);
                    bus.them(ct);
                }

                hienThiDanhSach(maPX);
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi thêm SP: " + ex.getMessage());
            }
        });


        btnTai.addActionListener(e -> {
            int maPX = Integer.parseInt(tfMaPhieuXuat.getText());
            hienThiDanhSach(maPX);
            tfSoLuong.setText("");
            tfDonGia.setText("");
            cboSanPham.select(0);
        });

        btnIn.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(tfMaPhieuXuat.getText());
                ArrayList<ChiTietPhieuXuatDTO> ds = bus.docTheoPhieu(ma);
                PrintWriter writer = new PrintWriter("phieuxuat_" + ma + ".txt");

                writer.println("PHIẾU XUẤT #" + ma);
                writer.println("--------------------------");

                double tong = 0;
                for (ChiTietPhieuXuatDTO ct : ds) {
                    writer.println("SP: " + ct.getMaSanPham() + " | SL: " + ct.getSoLuong() + " | Giá: " + ct.getDonGia());
                    tong += ct.getSoLuong() * ct.getDonGia();
                }

                writer.println("--------------------------");
                writer.println("Tổng cộng: " + tong + " VND");
                writer.close();

                taDanhSach.append("\n✅ Đã in ra file: phieuxuat_" + ma + ".txt\n");

            } catch (Exception ex) {
                taDanhSach.setText("Lỗi in: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void hienThiDanhSach(int maPhieuXuat) {
        taDanhSach.setText("");
        try {
            ArrayList<ChiTietPhieuXuatDTO> ds = bus.docTheoPhieu(maPhieuXuat);
            double tongTien = 0;

            for (ChiTietPhieuXuatDTO ct : ds) {
                double tien = ct.getSoLuong() * ct.getDonGia();
                tongTien += tien;

                taDanhSach.append(
                    "Mã PX: " + ct.getMaPhieuXuat() +
                    " | SP: " + ct.getMaSanPham() +
                    " | SL: " + ct.getSoLuong() +
                    " | Đơn giá: " + ct.getDonGia() +
                    " | Thành tiền: " + tien + "\n"
                );
            }

            taDanhSach.append("\n👉 Tổng tiền: " + tongTien + " VND\n");

        } catch (Exception e) {
            taDanhSach.setText("Lỗi tải danh sách: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChiTietPhieuXuatGUI(1);
    }
}
