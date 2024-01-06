package com.example.libraryapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.libraryapp.Fragment.BookFragment;
import com.example.libraryapp.Fragment.ChangePassFragment;
import com.example.libraryapp.Fragment.DTFragment;
import com.example.libraryapp.Fragment.ListUserFragment;
import com.example.libraryapp.Fragment.LoanvocherFragment;
import com.example.libraryapp.Fragment.TopFragment;
import com.example.libraryapp.Fragment.UserFragment;
import com.example.libraryapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private View headerView;
    private TextView tv_user;
    private NavigationView naview;
    private Fragment fragment = null;
    private long backPressedTime;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addcontroll();
        addevenst();
        Hierarchy();// phân cấp
    }

    // ánh xạ view
    private void addcontroll() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        naview = findViewById(R.id.naview);

        // hiển thị navigation
        headerView = naview.getHeaderView(0);
        tv_user = headerView.findViewById(R.id.tv_user);
        SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        String hoten = sharedPreferences.getString("hoten","");
        tv_user.setText("Welcome! "+hoten);

        //hiển thị toobar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menutobar);
    }

    // hàm xử lý sự kiện
    private void addevenst() {

        // add hoạt động cho naview
        naview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.phieumuon:
                            fragment = new LoanvocherFragment();
                            getFragment();
                        break;
                    case R.id.loaisach:
                        break;
                    case R.id.sach:
                            fragment = new BookFragment();
                            getFragment();
                        break;
                    case R.id.user:
                            fragment = new ListUserFragment();
                            getFragment();
                        break;
                    case R.id.sub_top:
                        fragment = new TopFragment();
                        getFragment();
                        break;
                    case R.id.sub_doanhthu:
                        fragment = new DTFragment();
                        getFragment();
                        break;
                    case R.id.add_user:
                        break;
                    case R.id.sub_pass:
                            fragment = new ChangePassFragment();
                            getFragment();
                        break;
                    case R.id.logout:
                        Loguot();
                        break;
                    default:

                        break;
                }

                // đổi title fragment
                getSupportActionBar().setTitle(item.getTitle());

                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    // hàm sử lý hoạt động toolbar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);// mở navigation bên trái
        }
        return super.onOptionsItemSelected(item);
    }

    // phân câp đăng nhâp
    public void Hierarchy() {
        // lấy role đã lưu ở ShraedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);// -1 là giá trị nếu nó không tìm thấy
        Menu menu = naview.getMenu();
        switch (role) {
            case 1:
                menu.findItem(R.id.phieumuon).setVisible(true);
                menu.findItem(R.id.loaisach).setVisible(true);
                menu.findItem(R.id.sach).setVisible(true);
                menu.findItem(R.id.user).setVisible(true);
                menu.findItem(R.id.sub_top).setVisible(true);
                menu.findItem(R.id.sub_doanhthu).setVisible(true);
                menu.findItem(R.id.add_user).setVisible(true);
                menu.findItem(R.id.sub_pass).setVisible(true);
                menu.findItem(R.id.logout).setVisible(true);
                menu.findItem(R.id.phieumuon).setVisible(true);
                break;
            case 2:
                menu.findItem(R.id.phieumuon).setVisible(false);
                menu.findItem(R.id.loaisach).setVisible(true);
                menu.findItem(R.id.sach).setVisible(true);
                menu.findItem(R.id.user).setVisible(true);
                menu.findItem(R.id.sub_top).setVisible(false);
                menu.findItem(R.id.sub_doanhthu).setVisible(false);
                menu.findItem(R.id.add_user).setVisible(false);
                menu.findItem(R.id.sub_pass).setVisible(true);
                menu.findItem(R.id.logout).setVisible(true);
                menu.findItem(R.id.user).setVisible(false);
                menu.findItem(R.id.phieumuon).setVisible(false);
                break;
        }

        // gọi userpage
        UserPage();
    }

    // method logout
    private void Loguot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đăng xuất khỏi tài khoản của bạn ?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();//
            }
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // method khởi tạo fragment
    public void getFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flcontent, fragment)
                .commit();
    }

    //
    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mToast = Toast.makeText(this, "ấn lại để thoát !", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    // medthod img_user
    public void UserPage() {
        View view = naview.getHeaderView(0);
        ImageView img_user = view.findViewById(R.id.img_user);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new UserFragment();
                getFragment();

                // trọn item navigation xong ẩn navigation
                drawerLayout.closeDrawer(GravityCompat.START);// thì đóng
            }
        });
    }
}