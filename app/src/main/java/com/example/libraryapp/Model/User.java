package com.example.libraryapp.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String matv;
    private String hoten;
    private String name;
    private String pass;
    private String namsinh;

    public User( String pass) {
        this.pass = pass;
    }

    public User(String matv, String hoten, String name, String pass) {
        this.matv = matv;
        this.hoten = hoten;
        this.name = name;
        this.pass = pass;
        this.namsinh = namsinh;
    }

    public User(String hoten, String name, String pass) {
        this.hoten = hoten;
        this.name = name;
        this.pass = pass;
    }

    public String getMatv() {
        return matv;
    }
    public void setMatv(String matv) {
        this.matv = matv;
    }
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
}
