package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.ListViewAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Category;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Country;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.SD;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.StaticMethods;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    private static String searchViewUrl = SD.getReceipeByName();
    private static String listViewUrl = SD.getRecipeByCategory();

    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Country> countries = new ArrayList<>();

    private String[] categoryNamesArray = new String[categories.size()];
    private String[] categoryImagesArray = new String[categories.size()];

    private String[] countryNamesArray = new String[countries.size()];

    private SearchView mSearchView;
    private ListView mListView;

    private ListViewAdapter listViewAdapterCategory;
    private ListViewAdapter listViewAdapterCountry;

    private DrawerLayout mDrawerLayout;
    private MenuItem mCategory;
    private MenuItem mCountry;
    private MenuItem mRandomRecipe;
    private MenuItem mFavouriteRecipes;
    private MenuItem mGoogleMaps;
    private MenuItem mTimer;
    private MenuItem mMarketList;
    private NavigationView mNavigationView;
    private Menu menu;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        getCategoriesFromJson(SD.getCategoriesUrl());
        mListView = findViewById(R.id.listView);
        mSearchView = findViewById(R.id.nameSearchView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent listViewIntent = new Intent(MainActivity.this, RecipesActivity.class);
                listViewIntent.putExtra("filterKey", listViewUrl +
                        mListView.getItemAtPosition(position).toString());
                MainActivity.this.startActivity(listViewIntent);


            }
        });


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchBoxIntent = new Intent(MainActivity.this, RecipesActivity.class);
                searchBoxIntent.putExtra("filterKey", searchViewUrl +
                        mSearchView.getQuery());
                MainActivity.this.startActivity(searchBoxIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mDrawerLayout= findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = findViewById(R.id.navigationview);
        menu=mNavigationView.getMenu();
        mCategory = menu.findItem(R.id.categoryAndName);
        mCountry = menu.findItem(R.id.countryAndIngredient);
        mRandomRecipe = menu.findItem(R.id.randomRecipe);
        mFavouriteRecipes = menu.findItem(R.id.favouriteRecipe);
        mGoogleMaps = menu.findItem(R.id.map);
        mTimer = menu.findItem(R.id.timer);
        mMarketList = menu.findItem(R.id.shoppingList);

        mCategory.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getCategoriesFromJson(SD.getCategoriesUrl());
                searchViewUrl = SD.getReceipeByName();
                listViewUrl = SD.getRecipeByCategory();
                mSearchView.setQueryHint("Recipe Name");
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });

        mCountry.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getCountriesFromJson(SD.getCountriesUrl());
                searchViewUrl = SD.getRecipeByIngredient();
                listViewUrl = SD.getRecipeByArea();
                mSearchView.setQueryHint("Ingredient Name");
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });

        mRandomRecipe.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent randomRecipeIntent = new Intent(MainActivity.this, RandomRecipeActivity.class);
                MainActivity.this.startActivity(randomRecipeIntent);
                return false;
            }
        });

        mFavouriteRecipes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent favouriteModeIntent = new Intent(MainActivity.this, RecipesActivity.class);
                favouriteModeIntent.putExtra("favouriteModeKey", true);
                startActivity(favouriteModeIntent);
                return false;
            }
        });

       mGoogleMaps.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               Intent googleMapsIntent = new Intent(MainActivity.this, GoogleMapsActivity.class);
               MainActivity.this.startActivity(googleMapsIntent);
               return false;
           }
       });
       mMarketList.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               Intent MarketListIntent = new Intent(MainActivity.this , ShoppingActivity.class);
               MainActivity.this.startActivity(MarketListIntent);
               return false;
           }
       });

        mTimer.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent timerIntent = new Intent(MainActivity.this, Timer2.class);
                MainActivity.this.startActivity(timerIntent);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected((item))){
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public static Context getMainActivityContext()
    {
        return MainActivity.context;
    }

    private void getCategoriesFromJson(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray categoriesJSONArray = response.getJSONArray("categories");

                    for(int i = 0; i < categoriesJSONArray.length(); i++)
                    {
                        JSONObject object = categoriesJSONArray.getJSONObject(i);
                        categories.add(new Category(
                                object.getString("idCategory"),
                                object.getString("strCategory"),
                                object.getString("strCategoryThumb")
                        ));
                    }
                    categoryNamesArray = StaticMethods.getCategoryNames(categories).toArray(categoryNamesArray);
                    categoryImagesArray = StaticMethods.getCategoryImages(categories).toArray(categoryImagesArray);

                    listViewAdapterCategory = new ListViewAdapter(MainActivity.this, categoryNamesArray, categoryImagesArray);
                    mListView.setAdapter(listViewAdapterCategory);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(SD.getTag(), e.toString());
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCountriesFromJson(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray countriesJSONArray = response.getJSONArray("meals");

                    for(int i = 0; i < countriesJSONArray.length(); i++)
                    {
                        JSONObject object = countriesJSONArray.getJSONObject(i);
                        countries.add(new Country(
                                object.getString("strArea"),
                                SD.getCountryImages()[i]
                        ));

                    }

                    countryNamesArray = StaticMethods.getCountryNames(countries).toArray(countryNamesArray);

                    listViewAdapterCountry = new ListViewAdapter(MainActivity.this, countryNamesArray, SD.getCountryImages());
                    mListView.setAdapter(listViewAdapterCountry);

                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(SD.getTag(), e.toString());
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
