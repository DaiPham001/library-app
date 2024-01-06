package com.example.libraryapp.Model;

import java.io.Serializable;
import java.util.Date;

public class Phieumuon implements Serializable {
    private int mapm;
    private String tensach;
    private String tentv;
    private String matv;
    private int masach;
    private double tienthue;
    private int trasach;
    private Date ngay;

    public Phieumuon() {
    }

    public Phieumuon(String matv, int masach, Date ngay, double tienthue, int trasach) {
        this.matv = matv;
        this.masach = masach;
        this.tienthue = tienthue;
        this.trasach = trasach;
        this.ngay = ngay;
    }

    public Phieumuon(int mapm, String matv, int masach, Date ngay, double tienthue, int trasach) {
        this.mapm = mapm;
        this.matv = matv;
        this.masach = masach;
        this.tienthue = tienthue;
        this.trasach = trasach;
        this.ngay = ngay;
    }

    public Phieumuon(int mapm, String tentv, String tensach, Date ngay, double tienthue, int trasach) {
        this.mapm = mapm;
        this.tensach = tensach;
        this.tentv = tentv;
        this.tienthue = tienthue;
        this.trasach = trasach;
        this.ngay = ngay;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getMatv() {
        return matv;
    }

    public void setMatv(String matv) {
        this.matv = matv;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public double getTienthue() {
        return tienthue;
    }

    public void setTienthue(double tienthue) {
        this.tienthue = tienthue;
    }

    public int getTrasach() {
        return trasach;
    }

    public void setTrasach(int trasach) {
        this.trasach = trasach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
