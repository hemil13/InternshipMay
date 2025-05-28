package com.example.internshipmay;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView product_recycler;

    int[] subcategoryidArray = {3,3,4,4};
    int[] idArray = {1,2,3,4};
    String[] nameArray = {"Redmi", "Oneplus", "Noise", "AirpodsMax"};
    int[] imageArray = {R.drawable.redmi, R.drawable.oneplus, R.drawable.noise, R.drawable.airpods_max};

    String[] priceArray = {"$100", "$200", "$300", "$400"};
    String [] descriptionArray = {"This is a description", "This is a description", "This is a description", "This is a description"};

    SharedPreferences sp;

    SQLiteDatabase db;

    ArrayList<ProductList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product_recycler = findViewById(R.id.product_recycler);

        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        String categoryQuery = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subcategoryQuery = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT ,categoryid INTEGER, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryQuery);

        String productQuery = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT ,subcategoryid INTEGER, name VARCHAR(50), image VARCHAR(100), price VARCHAR(10), description VARCHAR(100))";
        db.execSQL(productQuery);

        product_recycler.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        product_recycler.setItemAnimator(new DefaultItemAnimator());

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

//        arrayList = new ArrayList<>();
//        for(int i = 0; i<nameArray.length; i++){
//            if(Integer.parseInt(sp.getString(ConstantSp.subcategoryid, "")) == subcategoryidArray[i]){
//                ProductList productList = new ProductList();
//                productList.setSubcategoryid(subcategoryidArray[i]);
//                productList.setProductid(idArray[i]);
//                productList.setName(nameArray[i]);
//                productList.setImage(imageArray[i]);
//                productList.setPrice(priceArray[i]);
//                productList.setDescription(descriptionArray[i]);
//                arrayList.add(productList);
//            }
//
//        }


        for(int i=0; i<nameArray.length; i++){
            String selectProductQuery = "SELECT * FROM product WHERE name = '"+nameArray[i]+"' AND subcategoryid = '"+subcategoryidArray[i]+"'";
            Cursor cursor = db.rawQuery(selectProductQuery,null);
            if (cursor.getCount()>0){
                // no need to do anything
            } else{
                String insertProductQuery = "INSERT INTO product(subcategoryid, name, image, price, description) VALUES('"+subcategoryidArray[i]+"','"+nameArray[i]+"','"+imageArray[i]+"','"+priceArray[i]+"','"+descriptionArray[i]+"')";
                db.execSQL(insertProductQuery);
            }
        }

        String selectQuery = "SELECT * FROM product WHERE subcategoryid = '"+sp.getString(ConstantSp.subcategoryid , "")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);

        arrayList = new ArrayList<>();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                ProductList productList = new ProductList();
                productList.setProductid(cursor.getInt(0));
                productList.setSubcategoryid(cursor.getInt(1));
                productList.setName(cursor.getString(2));
                productList.setImage(cursor.getInt(3));
                productList.setPrice(cursor.getString(4));
                productList.setDescription(cursor.getString(5));
                arrayList.add(productList);
            }
            ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
            product_recycler.setAdapter(adapter);
        }


//        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
//        product_recycler.setAdapter(adapter);


    }


}