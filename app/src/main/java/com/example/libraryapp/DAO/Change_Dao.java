package com.example.libraryapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libraryapp.Database.DBHelper;
import com.example.libraryapp.Model.User;

import java.util.ArrayList;

public class Change_Dao {
    private DBHelper dbHelper;

    public Change_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean UpdateChange(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pass", user.getPass());
        int check = sqLiteDatabase.update("User", values, "pass = ?", new String[]{String.valueOf(user.getPass())});
        if (check >= 0) {// nếu >=0  nghĩa là có dong đc sửa
            return true;
        }
        return false;
    }

    public int updatepass(User user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("pass",user.getPass());
        return sqLiteDatabase.update("User",values,"matv = ?",new String[]{String.valueOf(user.getMatv())});
    }
    @SuppressLint("Range")
    public ArrayList<User> list(String sql,String...selectionArgs){
        ArrayList<User> list = new ArrayList<>();
        sql = "SELECT * FROM User";
        selectionArgs=null;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,selectionArgs);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new User(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }


    // get tất cả data
    public ArrayList<User> getall(){
        String sql = "SELECT * FROM User";
        return list(sql);
    }

    // get data theo id
    public User getid(String id){
        String sql = "SELECT * FROM User WHERE matv = ?";
        ArrayList<User> list = list(id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Handle the case when the list is empty
            return null; // or throw an exception, return a default user, etc.
        }
    }
}
