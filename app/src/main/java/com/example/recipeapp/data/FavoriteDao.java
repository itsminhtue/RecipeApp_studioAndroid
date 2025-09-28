package com.example.recipeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDao {
    private FavoriteDbHelper dbHelper;

    public FavoriteDao(Context context) {
        dbHelper = new FavoriteDbHelper(context);
    }

    public void addFavorite(String id, String name, String thumb) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteDbHelper.COL_ID, id);
        values.put(FavoriteDbHelper.COL_NAME, name);
        values.put(FavoriteDbHelper.COL_THUMB, thumb);
        db.insertWithOnConflict(FavoriteDbHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void removeFavorite(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(FavoriteDbHelper.TABLE_NAME, FavoriteDbHelper.COL_ID + "=?", new String[]{id});
        db.close();
    }

    public boolean isFavorite(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FavoriteDbHelper.TABLE_NAME,
                null,
                FavoriteDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public List<FavoriteItem> getAllFavorites() {
        List<FavoriteItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FavoriteDbHelper.TABLE_NAME,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDbHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDbHelper.COL_NAME));
            String thumb = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDbHelper.COL_THUMB));
            list.add(new FavoriteItem(id, name, thumb));
        }
        cursor.close();
        db.close();
        return list;
    }
}
