package GUI;

import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO; // Thêm import cho NhanVienDAO
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuXuatGUI extends JFrame {
    private JTextField tfMa;
    private JComboBox<String> cboTrangThai, cboKhachHang, cboLocKH;
    private JTable tableDanhSach;
    private DefaultTableModel tableModel;
    private PhieuXuatBUS bus;
    private SanPhamBUS sanPhamBus;
    private ArrayList<KhachHangDTO> dsKH;
    private int maNhomQuyen;
    private MenuChinhGUI menuChinh;
    private String taiKhoanEmail;

    public PhieuXuatGUI(int maNhomQuyen, MenuChinhGUI menuChinh, String taiKhoanEmail) {
        this.maNhomQuyen = maNhomQuyen;
        this.menuChinh = menuChinh;
        this.taiKhoanEmail = taiKhoanEmail;

        setTitle("📋 Quản lý Phiếu Xuất");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Màu sắc chủ đạo
        Color bgWhite = Color.WHITE;
        Color lightGray = new Color(240, 242, 245);
        Color primaryColor = new Color(44, 62, 80); // xanh navy
        Color hoverColor = new Color(52, 73, 94); // xanh navy nhạt khi hover

        // Panel trên (thông tin nhập liệu)
        JPanel panelTop = new JPanel(new GridBagLayout());
        panelTop.setBackground(bgWhite);
        panelTop.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfMa = new JTextField(20); tfMa.setEditable(false);
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Đã hủy", "Đang xử lý"});
        cboKhachHang = new JComboBox<>();
        cboLocKH = new JComboBox<>();

        // Vô hiệu hóa trường khách hàng cho vai trò khách hàng
        if (maNhomQuyen == 3) {
            cboKhachHang.setEnabled(false);
        }

        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        for (Component c : new Component[]{tfMa, cboTrangThai, cboKhachHang, cboLocKH}) {
            c.setFont(font);
            if (c instanceof JTextField) {
                ((JTextField) c).setBackground(lightGray);
                ((JTextField) c).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            } else if (c instanceof JComboBox) {
                ((JComboBox<?>) c).setBackground(lightGray);
                ((JComboBox<?>) c).setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            }
        }

        // Thêm các trường vào panelTop
        gbc.gridx = 0; gbc.gridy = 0; panelTop.add(new JLabel("Mã PX:"), gbc);
        gbc.gridx = 1; panelTop.add(tfMa, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelTop.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; panelTop.add(cboTrangThai, gbc);
        gbc.gridx = 2; gbc.gridy = 0; panelTop.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridx = 3; panelTop.add(cboKhachHang, gbc);
        gbc.gridx = 2; gbc.gridy = 1; panelTop.add(new JLabel("Lọc KH:"), gbc);
        gbc.gridx = 3; panelTop.add(cboLocKH, gbc);

        add(panelTop, BorderLayout.NORTH);

        // Khu vực nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        panelButtons.setBackground(bgWhite);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton btnThem = new JButton("➕ Thêm");
        JButton btnXoa = new JButton("🗑 Xoá");
        JButton btnTai = new JButton("🔃 Tải DS");
        JButton btnChiTiet = new JButton("📄 Chi tiết");

        // Tất cả nút đều hiển thị và khả dụng ở giao diện mặc định
        for (JButton btn : new JButton[]{btnThem, btnXoa, btnTai, btnChiTiet}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(primaryColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hiệu ứng hover
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(hoverColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(primaryColor);
                }
            });

            panelButtons.add(btn);
        }

        add(panelButtons, BorderLayout.CENTER);

        // Khu vực bảng danh sách
        String[] columnNames = {"Mã PX", "Thời gian", "Trạng thái", "Vai trò", "Mã KH", "Sản phẩm"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDanhSach = new JTable(tableModel);
        tableDanhSach.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tableDanhSach.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableDanhSach.getTableHeader().setBackground(new Color(230, 230, 230));
        tableDanhSach.setRowHeight(35);
        tableDanhSach.setGridColor(new Color(220, 220, 220));
        tableDanhSach.setShowGrid(true);
        tableDanhSach.setBackground(lightGray);
        tableDanhSach.setSelectionBackground(new Color(200, 220, 240));
        tableDanhSach.setSelectionForeground(Color.BLACK);
        tableDanhSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableDanhSach);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(scrollPane, BorderLayout.SOUTH);

        // Load dữ liệu
        try {
            bus = new PhieuXuatBUS();
            sanPhamBus = new SanPhamBUS();
            dsKH = new KhachHangDAO().docDSKhachHang();

            cboLocKH.addItem("Tất cả");
            for (KhachHangDTO kh : dsKH) {
                String line = kh.getMaKhachHang() + " - " + kh.getTenKhachHang();
                cboKhachHang.addItem(line);
                cboLocKH.addItem(line);
            }

            if (maNhomQuyen == 3) {
                int maKhachHang = new KhachHangDAO().layMaKhachHangTheoEmail(taiKhoanEmail);
                for (int i = 0; i < dsKH.size(); i++) {
                    if (dsKH.get(i).getMaKhachHang() == maKhachHang) {
                        cboKhachHang.setSelectedIndex(i);
                        cboLocKH.setSelectedIndex(i + 1);
                        break;
                    }
                }
            }

            hienThiDanhSach();
            clearFieldsExceptMa();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Sự kiện
        btnThem.addActionListener(e -> {
            try {
                if (cboTrangThai.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ma = bus.layMaTiepTheo();
                Date now = new Date();
                String trangThai = cboTrangThai.getSelectedItem().toString();
                Integer nguoiTao = null;
                int maKH;

                if (maNhomQuyen == 3) {
                    maKH = new KhachHangDAO().layMaKhachHangTheoEmail(taiKhoanEmail);
                } else {
                    nguoiTao = layMaNhanVienTuTaiKhoan(taiKhoanEmail);
                    if (nguoiTao == null) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    maKH = dsKH.get(cboKhachHang.getSelectedIndex()).getMaKhachHang();
                }

                PhieuXuatDTO px = new PhieuXuatDTO(ma, now, trangThai, nguoiTao, maKH);
                bus.them(px);
                hienThiDanhSach();
                clearFieldsExceptMa();
                JOptionPane.showMessageDialog(this, "Thêm phiếu xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi thêm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            try {
                if (tfMa.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ma = Integer.parseInt(tfMa.getText());
                bus.xoa(ma);
                hienThiDanhSach();
                clearFieldsExceptMa();
                JOptionPane.showMessageDialog(this, "Xóa phiếu xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTai.addActionListener(e -> {
            hienThiDanhSach();
            clearFieldsExceptMa();
        });

        btnChiTiet.addActionListener(e -> {
            if (tfMa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nào.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int ma = Integer.parseInt(tfMa.getText());
                new ChiTietPhieuXuatGUI(ma);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi mở chi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        cboLocKH.addItemListener(e -> hienThiDanhSach());

        tableDanhSach.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableDanhSach.getSelectedRow() != -1) {
                int selectedRow = tableDanhSach.getSelectedRow();
                tfMa.setText(tableModel.getValueAt(selectedRow, 0).toString());
                cboTrangThai.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());

                int maKH = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
                for (int i = 0; i < dsKH.size(); i++) {
                    if (dsKH.get(i).getMaKhachHang() == maKH) {
                        cboKhachHang.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        setVisible(true);
    }

    private void hienThiDanhSach() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            int index = cboLocKH.getSelectedIndex();
            int maLoc = (index > 0) ? dsKH.get(index - 1).getMaKhachHang() : -1;

            for (PhieuXuatDTO px : bus.getDSPhieuXuat()) {
                if (maLoc != -1 && px.getMaKhachHang() != maLoc) continue;

                String vaiTro = (px.getNguoiTao() == null) ? "Khách hàng" : "Nhân viên";
                String sanPhamList = layDanhSachSanPham(px.getMaPhieuXuat());
                tableModel.addRow(new Object[]{
                    px.getMaPhieuXuat(),
                    sdf.format(px.getThoiGian()),
                    px.getTrangThai(),
                    vaiTro,
                    px.getMaKhachHang(),
                    sanPhamList
                });
            }

            tfMa.setText(String.valueOf(bus.layMaTiepTheo()));
        } catch (Exception e) {
            tfMa.setText("?");
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String layDanhSachSanPham(int maPhieuXuat) {
        try {
            ArrayList<SanPhamDTO> dsSanPham = sanPhamBus.layDanhSachSanPhamTheoPhieuXuat(maPhieuXuat);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dsSanPham.size(); i++) {
                SanPhamDTO sp = dsSanPham.get(i);
                sb.append(sp.getMaSanPham()).append(" - ").append(sp.getTenSanPham());
                if (i < dsSanPham.size() - 1) sb.append(", ");
            }
            return sb.length() > 0 ? sb.toString() : "Chưa có sản phẩm";
        } catch (Exception e) {
            return "Lỗi tải sản phẩm";
        }
    }

    private void clearFieldsExceptMa() {
        cboTrangThai.setSelectedIndex(0);
        cboKhachHang.setSelectedIndex(0);
        tableDanhSach.clearSelection();
    }

    // Hàm lấy mã nhân viên từ email
    private Integer layMaNhanVienTuTaiKhoan(String email) {
        try {
            NhanVienDAO nhanVienDAO = new NhanVienDAO(); // Khởi tạo NhanVienDAO
            return nhanVienDAO.layMaNhanVienTheoEmail(email); // Gọi phương thức từ NhanVienDAO
        } catch (Exception e) {
            return null; // Trả về null nếu có lỗi
        }
    }
}