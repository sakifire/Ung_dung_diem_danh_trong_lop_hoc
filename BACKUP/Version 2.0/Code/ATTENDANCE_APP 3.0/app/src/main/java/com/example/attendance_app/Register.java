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

public class Register extends AppCompatActivity {
    EditText username, password, repassword, email;
    Button signup, signin;
    RadioButton male, female;
    RadioGroup gender;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        email = findViewById(R.id.email);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        gender = findViewById(R.id.radio_gender);
        male = findViewById(R.id.radioButton_male);
        female = findViewById(R.id.radioButton_female);


        DB = new DbHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();
                String gen = null;
                if (gender.getCheckedRadioButtonId() == male.getId()) {
                    gen = "Male";
                } else if (gender.getCheckedRadioButtonId() == female.getId())
                    gen = "Female";
                String finalGen = gen;

                if (user.equals("")) {
                    Toast.makeText(Register.this, "Please enter user name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equals("")) {
                    Toast.makeText(Register.this, "Please enter user pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (repass.equals("")) {
                    Toast.makeText(Register.this, "Please enter user repass", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkUserName(user);
                        if (checkuser == false) {
                            Boolean insert = DB.addUser(user, pass, mail, finalGen);
                            if (insert == true) {
                                Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }
}