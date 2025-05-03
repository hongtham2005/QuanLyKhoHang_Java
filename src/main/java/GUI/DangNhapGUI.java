package GUI;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Email:"));
        tfEmail = new JTextField(20);
        add(tfEmail);

        add(new JLabel("Mật khẩu:"));
        tfMatKhau = new JPasswordField(20);
        add(tfMatKhau);

        JButton btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBackground(new Color(0, 196, 228));
        btnDangNhap.setForeground(Color.WHITE);
        add(btnDangNhap);

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
                    new MenuChinhGUI(tk.getMaNhomQuyen()).setVisible(true); // Truyền maNhomQuyen
                } else {
                    JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu, hoặc tài khoản đã bị xóa.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhapGUI().setVisible(true));
    }
}