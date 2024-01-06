package com.example.libraryapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libraryapp.Database.DBHelper;
import com.example.libraryapp.Model.Loaisach;

import java.util.ArrayList;

public class KindBook_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public KindBook_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // method insert
    public boolean AddKindBook(Loaisach loaisach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai", loaisach.getMaloai());
        values.put("tenloai", loaisach.getTenloai());
        long check = sqLiteDatabase.insert("Loaisach", null, values);
        if (check > 1) {// nếu thêm thành công
            return true;
        }
        return false;
    }

    // method update
    public boolean UpdateKindBook(Loaisach loaisach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maloai", loaisach.getMaloai());
        values.put("tenloai", loaisach.getTenloai());
        int check = sqLiteDatabase.update("Loaisach", values, "maloai = ?", new String[]{String.valueOf(loaisach.getMaloai())});
        if (check >= 0) {// nếu có dòng đc sửa
            return true;
        }
        return false;
    }

    // method delete
    public boolean DeleteKindBook(Loaisach loaisach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("Loaisach", "maloai = ?", new String[]{String.valueOf(loaisach.getMaloai())});
        if (check > 0) {// nếu xóa thành công
            return true;
        }
        return false;
    }

    // method hiển thị danh sách
    public ArrayList<Loaisach> GetList_KindBook() {
        ArrayList<Loaisach> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Loaisach", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Loaisach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
