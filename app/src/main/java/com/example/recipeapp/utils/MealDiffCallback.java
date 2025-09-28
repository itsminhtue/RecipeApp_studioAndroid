package com.example.recipeapp.utils;

import androidx.recyclerview.widget.DiffUtil;
import com.example.recipeapp.model.Meal;
import java.util.List;

public class MealDiffCallback extends DiffUtil.Callback {
    private final List<Meal> oldList;
    private final List<Meal> newList;

    public MealDiffCallback(List<Meal> oldList, List<Meal> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() { return oldList.size(); }

    @Override
    public int getNewListSize() { return newList.size(); }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getIdMeal()
                .equals(newList.get(newItemPosition).getIdMeal());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
