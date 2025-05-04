package GUI;

import BUS.PhieuXuatBUS;
import DTO.PhieuXuatDTO;
import DTO.ChiTietPhieuXuatDTO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.KhachHangDTO;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PhieuXuatGUI extends JFrame {
    private JTextField tfMa;
    private JComboBox<String> cboTrangThai, cboKhachHang, cboLocKH;
    private JTable tableDanhSach;
    private DefaultTableModel tableModel;
    private PhieuXuatBUS bus;
    private ArrayList<KhachHangDTO> dsKH;
    private ArrayList<SanPhamDTO> dsSP;
    private Map<Integer, SanPhamDTO> sanPhamMap;
    private MenuChinhGUI menuChinh;
    private String taiKhoanEmail;

    public PhieuXuatGUI(MenuChinhGUI menuChinh, String taiKhoanEmail) {


        setTitle("📋 Quản lý Phiếu Xuất");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Màu sắc chủ đạo
        Color bgWhite = Color.WHITE;
        Color lightGray = new Color(240, 242, 245);
        Color primaryColor = new Color(44, 62, 80);
        Color hoverColor = new Color(52, 73, 94);

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

        for (JButton btn : new JButton[]{btnThem, btnXoa, btnTai, btnChiTiet}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(primaryColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        String[] columnNames = {"Mã PX", "Thời gian", "Trạng thái", "Người tạo", "Mã KH", "Sản phẩm"};
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
            dsKH = new KhachHangDAO().docDSKhachHang();
            dsSP = new SanPhamDAO().docDSSanPham();
            sanPhamMap = new HashMap<>();
            for (SanPhamDTO sp : dsSP) {
                sanPhamMap.put(sp.getMaSanPham(), sp);
            }

            cboLocKH.addItem("Tất cả");
            for (KhachHangDTO kh : dsKH) {
                String line = kh.getMaKhachHang() + " - " + kh.getTenKhachHang();
                cboKhachHang.addItem(line);
                cboLocKH.addItem(line);
            }

            hienThiDanhSach();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Sự kiện nút
        btnThem.addActionListener(e -> themPhieuXuat());
        btnXoa.addActionListener(e -> xoaPhieuXuat());
        btnTai.addActionListener(e -> hienThiDanhSach());
        btnChiTiet.addActionListener(e -> xemChiTiet());
        cboLocKH.addActionListener(e -> locDanhSach());

        // Sự kiện đóng cửa sổ
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void hienThiDanhSach() {
        try {
            tableModel.setRowCount(0);
            ArrayList<PhieuXuatDTO> dsPhieuXuat = bus.getDSPhieuXuat();
            for (PhieuXuatDTO px : dsPhieuXuat) {
                String khachHang = "N/A";
                for (KhachHangDTO kh : dsKH) {
                    if (kh.getMaKhachHang() == px.getMaKhachHang()) {
                        khachHang = kh.getMaKhachHang() + " - " + kh.getTenKhachHang();
                        break;
                    }
                }
                StringBuilder sanPhamStr = new StringBuilder();
                ArrayList<ChiTietPhieuXuatDTO> dsChiTiet = bus.layDanhSachSanPhamTheoPhieuXuat(px.getMaPhieuXuat());
                for (ChiTietPhieuXuatDTO ct : dsChiTiet) {
                    SanPhamDTO sp = sanPhamMap.get(ct.getMaSanPham());
                    String tenSanPham = sp != null ? sp.getTenSanPham() : "Unknown";
                    sanPhamStr.append(tenSanPham).append(" (").append(ct.getSoLuong()).append("), ");
                }
                if (sanPhamStr.length() > 0) {
                    sanPhamStr.setLength(sanPhamStr.length() - 2);
                }
                tableModel.addRow(new Object[]{
                    px.getMaPhieuXuat(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(px.getThoiGian()),
                    px.getTrangThai(),
                    px.getNguoiTao(),
                    khachHang,
                    sanPhamStr.toString()
                });
            }
            tfMa.setText(String.valueOf(bus.layMaTiepTheo()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themPhieuXuat() {
        try {
            if (cboKhachHang.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selected = cboKhachHang.getSelectedItem().toString();
            String[] parts = selected.split(" - ");
            if (parts.length < 1) {
                JOptionPane.showMessageDialog(this, "Dữ liệu khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int maKhachHang;
            try {
                maKhachHang = Integer.parseInt(parts[0]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String trangThai = cboTrangThai.getSelectedItem().toString();
            int maPhieuXuat = bus.layMaTiepTheo();
            Integer maNguoiTao = new NhanVienDAO().layMaNhanVienTheoEmail(taiKhoanEmail);
            if (maNguoiTao == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với email: " + taiKhoanEmail, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PhieuXuatDTO px = new PhieuXuatDTO(maPhieuXuat, new Date(), trangThai, maNguoiTao, maKhachHang);
            bus.them(px);
            JOptionPane.showMessageDialog(this, "Thêm phiếu xuất thành công! Mã phiếu: " + px.getMaPhieuXuat());
            hienThiDanhSach();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaPhieuXuat() {
        try {
            int selectedRow = tableDanhSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maPhieuXuat = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu xuất này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bus.xoa(maPhieuXuat);
                JOptionPane.showMessageDialog(this, "Xóa phiếu xuất thành công!");
                hienThiDanhSach();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xemChiTiet() {
        try {
            int selectedRow = tableDanhSach.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maPhieuXuat = (int) tableModel.getValueAt(selectedRow, 0);
            new ChiTietPhieuXuatGUI(maPhieuXuat).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xem chi tiết: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void locDanhSach() {
        try {
            String selectedKH = (String) cboLocKH.getSelectedItem();
            tableModel.setRowCount(0);
            ArrayList<PhieuXuatDTO> dsPhieuXuat = bus.getDSPhieuXuat();
            for (PhieuXuatDTO px : dsPhieuXuat) {
                String khachHang = "N/A";
                for (KhachHangDTO kh : dsKH) {
                    if (kh.getMaKhachHang() == px.getMaKhachHang()) {
                        khachHang = kh.getMaKhachHang() + " - " + kh.getTenKhachHang();
                        break;
                    }
                }
                if ("Tất cả".equals(selectedKH) || selectedKH.equals(khachHang)) {
                    StringBuilder sanPhamStr = new StringBuilder();
                    ArrayList<ChiTietPhieuXuatDTO> dsChiTiet = bus.layDanhSachSanPhamTheoPhieuXuat(px.getMaPhieuXuat());
                    for (ChiTietPhieuXuatDTO ct : dsChiTiet) {
                        SanPhamDTO sp = sanPhamMap.get(ct.getMaSanPham());
                        String tenSanPham = sp != null ? sp.getTenSanPham() : "Unknown";
                        sanPhamStr.append(tenSanPham).append(" (").append(ct.getSoLuong()).append("), ");
                    }
                    if (sanPhamStr.length() > 0) {
                        sanPhamStr.setLength(sanPhamStr.length() - 2);
                    }
                    tableModel.addRow(new Object[]{
                        px.getMaPhieuXuat(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(px.getThoiGian()),
                        px.getTrangThai(),
                        px.getNguoiTao(),
                        khachHang,
                        sanPhamStr.toString()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi lọc danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}