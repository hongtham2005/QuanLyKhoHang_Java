package GUI;

import BUS.SanPhamBUS;
import DAO.DonViTinhDAO;
import DAO.LoaiHangDAO;
import DTO.DonViTinhDTO;
import DTO.LoaiHangDTO;
import DTO.SanPhamDTO;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SanPhamGUI extends Frame {
    TextField tfMa, tfTen, tfXuatXu, tfGiaNhap, tfGiaXuat, tfHinhAnh;
    JSpinner spnHanSD;
    Choice cboDonViTinh, cboLoaiHang;
    JTextArea taDanhSach;

    SanPhamBUS bus;

    ArrayList<DonViTinhDTO> dsDonViTinh;
    ArrayList<LoaiHangDTO> dsLoaiHang;

    public SanPhamGUI() {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Không thể áp dụng Look and Feel: " + ex.getMessage());
        }

        setTitle("Quản lý Sản Phẩm");
        setSize(700, 550);
        setLayout(new FlowLayout());

        tfMa = new TextField(5); tfMa.setEditable(false);
        tfTen = new TextField(15);
        tfXuatXu = new TextField(10);
        spnHanSD = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnHanSD, "yyyy-MM-dd");
        spnHanSD.setEditor(dateEditor);
        spnHanSD.setValue(new Date());
        tfGiaNhap = new TextField(10);
        tfGiaXuat = new TextField(10);
        tfHinhAnh = new TextField(15);

        cboDonViTinh = new Choice();
        cboLoaiHang = new Choice();
        taDanhSach = new JTextArea(15, 65);
        taDanhSach.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taDanhSach);
        scrollPane.setPreferredSize(new Dimension(650, 250));

        add(new Label("Mã: ")); add(tfMa);
        add(new Label("Tên: ")); add(tfTen);
        add(new Label("Xuất xứ: ")); add(tfXuatXu);
        add(new Label("HSD: ")); add(spnHanSD);
        add(new Label("Giá nhập: ")); add(tfGiaNhap);
        add(new Label("Giá xuất: ")); add(tfGiaXuat);
        add(new Label("Hình ảnh: ")); add(tfHinhAnh);
        add(new Label("Đơn vị tính: ")); add(cboDonViTinh);
        add(new Label("Loại hàng: ")); add(cboLoaiHang);

        Button btnThem = new Button("Thêm");
        Button btnSua = new Button("Sửa");
        Button btnXoa = new Button("Xóa");
        Button btnTai = new Button("Tải DS");
        add(btnThem); add(btnSua); add(btnXoa); add(btnTai);

        TextField tfTimKiem = new TextField(15);
        Button btnTimKiem = new Button("Tìm");
        add(new Label("Tìm tên: ")); add(tfTimKiem); add(btnTimKiem);
        
        TextField tfTimMa = new TextField(5);
        Button btnTimMa = new Button("Tìm theo mã");
        add(new Label("Tìm mã: ")); add(tfTimMa); add(btnTimMa);


        add(scrollPane);

        try {
            bus = new SanPhamBUS();
            dsDonViTinh = new DonViTinhDAO().docDSDonViTinh();
            dsLoaiHang = new LoaiHangDAO().docDSLoaiHang();
            for (DonViTinhDTO dvt : dsDonViTinh) cboDonViTinh.add(dvt.toString());
            for (LoaiHangDTO lh : dsLoaiHang) cboLoaiHang.add(lh.toString());
            hienThiDanhSach();
        } catch (Exception e) {
            taDanhSach.setText("Lỗi khởi tạo: " + e.getMessage());
        }

        btnThem.addActionListener(e -> {
            try {
                int ma = bus.layMaTiepTheo();
                String ten = tfTen.getText();
                String xuatxu = tfXuatXu.getText();
                Date hsd = (Date) spnHanSD.getValue();
                double gianhap = Double.parseDouble(tfGiaNhap.getText());
                double giaxuat = Double.parseDouble(tfGiaXuat.getText());
                String hinhanh = tfHinhAnh.getText();
                int maDVT = dsDonViTinh.get(cboDonViTinh.getSelectedIndex()).getMaDonViTinh();
                int maLH = dsLoaiHang.get(cboLoaiHang.getSelectedIndex()).getMaLoaiHang();
                SanPhamDTO sp = new SanPhamDTO(ma, ten, xuatxu, hsd, gianhap, giaxuat, hinhanh, maDVT, maLH);
                bus.them(sp);
                hienThiDanhSach();
                clearFieldsExceptMa();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi thêm: " + ex.getMessage());
            }
        });

        btnSua.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(tfMa.getText());
                String ten = tfTen.getText();
                String xuatxu = tfXuatXu.getText();
                Date hsd = (Date) spnHanSD.getValue();
                double gianhap = Double.parseDouble(tfGiaNhap.getText());
                double giaxuat = Double.parseDouble(tfGiaXuat.getText());
                String hinhanh = tfHinhAnh.getText();
                int maDVT = dsDonViTinh.get(cboDonViTinh.getSelectedIndex()).getMaDonViTinh();
                int maLH = dsLoaiHang.get(cboLoaiHang.getSelectedIndex()).getMaLoaiHang();
                SanPhamDTO sp = new SanPhamDTO(ma, ten, xuatxu, hsd, gianhap, giaxuat, hinhanh, maDVT, maLH);
                bus.sua(sp);
                hienThiDanhSach();
                clearFieldsExceptMa();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi sửa: " + ex.getMessage());
            }
        });

        btnXoa.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(tfMa.getText());
                bus.xoa(ma);
                hienThiDanhSach();
                clearFieldsExceptMa();
            } catch (Exception ex) {
                taDanhSach.setText("Lỗi xóa: " + ex.getMessage());
            }
        });

        btnTai.addActionListener(e -> {
            hienThiDanhSach();
            clearFieldsExceptMa();
        });

        taDanhSach.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int y = e.getY();
                FontMetrics fm = taDanhSach.getFontMetrics(taDanhSach.getFont());
                int lineHeight = fm.getHeight();
                int line = y / lineHeight;
                String[] lines = taDanhSach.getText().split("\n");
                if (line >= 0 && line < lines.length) {
                    String[] parts = lines[line].split(" - ");
                    if (parts.length >= 9) {
                        tfMa.setText(parts[0]);
                        tfTen.setText(parts[1]);
                        tfXuatXu.setText(parts[2]);
                        try {
                            spnHanSD.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]));
                        } catch (Exception ex) {
                            spnHanSD.setValue(new Date());
                        }
                        tfGiaNhap.setText(parts[4]);
                        tfGiaXuat.setText(parts[5]);
                        tfHinhAnh.setText(parts[6]);
                        selectComboBox(cboDonViTinh, parts[7]);
                        selectComboBox(cboLoaiHang, parts[8]);
                    }
                }
            }
        });

        btnTimKiem.addActionListener(e -> {
            String keyword = tfTimKiem.getText().trim().toLowerCase();
            taDanhSach.setText("");
            for (SanPhamDTO sp : bus.getDSSanPham()) {
                if (sp.getTenSanPham().toLowerCase().contains(keyword)) {
                    taDanhSach.append(
                        sp.getMaSanPham() + " - " +
                        sp.getTenSanPham() + " - " +
                        sp.getXuatXu() + " - " +
                        new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()) + " - " +
                        sp.getGiaNhap() + " - " +
                        sp.getGiaXuat() + " - " +
                        sp.getHinhAnh() + " - " +
                        sp.getMaDonViTinh() + " - " +
                        sp.getMaLoaiHang() + "\n"
                    );
                }
            }
        });
        
        btnTimMa.addActionListener(e -> {
            try {
                int maCanTim = Integer.parseInt(tfTimMa.getText().trim());
                taDanhSach.setText("");
                for (SanPhamDTO sp : bus.getDSSanPham()) {
                    if (sp.getMaSanPham() == maCanTim) {
                        taDanhSach.append(
                            sp.getMaSanPham() + " - " +
                            sp.getTenSanPham() + " - " +
                            sp.getXuatXu() + " - " +
                            new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()) + " - " +
                            sp.getGiaNhap() + " - " +
                            sp.getGiaXuat() + " - " +
                            sp.getHinhAnh() + " - " +
                            sp.getMaDonViTinh() + " - " +
                            sp.getMaLoaiHang() + "\n"
                        );
                        return;
                    }
                }
                taDanhSach.setText("Không tìm thấy mã sản phẩm: " + maCanTim);
            } catch (NumberFormatException ex) {
                taDanhSach.setText("Mã phải là số nguyên.");
            }
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void hienThiDanhSach() {
        taDanhSach.setText("");
        for (SanPhamDTO sp : bus.getDSSanPham()) {
            taDanhSach.append(
                sp.getMaSanPham() + " - " +
                sp.getTenSanPham() + " - " +
                sp.getXuatXu() + " - " +
                new SimpleDateFormat("yyyy-MM-dd").format(sp.getHanSuDung()) + " - " +
                sp.getGiaNhap() + " - " +
                sp.getGiaXuat() + " - " +
                sp.getHinhAnh() + " - " +
                sp.getMaDonViTinh() + " - " +
                sp.getMaLoaiHang() + "\n"
            );
        }
        try {
            tfMa.setText(String.valueOf(bus.layMaTiepTheo()));
        } catch (Exception e) {
            tfMa.setText("?");
        }
    }

    private void clearFieldsExceptMa() {
        tfTen.setText("");
        tfXuatXu.setText("");
        spnHanSD.setValue(new Date());
        tfGiaNhap.setText("");
        tfGiaXuat.setText("");
        tfHinhAnh.setText("");
        cboDonViTinh.select(0);
        cboLoaiHang.select(0);
    }

    private void selectComboBox(Choice cbo, String value) {
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItem(i).startsWith(value + " ")) {
                cbo.select(i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        new SanPhamGUI();
    }
}
