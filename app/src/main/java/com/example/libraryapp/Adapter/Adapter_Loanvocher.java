package com.example.libraryapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DAO.Book_Dao;
import com.example.libraryapp.DAO.Loanvoucher_Dao;
import com.example.libraryapp.DAO.User_Dao;
import com.example.libraryapp.Model.Phieumuon;
import com.example.libraryapp.Model.Sach;
import com.example.libraryapp.Model.User;
import com.example.libraryapp.R;

import java.util.ArrayList;
import java.util.Date;

public class Adapter_Loanvocher extends RecyclerView.Adapter<ViewHolder_Loanvocher> {

    private Context context;
    private ArrayList<Sach> lists;
    private ArrayList<User> listu;
    private AlertDialog alertDialog;
    private ArrayList<Phieumuon> list;
    private Loanvoucher_Dao loanvoucher_dao;
    private Button btn_add, btn_cancel;
    private EditText ed_masach;
    private TextView tv_giathue, tv_date;
    private Spinner sp_loan, sp_name;
    private ImageView img_date;
    private CheckBox checkBox;
    private Book_Dao book_dao;
    private int position1, position2;
    private int  masach;
    private String matv,a;

    // lấy thời gian hiện tại
    private SimpleDateFormat sdfD = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendar = Calendar.getInstance();

    public Adapter_Loanvocher(Context context, ArrayList<Phieumuon> list) {
        this.context = context;
        this.list = list;
        loanvoucher_dao = new Loanvoucher_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder_Loanvocher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loanvocher, parent, false);
        return new ViewHolder_Loanvocher(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Loanvocher holder, @SuppressLint("RecyclerView") int position) {
        Phieumuon phieumuon = list.get(position);
        holder.tv_mapm.setText("Mã: " + phieumuon.getMapm());
        holder.tv_tensach.setText("Tên sách: " + phieumuon.getTensach());
        holder.tv_user.setText("Tên: " + phieumuon.getTentv());
        holder.tv_giathue.setText("Giá thuê: " + phieumuon.getTienthue());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.tv_ngaymuon.setText("Ngày mượn: " + sdf.format(phieumuon.getNgay()));
        int trasach = phieumuon.getTrasach();
        if (trasach == 1) {
            holder.tv_trasach.setText("đã trả");
            holder.tv_trasach.setTextColor(Color.BLUE);
        } else if (trasach == 0) {
            holder.tv_trasach.setText("chưa trả");
            holder.tv_trasach.setTextColor(Color.RED);
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(phieumuon.getMapm());
            }
        });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                v = LayoutInflater.from(context).inflate(R.layout.dialog_addloan, null);
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
                book_dao = new Book_Dao(context);
                lists = new ArrayList<Sach>();
                lists = book_dao.GetListBook();

                Sach_Spinner sach_spinner = new Sach_Spinner(context, lists);
                sp_loan.setAdapter(sach_spinner);

                // add dữ liệu vào spinner tên user
                User_Dao user_dao = new User_Dao(context);
                listu = new ArrayList<User>();
                listu = user_dao.getList_User();

                User_Spinner user_spinner = new User_Spinner(context, listu);
                sp_name.setAdapter(user_spinner);

                // sử lý sự kiện
                //ed_masach.setEnabled(false);// không cho user nhập mã

                // add sự kiện cho img_date

                img_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener calbcak2=new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(Calendar.HOUR_OF_DAY,i);
                                calendar.set(Calendar.MINUTE,i1);
                                // hiển thị thời gian sau khi thiết lập
                                tv_date.setText(sdfD.format(calendar.getTime()));
                                a = tv_date.getText().toString().trim();
                            }
                        };
                        // hiển thị hộp thoại để chọn ngày thang năm
                        DatePickerDialog datePickerDialog=new DatePickerDialog(
                                context,calbcak2,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                        // cho phép hiển thị để người dùng thay đổi thông tin
                        datePickerDialog.show();
                    }
                });
                // hiển thị thời gian sau khi thiết lập
                tv_date.setText(String.valueOf(phieumuon.getNgay()));

                // add hoạt động cho spinner tên sách
                sp_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        masach = lists.get(position).getMasach();
                        // Toast.makeText(context, "chon: " + lists.get(position).getTenloai(), Toast.LENGTH_SHORT).show();
                        //tv_giathue.setText(String.valueOf(lists.get(position).getGiathue()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // add hoạt động cho spinner tên user
                sp_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        matv = (listu.get(position).getMatv());
                        // Toast.makeText(context, "Chọn: " + listu.get(position).getHoten(), Toast.LENGTH_SHORT).show();
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


                // set dư liệu khi update
                ed_masach.setText("" + phieumuon.getMapm());
                tv_giathue.setText("" + phieumuon.getTienthue());
                tv_date.setText("" + sdf.format(phieumuon.getNgay()));
                int trasach = phieumuon.getTrasach();
                if (trasach == 1) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }

                for (int i = 0; i < listu.size(); i++)
                    if (phieumuon.getMatv() == (listu.get(i).getMatv())) {
                        position1 = i;
                    }
                Log.e("demo", "l" + position1);
                sp_name.setSelection(position1);


                for (int i = 0; i < lists.size(); i++)
                    if (phieumuon.getMasach() == lists.get(i).getMasach()) {
                        position2 = i;
                    }
                Log.e("demo", "l" + position2);
                sp_loan.setSelection(position2);


                // add hoat động cho btn update
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int mapm = Integer.parseInt(ed_masach.getText().toString().trim());
                        String mau = matv;
                        int mas =masach;
                        Double giathue = Double.valueOf(tv_giathue.getText().toString());
                        String date = tv_date.getText().toString().trim();
                        int trasach;
                        if (checkBox.isChecked()) {
                            trasach = 1;
                        } else {
                            trasach = 0;
                        }

                        Log.e("add","loi"+mapm+mau+mas+giathue+date+trasach);
                        Phieumuon phieumuon = new Phieumuon(mapm,mau,mas,new Date(date),giathue,trasach);
                        boolean check = loanvoucher_dao.UpdateLoan(phieumuon);
                        if (check == true){
                            Toast.makeText(context, "thành công", Toast.LENGTH_SHORT).show();
                            loadList();
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(context, "thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void ShowDialog(int mapm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thống báo");
        builder.setMessage("Xóa ?");
        builder.setIcon(R.drawable.ic_warning);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = loanvoucher_dao.DeleteLoan(mapm);
                if (check == true) {
                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    loadList();
                } else {
                    Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();// trả về số lượng item
        }
        return 0;
    }

    // method Loadlist
    private void loadList() {
        list.clear();
        list = loanvoucher_dao.GetListLoan();
        notifyDataSetChanged();
    }
}
