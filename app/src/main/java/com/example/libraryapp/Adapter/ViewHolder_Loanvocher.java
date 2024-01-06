package com.example.libraryapp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;

public class ViewHolder_Loanvocher extends RecyclerView.ViewHolder {

    public CardView cv;
    public TextView tv_mapm, tv_user, tv_tensach, tv_giathue, tv_ngaymuon, tv_trasach;
    public ImageView img_delete;

    public ViewHolder_Loanvocher(@NonNull View view) {
        super(view);
        tv_mapm = view.findViewById(R.id.tv_mapm);
        cv = view.findViewById(R.id.cv);
        tv_user = view.findViewById(R.id.tv_userL);
        tv_tensach = view.findViewById(R.id.tv_tensachL);
        tv_giathue = view.findViewById(R.id.tv_tienthuel);
        tv_ngaymuon = view.findViewById(R.id.tv_ngaymuon);
        tv_trasach = view.findViewById(R.id.tv_trasach);
        img_delete =  view.findViewById(R.id.img_deleteloan);
    }
}
