
package GUI;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel sidebar;
    private JPanel mainPanel;
    private String taiKhoanEmail;

    public MainFrame(String taiKhoanEmail) {
        this.taiKhoanEmail = taiKhoanEmail != null ? taiKhoanEmail : "unknown";

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Quản Lý Kho Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JMenuItem aboutItem = new JMenuItem("Giới thiệu");
        aboutItem.setForeground(Color.BLACK);
        aboutItem.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Quản Lý Kho Hàng\n" +
                "Icons sourced from various free resources",
                "Giới thiệu",
                JOptionPane.INFORMATION_MESSAGE);
        });

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(Color.white);
        toolBar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JButton toolbarOverviewBtn = new JButton("Tổng quan");
        JButton toolbarInventoryBtn = new JButton("Tồn kho");
        JButton toolbarRevenueBtn = new JButton("Doanh thu");
        JButton toolbarSuppliersBtn = new JButton("Nhà cung cấp");
        JButton toolbarCustomersBtn = new JButton("Khách hàng");

        JButton[] toolBarButtons = {toolbarOverviewBtn, toolbarInventoryBtn, toolbarRevenueBtn, toolbarSuppliersBtn, toolbarCustomersBtn};
        for (JButton btn : toolBarButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            btn.setFocusPainted(false);
            btn.setMargin(new Insets(5, 10, 5, 10));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(220, 220, 220));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(Color.WHITE);
                }
            });

            toolBar.add(btn);
            toolBar.add(Box.createHorizontalStrut(5));
        }

        add(toolBar, BorderLayout.NORTH);

        sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(240, 240, 240), 0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setBackground(Color.white);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JButton productsBtn = createButtonWithIcon("Quản lý sản phẩm", "/images/shopping-cart.png");
        JButton purchaseOrdersBtn = createButtonWithIcon("Quản lý phiếu nhập", "/images/import.png");
        JButton salesOrdersBtn = createButtonWithIcon("Quản lý phiếu xuất", "/images/export.png");
        JButton customersBtn = createButtonWithIcon("Quản lý khách hàng", "/images/user.png");
        JButton suppliersBtn = createButtonWithIcon("Quản lý nhà cung cấp", "/images/truck.png");
        JButton revenueStatsBtn = createButtonWithIcon("Thống kê doanh thu", "/images/statistics.png");
        JButton inventoryStatsBtn = createButtonWithIcon("Thống kê tồn kho", "/images/attribute.png");
        JButton logoutBtn = createButtonWithIcon("Đăng xuất", "/images/logout.png");

        productsBtn.addActionListener(e -> {
            try {
                System.out.println("Đang mở panel Quản lý sản phẩm...");
                mainPanel.removeAll();
                QuanLySanPhamPanel panel = new QuanLySanPhamPanel(null, taiKhoanEmail);
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                System.out.println("Đã mở panel Quản lý sản phẩm thành công!");
            } catch (Exception ex) {
                System.err.println("Lỗi khi mở panel Quản lý sản phẩm: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi mở panel quản lý sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton[] buttons = {productsBtn, purchaseOrdersBtn, salesOrdersBtn, customersBtn, suppliersBtn, revenueStatsBtn, inventoryStatsBtn, logoutBtn};
        for (JButton btn : buttons) {
            configureButton(btn);
            sidebar.add(Box.createVerticalStrut(15));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(5));
        }
        sidebar.add(Box.createVerticalGlue());

        add(sidebar, BorderLayout.WEST);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            java.net.URL iconUrl = getClass().getResource(iconPath);
            if (iconUrl == null) {
                System.err.println("Không tìm thấy biểu tượng tại: " + iconPath);
                JOptionPane.showMessageDialog(this, "Không thể tải icon: " + iconPath, "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image scaledIcon = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledIcon));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải icon: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            button.setIconTextGap(5);
            e.printStackTrace();
        }
        return button;
    }

    private void configureButton(JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(new Color(255, 255, 255));
        btn.setForeground(new Color(0, 0, 0));
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 200, 200));
                btn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 255, 255));
                btn.setForeground(new Color(0, 0, 0));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(150, 150, 150));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 200, 200));
            }
        });
    }
}
