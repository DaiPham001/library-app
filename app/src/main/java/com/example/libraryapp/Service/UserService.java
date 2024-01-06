package com.example.libraryapp.Service;

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

public class UserService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String matv = bundle.getString("matv");
        String name = bundle.getString("name");
        String loginname = bundle.getString("loginname");
        String pass = bundle.getString("pass");

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String matvcs = sharedPreferences.getString("matv", "");
        String namecs = sharedPreferences.getString("name1", "");
        if (matv.length() == matvcs.length()) {
            Toast.makeText(this, "Mã đã có !", Toast.LENGTH_SHORT).show();
        } else if (loginname.length() == namecs.length()) {
            Toast.makeText(this, "Tên đã tồn tại !", Toast.LENGTH_SHORT).show();
        } else if (name.equals("") || loginname.equals("null") || pass.equals("") || matv.equals("")) {
            Toast.makeText(this, "Không được bỏ trống !", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(matv, name, loginname, pass);
            User_Dao user_dao = new User_Dao(this);
            boolean check = user_dao.addUser(user);
            Log.e("lõi", String.valueOf(check));
            if (check == true) {
                Intent intentbr = new Intent();
                Bundle bundlebr = new Bundle();
                bundlebr.putBoolean("check",check);
                intentbr.setAction("checkuser");
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