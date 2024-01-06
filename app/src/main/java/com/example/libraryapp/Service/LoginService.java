package com.example.libraryapp.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.example.libraryapp.DAO.User_Dao;

public class LoginService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // nhận dữ liệu từ LoginActivity
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String pass = bundle.getString("pass");

        // gọi User_Dao
        User_Dao user_dao = new User_Dao(this);
        boolean check = user_dao.CheckLogin(name,pass);

        // truyền dữ liệu lại cho LoginActivity bằng Broadcast
        Intent intentbr = new Intent();
        Bundle bundlebr = new Bundle();
        bundlebr.putBoolean("check",check);
        intentbr.setAction("checklogin");
        intentbr.putExtras(bundlebr);
        sendBroadcast(intentbr);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}