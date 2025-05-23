package com.example.internshipmay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {


    Context context;
    int[] idArray;
    String[] nameArray;
    int[] imageArray;


    public CategoryAdapter(Context context, int[] idArray, String[] nameArray, int[] imageArray) {
        this.context = context;
        this.idArray = idArray;
        this.nameArray = nameArray;
        this.imageArray = imageArray;


    }

    @NonNull
    @Override
    public CategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView category_image;
        TextView category_text;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            category_image = itemView.findViewById(R.id.category_image);
            category_text = itemView.findViewById(R.id.category_text);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyHolder holder, int position) {
            holder.category_image.setImageResource(imageArray[position]);
            holder.category_text.setText(nameArray[position]);
    }

    @Override
    public int getItemCount() {
        return nameArray.length;
    }


}
