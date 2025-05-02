package GUI;

import BUS.PhieuXuatBUS;
import DTO.PhieuXuatDTO;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class PhieuXuatGUI extends Frame {
    TextField tfMa, tfThoiGian, tfTrangThai, tfNguoiTao;
    Choice cboKhachHang, cboLocKH;
    JTextArea taDanhSach;
    PhieuXuatBUS bus;
    ArrayList<KhachHangDTO> dsKH;

    public PhieuXuatGUI() {
        setTitle("Quản lý Phiếu Xuất");
        setSize(700, 500);
        setLayout(new FlowLayout());

        tfMa = new TextField(5);
        tfThoiGian = new TextField(16);
        tfTrangThai = new TextField(10);
        tfNguoiTao = new TextField(5);
        cboKhachHang = new Choice();
        cboLocKH = new Choice();
        taDanhSach = new JTextArea(15, 65);
        taDanhSach.setEditable(false);
        JScrollPane scroll = new JScrollPane(taDanhSach);
        scroll.setPreferredSize(new Dimension(650, 250));
        tfMa.setEditable(false);

        add(new Label("Mã PX:")); add(tfMa);
        add(new Label("Thời gian:")); add(tfThoiGian);
        add(new Label("Trạng thái:")); add(tfTrangThai);
        add(new Label("Mã NV:")); add(tfNguoiTao);
        add(new Label("Khách hàng:")); add(cboKhachHang);

        Button btnThem = new Button("Thêm");
        Button btnXoa = new Button("Xoá");
        Button btnTai = new Button("Tải DS");
        Button btnChiTiet = new Button("Chi tiết");

        add(btnThem); add(btnXoa); add(btnTai); add(btnChiTiet);
        add(new Label("Lọc KH:")); add(cboLocKH);
        add(scroll);

        try {
            bus = new PhieuXuatBUS();
            dsKH = new KhachHangDAO().docDSKhachHang();

            cboLocKH.add("Tất cả");
            for (KhachHangDTO kh : dsKH) {
                String line = kh.getMaKhachHang() + " - " + kh.getTenKhachHang();
                cboKhachHang.add(line);
                cboLocKH.add(line);
            }

            hienThiDanhSach();
            clearFieldsExceptMa();

        } catch (Exception e) {
            taDanhSach.setText("Lỗi: " + e.getMessage());
        }

        btnThem.addActionListener(e -> {
            try {
                int ma = bus.layMaTiepTheo();
                Date now = new Date();
                String trangThai = tfTrangThai.getText();
                int nguoiTao = Integer.parseInt(tfNguoiTao.getText());
                int maKH = dsKH.get(cboKhachHang.getSelectedIndex()).getMaKhachHang();

                PhieuXuatDTO px = new PhieuXuatDTO(ma, now, trangThai, nguoiTao, maKH);
                bus.them(px);
                hienThiDanhSach();
                clearFieldsExceptMa();

            } catch (Exception ex) {
                taDanhSach.setText("Lỗi thêm: " + ex.getMessage());
            }
        });

        btnXoa.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(tfMa.getText());
                bus.xoa(ma);
                hienThiDanhSach();
                clearFieldsExceptMa();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi xoá: " + ex.getMessage());
            }
        });

        btnTai.addActionListener(e -> {
            hienThiDanhSach();
            clearFieldsExceptMa();
        });

        btnChiTiet.addActionListener(e -> {
            if (tfMa.getText().isEmpty()) {
                taDanhSach.setText("Bạn chưa chọn phiếu nào.");
                return;
            }
            try {
                int ma = Integer.parseInt(tfMa.getText());
                new ChiTietPhieuXuatGUI(ma);
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi mở chi tiết: " + ex.getMessage());
            }
        });

        cboLocKH.addItemListener(e -> hienThiDanhSach());

        taDanhSach.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selected = getSelectedLine(taDanhSach, e.getPoint());
                if (selected != null) {
                    String[] parts = selected.split(" - ");
                    if (parts.length >= 5) {
                        tfMa.setText(parts[0].trim());
                        tfThoiGian.setText(parts[1].trim());
                        tfTrangThai.setText(parts[2].trim());
                        tfNguoiTao.setText(parts[3].trim());

                        for (int i = 0; i < dsKH.size(); i++) {
                            if (dsKH.get(i).getMaKhachHang() == Integer.parseInt(parts[4].trim())) {
                                cboKhachHang.select(i);
                                break;
                            }
                        }
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void hienThiDanhSach() {
        taDanhSach.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            int index = cboLocKH.getSelectedIndex();
            int maLoc = (index > 0) ? dsKH.get(index - 1).getMaKhachHang() : -1;

            for (PhieuXuatDTO px : bus.getDSPhieuXuat()) {
                if (maLoc != -1 && px.getMaKhachHang() != maLoc) continue;

                taDanhSach.append(
                    px.getMaPhieuXuat() + " - " +
                    sdf.format(px.getThoiGian()) + " - " +
                    px.getTrangThai() + " - " +
                    px.getNguoiTao() + " - " +
                    px.getMaKhachHang() + "\n"
                );
            }

            tfMa.setText(String.valueOf(bus.layMaTiepTheo()));
        } catch (Exception e) {
            tfMa.setText("?");
        }
    }

    private void clearFieldsExceptMa() {
        tfThoiGian.setText("");
        tfTrangThai.setText("");
        tfNguoiTao.setText("");
        cboKhachHang.select(0);
    }

    private String getSelectedLine(JTextArea ta, Point point) {
        int pos = ta.viewToModel2D(point);
        try {
            int line = ta.getLineOfOffset(pos);
            int start = ta.getLineStartOffset(line);
            int end = ta.getLineEndOffset(line);
            return ta.getText().substring(start, end).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        new PhieuXuatGUI();
    }
}
