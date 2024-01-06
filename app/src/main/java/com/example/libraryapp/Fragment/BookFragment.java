package com.example.libraryapp.Fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Adapter.Adapter_Book;
import com.example.libraryapp.Adapter.Loaisach_Spinner;
import com.example.libraryapp.DAO.Book_Dao;
import com.example.libraryapp.DAO.KindBook_Dao;
import com.example.libraryapp.Model.Loaisach;
import com.example.libraryapp.Model.Sach;
import com.example.libraryapp.R;
import com.example.libraryapp.Service.BookService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BookFragment extends Fragment {

    private Dialog dialog;
    private Button btn_cancel, btn_addbook;
    private EditText ed_masach, ed_tensach, ed_giathue;
    private Spinner spinner;

    private RecyclerView rcv_book;
    private FloatingActionButton fl_add;
    private Book_Dao book_dao;


    private Loaisach_Spinner loaisach_spinnerAdapter;
    private ArrayList<Loaisach> listls;
    private KindBook_Dao kindBook_dao;
    private Sach sach;
    private int maloaisach, position;

    private   IntentFilter intentFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_book, container, false);
        addcontroll(view);
        addevenst();
        return view;
    }

    // hàm ánh xạ view
    private void addcontroll(View view) {

        intentFilter = new IntentFilter();
        intentFilter.addAction("checkbook");
        rcv_book = view.findViewById(R.id.rcv_book);
        fl_add = view.findViewById(R.id.float_addbook);
        book_dao = new Book_Dao(getContext());
        LoadLisst();
    }

    private void LoadLisst() {
        ArrayList<Sach> list = book_dao.GetListBook();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_book.setLayoutManager(linearLayoutManager);
        Adapter_Book adapter_book = new Adapter_Book(list, getContext());
        rcv_book.setAdapter(adapter_book);
        // dòng kẻ của rcv
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_book.addItemDecoration(itemDecoration);
    }

    // hàm sử lý sự kiện
    private void addevenst() {
        fl_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog(getActivity(), 0);
            }

        });
    }

    private void OpenDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_addbook);
        btn_cancel = dialog.findViewById(R.id.btn_cancelbook);
        btn_addbook = dialog.findViewById(R.id.btn_addbook);
        ed_masach = dialog.findViewById(R.id.ed_addmasachbook);
        ed_tensach = dialog.findViewById(R.id.ed_addtensachbook);
        ed_giathue = dialog.findViewById(R.id.ed_addgiathuebook);
        spinner = dialog.findViewById(R.id.sp_tenloai);

        // add dữ liệu vào spinner
        kindBook_dao = new KindBook_Dao(getContext());
        listls = new ArrayList<Loaisach>();
        listls = (ArrayList<Loaisach>) kindBook_dao.GetList_KindBook();

        loaisach_spinnerAdapter = new Loaisach_Spinner(context, listls);
        spinner.setAdapter(loaisach_spinnerAdapter);

        // add hoạt động cho spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maloaisach = listls.get(position).getMaloai();
                Toast.makeText(getContext(), "Chọn " + listls.get(position).getTenloai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // kiểm tra tyoe insert 0 hay update 1
        ed_masach.setEnabled(false);
        //ed_masach.setVisibility(View.GONE);
        if (type != 0) {
            ed_masach.setText(String.valueOf(sach.getMasach()));
            ed_tensach.setText(sach.getTensach());
            ed_giathue.setText(String.valueOf(sach.getGiathue()));
            for (int i = 0; i < listls.size(); i++) {
                if (sach.getMaloai() == (listls.get(i).getMaloai())) {
                    position = i;
                }
                Log.e("demo", "possach " + position);
                spinner.setSelection(position);
            }
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenssach = ed_tensach.getText().toString().trim();
                Double giathue = Double.parseDouble(ed_giathue.getText().toString().trim());
                //sach = new Sach(tenssach, giathue, maloaisach);

                // đóng gói và truyền dữ liệu sang BookService
                Intent intent = new Intent(getActivity(), BookService.class);
                Bundle bundle = new Bundle();
                bundle.putString("tensach", tenssach);
                bundle.putDouble("giathue", giathue);
                bundle.putInt("maloai", maloaisach);
                intent.putExtras(bundle);
                getActivity().startService(intent);
            }
        });

        dialog.show();
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checkbook":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check == true) {
                        LoadLisst();
                        dialog.dismiss();
                        Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            // dialog.show();

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);

    }


}