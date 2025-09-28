package com.example.recipeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.ui.DetailActivity;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private Context context;
    private List<Meal> mealList;

    public MealAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
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
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.imgMeal);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("meal_id", meal.getIdMeal());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return mealList.size(); }

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
