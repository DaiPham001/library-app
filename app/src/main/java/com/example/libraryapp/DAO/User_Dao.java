package com.example.libraryapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libraryapp.Database.DBHelper;
import com.example.libraryapp.Model.User;

import java.util.ArrayList;

public class User_Dao {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public User_Dao(Context context) {
        dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    // phương thức insert
    public boolean addUser(User user) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", user.getMatv());
        values.put("hoten", user.getHoten());
        values.put("name", user.getName());
        values.put("pass", user.getPass());
        long check = sqLiteDatabase.insert("User", null, values);
        if (check > 1) {
            return true;
        }
        return false;
    }

    // method update
    public boolean Update_User(User user) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", user.getMatv());
        values.put("hoten",user.getHoten());
        values.put("name",user.getName());
        values.put("pass", user.getPass());
        int check = sqLiteDatabase.update("User", values, "matv = ?", new String[]{String.valueOf(user.getMatv())});
        if (check >= 0) {// nếu có dòng đc sửa
            return true;
        }
        return false;
    }

    // method delete
    public boolean Delete_User(String matv) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("User", "matv = ?", new String[]{matv});
        if (check > 0) {// nếu xóa thành công
            return true;
        }
        return false;
    }

    // method hiển thị danh sách User
    public ArrayList<User> getList_User() {
        ArrayList<User> list = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM User", null);
        if (cursor.getCount() >= 0) {
            cursor.moveToFirst();
            do {
                list.add(new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Check login
    public boolean CheckLogin(String name, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM User WHERE name = ? AND pass = ?", new String[]{name, pass});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matv",cursor.getString(0));
            editor.putString("name1", cursor.getString(2));
            editor.putString("hoten",cursor.getString(1));
            editor.putString("pass", cursor.getString(3));
            editor.putInt("role", cursor.getInt(5));
            editor.apply();
            return true;
        }
        return false;
    }

    // forgot
    public String Forgot(String name) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pass FROM User WHERE name =?", new String[]{name});
        if (cursor.getCount() > 0) {// nếu tìm thấy tài khoản
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return "";
    }
}
