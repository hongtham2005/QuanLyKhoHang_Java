package GUI;

import DAO.LoaiHangDAO;
import DTO.LoaiHangDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hong tham
 */
public class QuanLyLoaiHangPanel extends JPanel {
    private JTable bangLoaiHang;
    private DefaultTableModel moHinhBang;
    private JTextField txtMaLoaiHang, txtTenLoaiHang;
    private JButton btnThem, btnSua, btnXoa;
    private LoaiHangDAO loaiHangDAO;

    public QuanLyLoaiHangPanel() {
        loaiHangDAO = new LoaiHangDAO();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] cot = {"Mã loại hàng", "Tên loại hàng"};
        moHinhBang = new DefaultTableModel(cot, 0);
        bangLoaiHang = new JTable(moHinhBang);
        bangLoaiHang.setRowHeight(25);
        bangLoaiHang.setGridColor(new Color(200, 200, 200));
        bangLoaiHang.setBackground(Color.WHITE);
        bangLoaiHang.setForeground(new Color(0, 0, 0));
        bangLoaiHang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bangLoaiHang.getTableHeader().setForeground(Color.WHITE);
        bangLoaiHang.getTableHeader().setBackground(new Color(26, 60, 90));

        JScrollPane thanhCuon = new JScrollPane(bangLoaiHang);
        mainPanel.add(thanhCuon, BorderLayout.CENTER);

        JPanel nhapLieuPanel = new JPanel(new GridBagLayout());
        nhapLieuPanel.setBorder(BorderFactory.createTitledBorder("Thông tin loại hàng"));
        nhapLieuPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaLoaiHang = new JTextField();
        txtMaLoaiHang.setEditable(false);
        txtTenLoaiHang = new JTextField();

        JLabel lblMaLoaiHang = new JLabel("Mã loại hàng:");
        lblMaLoaiHang.setForeground(new Color(0, 0, 0));
        lblMaLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblMaLoaiHang, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtMaLoaiHang, gbc);

        JLabel lblTenLoaiHang = new JLabel("Tên loại hàng:");
        lblTenLoaiHang.setForeground(new Color(0, 0, 0));
        lblTenLoaiHang.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        nhapLieuPanel.add(lblTenLoaiHang, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        nhapLieuPanel.add(txtTenLoaiHang, gbc);

        JPanel nutPanel = new JPanel(new FlowLayout());
        nutPanel.setBackground(new Color(240, 242, 245));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        btnThem.setBackground(new Color(0, 196, 228));
        btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 149, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(255, 80, 80));
        btnXoa.setForeground(Color.WHITE);

        nutPanel.add(btnThem);
        nutPanel.add(btnSua);
        nutPanel.add(btnXoa);

        JPanel phiaNam = new JPanel(new BorderLayout());
        phiaNam.setBackground(new Color(240, 242, 245));
        phiaNam.add(nhapLieuPanel, BorderLayout.CENTER);
        phiaNam.add(nutPanel, BorderLayout.SOUTH);
        mainPanel.add(phiaNam, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        taiDanhSachLoaiHang();

        btnThem.addActionListener(e -> themLoaiHang());
        btnSua.addActionListener(e -> suaLoaiHang());
        btnXoa.addActionListener(e -> xoaLoaiHang());

        bangLoaiHang.getSelectionModel().addListSelectionListener(e -> chonLoaiHang());
    }

    private void taiDanhSachLoaiHang() {
        try {
            List<LoaiHangDTO> danhSach = loaiHangDAO.layDanhSachLoaiHang();
            moHinhBang.setRowCount(0);
            for (LoaiHangDTO lh : danhSach) {
                moHinhBang.addRow(new Object[]{
                    lh.getMaLoaiHang(),
                    lh.getTenLoaiHang()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách loại hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themLoaiHang() {
        try {
            String tenLoaiHang = txtTenLoaiHang.getText().trim();
            if (tenLoaiHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LoaiHangDTO lh = new LoaiHangDTO();
            lh.setTenLoaiHang(tenLoaiHang);
            loaiHangDAO.themLoaiHang(lh);
            JOptionPane.showMessageDialog(this, "Thêm loại hàng thành công!");
            taiDanhSachLoaiHang();
            xoaForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaLoaiHang() {
        try {
            if (txtMaLoaiHang.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tenLoaiHang = txtTenLoaiHang.getText().trim();
            if (tenLoaiHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại hàng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maLoaiHang = Integer.parseInt(txtMaLoaiHang.getText());
            LoaiHangDTO lh = new LoaiHangDTO();
            lh.setMaLoaiHang(maLoaiHang);
            lh.setTenLoaiHang(tenLoaiHang);
            loaiHangDAO.suaLoaiHang(lh);
            JOptionPane.showMessageDialog(this, "Sửa loại hàng thành công!");
            taiDanhSachLoaiHang();
            xoaForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaLoaiHang() {
        try {
            int dongChon = bangLoaiHang.getSelectedRow();
            if (dongChon == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int maLoaiHang = (int) moHinhBang.getValueAt(dongChon, 0);
            int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa loại hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (xacNhan == JOptionPane.YES_OPTION) {
                loaiHangDAO.xoaLoaiHang(maLoaiHang);
                JOptionPane.showMessageDialog(this, "Xóa loại hàng thành công!");
                taiDanhSachLoaiHang();
                xoaForm();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonLoaiHang() {
        int dongChon = bangLoaiHang.getSelectedRow();
        if (dongChon != -1) {
            txtMaLoaiHang.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 0)));
            txtTenLoaiHang.setText(String.valueOf(moHinhBang.getValueAt(dongChon, 1)));
        }
    }

    private void xoaForm() {
        txtMaLoaiHang.setText("");
        txtTenLoaiHang.setText("");
    }
}