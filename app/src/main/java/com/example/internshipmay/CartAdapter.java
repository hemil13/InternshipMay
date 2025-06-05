package com.example.internshipmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {
    Context Context;
    ArrayList<CartList> arraylist;
    SQLiteDatabase db;
    SharedPreferences sp;
    int iqty = 0;
    ImageView cart, cart_layout;
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


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iqty  = Integer.parseInt(arraylist.get(position).getQty());
                iqty++;
                updateMethod(iqty,"plus", position);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iqty--;
                if(iqty==0){
                    updateMethod(iqty, "Delete", position);
                }
                else{
                    updateMethod(iqty, "minus", position);
                }
            }
        });
    }
    private void updateMethod(int iqty, String type, int position) {
        int iProductAmount = Integer.parseInt(arraylist.get(position).getPrice());
        int iTotalPrice = iqty*iProductAmount;

        if(type.equalsIgnoreCase("plus") || type.equalsIgnoreCase("minus")){
            String updateCartQty = "UPDATE cart SET qty = '"+iqty+"', TotalPrice = '"+iTotalPrice+"' WHERE  cartid = '"+arraylist.get(position).getCartid()+"'";
            db.execSQL(updateCartQty);

            if(type.equalsIgnoreCase("plus")){
                CartActivity.iCartTotal += iProductAmount;
            }
            else {
                CartActivity.iCartTotal -= iProductAmount;
            }
            CartActivity.totalprice.setText("Total = " + ConstantSp.ruppees + CartActivity.iCartTotal);

            CartList list = new CartList();
            list.setCartid(arraylist.get(position).getCartid());
            list.setProductid(arraylist.get(position).getProductid());
            list.setSubcategoryid(arraylist.get(position).getSubcategoryid());
            list.setName(arraylist.get(position).getName());
            list.setImage(arraylist.get(position).getImage());
            list.setPrice(arraylist.get(position).getPrice());
            list.setDescription(arraylist.get(position).getDescription());
            list.setQty(arraylist.get(position).getQty());
            list.setTotalPrice(arraylist.get(position).getTotalPrice());
            arraylist.set(position, list);
            notifyDataSetChanged();

        }
        else{
            String deleteCartQty = "DELETE FROM cart WHERE cartid = '"+arraylist.get(position).getCartid()+"'";
            db.execSQL(deleteCartQty);
//            qty.setText(String.valueOf(iqty));
            arraylist.remove(position);
            notifyDataSetChanged();
            Toast.makeText(Context, "Removed From Cart", Toast.LENGTH_SHORT).show();
            CartActivity.iCartTotal -= iProductAmount;
            CartActivity.totalprice.setText("Total = " + ConstantSp.ruppees + CartActivity.iCartTotal);
        }
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


}
