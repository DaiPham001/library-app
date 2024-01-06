package com.example.libraryapp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.libraryapp.DAO.Account_Dao;

public class AccountService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // nhân dũ liệu từ AccountActivity
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String pass = bundle.getString("pass");
        String pass1 = bundle.getString("pass1");

        // lấy tên người dùng đã tồn tại
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String ten = sharedPreferences.getString("name1", "");
        Log.e("ten",ten);

        Account_Dao account_dao = new Account_Dao(this);
        boolean check = account_dao.create(name, pass);
        ///boolean checklogin = account_dao.Checkname(name);

        if (!pass.equals(pass1)) {// nếu không giống nhau
            Toast.makeText(this, "Mật khẩu không giống nhau !", Toast.LENGTH_SHORT).show();
        } else if (name.length() == ten.length()) {
            Toast.makeText(this, "Tên đã tồn tại !", Toast.LENGTH_SHORT).show();
        } else if (name.equals(null) || pass.equals(null) || pass1.equals(null)) {
            Toast.makeText(this, "Nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
        } else if (pass.equals(pass1) && name.length() != ten.length()) {
            if (check == true) {
                // truyền dữ liệu lại cho AccountActiviti
                Intent intentbr = new Intent();
                Bundle bundlebr = new Bundle();
                bundlebr.putBoolean("check1", check);
                //bundlebr.putBoolean("checklogin1",checklogin);
                intentbr.setAction("account");
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