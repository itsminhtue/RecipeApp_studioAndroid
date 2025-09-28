package com.example.recipeapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recipeapp.adapter.FavoriteAdapter;
import com.example.recipeapp.data.FavoriteDao;
import com.example.recipeapp.data.FavoriteItem;
import com.example.recipeapp.databinding.ActivityFavoritesBinding;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private ActivityFavoritesBinding binding;
    private FavoriteDao favoriteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        favoriteDao = new FavoriteDao(this);

        List<FavoriteItem> list = favoriteDao.getAllFavorites();

        FavoriteAdapter adapter = new FavoriteAdapter(this, list);
        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavorites.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
                List<FavoriteItem> list = favoriteDao.getAllFavorites();
        binding.rvFavorites.setAdapter(new FavoriteAdapter(this, list));
    }
}
