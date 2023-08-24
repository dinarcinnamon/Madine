package com.example.madine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private Button btnAdmin,btnUser;
    private MaterialTextView aboutButton;
    private MaterialTextView aboutOperate;
    protected static String noUnit, name, telpNo, plat, email, password,user;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        startService(intent);

        btnAdmin = findViewById(R.id.btn_adminLogin);
        btnUser = findViewById(R.id.btn_userLogin);
        aboutButton = findViewById(R.id.aboutustext);
        aboutOperate = findViewById(R.id.operateinfo);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(i);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UserLogin.class);
                startActivity(i);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengarahkan ke halaman activity_about.xml atau AboutActivity.java
                Intent intent = new Intent(MainActivity.this, AboutusActivity.class);
                startActivity(intent);
            }
        });

        aboutOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, OperateActivity.class);
                startActivity(intent1);
            }
        });
    }
}