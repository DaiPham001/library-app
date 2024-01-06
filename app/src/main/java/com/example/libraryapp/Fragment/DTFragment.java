package com.example.libraryapp.Fragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.libraryapp.DAO.Loanvoucher_Dao;
import com.example.libraryapp.R;

import java.text.SimpleDateFormat;


public class DTFragment extends Fragment {

    private TextView tv_tungay, tv_denngay, tv_sum;
    private EditText ed_tungay, ed_denngay;
    private Button btn_sum;
    // lấy thời gian hiện tại
    private Calendar calendar = Calendar.getInstance();
    // format cách hiển thị ngày/tháng/năm
    private SimpleDateFormat sdfD = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_d_t, container, false);
        addontroll(view);
        addevenst();
        return view;
    }

    //method ánh xạ view
    private void addontroll(View view) {
        tv_denngay = view.findViewById(R.id.tv_dengay);
        tv_tungay = view.findViewById(R.id.tv_tungay);
        tv_sum = view.findViewById(R.id.tv_sum);
        ed_denngay = view.findViewById(R.id.ed_denngay);
        ed_tungay = view.findViewById(R.id.ed_tungay);
        btn_sum = view.findViewById(R.id.btn_sum);
    }

    // method sử lý sự kiện
    private void addevenst() {
        // add hoạt động cho tv_tungay
        tv_tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo calback để quay lại
                DatePickerDialog.OnDateSetListener calbcak2=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DATE,i2);
                        // hiển thị thời gian sau khi thiết lập
                        ed_tungay.setText(sdfD.format(calendar.getTime()));
                    }
                };
                // hiển thị hộp thoại để chọn ngày thang năm
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        getActivity(),calbcak2,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                // cho phép hiển thị để người dùng thay đổi thông tin
                datePickerDialog.show();
            }
        });

        // add sự kiện cho tv_denngay
        tv_denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo calback để quay lại
                DatePickerDialog.OnDateSetListener calbcak2=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DATE,i2);
                        // hiển thị thời gian sau khi thiết lập
                        ed_denngay.setText(sdfD.format(calendar.getTime()));
                    }
                };
                // hiển thị hộp thoại để chọn ngày thang năm
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        getActivity(),calbcak2,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                // cho phép hiển thị để người dùng thay đổi thông tin
                datePickerDialog.show();
            }
        });

        // add sự kiện cho btn_sum
        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = ed_tungay.getText().toString().trim();
                String denngay = ed_denngay.getText().toString().trim();
                Loanvoucher_Dao loanvoucher_dao = new Loanvoucher_Dao(getContext());
                tv_sum.setText("Doanh thu: "+loanvoucher_dao.Getdoanhthu(tungay,denngay));
                Log.e("sum",tv_sum.getText().toString());
            }
        });
    }
}