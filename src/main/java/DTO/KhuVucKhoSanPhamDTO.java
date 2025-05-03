package DTO;

public class KhuVucKhoSanPhamDTO {
    private int maSanPham;
    private int maKhuVucKho;

    public KhuVucKhoSanPhamDTO() {}

    public KhuVucKhoSanPhamDTO(int maSanPham, int maKhuVucKho) {
        this.maSanPham = maSanPham;
        this.maKhuVucKho = maKhuVucKho;
    }

    public int getMaSanPham() {
        return maSanPham;
    }
    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }
    public int getMaKhuVucKho() {
        return maKhuVucKho;
    }
    public void setMaKhuVucKho(int maKhuVucKho) {
        this.maKhuVucKho = maKhuVucKho;
    }

    @Override
    public String toString() {
        return "KhuVucKhoSanPham{" + "maSanPham:" + maSanPham + ", maKhuVucKho:" + maKhuVucKho + '}';
    }
}
