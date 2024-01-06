package com.example.libraryapp.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.example.libraryapp.DAO.Book_Dao;
import com.example.libraryapp.Model.Sach;

public class BookService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String tensach = bundle.getString("tensach");
        Double giathue = bundle.getDouble("giathue");
        int maloai = bundle.getInt("maloai");

        Sach sach = new Sach(tensach,giathue,maloai);
        if (tensach.equals("") || giathue.equals("")){
            Toast.makeText(this, "Yêu cầu nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
        }else {
            Book_Dao book_dao = new Book_Dao(this);
            boolean check = book_dao.AddBook(sach);
            if (check == true){
                Intent intentbr = new Intent();
                Bundle bundlebr = new Bundle();
                bundlebr.putBoolean("check",check);
                intentbr.setAction("checkbook");
                intentbr.putExtras(bundlebr);
                sendBroadcast(intentbr);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}