/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://.netbeans/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hong tham
 */
public class KhachHangGUI extends JFrame {
    private MenuChinhGUI menuChinh;
    private String taiKhoanEmail;

    public KhachHangGUI(MenuChinhGUI menuChinh, String taiKhoanEmail) {
        this.menuChinh = menuChinh;
        this.taiKhoanEmail = taiKhoanEmail;

        setTitle("Quản lý khách hàng");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Giao diện Quản lý khách hàng (chưa hoàn thiện)"), BorderLayout.CENTER);
        add(panel);

        setVisible(true);
    }
}