package com.example.libraryapp.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;

public class ViewHolder_top extends RecyclerView.ViewHolder {
    public TextView tv_sach,tv_soluong;
    public ViewHolder_top(@NonNull View v) {
        super(v);
        tv_sach = v.findViewById(R.id.tv_sach);
        tv_soluong = v.findViewById(R.id.tv_soluong);
    }
}
