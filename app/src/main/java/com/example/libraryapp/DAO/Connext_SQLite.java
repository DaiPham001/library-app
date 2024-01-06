package com.example.libraryapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.libraryapp.Database.DBHelper;

public class Connext_SQLite {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public Connext_SQLite(Context context) {
       dbHelper = new DBHelper(context);
       db = dbHelper.getWritableDatabase();
    }
}
