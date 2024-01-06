package com.example.libraryapp.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.R;
import com.example.libraryapp.Service.LoginService;
import com.example.libraryapp.Util.SendMail;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private EditText ed_name;//ed_pass;
    private TextInputEditText ed_pass;
    private Button btn_login;
    private CheckBox checkBox;
    private TextView tv_forgot, tv_account;
    private SendMail sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addcontroll();
        addevenst();
        intentFilter = new IntentFilter();
        intentFilter.addAction("checklogin");
    }

    // hàm ánh xạ view
    private void addcontroll() {
        btn_login = findViewById(R.id.btn_login);
        ed_name = findViewById(R.id.ed_name);
        ed_pass = findViewById(R.id.ed_pass);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_account = findViewById(R.id.tv_account);
        checkBox = findViewById(R.id.checkBox);
    }

    // hàm xử lý hoạt đông
    private void addevenst() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString().trim();
                String pass = ed_pass.getText().toString().trim();

                // hàm lưu mật khẩu
                Savepass();

                // truyền dữ liệu sang LoginService
                Intent intent = new Intent(LoginActivity.this, LoginService.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("pass", pass);
                intent.putExtras(bundle);
                startService(intent);
            }
        });

        // add hoạt động cho tv_account
        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();//
            }
        });

        // add hoat động tv_forgot
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgot();
            }
        });
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checklogin":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check == true) {
                        Toast.makeText(context, "thành công", Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();//
                    } else {
                        Toast.makeText(context, "thất bại", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void Savepass() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", ed_name.getText().toString().trim());
        editor.putString("pass", ed_pass.getText().toString().trim());
        editor.putBoolean("save1", checkBox.isChecked());// nếu checkbox đc chọn
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hiển thị mật khẩu đã đc lưu
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String pass = sharedPreferences.getString("pass", "");
        boolean save = sharedPreferences.getBoolean("save1", false);
        if (save == true) {
            ed_name.setText(name);
            ed_pass.setText(pass);
        }
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void showDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        // hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);// ko cho người dùng tắt
        alertDialog.show();

        // ánh xạ view
        EditText ed_passfg = view.findViewById(R.id.ed_pass_fg);
        Button btn_senpass = view.findViewById(R.id.btn_senpass);
        Button btn_close = view.findViewById(R.id.btn_close);
        /// hàm hủy
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        //// hàm gửi pass

        btn_senpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_passfg.setText(ed_name.getText().toString().trim());
                String pass = ed_passfg.getText().toString().trim();
                User_Dao user_dao = new User_Dao(getApplicationContext());
                String mk = user_dao.Forgot(pass);
                if (mk.equals("")){
                    Toast.makeText(LoginActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                }else {
//                    sendMail = new SendMail();
//                    sendMail.Send(LoginActivity.this,pass,"Lấy lại mật khẩu","Mật khẩu của bạn là: "+mk);
//                    alertDialog.dismiss();
                }
            }
        });
    }
}