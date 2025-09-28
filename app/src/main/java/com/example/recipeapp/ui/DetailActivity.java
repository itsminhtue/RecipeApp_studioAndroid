package com.example.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.recipeapp.data.FavoriteDao;
import com.example.recipeapp.databinding.ActivityDetailBinding;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.network.ApiService;
import com.example.recipeapp.network.RetrofitClient;
import retrofit2.*;

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

        mealId = getIntent().getStringExtra("meal_id");
        mealName = getIntent().getStringExtra("meal_name");
        mealThumb = getIntent().getStringExtra("meal_thumb");

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

    private void updateFavoriteButton() {
        if (isFavorite) {
            binding.btnFavorite.setText("Bỏ Yêu thích");
        } else {
            binding.btnFavorite.setText("Thêm vào Yêu thích");
        }
    }
}
