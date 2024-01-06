package com.example.libraryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.libraryapp.R;

public class Screen_saver extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_saver);
        imageView = findViewById(R.id.img);
        Glide.with(this).load(R.drawable.f).into(imageView);
        // tự động chuyển màn hình
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Screen_saver.this,LoginActivity.class);
                startActivity(intent);
                finish();// đóng activity
            }
        },1000); // thời gian chay
    }
}