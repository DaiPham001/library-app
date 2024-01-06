package com.example.libraryapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.libraryapp.Database.DBHelper;
import com.example.libraryapp.Model.Phieumuon;
import com.example.libraryapp.Model.Top;

import java.util.ArrayList;
import java.util.Date;

public class Loanvoucher_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SharedPreferences sharedPreferences;

    public Loanvoucher_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("trasach", Context.MODE_PRIVATE);
    }

    // methor insert
    public boolean AddLoan(Phieumuon phieumuon) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", phieumuon.getMatv());
        values.put("masach", phieumuon.getMasach());
        values.put("ngay", sdf.format(phieumuon.getNgay()));
        values.put("tienthue", phieumuon.getTienthue());
        values.put("trasach", phieumuon.getTrasach());
        long check = sqLiteDatabase.insert("Phieumuon", null, values);
        if (check > 1) {
            return true;
        }
        return false;
    }

    //method update
    public boolean UpdateLoan(Phieumuon phieumuon) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mapm", phieumuon.getMapm());
        values.put("matv", phieumuon.getMatv());
        values.put("masach", phieumuon.getMasach());
        values.put("tienthue", phieumuon.getTienthue());
        values.put("trasach", phieumuon.getTrasach());
        values.put("ngay", sdf.format(phieumuon.getNgay()));
        int check = sqLiteDatabase.update("Phieumuon", values, "mapm = ?", new String[]{String.valueOf(phieumuon.getMapm())});
        if (check >= 0) {
            return true;
        }
        return false;
    }

    // method delete
    public boolean DeleteLoan(int mapm) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("phieumuon", "mapm = ?", new String[]{String.valueOf(mapm)});
        if (check > 0) {
            return true;
        }
        return false;
    }

    // method hiển thị list
    public ArrayList<Phieumuon> GetListLoan() {
        ArrayList<Phieumuon> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Phieumuon.mapm, User.hoten, Sach.tensach,Phieumuon.tienthue,Phieumuon.ngay, Phieumuon.trasach FROM Phieumuon" +
                " INNER JOIN User ON Phieumuon.matv = User.matv " +
                "INNER JOIN Sach ON Phieumuon.masach = Sach.masach", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int mapm = cursor.getInt(0);
                String hoten = cursor.getString(1);
                String tensach = cursor.getString(2);
                double tienthue = cursor.getDouble(3);
                String ngay = cursor.getString(4);
                int trasach = cursor.getInt(5);
                list.add(new Phieumuon(mapm, hoten, tensach, new Date(ngay), tienthue, trasach));
            } while (cursor.moveToNext());
        }
        cursor.close();// đóng con trỏ khi đã giải phóng tài nguyên
        return list;
    }

    public ArrayList<Top> GetTop() {
        ArrayList<Top> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Sach.tensach, COUNT(Phieumuon.masach) AS soluong FROM Phieumuon" +
                " INNER JOIN Sach ON Phieumuon.masach = Sach.masach" +
                " GROUP BY Phieumuon.masach" +
                " ORDER BY soluong DESC" +
                " LIMIT 10", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Top(cursor.getString(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public int Getdoanhthu(String tungay, String denngay) {
        ArrayList<Integer> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ngay, SUM(tienthue) AS doanhthu FROM Phieumuon WHERE ngay BETWEEN ? AND ? GROUP BY ngay", new String[]{tungay, denngay});
        if (cursor.getCount() >0){
            cursor.moveToFirst();
            do {
                try {
                    list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhthu"))));
                }catch (Exception e){
                    list.add(0);
                }
            }while (cursor.moveToNext());
        }
        return list.get(0);
    }
}
