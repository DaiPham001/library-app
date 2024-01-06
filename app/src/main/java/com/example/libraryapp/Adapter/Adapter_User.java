package com.example.libraryapp.Adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;
import com.example.libraryapp.Service.ListUser.UpdatelistService;

import java.util.ArrayList;

public class Adapter_User extends RecyclerView.Adapter<ViewHolder_User> {
    private ArrayList<User> list;
    private Context context;
    private User_Dao user_dao;
    private SharedPreferences sharedPreferences;
    private String matvcs, namecs;
    private AlertDialog alertDialog;
    private IntentFilter filter;

    public Adapter_User(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
        user_dao = new User_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new ViewHolder_User(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_User holder, int position) {
        User user = list.get(position);
        holder.tv_matv.setText(user.getMatv());
        holder.tv_hoten.setText(user.getHoten());
        holder.tv_name.setText(user.getName());
        holder.tv_pass.setText(user.getPass());

        filter = new IntentFilter();
        // đăng ký broadcast
        registerReceiver();
        /*sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        matvcs = sharedPreferences.getString("matv", "");
        namecs = sharedPreferences.getString("name1", "");*/

        // add sự kiện kiện img_update
        holder.img_upadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogUpdate(user);
            }
        });

        // add sự kiện img_delete
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogDelte(user.getMatv(), user.getName());
            }
        });
    }

    // medthor delete
    private void ShowDialogDelte(String matv, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Xóa \"" + name + "\"?");
        builder.setIcon(R.drawable.ic_warning);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = user_dao.Delete_User(matv);
                if (check == true) {
                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    LoadList();
                } else {
                    Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // medthor update
    private void ShowDialogUpdate(User user) {
        // khởi tao dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_updateusser, null);
        builder.setView(view);

        // hiển thị dialog
        alertDialog = builder.create();
        alertDialog.setCancelable(false);// không cho người dùng thoát
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// bo góc
        alertDialog.show();

        // ánh xạ view dialog
        EditText ed_name = view.findViewById(R.id.ed_name_u);
        EditText ed_loginname = view.findViewById(R.id.ed_loginname_u);
        EditText ed_pass = view.findViewById(R.id.ed_pass_u);
        Button btn_update = view.findViewById(R.id.btn_update_u);
        Button btn_cancel = view.findViewById(R.id.btn_cancel_u);

        // đưa dữ liệu lên ed dialog
        ed_name.setText(user.getHoten());
        ed_loginname.setText(user.getName());
        ed_pass.setText(user.getPass());
        String matv = user.getMatv();

        // add hoạt động cho btn_cancel
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // add hoat động cho btn_update
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_name.getText().toString().trim();
                String loginanme = ed_loginname.getText().toString().trim();
                String pass = ed_pass.getText().toString().trim();
                Intent intent = new Intent(context, UpdatelistService.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("loginname", loginanme);
                bundle.putString("pass", pass);
                bundle.putString("matv", matv);
                intent.putExtras(bundle);
                context.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size(); // trả về số lương item
        }
        return 0;
    }

    // load lại list
    private void LoadList() {
        list.clear();
        list = user_dao.getList_User();
        notifyDataSetChanged();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checkupdate":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check == true) {
                        Toast.makeText(context, "Update thành công !", Toast.LENGTH_SHORT).show();
                        LoadList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update thất bại !", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };


    public void registerReceiver() {

        // Thêm các action mà bạn muốn lắng nghe
        filter.addAction("checkupdate");
        // if (broadcastReceiver != null){
        context.registerReceiver(broadcastReceiver, filter);
        //}

    }



}
