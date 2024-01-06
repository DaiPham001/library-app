package com.example.libraryapp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;

public class ViewHolder_User extends RecyclerView.ViewHolder {
    public TextView tv_matv,tv_hoten,tv_name,tv_pass;
    public ImageView img_upadte,img_delete;
    public ViewHolder_User(@NonNull View view) {
        super(view);
        addcontroll(view);
    }

    // hàm ánh xạ view
    private void addcontroll(View view) {
        tv_name = view.findViewById(R.id.tv_name);
        tv_hoten = view.findViewById(R.id.tv_hoten);
        tv_matv = view.findViewById(R.id.tv_matv);
        tv_pass = view.findViewById(R.id.tv_pass);
        img_delete = view.findViewById(R.id.img_deleteuser);
        img_upadte = view.findViewById(R.id.img_updateuser);
    }
}
