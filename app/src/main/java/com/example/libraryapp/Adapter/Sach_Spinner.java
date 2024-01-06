package com.example.libraryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libraryapp.Model.Sach;
import com.example.libraryapp.R;

import java.util.ArrayList;

public class Sach_Spinner extends ArrayAdapter<Sach> {

    private Context context;
    private ArrayList<Sach> list;
    private TextView tv_masach,tv_tensach;


    public Sach_Spinner(@NonNull Context context, ArrayList<Sach> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sach_item_spinner,null);
        }
        final Sach sach = list.get(position);
        if (sach != null){
            tv_masach = view.findViewById(R.id.tv_mas);
            tv_masach.setText(String.valueOf(sach.getMasach())+"-");

            tv_tensach = view.findViewById(R.id.tv_tes);
            tv_tensach.setText(sach.getTensach());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sach_item_spinner,null);
        }
        final Sach sach = list.get(position);
        if (sach != null){
            tv_masach = view.findViewById(R.id.tv_mas);
            tv_masach.setText(String.valueOf(sach.getMasach())+"-");

            tv_tensach = view.findViewById(R.id.tv_tes);
            tv_tensach.setText(sach.getTensach());
        }
        return view;
    }
}
