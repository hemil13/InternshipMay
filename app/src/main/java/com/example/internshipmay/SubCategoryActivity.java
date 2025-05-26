package com.example.internshipmay;

import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_category);

        subcategory_recycler = findViewById(R.id.subcategory_recycler);

        subcategory_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        subcategory_recycler.setItemAnimator(new DefaultItemAnimator());


        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        arrayList = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            if(Integer.parseInt(sp.getString(ConstantSp.categoryid , "")) == categoryidArray[i]){

                SubcategoryList subcategoryList = new SubcategoryList();
                subcategoryList.setCategoryid(categoryidArray[i]);
                subcategoryList.setSubcategoryid(idArray[i]);
                subcategoryList.setName(nameArray[i]);
                subcategoryList.setImage(imageArray[i]);
                arrayList.add(subcategoryList);
            }
            }



        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayList);
        subcategory_recycler.setAdapter(adapter);

    }
}