package com.example.libraryapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DAO.Book_Dao;
import com.example.libraryapp.Model.Sach;
import com.example.libraryapp.R;

import java.util.ArrayList;

public class Adapter_Book extends RecyclerView.Adapter<ViewHolder_Book> {
    private ArrayList<Sach> list;
    private Context context;
    private Book_Dao book_dao;

    public Adapter_Book(ArrayList<Sach> list, Context context) {
        this.list = list;
        this.context = context;
        book_dao = new Book_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder_Book onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new ViewHolder_Book(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Book holder, int position) {
        Sach sach = list.get(position);
        holder.tv_masach.setText("Mã sách: " + sach.getMasach());
        holder.tv_tensach.setText("Tên sách: " + sach.getTensach());
        holder.tv_giathue.setText("Giá thuê: " + sach.getGiathue());
        holder.tv_loaisach.setText("Tên loại: " + sach.getTenloai());

        // sử lý sự kiện xóa
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogDlete(sach.getMaloai(), sach.getTenloai());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size(); // trả về sô lượng item
        }
        return 0;
    }

    // method dialog xóa book
    private void ShowDialogDlete(int maloai, String tenloai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thống báo");
        builder.setMessage("Xóa \"" + tenloai + "\"?");
        builder.setIcon(R.drawable.ic_warning);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = book_dao.DeleteBook(maloai);
                if (check == true) {
                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    loadList();
                } else {
                    Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // method Loadlist
    private void loadList() {
        list.clear();
        list = book_dao.GetListBook();
        notifyDataSetChanged();
    }
}
