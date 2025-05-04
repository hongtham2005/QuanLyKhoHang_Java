
package GUI;

import DAO.SanPhamDAO;
import DAO.LoaiHangDAO;
import DTO.SanPhamDTO;
import DTO.LoaiHangDTO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuanLySanPhamPanel extends JPanel {
    private JTable bangSanPham;
    private DefaultTableModel moHinhBang;
    private JTextField txtTimKiemTen;
    private JComboBox<String> cbTimKiemLoaiHang;
    private JButton btnThem, btnSua, btnXoa, btnQuanLyLoaiHang, btnTimKiem, btnLamMoi;
    private SanPhamDAO sanPhamDAO;
    private LoaiHangDAO loaiHangDAO;
    private MenuChinhGUI menuChinh;
    private String taiKhoanEmail;

    public QuanLySanPhamPanel(MenuChinhGUI menuChinh, String taiKhoanEmail) {
        this.menuChinh = menuChinh;
        this.taiKhoanEmail = taiKhoanEmail;

        try {
            sanPhamDAO = new SanPhamDAO();
            loaiHangDAO = new LoaiHangDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo DAO: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel timKiemPanel = new JPanel(new FlowLayout());
        timKiemPanel.setBackground(new Color(240, 242, 245));
        timKiemPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));

        JLabel lblTimKiemTen = new JLabel("Tên sản phẩm:");
        lblTimKiemTen.setForeground(new Color(33, 33, 33));
        lblTimKiemTen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTimKiemTen = new JTextField(15);
        txtTimKiemTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTimKiemTen.setBackground(new Color(240, 242, 245));
        txtTimKiemTen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JLabel lblTimKiemLoaiHang = new JLabel("Loại hàng:");
        lblTimKiemLoaiHang.setForeground(new Color(33, 33, 33));
        lblTimKiemLoaiHang.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cbTimKiemLoaiHang = new JComboBox<>();
        cbTimKiemLoaiHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbTimKiemLoaiHang.setBackground(new Color(240, 242, 245));
        cbTimKiemLoaiHang.addItem("Tất cả");

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(44, 62, 80));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTimKiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiem.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiem.setBackground(new Color(44, 62, 80));
            }
        });

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(44, 62, 80));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLamMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(44, 62, 80));
            }
        });

        timKiemPanel.add(lblTimKiemTen);
        timKiemPanel.add(txtTimKiemTen);
        timKiemPanel.add(lblTimKiemLoaiHang);
        timKiemPanel.add(cbTimKiemLoaiHang);
        timKiemPanel.add(btnTimKiem);
        timKiemPanel.add(btnLamMoi);

        mainPanel.add(timKiemPanel, BorderLayout.NORTH);

        String[] cot = {"Mã", "Tên", "Hạn sử dụng", "Giá nhập", "Giá xuất"};
        moHinhBang = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bangSanPham = new JTable(moHinhBang);
        bangSanPham.setRowHeight(35);
        bangSanPham.setGridColor(new Color(220, 220, 220));
        bangSanPham.setBackground(new Color(240, 242, 245));
        bangSanPham.setForeground(new Color(33, 33, 33));
        bangSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        bangSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        bangSanPham.getTableHeader().setForeground(Color.BLACK);
        bangSanPham.getTableHeader().setBackground(new Color(230, 230, 230));
        bangSanPham.setSelectionBackground(new Color(200, 220, 240));
        bangSanPham.setSelectionForeground(Color.BLACK);

        bangSanPham.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                } else {
                    c.setBackground(new Color(200, 220, 240));
                }
                return c;
            }
        });

        JScrollPane thanhCuon = new JScrollPane(bangSanPham);
        thanhCuon.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        mainPanel.add(thanhCuon, BorderLayout.CENTER);

        JPanel nutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        nutPanel.setBackground(new Color(240, 242, 245));

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnQuanLyLoaiHang = new JButton("Quản lý loại hàng");

        for (JButton btn : new JButton[]{btnThem, btnSua, btnXoa, btnQuanLyLoaiHang}) {
            btn.setBackground(new Color(44, 62, 80));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(52, 73, 94));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(44, 62, 80));
                }
            });
            nutPanel.add(btn);
        }

        add(nutPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Load dữ liệu
        taiDuLieuLoaiHang();
        taiDuLieuSanPham();

        // Thêm sự kiện
        btnThem.addActionListener(e -> moFormNhapLieu(true, null));
        btnSua.addActionListener(e -> {
            int row = bangSanPham.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maSanPham = (int) moHinhBang.getValueAt(row, 0);
            try {
                SanPhamDTO sp = sanPhamDAO.docDSSanPham().stream()
                        .filter(s -> s.getMaSanPham() == maSanPham)
                        .findFirst()
                        .orElse(null);
                if (sp != null) {
                    moFormNhapLieu(false, sp);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnXoa.addActionListener(e -> xoaSanPham());
        btnQuanLyLoaiHang.addActionListener(e -> moQuanLyLoaiHang());
        btnTimKiem.addActionListener(e -> timKiemSanPham());
        btnLamMoi.addActionListener(e -> lamMoi());
    }

    private void taiDuLieuLoaiHang() {
        try {
            cbTimKiemLoaiHang.removeAllItems();
            cbTimKiemLoaiHang.addItem("Tất cả");
            List<LoaiHangDTO> dsLoaiHang = loaiHangDAO.docDSLoaiHang();
            for (LoaiHangDTO lh : dsLoaiHang) {
                cbTimKiemLoaiHang.addItem(lh.getTenLoaiHang());
            }
            if (dsLoaiHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có loại hàng nào. Vui lòng thêm loại hàng trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải loại hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiDuLieuSanPham() {
        try {
            List<SanPhamDTO> dsSanPham = sanPhamDAO.docDSSanPham();
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : dsSanPham) {
                moHinhBang.addRow(new Object[]{
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void moFormNhapLieu(boolean laThem, SanPhamDTO sanPham) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), laThem ? "Thêm sản phẩm" : "Sửa sản phẩm", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(240, 242, 245));

        JPanel nhapLieuPanel = new JPanel(new GridBagLayout());
        nhapLieuPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        nhapLieuPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JTextField txtMaSanPham = new JTextField();
        txtMaSanPham.setEditable(false);
        txtMaSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaSanPham.setBackground(new Color(240, 242, 245));
        txtMaSanPham.setBorder(roundedBorder);

        JTextField txtTenSanPham = new JTextField();
        txtTenSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTenSanPham.setBackground(new Color(240, 242, 245));
        txtTenSanPham.setBorder(roundedBorder);
        txtTenSanPham.requestFocus();

        JTextField txtXuatXu = new JTextField();
        txtXuatXu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtXuatXu.setBackground(new Color(240, 242, 245));
        txtXuatXu.setBorder(roundedBorder);

        JTextField txtGiaNhap = new JTextField();
        txtGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtGiaNhap.setBackground(new Color(240, 242, 245));
        txtGiaNhap.setBorder(roundedBorder);

        JTextField txtGiaXuat = new JTextField();
        txtGiaXuat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtGiaXuat.setBackground(new Color(240, 242, 245));
        txtGiaXuat.setBorder(roundedBorder);

        JTextField txtHinhAnh = new JTextField();
        txtHinhAnh.setEditable(false);
        txtHinhAnh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHinhAnh.setBackground(new Color(240, 242, 245));
        txtHinhAnh.setBorder(roundedBorder);

        JButton btnChonHinhAnh = new JButton("Chọn hình");
        btnChonHinhAnh.setBackground(new Color(44, 62, 80));
        btnChonHinhAnh.setForeground(Color.WHITE);
        btnChonHinhAnh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChonHinhAnh.setFocusPainted(false);
        btnChonHinhAnh.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btnChonHinhAnh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnChonHinhAnh.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnChonHinhAnh.setBackground(new Color(44, 62, 80));
            }
        });

        JSpinner spHanSuDung = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spHanSuDung, "yyyy-MM-dd");
        spHanSuDung.setEditor(dateEditor);
        spHanSuDung.setValue(new Date());
        spHanSuDung.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spHanSuDung.setBackground(new Color(240, 242, 245));
        spHanSuDung.setBorder(roundedBorder);

        JComboBox<LoaiHangDTO> cbLoaiHang = new JComboBox<>();
        cbLoaiHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbLoaiHang.setBackground(new Color(240, 242, 245));
        cbLoaiHang.setBorder(roundedBorder);

        JLabel lblHinhAnhPreview = new JLabel("Không có hình ảnh", SwingConstants.CENTER);
        lblHinhAnhPreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        lblHinhAnhPreview.setBackground(Color.WHITE);
        lblHinhAnhPreview.setOpaque(true);
        lblHinhAnhPreview.setPreferredSize(new Dimension(150, 150));

        try {
            List<LoaiHangDTO> dsLoaiHang = loaiHangDAO.docDSLoaiHang();
            for (LoaiHangDTO lh : dsLoaiHang) {
                cbLoaiHang.addItem(lh);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Lỗi tải loại hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
            return;
        }

        if (!laThem && sanPham != null) {
            txtMaSanPham.setText(String.valueOf(sanPham.getMaSanPham()));
            txtTenSanPham.setText(sanPham.getTenSanPham());
            txtXuatXu.setText(sanPham.getXuatXu());
            spHanSuDung.setValue(sanPham.getHanSuDung() != null ? sanPham.getHanSuDung() : new Date());
            txtGiaNhap.setText(String.valueOf(sanPham.getGiaNhap()));
            txtGiaXuat.setText(String.valueOf(sanPham.getGiaXuat()));
            txtHinhAnh.setText(sanPham.getHinhAnh());
            for (int i = 0; i < cbLoaiHang.getItemCount(); i++) {
                if (cbLoaiHang.getItemAt(i).getMaLoaiHang() == sanPham.getMaLoaiHang()) {
                    cbLoaiHang.setSelectedIndex(i);
                    break;
                }
            }
            if (sanPham.getHinhAnh() != null && !sanPham.getHinhAnh().isEmpty()) {
                File imageFile = new File(sanPham.getHinhAnh());
                if (imageFile.exists()) {
                    try {
                        BufferedImage img = ImageIO.read(imageFile);
                        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        lblHinhAnhPreview.setIcon(new ImageIcon(scaledImg));
                        lblHinhAnhPreview.setText("");
                    } catch (IOException e) {
                        lblHinhAnhPreview.setIcon(null);
                        lblHinhAnhPreview.setText("Lỗi tải hình ảnh");
                    }
                }
            }
        }

        btnChonHinhAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.exists()) {
                    JOptionPane.showMessageDialog(dialog, "Tệp hình ảnh không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                txtHinhAnh.setText(file.getAbsolutePath());
                try {
                    BufferedImage img = ImageIO.read(file);
                    Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    lblHinhAnhPreview.setIcon(new ImageIcon(scaledImg));
                    lblHinhAnhPreview.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi tải hình ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    lblHinhAnhPreview.setIcon(null);
                    lblHinhAnhPreview.setText("Lỗi tải hình ảnh");
                }
            }
        });

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        JLabel lblMaSanPham = new JLabel("Mã sản phẩm:");
        lblMaSanPham.setForeground(new Color(33, 33, 33));
        lblMaSanPham.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblMaSanPham, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtMaSanPham, gbc);

        JLabel lblTenSanPham = new JLabel("Tên sản phẩm *:");
        lblTenSanPham.setForeground(new Color(33, 33, 33));
        lblTenSanPham.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblTenSanPham, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtTenSanPham, gbc);

        JLabel lblXuatXu = new JLabel("Xuất xứ *:");
        lblXuatXu.setForeground(new Color(33, 33, 33));
        lblXuatXu.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblXuatXu, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtXuatXu, gbc);

        JLabel lblHanSuDung = new JLabel("Hạn sử dụng:");
        lblHanSuDung.setForeground(new Color(33, 33, 33));
        lblHanSuDung.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblHanSuDung, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(spHanSuDung, gbc);

        JLabel lblGiaNhap = new JLabel("Giá nhập *:");
        lblGiaNhap.setForeground(new Color(33, 33, 33));
        lblGiaNhap.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaNhap, gbc);

        JLabel lblGiaXuat = new JLabel("Giá xuất *:");
        lblGiaXuat.setForeground(new Color(33, 33, 33));
        lblGiaXuat.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaXuat, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaXuat, gbc);

        JLabel lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setForeground(new Color(33, 33, 33));
        lblHinhAnh.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblHinhAnh, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        JPanel hinhAnhPanel = new JPanel(new BorderLayout(5, 0));
        hinhAnhPanel.setBackground(Color.WHITE);
        hinhAnhPanel.add(txtHinhAnh, BorderLayout.CENTER);
        hinhAnhPanel.add(btnChonHinhAnh, BorderLayout.EAST);
        nhapLieuPanel.add(hinhAnhPanel, gbc);

        JLabel lblLoaiHang = new JLabel("Loại hàng *:");
        lblLoaiHang.setForeground(new Color(33, 33, 33));
        lblLoaiHang.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblLoaiHang, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(cbLoaiHang, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 15, 10, 15);
        nhapLieuPanel.add(lblHinhAnhPreview, gbc);

        JPanel nutDialogPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        nutDialogPanel.setBackground(Color.WHITE);
        JButton btnXacNhan = new JButton(laThem ? "Thêm" : "Sửa");
        btnXacNhan.setBackground(new Color(44, 62, 80));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnXacNhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXacNhan.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXacNhan.setBackground(new Color(44, 62, 80));
            }
        });

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(44, 62, 80));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHuy.setFocusPainted(false);
        btnHuy.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnHuy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnHuy.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHuy.setBackground(new Color(44, 62, 80));
            }
        });

        nutDialogPanel.add(btnXacNhan);
        nutDialogPanel.add(btnHuy);

        dialog.add(nhapLieuPanel, BorderLayout.CENTER);
        dialog.add(nutDialogPanel, BorderLayout.SOUTH);

        dialog.getRootPane().setDefaultButton(btnXacNhan);
        dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        btnXacNhan.addActionListener(e -> {
            if (!isValidInput(txtTenSanPham, txtXuatXu, txtGiaNhap, txtGiaXuat, cbLoaiHang)) {
                return;
            }
            try {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setTenSanPham(txtTenSanPham.getText().trim());
                sp.setXuatXu(txtXuatXu.getText().trim());
                sp.setHanSuDung((Date) spHanSuDung.getValue());
                sp.setGiaNhap(Double.parseDouble(txtGiaNhap.getText().trim()));
                sp.setGiaXuat(Double.parseDouble(txtGiaXuat.getText().trim()));
                sp.setHinhAnh(txtHinhAnh.getText().trim());
                LoaiHangDTO loaiHang = (LoaiHangDTO) cbLoaiHang.getSelectedItem();
                sp.setMaLoaiHang(loaiHang.getMaLoaiHang());

                if (laThem) {
                    sanPhamDAO.them(sp);
                    JOptionPane.showMessageDialog(dialog, "Thêm sản phẩm thành công!");
                } else {
                    sp.setMaSanPham(Integer.parseInt(txtMaSanPham.getText()));
                    sanPhamDAO.sua(sp);
                    JOptionPane.showMessageDialog(dialog, "Sửa sản phẩm thành công!");
                }
                taiDuLieuSanPham();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đúng định dạng số cho giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi " + (laThem ? "thêm" : "sửa") + " sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private boolean isValidInput(JTextField txtTen, JTextField txtXuatXu, JTextField txtGiaNhap, JTextField txtGiaXuat, JComboBox<?> cbLoaiHang) {
        Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1, true);
        Border normalBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));

        boolean isValid = true;

        String ten = txtTen.getText().trim();
        if (ten.isEmpty()) {
            txtTen.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else if (ten.length() > 255) {
            txtTen.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            txtTen.setBorder(normalBorder);
        }

        String xuatXu = txtXuatXu.getText().trim();
        if (xuatXu.isEmpty()) {
            txtXuatXu.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Xuất xứ không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else if (xuatXu.length() > 255) {
            txtXuatXu.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Xuất xứ không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            txtXuatXu.setBorder(normalBorder);
        }

        String giaNhapStr = txtGiaNhap.getText().trim();
        if (giaNhapStr.isEmpty()) {
            txtGiaNhap.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Giá nhập không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            try {
                double giaNhap = Double.parseDouble(giaNhapStr);
                if (giaNhap < 0) {
                    txtGiaNhap.setBorder(errorBorder);
                    JOptionPane.showMessageDialog(this, "Giá nhập không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    isValid = false;
                } else {
                    txtGiaNhap.setBorder(normalBorder);
                }
            } catch (NumberFormatException e) {
                txtGiaNhap.setBorder(errorBorder);
                JOptionPane.showMessageDialog(this, "Giá nhập phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                isValid = false;
            }
        }

        String giaXuatStr = txtGiaXuat.getText().trim();
        if (giaXuatStr.isEmpty()) {
            txtGiaXuat.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Giá xuất không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            try {
                double giaXuat = Double.parseDouble(giaXuatStr);
                if (giaXuat < 0) {
                    txtGiaXuat.setBorder(errorBorder);
                    JOptionPane.showMessageDialog(this, "Giá xuất không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    isValid = false;
                } else {
                    txtGiaXuat.setBorder(normalBorder);
                }
            } catch (NumberFormatException e) {
                txtGiaXuat.setBorder(errorBorder);
                JOptionPane.showMessageDialog(this, "Giá xuất phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                isValid = false;
            }
        }

        if (cbLoaiHang.getSelectedItem() == null) {
            cbLoaiHang.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            cbLoaiHang.setBorder(normalBorder);
        }

        return isValid;
    }

    private void xoaSanPham() {
        int row = bangSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maSanPham = (int) moHinhBang.getValueAt(row, 0);
                sanPhamDAO.xoa(maSanPham);
                taiDuLieuSanPham();
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void moQuanLyLoaiHang() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Quản lý loại hàng", true);
        QuanLyLoaiHangPanel panel = new QuanLyLoaiHangPanel();
        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        taiDuLieuLoaiHang();
    }

    private void timKiemSanPham() {
        String ten = txtTimKiemTen.getText().trim();
        String tenLoaiHang = (String) cbTimKiemLoaiHang.getSelectedItem();
        try {
            Integer maLoaiHang = null;
            if (!"Tất cả".equals(tenLoaiHang)) {
                for (LoaiHangDTO lh : loaiHangDAO.docDSLoaiHang()) {
                    if (lh.getTenLoaiHang().equals(tenLoaiHang)) {
                        maLoaiHang = lh.getMaLoaiHang();
                        break;
                    }
                }
            }
            List<SanPhamDTO> dsSanPham = sanPhamDAO.timKiemSanPham(ten, maLoaiHang);
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : dsSanPham) {
                moHinhBang.addRow(new Object[]{
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            }
            if (dsSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoi() {
        txtTimKiemTen.setText("");
        cbTimKiemLoaiHang.setSelectedIndex(0);
        taiDuLieuSanPham();
    }
}
