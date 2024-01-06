package com.example.libraryapp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.libraryapp.DAO.Change_Dao;
import com.example.libraryapp.Model.User;

public class ChangeService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String passold = bundle.getString("passold");
        String passnew = bundle.getString("passnew");
        String passchange = bundle.getString("passchange");

        SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        String pass = sharedPreferences.getString("pass","");
        Log.e("pass",pass);

        User user = new User(passnew);
        // gọi Dao
        Change_Dao change_dao = new Change_Dao(this);

        boolean check = change_dao.UpdateChange(user);
       // boolean checkpass = change_dao.Ckeckpass(passnew);

        if (passold.equals("") || passnew.equals("") || passchange.equals("")){
            Toast.makeText(this, "Nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
        }else if (!passnew.equals(passchange)){
            Toast.makeText(this, "Mật khẩu không giống nhau !", Toast.LENGTH_SHORT).show();
        }else if (!passold.equals(pass)){
            Toast.makeText(this, "Mật khẩu cũ không giống nhau !", Toast.LENGTH_SHORT).show();
        }else if (passold.equals(pass)){
            //if (check == true){
                Intent intentbr = new Intent();
                Bundle bundlebr = new Bundle();
                bundlebr.putBoolean("change",check);
                //bundlebr.putBoolean("checkpass",checkpass);
                intentbr.setAction("checkchange");
                intentbr.putExtras(bundlebr);
                sendBroadcast(intentbr);
           // }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // metod lấy pass
    public void Getpass(){

    }
}