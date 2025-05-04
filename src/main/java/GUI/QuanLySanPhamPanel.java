package GUI;

import DAO.SanPhamDAO;
import DAO.LoaiHangDAO;
import DTO.SanPhamDTO;
import DTO.LoaiHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JTextField txtMaSanPham, txtTenSanPham, txtGiaNhap, txtGiaXuat, txtHinhAnh, txtTimKiemTen, txtXuatXu;
    private JSpinner spHanSuDung;
    private JComboBox<LoaiHangDTO> cbLoaiHang;
    private JComboBox<String> cbTimKiemLoaiHang;
    private JButton btnThem, btnSua, btnXoa, btnChonHinhAnh, btnQuanLyLoaiHang, btnTimKiem, btnLamMoi;
    private JLabel lblHinhAnhPreview;
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

        String[] cot = {"Mã", "Tên", "Hạn sử dụng", "Giá nhập", "Giá xuất"};
        moHinhBang = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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
        txtTenSanPham = new JTextField();
        txtXuatXu = new JTextField();
        txtGiaNhap = new JTextField();
        txtGiaXuat = new JTextField();
        txtHinhAnh = new JTextField();
        txtHinhAnh.setEditable(false);
        btnChonHinhAnh = new JButton("Chọn hình ảnh");
        btnChonHinhAnh.setBackground(new Color(0, 196, 228));
        btnChonHinhAnh.setForeground(Color.WHITE);
        spHanSuDung = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spHanSuDung, "yyyy-MM-dd");
        spHanSuDung.setEditor(dateEditor);
        spHanSuDung.setValue(new Date());
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

        JLabel lblXuatXu = new JLabel("Xuất xứ:");
        lblXuatXu.setForeground(new Color(0, 0, 0));
        lblXuatXu.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblXuatXu, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtXuatXu, gbc);

        JLabel lblHanSuDung = new JLabel("Hạn sử dụng:");
        lblHanSuDung.setForeground(new Color(0, 0, 0));
        lblHanSuDung.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblHanSuDung, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(spHanSuDung, gbc);

        JLabel lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setForeground(new Color(0, 0, 0));
        lblGiaNhap.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaNhap, gbc);

        JLabel lblGiaXuat = new JLabel("Giá xuất:");
        lblGiaXuat.setForeground(new Color(0, 0, 0));
        lblGiaXuat.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblGiaXuat, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtGiaXuat, gbc);

        JLabel lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setForeground(new Color(0, 0, 0));
        lblHinhAnh.setFont(new Font("Arial", Font.BOLD, 14));
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

        JLabel lblLoaiHang = new JLabel("Loại hàng:");
        lblLoaiHang.setForeground(new Color(0, 0, 0));
        lblLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblLoaiHang, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(cbLoaiHang, gbc);

        lblHinhAnhPreview = new JLabel();
        lblHinhAnhPreview.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 8;
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

        add(nhapLieuPanel, BorderLayout.EAST);
        add(nutPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Load dữ liệu
        taiDuLieuLoaiHang();
        taiDuLieuSanPham();

        // Thêm sự kiện
        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnQuanLyLoaiHang.addActionListener(e -> moQuanLyLoaiHang());
        btnTimKiem.addActionListener(e -> timKiemSanPham());
        btnLamMoi.addActionListener(e -> lamMoi());

        // Sự kiện chọn sản phẩm từ bảng
        bangSanPham.getSelectionModel().addListSelectionListener(e -> {
            int row = bangSanPham.getSelectedRow();
            if (row != -1) {
                try {
                    int maSanPham = (int) moHinhBang.getValueAt(row, 0);
                    SanPhamDTO sp = sanPhamDAO.docDSSanPham().stream()
                            .filter(s -> s.getMaSanPham() == maSanPham)
                            .findFirst()
                            .orElse(null);
                    if (sp != null) {
                        txtMaSanPham.setText(String.valueOf(sp.getMaSanPham()));
                        txtTenSanPham.setText(sp.getTenSanPham());
                        txtXuatXu.setText(sp.getXuatXu());
                        spHanSuDung.setValue(sp.getHanSuDung() != null ? sp.getHanSuDung() : new Date());
                        txtGiaNhap.setText(String.valueOf(sp.getGiaNhap()));
                        txtGiaXuat.setText(String.valueOf(sp.getGiaXuat()));
                        txtHinhAnh.setText(sp.getHinhAnh());
                        for (int i = 0; i < cbLoaiHang.getItemCount(); i++) {
                            if (cbLoaiHang.getItemAt(i).getMaLoaiHang() == sp.getMaLoaiHang()) {
                                cbLoaiHang.setSelectedIndex(i);
                                break;
                            }
                        }
                        if (sp.getHinhAnh() != null && !sp.getHinhAnh().isEmpty()) {
                            File imageFile = new File(sp.getHinhAnh());
                            if (imageFile.exists()) {
                                BufferedImage img = ImageIO.read(imageFile);
                                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                                lblHinhAnhPreview.setIcon(new ImageIcon(scaledImg));
                            } else {
                                lblHinhAnhPreview.setIcon(null);
                            }
                        } else {
                            lblHinhAnhPreview.setIcon(null);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi chọn sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void taiDuLieuLoaiHang() {
        try {
            cbLoaiHang.removeAllItems();
            cbTimKiemLoaiHang.removeAllItems();
            cbTimKiemLoaiHang.addItem("Tất cả");
            List<LoaiHangDTO> dsLoaiHang = loaiHangDAO.docDSLoaiHang();
            for (LoaiHangDTO lh : dsLoaiHang) {
                cbLoaiHang.addItem(lh);
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
            txtMaSanPham.setText(String.valueOf(sanPhamDAO.layMaTiepTheo()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonHinhAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "Tệp hình ảnh không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            txtHinhAnh.setText(file.getAbsolutePath());
            try {
                BufferedImage img = ImageIO.read(file);
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblHinhAnhPreview.setIcon(new ImageIcon(scaledImg));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi tải hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                lblHinhAnhPreview.setIcon(null);
            }
        }
    }

    private boolean isValidInput() {
        String ten = txtTenSanPham.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ten.length() > 255) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String xuatXu = txtXuatXu.getText().trim();
        if (xuatXu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Xuất xứ không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (xuatXu.length() > 255) {
            JOptionPane.showMessageDialog(this, "Xuất xứ không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String giaNhapStr = txtGiaNhap.getText().trim();
        if (giaNhapStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá nhập không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double giaNhap = Double.parseDouble(giaNhapStr);
            if (giaNhap < 0) {
                JOptionPane.showMessageDialog(this, "Giá nhập không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String giaXuatStr = txtGiaXuat.getText().trim();
        if (giaXuatStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá xuất không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double giaXuat = Double.parseDouble(giaXuatStr);
            if (giaXuat < 0) {
                JOptionPane.showMessageDialog(this, "Giá xuất không được âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá xuất phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cbLoaiHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void themSanPham() {
        try {
            if (!isValidInput()) return;
            SanPhamDTO sp = new SanPhamDTO();
            sp.setMaSanPham(Integer.parseInt(txtMaSanPham.getText()));
            sp.setTenSanPham(txtTenSanPham.getText().trim());
            sp.setXuatXu(txtXuatXu.getText().trim());
            sp.setHanSuDung((Date) spHanSuDung.getValue());
            sp.setGiaNhap(Double.parseDouble(txtGiaNhap.getText().trim()));
            sp.setGiaXuat(Double.parseDouble(txtGiaXuat.getText().trim()));
            sp.setHinhAnh(txtHinhAnh.getText().trim());
            LoaiHangDTO loaiHang = (LoaiHangDTO) cbLoaiHang.getSelectedItem();
            if (loaiHang == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            sp.setMaLoaiHang(loaiHang.getMaLoaiHang());
            sanPhamDAO.them(sp);
            taiDuLieuSanPham();
            clearFields();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaSanPham() {
        int row = bangSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (!isValidInput()) return;
            SanPhamDTO sp = new SanPhamDTO();
            sp.setMaSanPham((int) moHinhBang.getValueAt(row, 0));
            sp.setTenSanPham(txtTenSanPham.getText().trim());
            sp.setXuatXu(txtXuatXu.getText().trim());
            sp.setHanSuDung((Date) spHanSuDung.getValue());
            sp.setGiaNhap(Double.parseDouble(txtGiaNhap.getText().trim()));
            sp.setGiaXuat(Double.parseDouble(txtGiaXuat.getText().trim()));
            sp.setHinhAnh(txtHinhAnh.getText().trim());
            LoaiHangDTO loaiHang = (LoaiHangDTO) cbLoaiHang.getSelectedItem();
            if (loaiHang == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            sp.setMaLoaiHang(loaiHang.getMaLoaiHang());
            sanPhamDAO.sua(sp);
            taiDuLieuSanPham();
            clearFields();
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
                clearFields();
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
        clearFields();
    }

    private void clearFields() {
        try {
            txtMaSanPham.setText(String.valueOf(sanPhamDAO.layMaTiepTheo()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi lấy mã tiếp theo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        txtTenSanPham.setText("");
        txtXuatXu.setText("");
        spHanSuDung.setValue(new Date());
        txtGiaNhap.setText("");
        txtGiaXuat.setText("");
        txtHinhAnh.setText("");
        lblHinhAnhPreview.setIcon(null);
        if (cbLoaiHang.getItemCount() > 0) {
            cbLoaiHang.setSelectedIndex(0);
        }
        bangSanPham.clearSelection();
    }
}