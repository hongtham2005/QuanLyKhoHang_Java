package GUI;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class KhachHangGUI extends Frame {
    TextField tfMa, tfTen, tfDiaChi, tfSDT;
    JTextArea taDanhSach;
    KhachHangBUS bus;

    public KhachHangGUI() {
        setTitle("Quản lý Khách Hàng");
        setSize(600, 450);
        setLayout(new FlowLayout());

        tfMa = new TextField(5);
        tfTen = new TextField(15);
        tfDiaChi = new TextField(15);
        tfSDT = new TextField(10);
        taDanhSach = new JTextArea(10, 50);
        taDanhSach.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taDanhSach);
        scrollPane.setPreferredSize(new Dimension(500, 180));
        tfMa.setEditable(false);

        add(new Label("Mã:")); add(tfMa);
        add(new Label("Tên:")); add(tfTen);
        add(new Label("Địa chỉ:")); add(tfDiaChi);
        add(new Label("SĐT:")); add(tfSDT);

        Button btnThem = new Button("Thêm");
        Button btnSua = new Button("Sửa");
        Button btnXoa = new Button("Xoá");
        Button btnKhoiPhuc = new Button("Khôi phục");
        Button btnTai = new Button("Tải DS");

        add(btnThem); add(btnSua); add(btnXoa); add(btnKhoiPhuc); add(btnTai);
        add(scrollPane);

        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnKhoiPhuc.setEnabled(false);

        try {
            bus = new KhachHangBUS();
            hienThiDanhSach();
        } catch (Exception e) {
            e.printStackTrace();
            taDanhSach.setText("Không thể kết nối CSDL: " + e.getMessage());
        }

        btnThem.addActionListener(e -> {
            if (bus == null) {
                taDanhSach.setText("Không thể thêm vì chưa kết nối CSDL.");
                return;
            }
            try {
                int maMoi = new KhachHangDAO().layMaKhachHangTiepTheo();
                KhachHangDTO kh = new KhachHangDTO(
                    maMoi,
                    tfTen.getText(),
                    tfDiaChi.getText(),
                    tfSDT.getText(),
                    "Hoạt động"
                );
                bus.them(kh);
                hienThiDanhSach();
            } catch (Exception ex) {
                ex.printStackTrace();
                taDanhSach.setText("Lỗi thêm: " + ex.getMessage());
            }
        });

        btnSua.addActionListener(e -> {
            if (bus == null) {
                taDanhSach.setText("Không thể sửa vì chưa kết nối CSDL.");
                return;
            }
            try {
                String trangThai = "Hoạt động";
                for (KhachHangDTO kh : bus.getDSKhachHang()) {
                    if (kh.getMaKhachHang() == Integer.parseInt(tfMa.getText())) {
                        trangThai = kh.getTrangThai();
                        break;
                    }
                }

                KhachHangDTO kh = new KhachHangDTO(
                    Integer.parseInt(tfMa.getText()),
                    tfTen.getText(),
                    tfDiaChi.getText(),
                    tfSDT.getText(),
                    trangThai
                );
                bus.sua(kh);
                hienThiDanhSach();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi sửa: " + ex.getMessage());
            }
        });

        btnXoa.addActionListener(e -> {
            if (bus == null) {
                taDanhSach.setText("Không thể xóa vì chưa kết nối CSDL.");
                return;
            }
            try {
                int ma = Integer.parseInt(tfMa.getText());
                bus.xoa(ma);
                hienThiDanhSach();
                clearFields();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi xoá: " + ex.getMessage());
            }
        });

        btnKhoiPhuc.addActionListener(e -> {
            if (bus == null) {
                taDanhSach.setText("Không thể khôi phục vì chưa kết nối CSDL.");
                return;
            }
            try {
                int ma = Integer.parseInt(tfMa.getText());
                new KhachHangDAO().khoiPhuc(ma);
                hienThiDanhSach();
                clearFields();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi khôi phục: " + ex.getMessage());
            }
        });

        btnTai.addActionListener(e -> {
            if (bus == null) {
                taDanhSach.setText("Chưa kết nối được CSDL nên không thể tải danh sách.");
                return;
            }
            hienThiDanhSach();
        });

        taDanhSach.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selectedLine = getSelectedLine(taDanhSach, e.getPoint());
                if (selectedLine != null) {
                    String[] parts = selectedLine.split(" - ");
                    if (parts.length == 5) {
                        tfMa.setText(parts[0].trim());
                        tfTen.setText(parts[1].trim());
                        tfDiaChi.setText(parts[2].trim());
                        tfSDT.setText(parts[3].trim());
                        String trangThai = parts[4].trim();

                        if (trangThai.equalsIgnoreCase("Đã xóa")) {
                            btnSua.setEnabled(false);
                            btnXoa.setEnabled(false);
                            btnKhoiPhuc.setEnabled(true);
                        } else {
                            btnSua.setEnabled(true);
                            btnXoa.setEnabled(true);
                            btnKhoiPhuc.setEnabled(false);
                        }
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void hienThiDanhSach() {
        taDanhSach.setText("");

        try {
            bus = new KhachHangBUS();
        } catch (Exception e) {
            taDanhSach.setText("Không thể tải danh sách: " + e.getMessage());
            return;
        }

        ArrayList<KhachHangDTO> ds = bus.getDSKhachHang();
        for (KhachHangDTO kh : ds) {
            taDanhSach.append(
                kh.getMaKhachHang() + " - " +
                kh.getTenKhachHang() + " - " +
                kh.getDiaChi() + " - " +
                kh.getSoDienThoai() + " - " +
                kh.getTrangThai() + "\n"
            );
        }

        try {
            int maTiepTheo = new KhachHangDAO().layMaKhachHangTiepTheo();
            tfMa.setText(String.valueOf(maTiepTheo));
        } catch (Exception e) {
            tfMa.setText("?");
            System.out.println("Không lấy được mã tiếp theo: " + e.getMessage());
        }
    }

    private void clearFields() {
        tfMa.setText("");
        tfTen.setText("");
        tfDiaChi.setText("");
        tfSDT.setText("");
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

}

