package com.example.recipeapp.network;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("filter.php?c=Seafood")
    Call<MealResponse> getMeals();

    @GET("lookup.php")
    Call<MealDetailResponse> getMealDetail(@Query("i") String idMeal);

    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String query);

}
