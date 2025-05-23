package com.example.internshipmay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    TextView welcome;
    Button profile, logout, delete_profile, category;

    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcome = findViewById(R.id.dashboard_welcome);
        profile = findViewById(R.id.dashboard_profile);
        logout = findViewById(R.id.dashboard_Logout);
        delete_profile = findViewById(R.id.dahsboard_delete_profile);

        category = findViewById(R.id.dashboard_category);

        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        String name = sp.getString(ConstantSp.name, "");
        welcome.setText("Welcome "+name);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                onBackPressed();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        delete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(DashboardActivity.this);
                alert.setTitle("Delete Profile");
                alert.setMessage("Are you sure you want to delete your profile?");
                alert.setIcon(R.mipmap.ic_launcher);

                alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                    String deleteProfile = "DELETE FROM user WHERE userid = '"+sp.getString(ConstantSp.userid,"")+"'";
                    db.execSQL(deleteProfile);
                    sp.edit().clear().commit();
                    onBackPressed();
                });

                alert.setNegativeButton("No", (dialogInterface, i) -> {
                    alert.setCancelable(true);

                });

                alert.show();




            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
//        finishAffinity();
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = sp.getString(ConstantSp.name, "");
        welcome.setText("Welcome "+name);

    }
}