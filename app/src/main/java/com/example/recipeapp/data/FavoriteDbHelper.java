package com.example.recipeapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorites.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "favorites";
    public static final String COL_ID = "id";        // mealId
    public static final String COL_NAME = "name";
    public static final String COL_THUMB = "thumb";  // link ảnh

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_ID + " TEXT PRIMARY KEY," +
                        COL_NAME + " TEXT," +
                        COL_THUMB + " TEXT" +
                        ");";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
