package com.example.libraryapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Adapter.Adapter_Top;
import com.example.libraryapp.DAO.Loanvoucher_Dao;
import com.example.libraryapp.Model.Top;
import com.example.libraryapp.R;

import java.util.ArrayList;


public class TopFragment extends Fragment {

    private RecyclerView rcv_top;
    private ArrayList<Top> list;
    private Loanvoucher_Dao loanvoucher_dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_top, container, false);
        rcv_top = view.findViewById(R.id.rcv_top);
        addevenst();
        return view;
    }

    // method sử lý sự kiện
    private void addevenst() {
        Loanlist();
    }


    private void Loanlist() {
        list = new ArrayList<>();
        loanvoucher_dao = new Loanvoucher_Dao(getContext());
        list = loanvoucher_dao.GetTop();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_top.setLayoutManager(linearLayoutManager);
        Adapter_Top adapter_top = new Adapter_Top(getContext(),list);
        rcv_top.setAdapter(adapter_top);

        // dòng kẻ của rcv
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_top.addItemDecoration(itemDecoration);

    }
}