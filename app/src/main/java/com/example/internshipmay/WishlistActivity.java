package com.example.internshipmay;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase db;
    SharedPreferences sp;

    ArrayList<WishlistList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);


        db = openOrCreateDatabase("InternshipMay.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(tableQuery);


        String categoryQuery = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subcategoryQuery = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT ,categoryid INTEGER, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryQuery);

        String productQuery = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT ,subcategoryid INTEGER, name VARCHAR(50), image VARCHAR(100), price VARCHAR(10), description VARCHAR(100))";
        db.execSQL(productQuery);

        String wishlistQuery = "CREATE TABLE IF NOT EXISTS wishlist(wishlistid INTEGER PRIMARY KEY AUTOINCREMENT ,userid INTEGER, productid INTEGER)";
        db.execSQL(wishlistQuery);

        recyclerView = findViewById(R.id.wishlist_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(WishlistActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String selectWishlistQuery = "SELECT * FROM wishlist WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"'";
        Cursor cursor = db.rawQuery(selectWishlistQuery,null);

        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                WishlistList list = new WishlistList();
                list.setWishlistid(cursor.getString(0));

                String selectProductQuery = "SELECT * FROM product WHERE productid = '" + cursor.getInt(2) + "'";
                Cursor cursor1 = db.rawQuery(selectProductQuery, null);

                if (cursor1.getCount() > 0 && cursor1.moveToFirst()) {
                    list.setProductid(cursor1.getString(0));
                    list.setSubcategoryid(cursor1.getString(1));
                    list.setName(cursor1.getString(2));
                    list.setImage(cursor1.getInt(3));
                    list.setPrice(cursor1.getString(4));
                }

                cursor1.close();
                arrayList.add(list);
            }

            WishlistAdapter adapter = new WishlistAdapter(WishlistActivity.this, arrayList, db);
            recyclerView.setAdapter(adapter);

        }

    }
}