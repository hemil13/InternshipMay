package com.example.internshipmay;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    CardView cart_bottom_layout;
    TextView totalprice;
    Button checkout;

    RelativeLayout cart_bottom_layout_container;
    RecyclerView cart_page_recycler;

    SQLiteDatabase db;
    SharedPreferences sp;

    ArrayList<CartList> arrayList;

    Integer iCartTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sp=getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

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

        String cartQuery = "CREATE TABLE IF NOT EXISTS cart(cartid INTEGER PRIMARY KEY AUTOINCREMENT ,orderid INTEGER(10),userid INTEGER, productid INTEGER, qty INTEGER(3), price VARCHAR(10), TotalPrice VARCHAR(10))";
        db.execSQL(cartQuery);


        cart_bottom_layout = findViewById(R.id.cart_bottom_layout);
        totalprice = findViewById(R.id.cart_bottom_total);
        checkout = findViewById(R.id.cart_bottom_checkout);
        cart_bottom_layout_container = findViewById(R.id.cart_bottom_layout_container);
        cart_page_recycler = findViewById(R.id.cart_page_recycler);


        cart_page_recycler.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        cart_page_recycler.setItemAnimator(new DefaultItemAnimator());



        String selectCartQuery = "SELECT * FROM cart WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"'AND orderid = '0'";
        Cursor cursor = db.rawQuery(selectCartQuery,null);

        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                CartList list = new CartList();
                list.setCartid(cursor.getString(0));

                String selectProductQuery = "SELECT * FROM product WHERE productid = '" + cursor.getInt(2) + "'";
                Cursor cursor1 = db.rawQuery(selectProductQuery, null);

                if (cursor1.getCount() > 0) {
                    while(cursor1.moveToNext()){
                        list.setProductid(cursor1.getString(0));
                        list.setSubcategoryid(cursor1.getString(1));
                        list.setName(cursor1.getString(2));
                        list.setImage(cursor1.getInt(3));
                        list.setPrice(cursor1.getString(4));
                        list.setDescription(cursor1.getString(5));
                    }
                }
                list.setQty(cursor.getString(4));
                list.setTotalPrice(cursor.getString(6));

                iCartTotal += Integer.parseInt(list.getTotalPrice());

                cursor1.close();
                arrayList.add(list);
            }

            CartAdapter adapter = new CartAdapter(CartActivity.this, arrayList, db);
            cart_page_recycler.setAdapter(adapter);

            totalprice.setText("Total = " + ConstantSp.ruppees + iCartTotal);

        }



    }
}