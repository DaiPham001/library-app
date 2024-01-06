package com.example.libraryapp.Model;

import java.io.Serializable;

public class Sach implements Serializable {
    private int masach;
    private String tensach;
    private double giathue;
    private String tenloai;
    private int maloai;

    public Sach() {
    }

    public Sach(String tensach, double giathue, int maloai) {
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
    }

    public Sach(int masach, String tensach, double giathue, int maloai, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.tenloai = tenloai;
        this.maloai = maloai;
    }

    public Sach(int masach, String tensach, double giathue, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.tenloai = tenloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public double getGiathue() {
        return giathue;
    }

    public void setGiathue(double giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
