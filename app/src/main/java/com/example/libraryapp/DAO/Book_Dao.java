package com.example.libraryapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libraryapp.Database.DBHelper;
import com.example.libraryapp.Model.Sach;

import java.util.ArrayList;

public class Book_Dao {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Book_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // method insert
    public boolean AddBook(Sach sach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTensach());
        values.put("giathue", sach.getGiathue());
        values.put("maloai", sach.getMaloai());
        long check = sqLiteDatabase.insert("Sach", null, values);
        if (check > 1) {
            return true;
        }
        return false;
    }

    // method update
    public boolean UpdateBook(Sach sach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTensach());
        values.put("giathue", sach.getGiathue());
        values.put("maloai", sach.getMaloai());
        int check = sqLiteDatabase.update("Sach", values, "masach = ?", new String[]{String.valueOf(sach.getMaloai())});
        if (check >= 0) {
            return true;
        }
        return false;
    }

    // method delete
    public boolean DeleteBook(int maloai) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("Sach", "maloai = ?", new String[]{String.valueOf(maloai)});
        if (check > 0) {
            return true;
        }
        return false;
    }

    // method hiển thị danh sách
    public ArrayList<Sach> GetListBook() {
        ArrayList<Sach> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT s.masach, s.tensach, s.giathue, s.maloai, l.tenloai FROM Sach s, Loaisach l WHERE s.maloai = l.maloai", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2),cursor.getInt(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
