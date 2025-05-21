package com.example.internshipmay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email, password, cnfpassword;
    Button change_password;
    SQLiteDatabase db;

    String pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        change_password = findViewById(R.id.main_login);
        email = findViewById(R.id.forget_email);
        password = findViewById(R.id.forget_password);
        cnfpassword = findViewById(R.id.forget_cnfpassword);


        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                }
                else if(!email.getText().toString().matches(pattern)){
                    email.setError("Enter a valid Email");
                }
                else if(cnfpassword.getText().toString().equals("")){
                    cnfpassword.setError("Confirm Password is empty");
                }
                else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                }

                else if (cnfpassword.getText().toString().length()<6) {
                    cnfpassword.setError("Minimum 6 characters");
                }

                else if(!password.getText().toString().matches(cnfpassword.getText().toString())){
                    cnfpassword.setError("Password And Confirm Password Doesn't matches ");
                }

                else{
                    String checkEmail = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(checkEmail,null);
                    if(cursor.getCount()>0){
                        String updatePassword = "UPDATE user SET password = '"+password.getText().toString()+"' WHERE email = '"+email.getText().toString()+"'";
                        db.execSQL(updatePassword);
                        Toast.makeText(ForgetPasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else {
                        Toast.makeText(ForgetPasswordActivity.this,"Email Doesn't Exists", Toast.LENGTH_LONG).show();
                    }
                }




            }
        });
    }
}