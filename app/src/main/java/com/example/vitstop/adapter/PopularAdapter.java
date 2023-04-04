package com.example.vitstop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vitstop.DetailActivity;
import com.example.vitstop.Features.Popular;
import com.example.vitstop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    Context context;
    List<Popular> listPopular;

    public PopularAdapter(Context context, List<Popular> listPopular){
        this.context = context;
        this.listPopular = listPopular;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_popular_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mpprice.setText(listPopular.get(position).getPrice()+"₹");
        holder.mpname.setText(listPopular.get(position).getName());
        Glide.with(context).load(listPopular.get(position).getImg_url()).into(holder.mpimg);
        holder.mpimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", listPopular.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mpimg;
        TextView mpprice;
        TextView mpname;

        public ViewHolder(View itemView) {
            super(itemView);
            mpimg = itemView.findViewById(R.id.pimg);
            mpprice = itemView.findViewById(R.id.pprice);
            mpname = itemView.findViewById(R.id.pname);
        }
    }
}
