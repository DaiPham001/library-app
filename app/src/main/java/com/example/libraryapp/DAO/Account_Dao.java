package com.example.libraryapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.libraryapp.Database.DBHelper;

public class Account_Dao {
    private DBHelper dbHelper;

    public Account_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // method create Login
    public boolean create(String name, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int role = 1;
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("pass", pass);
        values.put("role", 2);
        long check = sqLiteDatabase.insert("User", null, values);
        Log.e("loi",String.valueOf(check));
        if (check != -1) {// nếu thêm thành công
            return true;
        }
        return false;
    }

    // method checkname
    public boolean Checkname(String name){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM User WHERE name = ?",new String[]{name});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            cursor.getString(2);
            return true;
        }
        return false;
    }
}
