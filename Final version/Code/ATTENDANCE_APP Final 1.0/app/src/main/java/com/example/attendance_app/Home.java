package com.example.attendance_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity {
    CardView classPage, infoPage, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        classPage = findViewById(R.id.class_page);
        infoPage = findViewById(R.id.info_page);
        logout = findViewById(R.id.logout);

        Intent intent = getIntent();
        String newUID = Integer.toString(intent.getIntExtra("uid", -1));
        int uid = intent.getIntExtra("uid", -1);

        classPage.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ClassActivity.class);
            intent1.putExtra("uid_forClass", uid);
            startActivity(intent1);

        });

        infoPage.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, EditActivity.class);
            intent1.putExtra("uid1", newUID);
            intent1.putExtra("username", intent.getStringExtra("username"));
            intent1.putExtra("pass", intent.getStringExtra("pass"));
            intent1.putExtra("email", intent.getStringExtra("email"));
            intent1.putExtra("gender", intent.getStringExtra("gender"));
            startActivity(intent1);
        });

        logout.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(),Login.class);
            startActivity(intent1);
        });
    }
}