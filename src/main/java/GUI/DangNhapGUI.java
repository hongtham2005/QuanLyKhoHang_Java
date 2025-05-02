package GUI;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DangNhapGUI extends Frame {
    TextField tfEmail, tfMatKhau;

    public DangNhapGUI() {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Không thể áp dụng Look and Feel: " + e.getMessage());
        }

        setTitle("Đăng nhập hệ thống");
        setSize(300, 200);
        setLayout(new FlowLayout());

        add(new Label("Email:"));
        tfEmail = new TextField(20);
        add(tfEmail);

        add(new Label("Mật khẩu:"));
        tfMatKhau = new TextField(20);
        tfMatKhau.setEchoChar('*');
        add(tfMatKhau);

        Button btnDangNhap = new Button("Đăng nhập");
        add(btnDangNhap);

        btnDangNhap.addActionListener(e -> {
            String email = tfEmail.getText().trim();
            String matKhau = tfMatKhau.getText().trim();

            if (email.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ email và mật khẩu.");
                return;
            }

            try {
                TaiKhoanDAO dao = new TaiKhoanDAO();
                TaiKhoanDTO tk = dao.timTheoEmailVaMatKhau(email, matKhau);

                if (tk != null && !"Đã xóa".equalsIgnoreCase(tk.getTrangThai())) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    dispose();
                    new MenuChinhGUI(); // Mở giao diện menu chính
                } else {
                    JOptionPane.showMessageDialog(null, "Sai email hoặc mật khẩu, hoặc tài khoản đã bị xóa.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new DangNhapGUI();
    }
}


