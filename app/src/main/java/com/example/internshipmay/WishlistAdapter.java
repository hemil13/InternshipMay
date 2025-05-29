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

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {

    Context context;
    ArrayList<WishlistList> arrayList;
    SharedPreferences sp;

    SQLiteDatabase db;

    public WishlistAdapter(Context context, ArrayList<WishlistList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;

        sp = context.getSharedPreferences(ConstantSp.pref, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public WishlistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false);
        return new WishlistAdapter.MyHolder(view);
    }

    public class MyHolder extends  RecyclerView.ViewHolder{
        ImageView image, remove;
        TextView name, price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.wishlist_image);
            name = itemView.findViewById(R.id.wishlist_name);
            price = itemView.findViewById(R.id.wishlist_price);
            remove = itemView.findViewById(R.id.custom_wishlist_remove);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyHolder holder, int position) {
        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteWishlistQuery = "DELETE FROM wishlist WHERE wishlistid = '"+arrayList.get(position).getWishlistid()+"'";
                db.execSQL(deleteWishlistQuery);
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.productid, String.valueOf(arrayList.get(position).getProductid())).commit();
                sp.edit().putString(ConstantSp.prod_name, arrayList.get(position).getName()).commit();
                sp.edit().putInt(ConstantSp.prod_image, arrayList.get(position).getImage()).commit();
                sp.edit().putString(ConstantSp.prod_price, arrayList.get(position).getPrice()).commit();
                sp.edit().putString(ConstantSp.prod_description, arrayList.get(position).getDescription()).commit();

                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
