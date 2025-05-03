package GUI;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DangNhapGUI extends JFrame {
    private JTextField tfEmail;
    private JPasswordField tfMatKhau;

    public DangNhapGUI() {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Không thể áp dụng Look and Feel: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi thiết lập giao diện: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Đăng nhập hệ thống");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Padding
        panelMain.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Đăng nhập hệ thống");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(0, 122, 204));
        panelMain.add(lblTitle);

        panelMain.add(Box.createVerticalStrut(20));

        JLabel lblEmail = new JLabel("Email:");
        tfEmail = new JTextField(20);
        tfEmail.setMaximumSize(tfEmail.getPreferredSize());

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        tfMatKhau = new JPasswordField(20);
        tfMatKhau.setMaximumSize(tfMatKhau.getPreferredSize());

        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMatKhau.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfMatKhau.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMain.add(lblEmail);
        panelMain.add(tfEmail);
        panelMain.add(Box.createVerticalStrut(10));
        panelMain.add(lblMatKhau);
        panelMain.add(tfMatKhau);
        panelMain.add(Box.createVerticalStrut(20));

        JButton btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBackground(new Color(0, 122, 204));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 14));
        btnDangNhap.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelMain.add(btnDangNhap);

        add(panelMain);

        btnDangNhap.addActionListener(e -> {
            String email = tfEmail.getText().trim();
            String matKhau = new String(tfMatKhau.getPassword()).trim();

            if (email.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ email và mật khẩu.");
                return;
            }

            try {
                TaiKhoanDAO dao = new TaiKhoanDAO();
                TaiKhoanDTO tk = dao.timTheoEmailVaMatKhau(email, matKhau);

                if (tk != null && !"Đã xóa".equalsIgnoreCase(tk.getTrangThai())) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                    dispose();
                    new MenuChinhGUI(tk.getMaNhomQuyen(), email).setVisible(true); // Truyền email
                } else {
                    JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu, hoặc tài khoản đã bị xóa.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhapGUI());
    }
}