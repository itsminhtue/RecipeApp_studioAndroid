package com.example.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.adapter.MealAdapter;
import com.example.recipeapp.model.MealResponse;
import com.example.recipeapp.network.ApiService;
import com.example.recipeapp.network.RetrofitClient;
import com.example.recipeapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MealAdapter adapter;
    private ApiService api;

    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = RetrofitClient.getInstance().create(ApiService.class);

                binding.rvMeals.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MealAdapter(this, new ArrayList<>());
        binding.rvMeals.setAdapter(adapter);

                loadMeals();

                binding.btnFavorites.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        });

                binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    searchMeals(query.trim());
                } else {
                    Toast.makeText(MainActivity.this, "Nhập từ khóa để tìm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) searchHandler.removeCallbacks(searchRunnable);
                searchRunnable = () -> {
                    if (!newText.trim().isEmpty()) {
                        searchMeals(newText.trim());
                    }
                };
                searchHandler.postDelayed(searchRunnable, 600);
                return true;
            }
        });
    }

    private void loadMeals() {
        binding.progressBar.setVisibility(android.view.View.VISIBLE);
        api.getMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateMeals(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMeals(String keyword) {
        binding.progressBar.setVisibility(android.view.View.VISIBLE);
        api.searchMeals(keyword).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    adapter.updateMeals(response.body().getMeals());
                } else {
                    adapter.updateMeals(new ArrayList<>());                     Toast.makeText(MainActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(MainActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network fail: " + t.getMessage(), t);
            }
        });
    }
}
