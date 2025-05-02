package GUI;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuChinhGUI extends Frame {
    public MenuChinhGUI() {
        // Thiết lập Look and Feel
        try {
            FlatLightLaf.setup(); // FlatLaf Look and Feel
        } catch (Exception ex) {
            System.err.println("Không thể áp dụng FlatLaf: " + ex.getMessage());
        }

        setTitle("Menu Chính");
        setSize(300, 300);
        setLayout(new FlowLayout());

        Label lblTitle = new Label("Chọn chức năng:");
        add(lblTitle);

        Button btnKhachHang = new Button("Quản lý khách hàng");
        Button btnSanPham = new Button("Quản lý sản phẩm");
        Button btnPhieuXuat = new Button("Quản lý phiếu xuất");

        add(btnKhachHang);
        add(btnSanPham);
        add(btnPhieuXuat);

        btnKhachHang.addActionListener(e -> new KhachHangGUI());
        btnSanPham.addActionListener(e -> new SanPhamGUI());
        btnPhieuXuat.addActionListener(e -> new PhieuXuatGUI());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuChinhGUI();
    }
}

