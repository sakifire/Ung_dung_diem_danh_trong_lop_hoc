package com.example.attendance_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username, password;
    TextView resignter;
    Button btnLogin;
    DbHelper DB;
    UserItem userItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        resignter = findViewById(R.id.resignter);
        btnLogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DbHelper(this);
        resignter.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);

            startActivity(intent);
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();


                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Boolean checkuserpass = DB.checkUserNamePassword(user, pass);
                    if (checkuserpass == true) {
                        int userId = DB.getUser(user);
                        Toast.makeText(Login.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("uid", userId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}