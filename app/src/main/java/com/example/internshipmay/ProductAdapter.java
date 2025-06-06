package com.example.internshipmay;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> arrayList;

    SharedPreferences sp;
    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {

        this.context = context;
        this.arrayList = arrayList;

        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image, wishlist;
        TextView name, price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            wishlist = itemView.findViewById(R.id.item_product_cart_wishlist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {
        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice());

        if(arrayList.get(position).isWishlist()){
            holder.wishlist.setImageResource(R.drawable.wishlist_fill);
        } else{
            holder.wishlist.setImageResource(R.drawable.wishlist_empty);
        }

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
