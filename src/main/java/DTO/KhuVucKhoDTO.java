package DTO;

public class KhuVucKhoDTO {
    private int maKhuVucKho;
    private String tenKhuVucKho;

    public KhuVucKhoDTO() {
    }

    public KhuVucKhoDTO(int maKhuVucKho, String tenKhuVucKho) {
        this.maKhuVucKho = maKhuVucKho;
        this.tenKhuVucKho = tenKhuVucKho;
    }

    public int getMaKhuVucKho() {
        return maKhuVucKho;
    }

    public void setMaKhuVucKho(int maKhuVucKho) {
        this.maKhuVucKho = maKhuVucKho;
    }

    public String getTenKhuVucKho() {
        return tenKhuVucKho;
    }

    public void setTenKhuVucKho(String tenKhuVucKho) {
        this.tenKhuVucKho = tenKhuVucKho;
    }

    @Override
    public String toString() {
        return "KhuVucKho{" + "maKhuVucKho=" + maKhuVucKho + ", tenKhuVucKho=" + tenKhuVucKho + '}';
    }
}