package com.example.vitstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vitstop.AllItems;
import com.example.vitstop.Features.CategoryFragment;
import com.example.vitstop.ItemActivity;
import com.example.vitstop.R;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private final List<CategoryFragment> listCategory;
    public CategoryAdapter(Context context, List<CategoryFragment> listCategory){
        this.context = context;
        this.listCategory = listCategory;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);  //check this line
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listCategory.get(position).getImg_url()).into(holder.typeImage);
        holder.typeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AllItems.class);
                intent.putExtra("type",listCategory.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView typeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeImage = itemView.findViewById(R.id.category_img);
        }
    }
}
