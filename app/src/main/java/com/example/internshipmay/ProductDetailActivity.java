package com.example.internshipmay;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {


    ImageView image, wishlist, cart;
    TextView name, price, description;

    SharedPreferences sp;

    SQLiteDatabase db;

    boolean isWishlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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





        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        image = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);
        wishlist = findViewById((R.id.product_detail_wishlist));
//        cart = findViewById(R.id.product_detail_cart);

        image.setImageResource(sp.getInt(ConstantSp.prod_image, 0));
        name.setText(sp.getString(ConstantSp.prod_name, ""));
        price.setText(ConstantSp.ruppees+String.valueOf(sp.getString(ConstantSp.prod_price, "")));
        description.setText(sp.getString(ConstantSp.prod_description, ""));



        /*
        * wishlist table
        * wishlistid
        * userid
        * productid
        * */



        String selectWishlistQuery = "SELECT * FROM wishlist WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"' AND productid = '"+sp.getString(ConstantSp.productid, "")+"'";
        Cursor cursor = db.rawQuery(selectWishlistQuery,null);
        if(cursor.getCount()>0){
            isWishlist = true;
            wishlist.setImageResource(R.drawable.wishlist_fill);
        }
        else{
            isWishlist = false;
            wishlist.setImageResource(R.drawable.wishlist_empty);
        }


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWishlist){
                    String deleteWishlistQuery = "DELETE FROM wishlist WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"' AND productid = '"+sp.getString(ConstantSp.productid, "")+"'";
                    db.execSQL(deleteWishlistQuery);
                    wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(ProductDetailActivity.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                    isWishlist = false;
                }
                else{
                    String insertWishlistQuery = "INSERT INTO wishlist VALUES(NULL,'"+sp.getString(ConstantSp.userid, "")+"','"+sp.getString(ConstantSp.productid, "")+"')";
                    db.execSQL(insertWishlistQuery);
                    wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(ProductDetailActivity.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    isWishlist = true;

                }
            }
        });





    }
}