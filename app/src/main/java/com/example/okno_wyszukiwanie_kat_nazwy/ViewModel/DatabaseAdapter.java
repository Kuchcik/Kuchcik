package com.example.okno_wyszukiwanie_kat_nazwy.ViewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.ProductModel;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Recipe;

import java.util.ArrayList;

public class DatabaseAdapter extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Kuchcik.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_RECIPES = "recipes_table";

    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_NAME = "recipe_name";
    public static final String COL_CATEGORY = "category";
    public static final String COL_AREA = "area";
    public static final String COL_INSTRUCTIONS = "instructions";
    public static final String COL_RECIPE_IMAGE = "recipe_image";
    public static final String COL_YOUTUBE_URL = "youtube_url";
    public static final String COL_INGREDIENTS = "ingredients";
    public static final String COL_MEASURES = "measures";
    public static final String TABLE_PRODUCTS = "products_table";
    public static final String COL_PRODUCT_ID = "product_id";
    public static final String COL_PRODUCT_NAME = "product_name";
    public static final String COL_PRODUCT_QUANTITY = "product_quantity";

    public static final String PRODUCT_CREATE_STATEMENT = "CREATE TABLE " + TABLE_PRODUCTS + " (" + COL_PRODUCT_ID + " TEXT PRIMARY KEY, " + COL_PRODUCT_NAME + " TEXT, " + COL_PRODUCT_QUANTITY + " TEXT" + ")";

    public static final String RECIPES_CREATE_STATEMENT = "CREATE TABLE " + TABLE_RECIPES + " (" + COL_RECIPE_ID + " TEXT PRIMARY KEY, "
            + COL_RECIPE_NAME + " TEXT, " + COL_CATEGORY + " TEXT, " + COL_AREA + " TEXT, " + COL_INSTRUCTIONS + " TEXT, "
            + COL_RECIPE_IMAGE + " TEXT, " + COL_YOUTUBE_URL + " TEXT, " + COL_INGREDIENTS + " TEXT, " + COL_MEASURES + " TEXT" + ")";


    public DatabaseAdapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECIPES_CREATE_STATEMENT);
        db.execSQL(PRODUCT_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean insertFavouriteRecipeToDb(String id, String name, String category, String area, String instructions,
                                             String image, String youtube, String ingredients, String measures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RECIPE_ID, id);
        contentValues.put(COL_RECIPE_NAME, name);
        contentValues.put(COL_CATEGORY, category);
        contentValues.put(COL_AREA, area);
        contentValues.put(COL_INSTRUCTIONS, instructions);
        contentValues.put(COL_RECIPE_IMAGE, image);
        contentValues.put(COL_YOUTUBE_URL, youtube);
        contentValues.put(COL_INGREDIENTS, ingredients);
        contentValues.put(COL_MEASURES, measures);
        long result = db.insert(TABLE_RECIPES, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertShoppingListToDB(String id, String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRODUCT_ID, id);
        contentValues.put(COL_PRODUCT_NAME, name);
        contentValues.put(COL_PRODUCT_QUANTITY, quantity);
        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getRecipeIdsFromDb() {
        ArrayList<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_RECIPE_ID + " FROM " + TABLE_RECIPES, null);
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(cursor.getColumnIndex(COL_RECIPE_ID));
                    ids.add(id);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            Log.d(SD.getTag(), e.getMessage());
            return null;
        } finally {
            cursor.close();
            db.close();
        }
        return ids;
    }

    public void removeProductFromDB(ArrayList<ProductModel> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 2);
        for (int i = 0; i < list.size(); i++) {
            insertShoppingListToDB(String.valueOf(i), list.get(i).getProductName(), list.get(i).getQuantity());
        }
    }

    public ArrayList<ProductModel> getAllProductsFromDB() {
        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ProductModel> productList = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    ProductModel product = new ProductModel(
                            cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_PRODUCT_QUANTITY)),
                            cursor.getString(cursor.getColumnIndex(COL_PRODUCT_ID))
                    );
                    productList.add(product);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.d(SD.getTag(), e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }
        return productList;
    }

    public ArrayList<Recipe> getAllFavouriteRecipesFromDb() {
        String query = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Recipe recipe = new Recipe(
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_ID))
                    );
                    recipes.add(recipe);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.d(SD.getTag(), e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }
        return recipes;

    }

    public Recipe getFavouriteRecipeDetailsById(String id) {
        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COL_RECIPE_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Recipe favouriteRecipe;
        try {
            if (cursor.moveToFirst()) {
                do {
                    favouriteRecipe = new Recipe(
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_ID)),
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_NAME)),
                            cursor.getString(cursor.getColumnIndex(COL_CATEGORY)),
                            cursor.getString(cursor.getColumnIndex(COL_AREA)),
                            cursor.getString(cursor.getColumnIndex(COL_INSTRUCTIONS)),
                            cursor.getString(cursor.getColumnIndex(COL_RECIPE_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(COL_YOUTUBE_URL)),
                            StaticMethods.convertStringToArrayList(cursor.getString(cursor.getColumnIndex(COL_INGREDIENTS))),
                            StaticMethods.convertStringToArrayList(cursor.getString(cursor.getColumnIndex(COL_MEASURES)))
                    );
                    return favouriteRecipe;
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.d(SD.getTag(), e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }
    public boolean deleteRecipeFromFavouritesById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RECIPES, COL_RECIPE_ID + "=" + id, null) > 0;
    }
}
