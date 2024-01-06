package com.example.libraryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libraryapp.Model.Loaisach;
import com.example.libraryapp.R;

import java.util.ArrayList;

public class Loaisach_Spinner extends ArrayAdapter<Loaisach> {

    private Context context;
    private ArrayList<Loaisach> list;
    private TextView tv_mals, tv_tenls;

    public Loaisach_Spinner(@NonNull Context context, ArrayList<Loaisach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }
        final Loaisach loaisach = list.get(position);
        if (loaisach != null) {
            tv_mals = view.findViewById(R.id.tv_mals);
            tv_mals.setText(loaisach.getMaloai()+"-");

            tv_tenls = view.findViewById(R.id.tv_tenls);
            tv_tenls.setText(loaisach.getTenloai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }
        final Loaisach loaisach = list.get(position);
        if (loaisach != null) {
            tv_mals = view.findViewById(R.id.tv_mals);
            tv_mals.setText(loaisach.getMaloai()+"-");

            tv_tenls = view.findViewById(R.id.tv_tenls);
            tv_tenls.setText(loaisach.getTenloai());
        }
        return view;
    }
}
