package com.example.okno_wyszukiwanie_kat_nazwy.ViewModel;

import android.net.Uri;

import com.example.okno_wyszukiwanie_kat_nazwy.R;

public class SD {

    //TAG
    private static final String TAG = "Kuchcik";

    //URL's
    private static final String CATEGORIES_URL = "https://www.themealdb.com/api/json/v1/1/categories.php";
    private static final String COUNTRIES_URL = "https://www.themealdb.com/api/json/v1/1/list.php?a=list";
    private static final String RECIPE_BY_NAME = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    private static final String RECIPE_BY_INGREDIENT = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
    private static final String RECIPE_BY_CATEGORY = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";
    private static final String RECIPE_BY_AREA = "https://www.themealdb.com/api/json/v1/1/filter.php?a=";
    private static final String RANDOM_RECIPE = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static final String RECIPE_BY_ID = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";


    //Images
    private static final String[] COUNTRY_IMAGES = {getImageFromDrawable(R.drawable.usa), getImageFromDrawable(R.drawable.uk),
    getImageFromDrawable(R.drawable.canada), getImageFromDrawable(R.drawable.china), getImageFromDrawable(R.drawable.holland),
    getImageFromDrawable(R.drawable.egypt), getImageFromDrawable(R.drawable.france), getImageFromDrawable(R.drawable.greece),
    getImageFromDrawable(R.drawable.india), getImageFromDrawable(R.drawable.ireland), getImageFromDrawable(R.drawable.italy),
    getImageFromDrawable(R.drawable.jamaica), getImageFromDrawable(R.drawable.japan), getImageFromDrawable(R.drawable.kenya),
    getImageFromDrawable(R.drawable.malaysia), getImageFromDrawable(R.drawable.mexico), getImageFromDrawable(R.drawable.morocco),
    getImageFromDrawable(R.drawable.russia), getImageFromDrawable(R.drawable.spain), getImageFromDrawable(R.drawable.thailand),
    getImageFromDrawable(R.drawable.tunisia), getImageFromDrawable(R.drawable.turkey), getImageFromDrawable(R.drawable.unknown),
    getImageFromDrawable(R.drawable.vietnam)};


    public static String getTag() {
        return TAG;
    }

    public static String getCategoriesUrl() {
        return CATEGORIES_URL;
    }

    public static String getCountriesUrl() {
        return COUNTRIES_URL;
    }

    public static String getReceipeByName() {return RECIPE_BY_NAME;}

    public static String getRecipeByIngredient() {return RECIPE_BY_INGREDIENT;}

    public static String getRecipeByCategory() {return RECIPE_BY_CATEGORY;}

    public static String getRecipeByArea() {return RECIPE_BY_AREA;}

    public static String[] getCountryImages() {return COUNTRY_IMAGES;}

    public static String getRandomRecipe() {return RANDOM_RECIPE;}

    public static String getRecipeById() {return RECIPE_BY_ID;}



    private static String getImageFromDrawable(int image) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +image).toString();
    }
}
