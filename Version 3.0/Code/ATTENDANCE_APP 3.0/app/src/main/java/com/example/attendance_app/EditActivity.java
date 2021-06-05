package com.example.attendance_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText username, password, repassword, email;
    Button back, save;
    RadioButton male, female;
    RadioGroup gender;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        repassword = findViewById(R.id.edt_repassword);
        email = findViewById(R.id.edt_email);
        save = findViewById(R.id.btnsave);
        back = findViewById(R.id.btnback);
        gender = findViewById(R.id.edt_radio_gender);
        male = findViewById(R.id.radioButton_male);
        female = findViewById(R.id.radioButton_female);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));
        password.setText(intent.getStringExtra("pass"));
        repassword.setText(intent.getStringExtra("pass"));
        email.setText(intent.getStringExtra("email"));
        String uid = intent.getStringExtra("uid1");


        DB = new DbHelper(this);
        save.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();
            String mail = email.getText().toString();
            String gen = intent.getStringExtra("gender");


            if (user.equals("")) {
                Toast.makeText(EditActivity.this, "Please enter user name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.equals("")) {
                Toast.makeText(EditActivity.this, "Please enter user pass", Toast.LENGTH_SHORT).show();
                return;
            }
            if (repass.equals("")) {
                Toast.makeText(EditActivity.this, "Please enter user repass", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (pass.equals(repass)) {
                    if (user.equals(intent.getStringExtra("username"))) {
                        DB.updateUser(uid, user, pass, mail, gen);
                        Toast.makeText(EditActivity.this, "Update success1", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkUser = DB.checkUserName(user);
                        if (checkUser == false) {
                            DB.updateUser(uid, user, pass, mail, gen);
                            Toast.makeText(EditActivity.this, "Update success2", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(EditActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }
            onBackPressed();
        });


        back.setOnClickListener(v -> onBackPressed());


    }

}
