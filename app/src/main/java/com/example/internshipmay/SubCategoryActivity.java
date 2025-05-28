package com.example.internshipmay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    RecyclerView subcategory_recycler;

    int[] categoryidArray = {1,1,2,2};
    int[] idArray = {1,2,3,4};
    String[] nameArray = {"Shirts", "Tshirts", "Mobile", "Headphones"};
    int[] imageArray = {R.drawable.shirt, R.drawable.thsirt, R.drawable.mobile, R.drawable.headphone};

    ArrayList<SubcategoryList> arrayList;

    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_category);

        subcategory_recycler = findViewById(R.id.subcategory_recycler);


        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        String categoryQuery = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subcategoryQuery = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT ,categoryid INTEGER, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryQuery);

        subcategory_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        subcategory_recycler.setItemAnimator(new DefaultItemAnimator());


        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

//        arrayList = new ArrayList<>();
//        for (int i = 0; i < nameArray.length; i++) {
//            if(Integer.parseInt(sp.getString(ConstantSp.categoryid , "")) == categoryidArray[i]){
//
//                SubcategoryList subcategoryList = new SubcategoryList();
//                subcategoryList.setCategoryid(categoryidArray[i]);
//                subcategoryList.setSubcategoryid(idArray[i]);
//                subcategoryList.setName(nameArray[i]);
//                subcategoryList.setImage(imageArray[i]);
//                arrayList.add(subcategoryList);
//            }
//        }


        arrayList = new ArrayList<>();
        for(int i=0; i<nameArray.length; i++){
            String selectSubCategoryQuery = "SELECT * FROM subcategory WHERE name = '"+nameArray[i]+"' AND categoryid = '"+categoryidArray[i]+"'";
            Cursor cursor = db.rawQuery(selectSubCategoryQuery,null);
            if (cursor.getCount()>0){
             // no need to do anything
            } else{
                String insertSubCategoryQuery = "INSERT INTO subcategory(categoryid, name, image) VALUES('"+categoryidArray[i]+"','"+nameArray[i]+"','"+imageArray[i]+"')";
                db.execSQL(insertSubCategoryQuery);
            }
        }

        String selectQuery = "SELECT * FROM subcategory WHERE categoryid = '"+sp.getString(ConstantSp.categoryid , "")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                SubcategoryList subcategoryList = new SubcategoryList();
                subcategoryList.setCategoryid(cursor.getInt(1));
                subcategoryList.setSubcategoryid(cursor.getInt(0));
                subcategoryList.setName(cursor.getString(2));
                subcategoryList.setImage(cursor.getInt(3));
                arrayList.add(subcategoryList);
            }
            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayList);
            subcategory_recycler.setAdapter(adapter);
        }

//        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayList);
//        subcategory_recycler.setAdapter(adapter);

    }
}