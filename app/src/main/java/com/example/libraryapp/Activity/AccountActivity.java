package com.example.libraryapp.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.R;
import com.example.libraryapp.Service.AccountService;

public class AccountActivity extends AppCompatActivity {
    private EditText ed_name,ed_pass,ed_pass1;
    private Button btn_create;
    private ImageView img_back;
    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        addcontroll();
        addevenst();
        // đăng ký broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction("account");
    }

    // method ánh xạ view
    private void addcontroll() {
        ed_name = findViewById(R.id.ed_name_a);
        ed_pass = findViewById(R.id.ed_pass_a);
        ed_pass1 = findViewById(R.id.ed_pass_a1);
        btn_create = findViewById(R.id.btn_create);
        img_back = findViewById(R.id.img_back);
    }

    // method xử lý sự kiện
    private void addevenst() {
        // add sự liện img_back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        // add sự kiện btn_create
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString().trim();
                String pass = ed_pass.getText().toString().trim();
                String pass1 = ed_pass1.getText().toString().trim();

                // đóng gói và truyền dữ liệu sang AccountService
                Intent intent = new Intent(AccountActivity.this, AccountService.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("pass",pass);
                bundle.putString("pass1",pass1);
                intent.putExtras(bundle);
                startService(intent);
            }
        });
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "account":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check1");
                    boolean checklogin = bundle.getBoolean("checklogin1");
                    Log.e("loi",String.valueOf(check));
                    //if (checklogin == true){// nếu tài khoản không tồn tại
                        if (check == true){
                            intent = new Intent(AccountActivity.this,LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(context, "Tạo thành công", Toast.LENGTH_SHORT).show();
                            finish();//
                        }else {
                            Toast.makeText(context, "không thành công", Toast.LENGTH_SHORT).show();
                        }
                   // }else {
                       // Toast.makeText(AccountActivity.this, "Tên đã tồn tại !", Toast.LENGTH_SHORT).show();
                   // }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,intentFilter);
    }
}