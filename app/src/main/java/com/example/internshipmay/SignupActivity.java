package com.example.internshipmay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    TextView have_account;
    EditText name, email, contact, password, cnfpassword;
    Button signup;

    String pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        have_account = findViewById(R.id.signup_have_account);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        cnfpassword = findViewById(R.id.signup_cnfpassword);
        signup = findViewById(R.id.signup_button);

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                }
                if(!email.getText().toString().matches(pattern)){
                    email.setError("Enter a valid Email");
                }

                else if (name.getText().toString().trim().equals("")) {
                    name.setError("Enter Name");
                }

                else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Enter Contact Number");

                } else if (password.getText().toString().equals("")) {
                    password.setError("Enter Password");
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
                    Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }
}