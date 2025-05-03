package GUI;

import DAO.ChiTietQuyenDAO;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MenuChinhGUI extends JFrame {
    private QuanLySanPhamPanel sanPhamPanel;
    private KhachHangGUI khachHangGUI;
    private PhieuXuatGUI phieuXuatGUI;
    private int maNhomQuyen;
    private String taiKhoanEmail;
    private ChiTietQuyenDAO chiTietQuyenDAO;

    public MenuChinhGUI(int maNhomQuyen, String taiKhoanEmail) {
        this.maNhomQuyen = maNhomQuyen;
        this.taiKhoanEmail = taiKhoanEmail;
        this.chiTietQuyenDAO = new ChiTietQuyenDAO();

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Không thể áp dụng FlatLaf: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi thiết lập giao diện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Menu Chính");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("Chức Năng Chính");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(33, 47, 61));
        mainPanel.add(lblTitle);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        String tenNutKhachHang = (maNhomQuyen == 3) ? "Thông tin cá nhân" : "Quản lý khách hàng";
        JButton btnKhachHang = taoNut(tenNutKhachHang);
        JButton btnSanPham = taoNut("Quản lý sản phẩm");
        JButton btnPhieuXuat = taoNut("Quản lý phiếu xuất");

        try {
            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 2, "XEM")) {
                mainPanel.add(btnKhachHang);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            } else {
                btnKhachHang.setEnabled(false);
            }

            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "XEM")) {
                mainPanel.add(btnSanPham);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            } else {
                btnSanPham.setEnabled(false);
            }

            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 3, "XEM")) {
                mainPanel.add(btnPhieuXuat);
            } else {
                btnPhieuXuat.setEnabled(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        add(mainPanel, BorderLayout.CENTER);

        btnKhachHang.addActionListener(e -> moKhachHangGUI());
        btnSanPham.addActionListener(e -> moSanPhamPanel());
        btnPhieuXuat.addActionListener(e -> moPhieuXuatGUI());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    private JButton taoNut(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 100, 210));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 123, 255));
            }
        });

        return btn;
    }

    private void moKhachHangGUI() {
        try {
            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 2, "XEM")) {
                if (khachHangGUI == null || !khachHangGUI.isVisible()) {
                    khachHangGUI = new KhachHangGUI(maNhomQuyen, this, taiKhoanEmail);
                    khachHangGUI.setVisible(true);
                } else {
                    khachHangGUI.toFront();
                    khachHangGUI.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void moSanPhamPanel() {
        try {
            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 1, "XEM")) {
                if (sanPhamPanel == null || !sanPhamPanel.isVisible()) {
                    JDialog dialog = new JDialog(this, "Quản lý sản phẩm", true);
                    sanPhamPanel = new QuanLySanPhamPanel(maNhomQuyen);
                    dialog.add(sanPhamPanel);
                    dialog.setSize(900, 600);
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);
                } else {
                    sanPhamPanel.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void moPhieuXuatGUI() {
        try {
            if (chiTietQuyenDAO.kiemTraQuyen(maNhomQuyen, 3, "XEM")) {
                if (phieuXuatGUI == null || !phieuXuatGUI.isVisible()) {
                    phieuXuatGUI = new PhieuXuatGUI(maNhomQuyen, this, taiKhoanEmail);
                    phieuXuatGUI.setVisible(true);
                } else {
                    phieuXuatGUI.toFront();
                    phieuXuatGUI.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra quyền: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}