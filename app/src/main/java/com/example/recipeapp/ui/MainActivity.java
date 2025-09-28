package com.example.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.recipeapp.R;
import com.example.recipeapp.adapter.MealAdapter;
import com.example.recipeapp.model.MealResponse;
import com.example.recipeapp.network.ApiService;
import com.example.recipeapp.network.RetrofitClient;
import com.example.recipeapp.databinding.ActivityMainBinding;
import retrofit2.*;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Sự kiện bấm nút Yêu thích
        binding.btnFavorites.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(i);
        });

        binding.rvMeals.setLayoutManager(new LinearLayoutManager(this));

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.getMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.rvMeals.setAdapter(
                            new MealAdapter(MainActivity.this, response.body().getMeals()));
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
