package com.example.libraryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;

import java.util.ArrayList;

public class User_Spinner extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> list;
    private TextView tv_ma,tv_name;
    public User_Spinner(@NonNull Context context, ArrayList<User> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_item_spinner,null);
        }
        final User user = list.get(position);
        if (user != null){
            tv_ma = view.findViewById(R.id.tv_mau);
            tv_ma.setText(user.getMatv()+"-");

            tv_name = view.findViewById(R.id.tv_tenu);
            tv_name.setText(user.getHoten());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_item_spinner,null);
        }
        final User user = list.get(position);
        if (user != null){
            tv_ma = view.findViewById(R.id.tv_mau);
            tv_ma.setText(user.getMatv()+"-");

            tv_name = view.findViewById(R.id.tv_tenu);
            tv_name.setText(user.getHoten());
        }
        return view;
    }
}
