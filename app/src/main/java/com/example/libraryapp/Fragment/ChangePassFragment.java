package com.example.libraryapp.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.libraryapp.DAO.Change_Dao;
import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePassFragment extends Fragment {

    private TextInputEditText ed_passold, ed_passnew, ed_passchange;
    private Button btn_change, btn_huy;
    private IntentFilter intentFilter;
    private Change_Dao change_dao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_change_pass, container, false);
        addcontroll(view);
        addevenst();
        return view;
    }

    // method ánh xạ view
    private void addcontroll(View view) {
        ed_passchange = view.findViewById(R.id.ed_pass_change);
        ed_passnew = view.findViewById(R.id.ed_pass_new);
        ed_passold = view.findViewById(R.id.ed_pass_old);
        btn_change = view.findViewById(R.id.btn_change);
        btn_huy = view.findViewById(R.id.btn_huy);

        // đăng ký broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction("checkchange");

        // khởi tạo changedao
        change_dao = new Change_Dao(getContext());
    }

    // method xứ lý sự kiện
    private void addevenst() {
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_passchange.setText("");
                ed_passnew.setText("");
                ed_passold.setText("");
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passold = ed_passold.getText().toString().trim();
                String passnew = ed_passnew.getText().toString().trim();
                String passchange = ed_passchange.getText().toString().trim();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User",MODE_PRIVATE);
                String pass = sharedPreferences.getString("pass","");

                if (validate() > 0){
                    User user = change_dao.getid(pass);
                    user.setPass(ed_passnew.getText().toString().trim());

                    if (change_dao.updatepass(user)>0){// nếu thay pas thành công
                        ed_passold.setText("");
                        ed_passnew.setText("");
                        ed_passchange.setText("");
                        Toast.makeText(getContext(), "Thay đổi pass thành công !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thay đổi pass thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
               /*Intent intent = new Intent(getActivity(),ChangeService.class);
                Bundle bundle = new Bundle();
                bundle.putString("passold",passold);
                bundle.putString("passnew",passnew);
                bundle.putString("passchange",passchange);
                intent.putExtras(bundle);
                getActivity().startService(intent);*/
            }
        });
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checkchange":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("change");
                    //boolean checkpass = bundle.getBoolean("checkpass");
                    if (check == true) {
                        Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    public int validate() {
        int check = 1;
        if (ed_passold.equals("") || ed_passchange.equals("") || ed_passnew.equals("")) {
            Toast.makeText(getContext(), "Yêu cầu nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String passold = ed_passold.getText().toString().trim();
            String passnew = ed_passnew.getText().toString().trim();
            String passchange = ed_passchange.getText().toString().trim();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
            String pass = sharedPreferences.getString("pass", "");
            if (!passold.equals(pass)) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai !", Toast.LENGTH_SHORT).show();
                check = -1;
            } else if (!passnew.equals(passchange)){
                Toast.makeText(getContext(), "Mật khẩu không giống nhau !", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}