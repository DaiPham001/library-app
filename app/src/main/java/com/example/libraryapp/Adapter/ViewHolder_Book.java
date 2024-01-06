package com.example.libraryapp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;

public class ViewHolder_Book extends RecyclerView.ViewHolder {
    public TextView tv_masach,tv_tensach,tv_giathue,tv_loaisach;
    public ImageView img_delete;
    public ViewHolder_Book(@NonNull View itemView) {
        super(itemView);
       tv_giathue = itemView.findViewById(R.id.tv_guathue);
       tv_loaisach = itemView.findViewById(R.id.tv_loaisach);
       tv_tensach = itemView.findViewById(R.id.tv_tensach);
       tv_masach = itemView.findViewById(R.id.tv_masach);
       img_delete =itemView.findViewById(R.id.img_deletebook);
    }
}
