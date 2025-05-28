package com.example.internshipmay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Currency;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView category_recycler;

    SQLiteDatabase db;


    int[] idArray = {1,2,3};
    String[] nameArray = {"Clothes", "Electronics", "Books"};
    int[] imageArray = {R.drawable.clothes, R.drawable.electronics, R.drawable.books};

    ArrayList<CategoryList> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        String categoryQuery = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryQuery);

        category_recycler = findViewById(R.id.category_recycler);

//        category_recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        for(int i=0; i<nameArray.length; i++){
            String selectQuery = "SELECT * FROM category WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(selectQuery,null);

            if(cursor.getCount()>0){
            }
            else{
                String insertQuery = "INSERT INTO category(name, image) VALUES('"+nameArray[i]+"','"+imageArray[i]+"')";
                db.execSQL(insertQuery);
            }
        }

        String selectQuery = "SELECT * FROM category";
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while(cursor.moveToNext()){
                CategoryList categoryList = new CategoryList();
                categoryList.setId(cursor.getInt(0));
                categoryList.setName(cursor.getString(1));
                categoryList.setImage(cursor.getInt(2));
                arrayList.add(categoryList);
            }
            CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, arrayList);
            category_recycler.setAdapter(adapter);
        }


//        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, idArray, nameArray, imageArray);
//        category_recycler.setAdapter(adapter);




    }
}