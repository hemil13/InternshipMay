package com.example.internshipmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {
    Context Context;
    ArrayList<CartList> arraylist;
    SQLiteDatabase db;
    SharedPreferences sp;
    public CartAdapter(Context Context, ArrayList<CartList> arrayList, SQLiteDatabase db) {
        this.Context = Context;
        this.arraylist = arrayList;
        this.db = db;

        sp = Context.getSharedPreferences(ConstantSp.pref, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image, plus, minus;
        TextView name, price, qty;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_image);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            plus = itemView.findViewById(R.id.cart_qty_add);
            minus = itemView.findViewById(R.id.cart_qty_substract);
            qty = itemView.findViewById(R.id.cart_qty);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyHolder holder, int position) {
        holder.name.setText(arraylist.get(position).getName());
        holder.price.setText(ConstantSp.ruppees + arraylist.get(position).getPrice() + " * "+ arraylist.get(position).getQty() + " = " + arraylist.get(position).getTotalPrice());
        holder.image.setImageResource(arraylist.get(position).getImage());
        holder.qty.setText(String.valueOf(arraylist.get(position).getQty()));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.productid, String.valueOf(arraylist.get(position).getProductid())).commit();
                sp.edit().putString(ConstantSp.prod_name, arraylist.get(position).getName()).commit();
                sp.edit().putInt(ConstantSp.prod_image, arraylist.get(position).getImage()).commit();
                sp.edit().putString(ConstantSp.prod_price, arraylist.get(position).getPrice()).commit();
                sp.edit().putString(ConstantSp.prod_description, arraylist.get(position).getDescription()).commit();

                Intent intent = new Intent(Context, ProductDetailActivity.class);
                Context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


}
