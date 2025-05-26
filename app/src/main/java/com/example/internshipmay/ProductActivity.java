package com.example.internshipmay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView product_recycler;

    int[] subcategoryidArray = {3,3,4,4};
    int[] idArray = {1,2,3,4};
    String[] nameArray = {"Redmi", "Oneplus", "Noise", "Airpods Max"};
    int[] imageArray = {R.drawable.redmi, R.drawable.oneplus, R.drawable.noise, R.drawable.airpods_max};

    String[] priceArray = {"$100", "$200", "$300", "$400"};
    String [] descriptionArray = {"This is a description", "This is a description", "This is a description", "This is a description"};

    SharedPreferences sp;

    ArrayList<ProductList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product_recycler = findViewById(R.id.product_recycler);

        product_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        product_recycler.setItemAnimator(new DefaultItemAnimator());

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        arrayList = new ArrayList<>();

        for(int i = 0; i<nameArray.length; i++){
            if(Integer.parseInt(sp.getString(ConstantSp.subcategoryid, "")) == subcategoryidArray[i]){
                ProductList productList = new ProductList();
                productList.setSubcategoryid(subcategoryidArray[i]);
                productList.setProductid(idArray[i]);
                productList.setName(nameArray[i]);
                productList.setImage(imageArray[i]);
                productList.setPrice(priceArray[i]);
                productList.setDescription(descriptionArray[i]);
                arrayList.add(productList);
            }

        }

        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
        product_recycler.setAdapter(adapter);


    }


}