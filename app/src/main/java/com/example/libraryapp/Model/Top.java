package com.example.libraryapp.Model;

import java.io.Serializable;

public class Top implements Serializable {
    private String tensach;
    private int soluong;
    private int doanhthu;

    public Top(String tensach, int soluong) {
        this.tensach = tensach;
        this.soluong = soluong;
    }

    public Top(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    public int getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
