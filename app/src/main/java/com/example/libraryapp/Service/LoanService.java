package com.example.libraryapp.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.libraryapp.DAO.Loanvoucher_Dao;
import com.example.libraryapp.Model.Phieumuon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanService extends Service {

    private SimpleDateFormat sdfD = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        int masach = bundle.getInt("masach");
        String date = bundle.getString("date");
        Double giathue = bundle.getDouble("giathue");
        int trasach = bundle.getInt("trasach");

        Log.e("Loan","lỗi: "+name + masach+date+giathue+trasach);
        Phieumuon phieumuon = new Phieumuon(name, masach, new Date(date), giathue, trasach);
        if (name.equals("")) {
            Toast.makeText(this, "Yêu cầu nhập tên !", Toast.LENGTH_SHORT).show();
        } else {
            Loanvoucher_Dao loanvoucher_dao = new Loanvoucher_Dao(this);
            boolean check = loanvoucher_dao.AddLoan(phieumuon);
            if (check == true) {
                Intent intentbr = new Intent();
                Bundle bundlebr = new Bundle();
                bundlebr.putBoolean("check",check);
                intentbr.setAction("checkaddloan");
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