package com.example.libraryapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static  String NAME = "Library";
    public static final int VERSION = 1;
    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tạo bảng thành viên
        String db_thanhvien = "CREATE TABLE User(matv TEXT PRIMARY KEY, hoten TEXT , name TEXT, pass TEXT, namsinh TEXT,role INTEGER)";
        db.execSQL(db_thanhvien);

        // thêm dữ liệu vào bằng User
        String d_user = "INSERT INTO User VALUES(1,'Phạm Văn Đại','dai','123','07/06/2002',1),(2,'Phạm Văn A','a','123','07/06/2002',2)";
        db.execSQL(d_user);

        // tạo bảng loại sách
        String db_loaisach = "CREATE TABLE Loaisach(maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT NOT NULL)";
        db.execSQL(db_loaisach);

        // thêm dữ liệu vào bảng loaisach
        String d_loaisach = "INSERT INTO Loaisach VALUES(1,'Tiếng anh cơ bản'),(2,'Tiêng anh nâng cao'),(3,'Lập trình cơ bản')" +
                ",(4,'Lập trình android')" +
                ",(5,'Lập trình java')" +
                ",(6,'Lập trình wed')";
        db.execSQL(d_loaisach);

        // tạo bảng sách
        String db_sach = "CREATE TABLE Sach(masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT NOT NULL, giathue DOUBLE NOT NULL, maloai INTEGER REFERENCES Loaisach(maloai))";
        db.execSQL(db_sach);

        // thêm dữ liệu vào bảng sach
        String d_sach = "INSERT INTO Sach VALUES(1,'Lâp trình Android nâng cao',40000,4),(2,'Lâp trình trực quan java',45000,5),(3,'Lâp trình wed cơ bản',36000,6)" +
                ",(4,'Tiếng anh 1',25000,1),(5,'Tiếng anh chuyên ngành',70000,2),(6,'Lập trình c++',46000,3)";
        db.execSQL(d_sach);

        // tạo bảng phiếu mượn
        String db_phieumuon = "CREATE TABLE Phieumuon(mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
                " matv TEXT REFERENCES User(matv), masach INTEGER REFERENCES Sach(masach)," +
                "tienthue DOUBLE,ngay DATE NOT NULL, trasach INTEGER NOT NULL)";
        db.execSQL(db_phieumuon);

        String d_phieumuon = "INSERT INTO Phieumuon VALUES(1,1,1,30000,'20/09/2023',1),(2,1,2,30000,'20/09/2023',0),(3,1,3,30000,'20/09/2023',0)" +
                ",(4,1,4,30000,'20/09/2023',0),(5,1,5,30000,'20/09/2023',0),(6,1,6,30000,'20/09/2023',0)";
        db.execSQL(d_phieumuon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1){// nếu version khác nhau xóa hết table cũ sẽ tạo lại table mới
            db.execSQL("DROP TABLE IF EXISTS Loaisach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS Thuthu");
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Phieumuon");
            onCreate(db);
        }

    }
}
