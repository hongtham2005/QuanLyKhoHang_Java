package GUI;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class KhachHangGUI extends JFrame {
    private JTextField tfMa, tfTen, tfDiaChi, tfSDT, tfEmail;
    private JComboBox<String> cbTrangThai;
    private JTextArea taDanhSach;
    private JButton btnThem, btnSua, btnXoa, btnKhoiPhuc, btnTai, btnQuayLai, btnQuanLyPhieuXuat;
    private KhachHangBUS bus;
    private int maNhomQuyen;
    private MenuChinhGUI menuChinh;
    private String taiKhoanEmail;

    public KhachHangGUI(int maNhomQuyen, MenuChinhGUI menuChinh, String taiKhoanEmail) {
        this.maNhomQuyen = maNhomQuyen;
        this.menuChinh = menuChinh;
        this.taiKhoanEmail = taiKhoanEmail;

        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(maNhomQuyen == 3 ? "Thông tin cá nhân" : "Quản lý Khách Hàng");
        setSize(900, maNhomQuyen == 3 ? 450 : 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        tfMa = new JTextField(); tfMa.setEditable(false);
        tfTen = new JTextField(); tfTen.setEditable(maNhomQuyen != 3);
        tfDiaChi = new JTextField(); tfDiaChi.setEditable(maNhomQuyen != 3);
        tfSDT = new JTextField(); tfSDT.setEditable(maNhomQuyen != 3);
        tfEmail = new JTextField(); tfEmail.setEditable(maNhomQuyen != 3);
        cbTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Đã xóa"});
        cbTrangThai.setEnabled(maNhomQuyen != 3);

        formPanel.add(new JLabel("Mã khách hàng:"));
        formPanel.add(tfMa);
        formPanel.add(new JLabel("Tên khách hàng:"));
        formPanel.add(tfTen);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(tfDiaChi);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(tfSDT);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(tfEmail);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(cbTrangThai);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        if (maNhomQuyen != 3) {
            btnThem = new JButton("Thêm");
            btnSua = new JButton("Sửa");
            btnXoa = new JButton("Xoá");
            btnKhoiPhuc = new JButton("Khôi phục");
            btnTai = new JButton("Tải DS");
            btnQuayLai = new JButton("Quay lại");
            JButton[] buttons = {btnThem, btnSua, btnXoa, btnKhoiPhuc, btnTai, btnQuayLai};
            for (JButton btn : buttons) {
                btn.setBackground(new Color(0, 123, 255));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                buttonPanel.add(btn);
            }
        } else {
            btnQuayLai = new JButton("Quay lại");
            btnQuanLyPhieuXuat = new JButton("Quản lý phiếu xuất");
            JButton[] buttons = {btnQuanLyPhieuXuat, btnQuayLai};
            for (JButton btn : buttons) {
                btn.setBackground(new Color(0, 123, 255));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                buttonPanel.add(btn);
            }
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        if (maNhomQuyen != 3) {
            taDanhSach = new JTextArea();
            taDanhSach.setFont(new Font("Monospaced", Font.PLAIN, 13));
            taDanhSach.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(taDanhSach);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            panel.add(scrollPane, BorderLayout.SOUTH);
        }

        add(panel);
        setupEventHandlers();

        try {
            bus = new KhachHangBUS();
            if (maNhomQuyen == 3) {
                hienThiThongTinCaNhan();
            } else {
                hienThiDanhSach();
            }
        } catch (Exception e) {
            if (maNhomQuyen != 3) {
                taDanhSach.setText("Không thể kết nối CSDL: " + e.getMessage());
            } else {
                tfMa.setText("Lỗi: " + e.getMessage());
            }
        }

        setVisible(true);
    }

    private void setupEventHandlers() {
        if (maNhomQuyen != 3) {
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnKhoiPhuc.setEnabled(false);

            btnThem.addActionListener(e -> {
                if (bus == null) return;
                try {
                    if (tfTen.getText().trim().isEmpty() || tfDiaChi.getText().trim().isEmpty() || tfSDT.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int maMoi = new KhachHangDAO().layMaKhachHangTiepTheo();
                    KhachHangDTO kh = new KhachHangDTO(
                        maMoi, tfTen.getText(), tfDiaChi.getText(), tfSDT.getText(),
                        (String) cbTrangThai.getSelectedItem(), tfEmail.getText()
                    );
                    bus.them(kh);
                    hienThiDanhSach();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi thêm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnSua.addActionListener(e -> {
                if (bus == null) return;
                try {
                    if (tfMa.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int maKhachHang = Integer.parseInt(tfMa.getText());
                    if (tfTen.getText().trim().isEmpty() || tfDiaChi.getText().trim().isEmpty() || tfSDT.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    KhachHangDTO kh = new KhachHangDTO(
                        maKhachHang, tfTen.getText(), tfDiaChi.getText(), tfSDT.getText(),
                        (String) cbTrangThai.getSelectedItem(), tfEmail.getText()
                    );
                    bus.sua(kh);
                    hienThiDanhSach();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi sửa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnXoa.addActionListener(e -> {
                if (bus == null) return;
                try {
                    if (tfMa.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int maKhachHang = Integer.parseInt(tfMa.getText());
                    bus.xoa(maKhachHang);
                    hienThiDanhSach();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnKhoiPhuc.addActionListener(e -> {
                if (bus == null) return;
                try {
                    if (tfMa.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để khôi phục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int maKhachHang = Integer.parseInt(tfMa.getText());
                    new KhachHangDAO().khoiPhuc(maKhachHang);
                    hienThiDanhSach();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Khôi phục khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khôi phục: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnTai.addActionListener(e -> {
                if (bus == null) return;
                hienThiDanhSach();
            });

            taDanhSach.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    String line = getSelectedLine(taDanhSach, e.getPoint());
                    if (line != null) {
                        String[] parts = line.split(" - ");
                        if (parts.length >= 5) {
                            tfMa.setText(parts[0]);
                            tfTen.setText(parts[1]);
                            tfDiaChi.setText(parts[2]);
                            tfSDT.setText(parts[3]);
                            cbTrangThai.setSelectedItem(parts[4]);
                            tfEmail.setText(parts.length > 5 ? parts[5] : "");

                            boolean daXoa = parts[4].equalsIgnoreCase("Đã xóa");
                            btnSua.setEnabled(!daXoa);
                            btnXoa.setEnabled(!daXoa);
                            btnKhoiPhuc.setEnabled(daXoa);
                        }
                    }
                }
            });
        } else {
            btnQuanLyPhieuXuat.addActionListener(e -> {
                new PhieuXuatGUI(maNhomQuyen, menuChinh, taiKhoanEmail);
            });
        }

        btnQuayLai.addActionListener(e -> {
            dispose();
            if (menuChinh != null && !menuChinh.isVisible()) {
                menuChinh.setVisible(true);
            } else if (menuChinh != null) {
                menuChinh.toFront();
            }
        });
    }

    private void hienThiDanhSach() {
        taDanhSach.setText("");
        try {
            ArrayList<KhachHangDTO> ds = bus.getDSKhachHang();
            for (KhachHangDTO kh : ds) {
                taDanhSach.append(
                    kh.getMaKhachHang() + " - " +
                    kh.getTenKhachHang() + " - " +
                    kh.getDiaChi() + " - " +
                    kh.getSoDienThoai() + " - " +
                    kh.getTrangThai() + " - " +
                    kh.getEmail() + "\n"
                );
            }

            int maTiepTheo = new KhachHangDAO().layMaKhachHangTiepTheo();
            tfMa.setText(String.valueOf(maTiepTheo));
        } catch (Exception e) {
            taDanhSach.setText("Không thể tải danh sách: " + e.getMessage());
        }
    }

    private void hienThiThongTinCaNhan() {
        try {
            int maKhachHangDangNhap = new KhachHangDAO().layMaKhachHangTheoEmail(taiKhoanEmail);
            KhachHangDTO kh = bus.timKiemTheoMa(maKhachHangDangNhap);
            if (kh != null) {
                tfMa.setText(String.valueOf(kh.getMaKhachHang()));
                tfTen.setText(kh.getTenKhachHang());
                tfDiaChi.setText(kh.getDiaChi());
                tfSDT.setText(kh.getSoDienThoai());
                tfEmail.setText(kh.getEmail());
                cbTrangThai.setSelectedItem(kh.getTrangThai());
            } else {
                tfMa.setText("Không tìm thấy thông tin.");
            }
        } catch (Exception e) {
            tfMa.setText("Không tìm thấy thông tin khách hàng với email: " + taiKhoanEmail);
        }
    }

    private void clearFields() {
        tfMa.setText("");
        tfTen.setText("");
        tfDiaChi.setText("");
        tfSDT.setText("");
        tfEmail.setText("");
        cbTrangThai.setSelectedIndex(0);
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