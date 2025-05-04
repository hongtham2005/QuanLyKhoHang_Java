package GUI;

import BUS.SanPhamBUS;
import DAO.LoaiHangDAO;
import DTO.LoaiHangDTO;
import DTO.SanPhamDTO;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SanPhamGUI extends JFrame {
    private JTextField tfMa, tfTen, tfXuatXu, tfGiaNhap, tfGiaXuat, tfHinhAnh;
    private JSpinner spnHanSD;
    private JComboBox<LoaiHangDTO> cboLoaiHang;
    private JTable tblSanPham;
    private DefaultTableModel moHinhBang;
    private SanPhamBUS bus;
    private ArrayList<LoaiHangDTO> dsLoaiHang;
    private JLabel lblHinhAnhPreview;

    public SanPhamGUI() {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo Look and Feel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Quản lý Sản Phẩm");
        setSize(1000, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel nhapLieuPanel = new JPanel(new GridBagLayout());
        nhapLieuPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        nhapLieuPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfMa = new JTextField(5);
        tfMa.setEditable(false);
        tfTen = new JTextField(15);
        tfXuatXu = new JTextField(10);
        spnHanSD = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnHanSD, "yyyy-MM-dd");
        spnHanSD.setEditor(dateEditor);
        spnHanSD.setValue(new Date());
        tfGiaNhap = new JTextField(10);
        tfGiaXuat = new JTextField(10);
        tfHinhAnh = new JTextField(15);
        tfHinhAnh.setEditable(false);
        cboLoaiHang = new JComboBox<>();
        JButton btnChonHinhAnh = new JButton("Chọn hình ảnh");
        btnChonHinhAnh.setBackground(new Color(0, 196, 228));
        btnChonHinhAnh.setForeground(Color.WHITE);

        JLabel lblMa = new JLabel("Mã:");
        JLabel lblTen = new JLabel("Tên:");
        JLabel lblXuatXu = new JLabel("Xuất xứ:");
        JLabel lblHanSD = new JLabel("HSD:");
        JLabel lblGiaNhap = new JLabel("Giá nhập:");
        JLabel lblGiaXuat = new JLabel("Giá xuất:");
        JLabel lblHinhAnh = new JLabel("Hình ảnh:");
        JLabel lblLoaiHang = new JLabel("Loại hàng:");
        lblMa.setFont(new Font("Arial", Font.BOLD, 14));
        lblTen.setFont(new Font("Arial", Font.BOLD, 14));
        lblXuatXu.setFont(new Font("Arial", Font.BOLD, 14));
        lblHanSD.setFont(new Font("Arial", Font.BOLD, 14));
        lblGiaNhap.setFont(new Font("Arial", Font.BOLD, 14));
        lblGiaXuat.setFont(new Font("Arial", Font.BOLD, 14));
        lblHinhAnh.setFont(new Font("Arial", Font.BOLD, 14));
        lblLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2; nhapLieuPanel.add(lblMa, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(tfMa, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2; nhapLieuPanel.add(lblTen, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(tfTen, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.2; nhapLieuPanel.add(lblXuatXu, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(tfXuatXu, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.2; nhapLieuPanel.add(lblHanSD, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(spnHanSD, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.2; nhapLieuPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(tfGiaNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.2; nhapLieuPanel.add(lblGiaXuat, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(tfGiaXuat, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.2; nhapLieuPanel.add(lblHinhAnh, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        JPanel hinhAnhPanel = new JPanel(new BorderLayout(5, 0));
        hinhAnhPanel.setBackground(Color.WHITE);
        hinhAnhPanel.add(tfHinhAnh, BorderLayout.CENTER);
        hinhAnhPanel.add(btnChonHinhAnh, BorderLayout.EAST);
        nhapLieuPanel.add(hinhAnhPanel, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.2; nhapLieuPanel.add(lblLoaiHang, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; nhapLieuPanel.add(cboLoaiHang, gbc);

        lblHinhAnhPreview = new JLabel();
        lblHinhAnhPreview.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 8; gbc.weightx = 0.3; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        nhapLieuPanel.add(lblHinhAnhPreview, gbc);

        JPanel nutPanel = new JPanel(new FlowLayout());
        nutPanel.setBackground(new Color(240, 242, 245));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTai = new JButton("Tải DS");
        JButton btnXoaForm = new JButton("Xóa Form");
        btnThem.setBackground(new Color(0, 196, 228));
        btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 149, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(255, 80, 80));
        btnXoa.setForeground(Color.WHITE);
        btnTai.setBackground(new Color(100, 149, 237));
        btnTai.setForeground(Color.WHITE);
        btnXoaForm.setBackground(new Color(169, 169, 169));
        btnXoaForm.setForeground(Color.WHITE);
        nutPanel.add(btnThem);
        nutPanel.add(btnSua);
        nutPanel.add(btnXoa);
        nutPanel.add(btnTai);
        nutPanel.add(btnXoaForm);

        JPanel timKiemPanel = new JPanel(new FlowLayout());
        timKiemPanel.setBackground(new Color(240, 242, 245));
        timKiemPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sản phẩm"));
        JTextField tfTimKiem = new JTextField(15);
        JButton btnTimKiem = new JButton("Tìm theo tên");
        btnTimKiem.setBackground(new Color(50, 205, 50));
        btnTimKiem.setForeground(Color.WHITE);
        JTextField tfTimMa = new JTextField(5);
        JButton btnTimMa = new JButton("Tìm theo mã");
        btnTimMa.setBackground(new Color(50, 205, 50));
        btnTimMa.setForeground(Color.WHITE);
        timKiemPanel.add(new JLabel("Tên:"));
        timKiemPanel.add(tfTimKiem);
        timKiemPanel.add(btnTimKiem);
        timKiemPanel.add(new JLabel("Mã:"));
        timKiemPanel.add(tfTimMa);
        timKiemPanel.add(btnTimMa);

        String[] cot = {"Mã", "Tên", "Hạn sử dụng", "Giá nhập", "Giá xuất"};
        moHinhBang = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSanPham = new JTable(moHinhBang);
        tblSanPham.setRowHeight(25);
        tblSanPham.setGridColor(new Color(200, 200, 200));
        tblSanPham.setBackground(Color.WHITE);
        tblSanPham.setForeground(new Color(0, 0, 0));
        tblSanPham.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblSanPham.getTableHeader().setForeground(Color.WHITE);
        tblSanPham.getTableHeader().setBackground(new Color(26, 60, 90));
        tblSanPham.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        JScrollPane thanhCuon = new JScrollPane(tblSanPham);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(timKiemPanel, BorderLayout.NORTH);
        mainPanel.add(thanhCuon, BorderLayout.CENTER);
        JPanel phiaNam = new JPanel(new BorderLayout());
        phiaNam.setBackground(new Color(240, 242, 245));
        phiaNam.add(nhapLieuPanel, BorderLayout.CENTER);
        phiaNam.add(nutPanel, BorderLayout.SOUTH);
        mainPanel.add(phiaNam, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        try {
            bus = new SanPhamBUS();
            dsLoaiHang = new LoaiHangDAO().docDSLoaiHang();
            for (LoaiHangDTO lh : dsLoaiHang) {
                cboLoaiHang.addItem(lh);
            }
            cboLoaiHang.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof LoaiHangDTO) {
                        setText(((LoaiHangDTO) value).getTenLoaiHang());
                    }
                    return this;
                }
            });
            hienThiDanhSach();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        btnChonHinhAnh.addActionListener(e -> chonHinhAnh());
        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnTai.addActionListener(e -> {
            hienThiDanhSach();
            clearFieldsExceptMa();
        });
        btnXoaForm.addActionListener(e -> clearFieldsExceptMa());
        btnTimKiem.addActionListener(e -> timKiemSanPham(tfTimKiem.getText().trim()));
        btnTimMa.addActionListener(e -> timKiemTheoMa(tfTimMa.getText().trim()));
        tblSanPham.getSelectionModel().addListSelectionListener(e -> chonSanPham());
    }

    private void chonHinhAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn hình ảnh sản phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.exists()) {
                JOptionPane.showMessageDialog(this, "Tệp hình ảnh không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String imagePath = selectedFile.getAbsolutePath();
            tfHinhAnh.setText(imagePath);
            hienThiHinhAnh(imagePath);
        }
    }

    private void hienThiHinhAnh(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    lblHinhAnhPreview.setIcon(null);
                    tfHinhAnh.setText("");
                    return;
                }
                BufferedImage img = ImageIO.read(imageFile);
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
                tfHinhAnh.setText("");
            }
        } catch (IOException e) {
            lblHinhAnhPreview.setIcon(null);
            tfHinhAnh.setText("");
            JOptionPane.showMessageDialog(this, "Không thể hiển thị hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiDanhSach() {
        try {
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : bus.getDSSanPham()) {
                moHinhBang.addRow(new Object[]{
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            }
            tfMa.setText(String.valueOf(bus.layMaTiepTheo()));
            if (!bus.getDSSanPham().isEmpty()) {
                tblSanPham.setRowSelectionInterval(0, 0);
                chonSanPham();
            } else {
                clearFieldsExceptMa();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFieldsExceptMa() {
        tfTen.setText("");
        tfXuatXu.setText("");
        spnHanSD.setValue(new Date());
        tfGiaNhap.setText("");
        tfGiaXuat.setText("");
        tfHinhAnh.setText("");
        if (cboLoaiHang.getItemCount() > 0) {
            cboLoaiHang.setSelectedIndex(0);
        }
        lblHinhAnhPreview.setIcon(null);
        tblSanPham.clearSelection();
    }

    private boolean isValidInput() {
        String ten = tfTen.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ten.length() > 255) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String xuatXu = tfXuatXu.getText().trim();
        if (xuatXu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Xuất xứ không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (xuatXu.length() > 255) {
            JOptionPane.showMessageDialog(this, "Xuất xứ không được vượt quá 255 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String giaNhapStr = tfGiaNhap.getText().trim();
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
        String giaXuatStr = tfGiaXuat.getText().trim();
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
        if (cboLoaiHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void themSanPham() {
        try {
            if (!isValidInput()) return;
            int ma = bus.layMaTiepTheo();
            String ten = tfTen.getText().trim();
            String xuatxu = tfXuatXu.getText().trim();
            Date hsd = (Date) spnHanSD.getValue();
            double gianhap = Double.parseDouble(tfGiaNhap.getText().trim());
            double giaxuat = Double.parseDouble(tfGiaXuat.getText().trim());
            String hinhanh = tfHinhAnh.getText().trim();
            int maLH = ((LoaiHangDTO) cboLoaiHang.getSelectedItem()).getMaLoaiHang();
            SanPhamDTO sp = new SanPhamDTO(ma, ten, xuatxu, hsd, gianhap, giaxuat, hinhanh, maLH);
            bus.them(sp);
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công! Mã sản phẩm: " + ma);
            hienThiHinhAnh(hinhanh);
            hienThiDanhSach();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaSanPham() {
        try {
            if (!isValidInput()) return;
            if (tfMa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ma = Integer.parseInt(tfMa.getText().trim());
            String ten = tfTen.getText().trim();
            String xuatxu = tfXuatXu.getText().trim();
            Date hsd = (Date) spnHanSD.getValue();
            double gianhap = Double.parseDouble(tfGiaNhap.getText().trim());
            double giaxuat = Double.parseDouble(tfGiaXuat.getText().trim());
            String hinhanh = tfHinhAnh.getText().trim();
            int maLH = ((LoaiHangDTO) cboLoaiHang.getSelectedItem()).getMaLoaiHang();
            SanPhamDTO sp = new SanPhamDTO(ma, ten, xuatxu, hsd, gianhap, giaxuat, hinhanh, maLH);
            bus.sua(sp);
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
            hienThiHinhAnh(hinhanh);
            hienThiDanhSach();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSanPham() {
        try {
            if (tfMa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ma = Integer.parseInt(tfMa.getText().trim());
            int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (xacNhan == JOptionPane.YES_OPTION) {
                bus.xoa(ma);
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                hienThiDanhSach();
                clearFieldsExceptMa();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemSanPham(String keyword) {
        try {
            ArrayList<SanPhamDTO> danhSach = bus.timKiemSanPham(keyword, null);
            moHinhBang.setRowCount(0);
            for (SanPhamDTO sp : danhSach) {
                moHinhBang.addRow(new Object[]{
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            }
            if (danhSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemTheoMa(String maStr) {
        try {
            int ma = Integer.parseInt(maStr);
            SanPhamDTO sp = bus.timKiemSanPhamTheoMa(ma);
            moHinhBang.setRowCount(0);
            if (sp != null) {
                moHinhBang.addRow(new Object[]{
                        sp.getMaSanPham(),
                        sp.getTenSanPham(),
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với mã: " + ma, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm phải là số nguyên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonSanPham() {
        int dongChon = tblSanPham.getSelectedRow();
        if (dongChon != -1) {
            try {
                int maSanPham = (int) moHinhBang.getValueAt(dongChon, 0);
                SanPhamDTO sp = null;
                for (SanPhamDTO s : bus.getDSSanPham()) {
                    if (s.getMaSanPham() == maSanPham) {
                        sp = s;
                        break;
                    }
                }
                if (sp != null) {
                    tfMa.setText(String.valueOf(sp.getMaSanPham()));
                    tfTen.setText(sp.getTenSanPham());
                    tfXuatXu.setText(sp.getXuatXu());
                    spnHanSD.setValue(sp.getHanSuDung());
                    tfGiaNhap.setText(String.valueOf(sp.getGiaNhap()));
                    tfGiaXuat.setText(String.valueOf(sp.getGiaXuat()));
                    tfHinhAnh.setText(sp.getHinhAnh());
                    hienThiHinhAnh(sp.getHinhAnh());
                    for (int i = 0; i < cboLoaiHang.getItemCount(); i++) {
                        if (cboLoaiHang.getItemAt(i).getMaLoaiHang() == sp.getMaLoaiHang()) {
                            cboLoaiHang.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}