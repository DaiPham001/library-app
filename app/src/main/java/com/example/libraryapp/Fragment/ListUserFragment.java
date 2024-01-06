package com.example.libraryapp.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Adapter.Adapter_User;
import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;
import com.example.libraryapp.Service.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListUserFragment extends Fragment {

    private IntentFilter intentFilter;
    private User_Dao user_dao;
    private RecyclerView rcv_user;
    private FloatingActionButton fl_user;
    private AlertDialog alertDialog;
    private BroadcastReceiver receiver;
    private Adapter_User adapter_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_user, container, false);
        addcontroll(view);
        addevenst();
        return view;
    }

    // method xử lý sự kiện
    private void addevenst() {

        fl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(R.layout.dialog_user, null);
                builder.setView(v);

                // hiển thị dialog
                alertDialog = builder.create();
                alertDialog.setCancelable(false);// ko cho người dùng thoát
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// bo góc
                alertDialog.show();

                // ánh xạ view dialog
                EditText ed_matv = v.findViewById(R.id.ed_addmatv);
                EditText ed_name = v.findViewById(R.id.ed_addname);
                EditText ed_loginname = v.findViewById(R.id.ed_loginname);
                EditText ed_pass = v.findViewById(R.id.ed_addpass);
                Button btn_add = v.findViewById(R.id.btn_adduser);
                Button btn_cancel = v.findViewById(R.id.btn_cancel);

                // add sự kiện cho btn_cancel
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                // add sự kiện cho btn_add
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String matv = ed_matv.getText().toString().trim();
                        String name = ed_name.getText().toString().trim();
                        String loginname = ed_loginname.getText().toString().trim();
                        String pass = ed_pass.getText().toString().trim();

                        // đóng gói và truyền dữ liệu sang UserService
                        Intent intent = new Intent(getActivity(), UserService.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("loginname", loginname);
                        bundle.putString("pass", pass);
                        bundle.putString("matv", matv);
                        intent.putExtras(bundle);
                        getActivity().startService(intent);

                    }
                });
            }
        });
    }

    // method ánh xạ view
    private void addcontroll(View view) {
        rcv_user = view.findViewById(R.id.rcv_user);
        fl_user = view.findViewById(R.id.fl_user);
        Loadlist();

        // đăng ký broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction("checkuser");
    }

    public void Loadlist() {
        user_dao = new User_Dao(getContext());
        ArrayList<User> list = user_dao.getList_User();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_user.setLayoutManager(linearLayoutManager);
        adapter_user = new Adapter_User(list, getContext());
        rcv_user.setAdapter(adapter_user);

        // dòng kẻ của rcv
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_user.addItemDecoration(itemDecoration);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checkuser":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check == true) {
                        Loadlist();
                        alertDialog.dismiss();
                        Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    // đăng ký
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }

    // hủy đăng ký
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
       // getActivity().unregisterReceiver(receiver);
    }
}