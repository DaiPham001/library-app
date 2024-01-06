package com.example.libraryapp.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Adapter.Adapter_Loanvocher;
import com.example.libraryapp.Adapter.Sach_Spinner;
import com.example.libraryapp.Adapter.User_Spinner;
import com.example.libraryapp.DAO.Book_Dao;
import com.example.libraryapp.DAO.Loanvoucher_Dao;
import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.Model.Phieumuon;
import com.example.libraryapp.Model.Sach;
import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;
import com.example.libraryapp.Service.LoanService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LoanvocherFragment extends Fragment {


    private RecyclerView rcv_addLoan;
    private FloatingActionButton fl_loan;
    private Adapter_Loanvocher adapter_loanvocher;
    private AlertDialog alertDialog;

    private ArrayList<Sach> list;
    private ArrayList<User> listu;
    private Book_Dao book_dao;
    private int masach, position;
    private String matv;

    private Button btn_add, btn_cancel;
    private EditText ed_masach;
    private TextView tv_giathue, tv_date;
    private Spinner sp_loan, sp_name;
    private ImageView img_date;
    private CheckBox checkBox;
    private IntentFilter intentFilter;
    private RelativeLayout rll;

    // lấy thời gian hiện tại
    private Calendar calendar = Calendar.getInstance();
    // format cách hiển thị ngày/tháng/năm
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_loanvocher, container, false);
        addcontroll(view);
        addevenst();
        return view;
    }

    // method ánh xạ view
    private void addcontroll(View v) {
        // đăng ký broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction("checkaddloan");

        rcv_addLoan = v.findViewById(R.id.rcv_loan);
        fl_loan = v.findViewById(R.id.fl_addloan);
        rll = v.findViewById(R.id.rll);
        Loadlist();
    }

    // method Load danh sách
    private void Loadlist() {
        Loanvoucher_Dao loanvoucher_dao = new Loanvoucher_Dao(getContext());
        ArrayList<Phieumuon> list = loanvoucher_dao.GetListLoan();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_addLoan.setLayoutManager(linearLayoutManager);
        adapter_loanvocher = new Adapter_Loanvocher(getContext(), list);
        rcv_addLoan.setAdapter(adapter_loanvocher);

        // dòng kẻ của rcv
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_addLoan.addItemDecoration(itemDecoration);

    }

    // method sử lý sự kiện
    private void addevenst() {
        fl_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(R.layout.dialog_addloan, null);
                builder.setView(v);

                // hiển thị dialog
                alertDialog = builder.create();
                alertDialog.setCancelable(false);// ko cho người dùng thoát
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// bo góc
                alertDialog.show();

                // ánh xạ view dialog
                btn_add = v.findViewById(R.id.btn_addl);
                ed_masach = v.findViewById(R.id.ed_masach);
                sp_name = v.findViewById(R.id.sp_name);
                tv_date = v.findViewById(R.id.txt_date);
                tv_giathue = v.findViewById(R.id.tv_tienthue);
                btn_cancel = v.findViewById(R.id.btn_cancelL);
                sp_loan = v.findViewById(R.id.sp_tensach);
                img_date = v.findViewById(R.id.img_date);
                checkBox = v.findViewById(R.id.cb_Lc);

                // add dữ liệu vào spinner tên sach
                book_dao = new Book_Dao(getContext());
                list = new ArrayList<Sach>();
                list = book_dao.GetListBook();

                Sach_Spinner sach_spinner = new Sach_Spinner(getContext(), list);
                sp_loan.setAdapter(sach_spinner);

                // add dữ liệu vào spinner tên user
                User_Dao user_dao = new User_Dao(getContext());
                listu = new ArrayList<User>();
                listu = user_dao.getList_User();

                User_Spinner user_spinner = new User_Spinner(getContext(), listu);
                sp_name.setAdapter(user_spinner);

                // sử lý sự kiện
                ed_masach.setEnabled(false);// không cho user nhập mã

                // hiển thị thời gian sau khi thiết lập
                tv_date.setText(sdf.format(new Date()));

                // add hoạt động cho spinner tên sách
                sp_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        masach = list.get(position).getMasach();
                        Toast.makeText(getContext(), "chon: " + list.get(position).getTenloai(), Toast.LENGTH_SHORT).show();
                        tv_giathue.setText(String.valueOf(list.get(position).getGiathue()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // add hoạt động cho spinner tên user
                sp_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        matv = listu.get(position).getMatv();
                        Toast.makeText(getContext(), "Chọn: " + listu.get(position).getHoten(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // add hoạt động cho btn_cancel
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                // add hoạt động cho btn_add
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = tv_date.getText().toString().trim();
                        Double giathue = Double.valueOf(tv_giathue.getText().toString().trim());
                        int trasach;
                        if (checkBox.isChecked()) {
                            trasach = 1;
                        } else {
                            trasach = 0;
                        }
                        // đóng gói và truyền dữ liệu sang LoanService
                        Intent intent = new Intent(getActivity(), LoanService.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", matv);
                        bundle.putInt("masach", masach);
                        bundle.putString("date", date);
                        bundle.putDouble("giathue", giathue);
                        bundle.putInt("trasach", trasach);
                        intent.putExtras(bundle);
                        getActivity().startService(intent);
                    }
                });
            }
        });

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "checkaddloan":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    Log.e("Loan",String.valueOf(check));
                    if (check == true) {
                        Loadlist();
                        alertDialog.dismiss();
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // hủy đăng ký broadcast
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}