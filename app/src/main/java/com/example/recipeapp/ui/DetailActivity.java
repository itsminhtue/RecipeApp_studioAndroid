package com.example.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.data.FavoriteDao;
import com.example.recipeapp.databinding.ActivityDetailBinding;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.MealDetail;
import com.example.recipeapp.network.ApiService;
import com.example.recipeapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private FavoriteDao favoriteDao;
    private String mealId, mealName, mealThumb;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận dữ liệu từ Intent
        mealId = getIntent().getStringExtra("meal_id");
        mealName = getIntent().getStringExtra("meal_name");
        mealThumb = getIntent().getStringExtra("meal_thumb");

        // Load chi tiết món ăn từ API
        if (mealId != null) {
            loadMealDetail(mealId);
        }

        // Xử lý Yêu thích
        favoriteDao = new FavoriteDao(this);
        isFavorite = favoriteDao.isFavorite(mealId);
        updateFavoriteButton();

        binding.btnFavorite.setOnClickListener(v -> {
            if (isFavorite) {
                favoriteDao.removeFavorite(mealId);
                isFavorite = false;
                Toast.makeText(this, "Đã xóa khỏi Yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                favoriteDao.addFavorite(mealId, mealName, mealThumb);
                isFavorite = true;
                Toast.makeText(this, "Đã thêm vào Yêu thích", Toast.LENGTH_SHORT).show();
            }
            updateFavoriteButton();
        });
    }

    private void loadMealDetail(String id) {
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.getMealDetail(id).enqueue(new Callback<MealDetailResponse>() {
            @Override
            public void onResponse(Call<MealDetailResponse> call, Response<MealDetailResponse> response) {
                if (isFinishing() || isDestroyed()) {
                    return; // Activity đã đóng, không làm gì nữa
                }

                if (response.isSuccessful() && response.body() != null && !response.body().getMeals().isEmpty()) {
                    MealDetail meal = response.body().getMeals().get(0);

                    mealName = meal.getStrMeal();
                    mealThumb = meal.getStrMealThumb();

                    binding.tvMealTitle.setText(meal.getStrMeal());
                    binding.tvInstructions.setText(meal.getStrInstructions());

                    // Hiển thị danh sách nguyên liệu
                    StringBuilder ingBuilder = new StringBuilder();
                    for (String ing : meal.getIngredientsList()) {
                        ingBuilder.append("• ").append(ing).append("\n");
                    }
                    binding.tvIngredientsList.setText(ingBuilder.toString());

                    // Load ảnh an toàn
                    if (!isFinishing() && !isDestroyed()) {
                        Glide.with(DetailActivity.this)
                                .load(meal.getStrMealThumb())
                                .placeholder(R.drawable.ic_placeholder)
                                .error(R.drawable.ic_error)
                                .into(binding.imgDetail);
                    }
                }
            }

            @Override
            public void onFailure(Call<MealDetailResponse> call, Throwable t) {
                if (!isFinishing() && !isDestroyed()) {
                    Toast.makeText(DetailActivity.this, "Lỗi tải chi tiết: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateFavoriteButton() {
        binding.btnFavorite.setText(isFavorite ? "Bỏ Yêu thích" : "Thêm vào Yêu thích");
    }
}
