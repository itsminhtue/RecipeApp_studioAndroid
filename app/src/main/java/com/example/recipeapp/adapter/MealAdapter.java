package com.example.recipeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.recipeapp.R;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.ui.DetailActivity;
import com.example.recipeapp.utils.MealDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private final Context context;
    private List<Meal> mealList = new ArrayList<>();

    public MealAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        if (mealList != null) {
            this.mealList.addAll(mealList);
        }
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        holder.tvMealName.setText(meal.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .thumbnail(0.1f)                 .diskCacheStrategy(DiskCacheStrategy.ALL)                 .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imgMeal);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("meal_id", meal.getIdMeal());
            intent.putExtra("meal_name", meal.getStrMeal());
            intent.putExtra("meal_thumb", meal.getStrMealThumb());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    /**
     * Cập nhật danh sách bằng DiffUtil để mượt hơn
     */
    public void updateMeals(List<Meal> newList) {
        if (newList == null) newList = new ArrayList<>();

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MealDiffCallback(mealList, newList));
        mealList.clear();
        mealList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName;
        ImageView imgMeal;

        MealViewHolder(View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            imgMeal = itemView.findViewById(R.id.imgMeal);
        }
    }
}
