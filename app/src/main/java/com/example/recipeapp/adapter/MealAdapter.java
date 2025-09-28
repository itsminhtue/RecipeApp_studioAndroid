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

        // Set text
        holder.tvMealName.setText(meal.getStrMeal());

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_placeholder) // ảnh mặc định khi chờ load
                .error(R.drawable.ic_error)       // ảnh nếu load lỗi
                .into(holder.imgMeal);

        // Khi click thì mở DetailActivity và gửi đủ dữ liệu
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
