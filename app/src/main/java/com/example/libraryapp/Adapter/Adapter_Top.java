package com.example.libraryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Model.Top;
import com.example.libraryapp.R;

import java.util.ArrayList;

public class Adapter_Top extends RecyclerView.Adapter<ViewHolder_top> {
    private Context context;
    private ArrayList<Top> list;

    public Adapter_Top(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder_top onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top,parent,false);
        return new ViewHolder_top(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_top holder, int position) {
        Top top = list.get(position);
        holder.tv_sach.setText("Sach: "+top.getTensach());
        holder.tv_soluong.setText("Số lượng: "+top.getSoluong());

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();// trả về số lượng item
        }
        return 0;
    }
}
