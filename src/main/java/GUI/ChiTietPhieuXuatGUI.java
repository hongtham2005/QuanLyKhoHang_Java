package GUI;

import BUS.ChiTietPhieuXuatBUS;
import DAO.SanPhamDAO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.SanPhamDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChiTietPhieuXuatGUI extends JFrame {
    private JTextField tfMaPhieuXuat, tfSoLuong, tfDonGia;
    private JComboBox<String> cboSanPham;
    private JTextArea taDanhSach;
    private ChiTietPhieuXuatBUS bus;
    private ArrayList<SanPhamDTO> dsSP;

    public ChiTietPhieuXuatGUI(int maPhieuXuat) {
        setTitle("Chi tiết Phiếu Xuất #" + maPhieuXuat);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        tfMaPhieuXuat = new JTextField(5); tfMaPhieuXuat.setEditable(false);
        tfSoLuong = new JTextField(5);
        tfDonGia = new JTextField(8);
        cboSanPham = new JComboBox<>();
        taDanhSach = new JTextArea(15, 70); taDanhSach.setEditable(false);

        add(new JLabel("Mã PX:")); add(tfMaPhieuXuat);
        add(new JLabel("Sản phẩm:")); add(cboSanPham);
        add(new JLabel("Số lượng:")); add(tfSoLuong);
        add(new JLabel("Đơn giá:")); add(tfDonGia);

        JButton btnThem = new JButton("Thêm SP");
        JButton btnTai = new JButton("Tải DS");
        JButton btnIn = new JButton("In ra file");

        add(btnThem); add(btnTai); add(btnIn);
        add(taDanhSach);

        try {
            bus = new ChiTietPhieuXuatBUS();
            dsSP = new SanPhamDAO().docDSSanPham();

            for (SanPhamDTO sp : dsSP) {
                cboSanPham.addItem(sp.getMaSanPham() + " - " + sp.getTenSanPham());
            }

            tfMaPhieuXuat.setText(String.valueOf(maPhieuXuat));
            hienThiDanhSach(maPhieuXuat);
        } catch (Exception e) {
            taDanhSach.setText("Lỗi khởi tạo: " + e.getMessage());
        }

        btnThem.addActionListener(e -> {
            try {
                String soLuongText = tfSoLuong.getText().trim();
                String donGiaText = tfDonGia.getText().trim();
                if (soLuongText.isEmpty() || donGiaText.isEmpty()) {
                    taDanhSach.setText("Vui lòng nhập số lượng và đơn giá!");
                    return;
                }
                int soLuongMoi = Integer.parseInt(soLuongText);
                double donGiaMoi = Double.parseDouble(donGiaText);
                if (soLuongMoi <= 0 || donGiaMoi < 0) {
                    taDanhSach.setText("Số lượng phải lớn hơn 0 và đơn giá không được âm!");
                    return;
                }

                int maPX = Integer.parseInt(tfMaPhieuXuat.getText());
                int maSP = dsSP.get(cboSanPham.getSelectedIndex()).getMaSanPham();

                ArrayList<ChiTietPhieuXuatDTO> ds = bus.docTheoPhieu(maPX);
                boolean daTonTai = false;

                for (ChiTietPhieuXuatDTO ct : ds) {
                    if (ct.getMaSanPham() == maSP) {
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
            } catch (NumberFormatException ex) {
                taDanhSach.setText("Số lượng và đơn giá phải là số hợp lệ!");
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi thêm SP: " + ex.getMessage());
            }
        });

        btnTai.addActionListener(e -> {
            int maPX = Integer.parseInt(tfMaPhieuXuat.getText());
            hienThiDanhSach(maPX);
            tfSoLuong.setText("");
            tfDonGia.setText("");
            cboSanPham.setSelectedIndex(0);
        });

        btnIn.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(tfMaPhieuXuat.getText());
                ArrayList<ChiTietPhieuXuatDTO> ds = bus.docTheoPhieu(ma);
                try (PrintWriter writer = new PrintWriter("phieuxuat_" + ma + ".txt")) {
                    writer.println("PHIẾU XUẤT #" + ma);
                    writer.println("--------------------------");

                    double tong = 0;
                    for (ChiTietPhieuXuatDTO ct : ds) {
                        writer.println("SP: " + ct.getMaSanPham() + " | SL: " + ct.getSoLuong() + " | Giá: " + ct.getDonGia());
                        tong += ct.getSoLuong() * ct.getDonGia();
                    }

                    writer.println("--------------------------");
                    writer.println("Tổng cộng: " + tong + " VND");
                }

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
}