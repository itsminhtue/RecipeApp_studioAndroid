package com.example.recipeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.data.FavoriteItem;
import com.example.recipeapp.ui.DetailActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {
    private Context context;
    private List<FavoriteItem> list;

    public FavoriteAdapter(Context context, List<FavoriteItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FavoriteItem item = list.get(position);
        holder.tvName.setText(item.name);

        if (item.thumb != null && !item.thumb.isEmpty()) {
            Glide.with(context)
                    .load(item.thumb)
                    .placeholder(R.drawable.ic_placeholder)                     .error(R.drawable.ic_error)                           .into(holder.imgThumb);
        } else {
            holder.imgThumb.setImageResource(R.drawable.ic_placeholder);
        }

                holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("meal_id", item.id);
            i.putExtra("meal_name", item.name);
            i.putExtra("meal_thumb", item.thumb);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView tvName;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
