package GUI;

import DAO.SanPhamDAO;
import DAO.LoaiHangDAO;
import DAO.ChiTietQuyenDAO;
import DTO.SanPhamDTO;
import DTO.LoaiHangDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class QuanLySanPhamPanel extends JPanel {
    private JTable bangSanPham;
    private DefaultTableModel moHinhBang;
    private JTextField txtMaSanPham, txtTenSanPham, txtGiaNhap, txtGiaXuat, txtHinhAnh, txtTimKiemTen;
    private JSpinner spHanSuDung;
    private JComboBox<String> cbLoaiHang, cbTimKiemLoaiHang;
    private JButton btnThem, btnSua, btnXoa, btnChonHinhAnh, btnQuanLyLoaiHang, btnTimKiem, btnLamMoi;
    private JLabel lblHinhAnhPreview;
    private SanPhamDAO sanPhamDAO;
    private LoaiHangDAO loaiHangDAO;
    private ChiTietQuyenDAO chiTietQuyenDAO;
    private int maNhomQuyen;

    public QuanLySanPhamPanel(int maNhomQuyen) {
        System.out.println("Đang khởi tạo QuanLySanPhamPanel...");
        this.maNhomQuyen = maNhomQuyen;
        sanPhamDAO = new SanPhamDAO();
        loaiHangDAO = new LoaiHangDAO();
        chiTietQuyenDAO = new ChiTietQuyenDAO();

        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search panel
        JPanel timKiemPanel = new JPanel(new FlowLayout());
        timKiemPanel.setBackground(new Color(240, 242, 245));
        timKiemPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));

        JLabel lblTimKiemTen = new JLabel("Tên sản phẩm:");
        lblTimKiemTen.setForeground(new Color(0, 0, 0));
        lblTimKiemTen.setFont(new Font("Arial", Font.BOLD, 14));
        txtTimKiemTen = new JTextField(15);

        JLabel lblTimKiemLoaiHang = new JLabel("Loại hàng:");
        lblTimKiemLoaiHang.setForeground(new Color(0, 0, 0));
        lblTimKiemLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));
        cbTimKiemLoaiHang = new JComboBox<>();
        cbTimKiemLoaiHang.addItem("Tất cả");

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(50, 205, 50));
        btnTimKiem.setForeground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(255, 165, 0));
        btnLamMoi.setForeground(Color.WHITE);

        timKiemPanel.add(lblTimKiemTen);
        timKiemPanel.add(txtTimKiemTen);
        timKiemPanel.add(lblTimKiemLoaiHang);
        timKiemPanel.add(cbTimKiemLoaiHang);
        timKiemPanel.add(btnTimKiem);
        timKiemPanel.add(btnLamMoi);

        mainPanel.add(timKiemPanel, BorderLayout.NORTH);

        String[] cot = {"Mã", "Tên", "Hạn sử dụng", "Giá nhập", "Giá xuất", "Loại hàng"};
        moHinhBang = new DefaultTableModel(cot, 0);
        bangSanPham = new JTable(moHinhBang);
        bangSanPham.setRowHeight(25);
        bangSanPham.setGridColor(new Color(200, 200, 200));

        bangSanPham.setBackground(Color.WHITE);
        bangSanPham.setForeground(new Color(0, 0, 0));

        bangSanPham.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bangSanPham.getTableHeader().setForeground(Color.WHITE);
        bangSanPham.getTableHeader().setBackground(new Color(26, 60, 90));

        bangSanPham.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 235, 240));
                } else {
                    c.setBackground(new Color(173, 216, 230));
                }
                return c;
            }
        });

        JScrollPane thanhCuon = new JScrollPane(bangSanPham);
        mainPanel.add(thanhCuon, BorderLayout.CENTER);

        JPanel nhapLieuPanel = new JPanel(new GridBagLayout());
        nhapLieuPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        nhapLieuPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSanPham = new JTextField();
        txtMaSanPham.setEditable(false);
        txtMaSanPham.setVisible(true);
        txtTenSanPham = new JTextField();
        txtGiaNhap = new JTextField();
        txtGiaXuat = new JTextField();
        txtHinhAnh = new JTextField();
        txtHinhAnh.setEditable(false);
        btnChonHinhAnh = new JButton("Chọn hình ảnh");
        btnChonHinhAnh.setBackground(new Color(0, 196, 228));
        btnChonHinhAnh.setForeground(Color.WHITE);
        spHanSuDung = new JSpinner(new SpinnerDateModel());
        cbLoaiHang = new JComboBox<>();

        btnChonHinhAnh.addActionListener(e -> chonHinhAnh());

        JLabel lblMaSanPham = new JLabel("Mã sản phẩm:");
        lblMaSanPham.setForeground(new Color(0, 0, 0));
        lblMaSanPham.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblMaSanPham, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtMaSanPham, gbc);

        JLabel lblTenSanPham = new JLabel("Tên sản phẩm:");
        lblTenSanPham.setForeground(new Color(0, 0, 0));
        lblTenSanPham.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblTenSanPham, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtTenSanPham, gbc);

        JLabel lblHanSuDung = new JLabel("Hạn sử dụng:");
        lblHanSuDung.setForeground(new Color(0, 0, 0));
        lblHanSuDung.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblHanSuDung, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(spHanSuDung, gbc);

        JLabel lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setForeground(new Color(0, 0, 0));
        lblGiaNhap.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaNhap, gbc);

        JLabel lblGiaXuat = new JLabel("Giá xuất:");
        lblGiaXuat.setForeground(new Color(0, 0, 0));
        lblGiaXuat.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaXuat, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaXuat, gbc);

        JLabel lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setForeground(new Color(0, 0, 0));
        lblHinhAnh.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblHinhAnh, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        JPanel hinhAnhPanel = new JPanel(new BorderLayout(5, 0));
        hinhAnhPanel.setBackground(Color.WHITE);
        hinhAnhPanel.add(txtHinhAnh, BorderLayout.CENTER);
        hinhAnhPanel.add(btnChonHinhAnh, BorderLayout.EAST);
        nhapLieuPanel.add(hinhAnhPanel, gbc);

        JLabel lblLoaiHang = new JLabel("Loại hàng:");
        lblLoaiHang.setForeground(new Color(0, 0, 0));
        lblLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblLoaiHang, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(cbLoaiHang, gbc);

        lblHinhAnhPreview = new JLabel();
        lblHinhAnhPreview.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 5, 10, 10);
        nhapLieuPanel.add(lblHinhAnhPreview, gbc);

        JPanel nutPanel = new JPanel(new FlowLayout());
        nutPanel.setBackground(new Color(240, 242, 245));
        btnThem = new JButton("Add");
        btnSua = new JButton("Update");
        btnXoa = new JButton("Delete");
        btnQuanLyLoaiHang = new JButton("Quản lý loại hàng");

        btnThem.setBackground(new Color(0, 196, 228));
        btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 149, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(255, 80, 80));
        btnXoa.setForeground(Color.WHITE);
        btnQuanLyLoaiHang.setBackground(new Color(100, 149, 237));
        btnQuanLyLoaiHang.setForeground(Color.WHITE);

        nutPanel.add(btnThem);
        nutPanel.add(btnSua);
        nutPanel.add(btnXoa);
        nutPanel.add(btnQuanLyLoaiHang);

        JPanel phiaNam = new JPanel(new BorderLayout());
        phiaNam.setBackground(new Color(240, 242, 245));
        phiaNam.add(nhapLieuPanel, BorderLayout.CENTER);
        phiaNam.add(nutPanel, BorderLayout.SOUTH);
        mainPanel.add(phiaNam, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        System.out.println("Đang tải danh sách loại hàng...");
        taiDanhSachLoaiHang();
        System.out.println("Đang tải danh sách sản phẩm...");
        taiDanhSachSanPham();
        System.out.println("Khởi tạo QuanLySanPhamPanel thành công!");

        btnThem.addActionListener(e -> {
            try {
                if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "THEM")) {
                    themSanPham();
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn không có quyền thêm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            try {
                if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "SUA")) {
                    suaSanPham();
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn không có quyền sửa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            try {
                if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "XOA")) {
                    xoaSanPham();
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnQuanLyLoaiHang.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Quản lý loại hàng", true);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(this);
            QuanLyLoaiHangPanel quanLyLoaiHangPanel = new QuanLyLoaiHangPanel();
            dialog.add(quanLyLoaiHangPanel);
            dialog.setVisible(true);
            taiDanhSachLoaiHang();
        });

        btnTimKiem.addActionListener(e -> timKiemSanPham());

        btnLamMoi.addActionListener(e -> lamMoiTimKiem());

        bangSanPham.getSelectionModel().addListSelectionListener(e -> {
            try {
                if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "XEM")) {
                    chonSanPham();
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn không có quyền xem chi tiết sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void lamMoiTimKiem() {
        txtTimKiemTen.setText("");
        cbTimKiemLoaiHang.setSelectedIndex(0);
        taiDanhSachSanPham();
    }

    private void timKiemSanPham() {
        try {
            String tenSanPham = txtTimKiemTen.getText().trim();
            Integer maLoaiHang = null;
            int selectedIndex = cbTimKiemLoaiHang.getSelectedIndex();
            if (selectedIndex > 0) { // "Tất cả" có index 0
                List<LoaiHangDTO> danhSachLoaiHang = loaiHangDAO.layDanhSachLoaiHang();
                maLoaiHang = danhSachLoaiHang.get(selectedIndex - 1).getMaLoaiHang();
            }

            List<SanPhamDTO> danhSach = sanPhamDAO.timKiemSanPham(tenSanPham, maLoaiHang);
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : danhSach) {
                moHinhBang.addRow(new Object[]{
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getHanSuDung(),
                    sp.getGiaNhap(),
                    sp.getGiaXuat(),
                    layTenLoaiHang(sp.getMaLoaiHang())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return input.matches("^[0-9]+(\\.[0-9]+)?$");
    }

    private boolean isValidTenSanPham(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return !input.matches("^[0-9]+$");
    }

    private void chonHinhAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn hình ảnh sản phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtHinhAnh.setText(selectedFile.getAbsolutePath());
            hienThiHinhAnh(selectedFile.getAbsolutePath());
        }
    }

    private void hienThiHinhAnh(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                BufferedImage img = ImageIO.read(new File(imagePath));
                int maxWidth = 180;
                int maxHeight = 300;
                int imgWidth = img.getWidth();
                int imgHeight = img.getHeight();
                float ratio = Math.min((float) maxWidth / imgWidth, (float) maxHeight / imgHeight);
                int newWidth = (int) (imgWidth * ratio);
                int newHeight = (int) (imgHeight * ratio);
                Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                lblHinhAnhPreview.setIcon(new ImageIcon(scaledImg));
            } else {
                lblHinhAnhPreview.setIcon(null);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi hiển thị hình ảnh: " + e.getMessage());
            lblHinhAnhPreview.setIcon(null);
            JOptionPane.showMessageDialog(this, "Không thể hiển thị hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiDanhSachLoaiHang() {
        try {
            List<LoaiHangDTO> danhSach = loaiHangDAO.layDanhSachLoaiHang();
            System.out.println("Số lượng loại hàng: " + danhSach.size());
            cbLoaiHang.removeAllItems();
            cbTimKiemLoaiHang.removeAllItems();
            cbTimKiemLoaiHang.addItem("Tất cả");
            for (LoaiHangDTO lh : danhSach) {
                cbLoaiHang.addItem(lh.getTenLoaiHang());
                cbTimKiemLoaiHang.addItem(lh.getTenLoaiHang());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong taiDanhSachLoaiHang: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách loại hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiDanhSachSanPham() {
        try {
            List<SanPhamDTO> danhSach = sanPhamDAO.layDanhSachSanPham();
            System.out.println("Số lượng sản phẩm: " + danhSach.size());
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : danhSach) {
                moHinhBang.addRow(new Object[]{
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getHanSuDung(),
                    sp.getGiaNhap(),
                    sp.getGiaXuat(),
                    layTenLoaiHang(sp.getMaLoaiHang())
                });
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong taiDanhSachSanPham: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themSanPham() {
        try {
            String tenSanPham = txtTenSanPham.getText().trim();
            if (!isValidTenSanPham(tenSanPham)) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không hợp lệ! Vui lòng nhập tên có ý nghĩa (không chỉ chứa số).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String giaNhapStr = txtGiaNhap.getText().trim();
            if (giaNhapStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!isValidNumber(giaNhapStr)) {
                JOptionPane.showMessageDialog(this, "Giá nhập không hợp lệ! Vui lòng nhập số (ví dụ: 1000 hoặc 1000.50).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String giaXuatStr = txtGiaXuat.getText().trim();
            if (giaXuatStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!isValidNumber(giaXuatStr)) {
                JOptionPane.showMessageDialog(this, "Giá xuất không hợp lệ! Vui lòng nhập số (ví dụ: 1000 hoặc 1000.50).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SanPhamDTO sp = new SanPhamDTO();
            sp.setTenSanPham(tenSanPham);
            sp.setHanSuDung((Date) spHanSuDung.getValue());
            sp.setGiaNhap(new BigDecimal(giaNhapStr));
            sp.setGiaXuat(new BigDecimal(giaXuatStr));
            sp.setHinhAnh(txtHinhAnh.getText());
            sp.setMaLoaiHang(cbLoaiHang.getSelectedIndex() + 1);

            int maSanPhamMoi = sanPhamDAO.themSanPham(sp);
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công! Mã sản phẩm: " + maSanPhamMoi);
            
            txtMaSanPham.setText(String.valueOf(maSanPhamMoi));
            taiDanhSachSanPham();
            hienThiHinhAnh(txtHinhAnh.getText());
        } catch (Exception e) {
            System.err.println("Lỗi trong themSanPham: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaSanPham() {
        try {
            if (txtMaSanPham.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tenSanPham = txtTenSanPham.getText().trim();
            if (!isValidTenSanPham(tenSanPham)) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không hợp lệ! Vui lòng nhập tên có ý nghĩa (không chỉ chứa số).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String giaNhapStr = txtGiaNhap.getText().trim();
            if (giaNhapStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!isValidNumber(giaNhapStr)) {
                JOptionPane.showMessageDialog(this, "Giá nhập không hợp lệ! Vui lòng nhập số (ví dụ: 1000 hoặc 1000.50).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String giaXuatStr = txtGiaXuat.getText().trim();
            if (giaXuatStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!isValidNumber(giaXuatStr)) {
                JOptionPane.showMessageDialog(this, "Giá xuất không hợp lệ! Vui lòng nhập số (ví dụ: 1000 hoặc 1000.50).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maSanPham = Integer.parseInt(txtMaSanPham.getText());
            SanPhamDTO sp = new SanPhamDTO();
            sp.setMaSanPham(maSanPham);
            sp.setTenSanPham(tenSanPham);
            sp.setHanSuDung((Date) spHanSuDung.getValue());
            sp.setGiaNhap(new BigDecimal(giaNhapStr));
            sp.setGiaXuat(new BigDecimal(giaXuatStr));
            sp.setHinhAnh(txtHinhAnh.getText());
            sp.setMaLoaiHang(cbLoaiHang.getSelectedIndex() + 1);

            sanPhamDAO.suaSanPham(sp);
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
            taiDanhSachSanPham();
            hienThiHinhAnh(txtHinhAnh.getText());
        } catch (Exception e) {
            System.err.println("Lỗi trong suaSanPham: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSanPham() {
        try {
            int dongChon = bangSanPham.getSelectedRow();
            if (dongChon == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maSanPham = (int) moHinhBang.getValueAt(dongChon, 0);
            int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (xacNhan == JOptionPane.YES_OPTION) {
                sanPhamDAO.xoaSanPham(maSanPham);
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                taiDanhSachSanPham();
                xoaForm();
                lblHinhAnhPreview.setIcon(null);
            }
        } catch (Exception e) {
            System.err.println("Lỗi trong xoaSanPham: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonSanPham() {
        int dongChon = bangSanPham.getSelectedRow();
        if (dongChon != -1) {
            try {
                int maSanPham = (int) moHinhBang.getValueAt(dongChon, 0);
                txtMaSanPham.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 0)));
                txtTenSanPham.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 1)));
                spHanSuDung.setValue(moHinhBang.getValueAt(dongChon, 2));
                txtGiaNhap.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 3)));
                txtGiaXuat.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 4)));
                String imagePath = sanPhamDAO.layHinhAnhSanPham(maSanPham);
                txtHinhAnh.setText(imagePath);
                hienThiHinhAnh(imagePath);
                cbLoaiHang.setSelectedIndex(sanPhamDAO.layMaLoaiHangSanPham(maSanPham) - 1);
            } catch (SQLException e) {
                System.err.println("Lỗi trong chonSanPham: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaForm() {
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtGiaNhap.setText("");
        txtGiaXuat.setText("");
        txtHinhAnh.setText("");
        spHanSuDung.setValue(new Date());
        cbLoaiHang.setSelectedIndex(0);
        lblHinhAnhPreview.setIcon(null);
    }

    private String layTenLoaiHang(int maLoaiHang) {
        try {
            List<LoaiHangDTO> danhSach = loaiHangDAO.layDanhSachLoaiHang();
            for (LoaiHangDTO lh : danhSach) {
                if (lh.getMaLoaiHang() == maLoaiHang) {
                    return lh.getTenLoaiHang();
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong layTenLoaiHang: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}