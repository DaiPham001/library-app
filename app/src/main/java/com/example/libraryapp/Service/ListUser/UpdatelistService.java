package com.example.libraryapp.Service.ListUser;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.Model.User;

public class UpdatelistService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String loginame = bundle.getString("loginname");
        String pass = bundle.getString("pass");
        String matv = bundle.getString("matv");
        Log.e("adapter",name+loginame+pass);


       SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String namecs = sharedPreferences.getString("name1", "");
        if (loginame.length() == namecs.length()) {
            Toast.makeText(this, "Tên đã tồn tại !", Toast.LENGTH_SHORT).show();
        } else if (name.equals("") || loginame.equals("") || pass.equals("")) {
            Toast.makeText(this, "Yêu cầu nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(matv, name, loginame, pass);
            User_Dao user_dao = new User_Dao(this);
            boolean check = user_dao.Update_User(user);
            if (check == true) {
               Intent intentbr = new Intent();
               Bundle bundlebr = new Bundle();
               bundlebr.putBoolean("check",check);
               intentbr.setAction("checkupdate");
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