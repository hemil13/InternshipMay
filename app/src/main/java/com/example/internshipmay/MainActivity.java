package com.example.internshipmay;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;

    TextView create_account, forget_password;

    String pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    ImageView showiv,hideiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        create_account = findViewById(R.id.main_create_account);
        forget_password = findViewById(R.id.main_forget_password);

        hideiv = findViewById(R.id.hideIV);
        showiv = findViewById(R.id.showIV);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        hideiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideiv.setVisibility(View.GONE);
                showiv.setVisibility(View.VISIBLE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        showiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideiv.setVisibility(View.VISIBLE);
                showiv.setVisibility(View.GONE);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email Or Contact");
                }

                else if (password.getText().toString().equals("")) {
                    password.setError("Enter Password");
                }
                else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                }
                else{

                    String loginQuery = "SELECT * FROM user WHERE (email = '"+email.getText().toString()+"' OR contact = '"+email.getText().toString()+"') AND password = '"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(loginQuery,null);

                    if(cursor.getCount()>0){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });



    }
}