package com.example.vitstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vitstop.DetailActivity;
import com.example.vitstop.Features.Items;
import com.example.vitstop.R;

import org.w3c.dom.Text;

import java.util.List;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {
    Context applicationContext;
    List<Items> allitemsList;
    public AllItemsAdapter(Context applicationContext, List<Items> allitemsList) {
        this.applicationContext=applicationContext;
        this.allitemsList = allitemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.single_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mcost.setText("₹ "+allitemsList.get(position).getPrice());
        holder.mName.setText(allitemsList.get(position).getName());
        Glide.with(applicationContext).load(allitemsList.get(position).getImg_url()).into(holder.mItemImg);
        holder.mItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(applicationContext, DetailActivity.class);
                intent.putExtra("detail", allitemsList.get(position));
                applicationContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allitemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mItemImg;
        private TextView mcost;
        private TextView mName;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemImg = itemView.findViewById(R.id.Allitem_img);
            mcost = itemView.findViewById(R.id.Allitem_cost);
            mName = itemView.findViewById(R.id.Allitem_name);
        }
    }

}
