/*
 * Click nb fs://nb fs/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb fs://nb fs/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author hong tham
 */
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    private JPanel sidebar;

    public MainFrame() {
        // Cài đặt FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Quản Lý Kho Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Thiết lập bố cục chính
        setLayout(new BorderLayout());

        // Tạo Menu Bar (phía trên cùng)
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 40, 20)); // Màu nâu đậm
        menuBar.setForeground(Color.BLACK);
        JMenu overviewMenu = new JMenu("Tổng quan");
        JMenu inventoryMenu = new JMenu("Tồn kho");
        JMenu revenueMenu = new JMenu("Doanh thu");
        JMenu suppliersMenu = new JMenu("Nhà cung cấp");
        JMenu customersMenu = new JMenu("Khách hàng");
        JMenu helpMenu = new JMenu("Trợ giúp");

        // Tùy chỉnh màu chữ của menu
        for (JMenu menu : new JMenu[]{overviewMenu, inventoryMenu, revenueMenu, suppliersMenu, customersMenu, helpMenu}) {
            menu.setForeground(Color.WHITE);
            menu.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // Thêm mục "Giới thiệu" vào menu Trợ giúp
        JMenuItem aboutItem = new JMenuItem("Giới thiệu");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Quản Lý Kho Hàng\n" +
                "Icons sourced from various free resources",
                "Giới thiệu",
                JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);

        // Thêm các menu vào menu bar
        menuBar.add(overviewMenu);
        menuBar.add(inventoryMenu);
        menuBar.add(revenueMenu);
        menuBar.add(suppliersMenu);
        menuBar.add(customersMenu);
        menuBar.add(helpMenu);

        // Đặt menu bar vào frame
        setJMenuBar(menuBar);

        // Tạo Header với gradient màu nâu cà phê
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(139, 69, 19), 0, getHeight(), new Color(200, 120, 50));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 70)); // Tăng chiều cao header

        // Logo (Sử dụng home.png)
        JLabel logoLabel = new JLabel();
        try {
            String logoPath = "/images/home.png";
            java.net.URL logoUrl = getClass().getResource(logoPath);
            if (logoUrl == null) {
                System.err.println("Không tìm thấy logo tại: " + logoPath);
                JOptionPane.showMessageDialog(this, "Không thể tải logo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                logoLabel.setText("Logo");
            } else {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image logoImage = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(logoImage));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải logo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            logoLabel.setText("Logo");
            e.printStackTrace();
        }
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Tiêu đề với bóng đổ
        JLabel headerLabel = new JLabel("QUẢN LÝ KHO HÀNG", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 50)); // Bóng mờ
                g2d.setFont(getFont());
                g2d.drawString(getText(), getWidth() / 2 - getFontMetrics(getFont()).stringWidth(getText()) / 2 + 2, getHeight() / 2 + 6);
                super.paintComponent(g);
            }
        };
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Thêm header vào frame
        add(headerPanel, BorderLayout.NORTH);

        // Tạo Sidebar với gradient
        sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 220, 220), 0, getHeight(), new Color(180, 180, 180));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 600)); // Rộng hơn một chút
        sidebar.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));

        // Xử lý thay đổi kích thước động
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
                sidebar.setPreferredSize(new Dimension(220, getHeight()));
                revalidate();
            }
        });

        // Các nút trong sidebar
        JButton productsBtn = createButtonWithIcon("Quản lý sản phẩm", "/images/shopping-cart.png");
        JButton purchaseOrdersBtn = createButtonWithIcon("Quản lý phiếu nhập", "/images/import.png");
        JButton salesOrdersBtn = createButtonWithIcon("Quản lý phiếu xuất", "/images/export.png");
        JButton customersBtn = createButtonWithIcon("Quản lý khách hàng", "/images/user.png");
        JButton suppliersBtn = createButtonWithIcon("Quản lý nhà cung cấp", "/images/truck.png");
        JButton revenueStatsBtn = createButtonWithIcon("Thống kê doanh thu", "/images/statistics.png");
        JButton inventoryStatsBtn = createButtonWithIcon("Thống kê tồn kho", "/images/attribute.png");
        JButton logoutBtn = createButtonWithIcon("Đăng xuất", "/images/logout.png");

        // Tùy chỉnh nút
        JButton[] buttons = {productsBtn, purchaseOrdersBtn, salesOrdersBtn, customersBtn, suppliersBtn, revenueStatsBtn, inventoryStatsBtn, logoutBtn};
        for (JButton btn : buttons) {
            configureButton(btn);
            sidebar.add(Box.createVerticalStrut(15));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(5));
        }
        sidebar.add(Box.createVerticalGlue()); // Đẩy các nút lên trên

        // Thêm sidebar vào frame
        add(sidebar, BorderLayout.WEST);

        // Thêm panel chính (màu nền nhẹ)
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel, BorderLayout.CENTER);
    }

    // Phương thức tạo nút với icon
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

    // Phương thức cấu hình nút
    private void configureButton(JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(new Color(255, 255, 255));
        btn.setForeground(new Color(70, 40, 20)); // Màu chữ nâu đậm
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        btn.setOpaque(true);

        // Hiệu ứng hover và pressed
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 150, 100)); // Màu nâu nhạt khi hover
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 255, 255));
                btn.setForeground(new Color(70, 40, 20));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(150, 100, 50)); // Màu nâu đậm hơn khi nhấn
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 150, 100));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}