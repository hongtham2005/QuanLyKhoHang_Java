package GUI;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class MenuChinhGUI extends JFrame {
    private QuanLySanPhamPanel sanPhamPanel;
    private KhachHangGUI khachHangGUI;
    private PhieuXuatGUI phieuXuatGUI;
    private int maNhomQuyen;

    public MenuChinhGUI(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Không thể áp dụng FlatLaf: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi thiết lập giao diện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Menu Chính");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Chọn Chức Năng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(26, 60, 90));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnKhachHang = new JButton("Quản lý khách hàng");
        JButton btnSanPham = new JButton("Quản lý sản phẩm");
        JButton btnPhieuXuat = new JButton("Quản lý phiếu xuất");

        btnKhachHang.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSanPham.setFont(new Font("Arial", Font.PLAIN, 14));
        btnPhieuXuat.setFont(new Font("Arial", Font.PLAIN, 14));

        btnKhachHang.setBackground(new Color(0, 196, 228));
        btnSanPham.setBackground(new Color(0, 196, 228));
        btnPhieuXuat.setBackground(new Color(0, 196, 228));

        btnKhachHang.setForeground(Color.WHITE);
        btnSanPham.setForeground(Color.WHITE);
        btnPhieuXuat.setForeground(Color.WHITE);

        btnKhachHang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnSanPham.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnPhieuXuat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btnKhachHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSanPham.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPhieuXuat.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(btnKhachHang);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnSanPham);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnPhieuXuat);

        add(mainPanel, BorderLayout.CENTER);

        btnKhachHang.addActionListener(e -> moKhachHangGUI());
        btnSanPham.addActionListener(e -> moSanPhamPanel());
        btnPhieuXuat.addActionListener(e -> moPhieuXuatGUI());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                dispose();
            }
        });
    }

    private void moKhachHangGUI() {
        if (khachHangGUI == null || !khachHangGUI.isVisible()) {
            khachHangGUI = new KhachHangGUI();
            khachHangGUI.setVisible(true);
        } else {
            khachHangGUI.toFront();
            khachHangGUI.requestFocus();
        }
    }

    private void moSanPhamPanel() {
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
    }

    private void moPhieuXuatGUI() {
        if (phieuXuatGUI == null || !phieuXuatGUI.isVisible()) {
            phieuXuatGUI = new PhieuXuatGUI();
            phieuXuatGUI.setVisible(true);
        } else {
            phieuXuatGUI.toFront();
            phieuXuatGUI.requestFocus();
        }
    }

}