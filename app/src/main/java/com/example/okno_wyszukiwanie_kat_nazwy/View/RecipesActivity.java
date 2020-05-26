package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.DatabaseAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.GridSpacingItemDecoration;
import com.example.okno_wyszukiwanie_kat_nazwy.Interfaces.OnItemClickListener;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Recipe;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.RecipesAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.SD;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.StaticMethods;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecipesActivity extends AppCompatActivity  {

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerView mRecycleView;
    private RecipesAdapter mRecipesAdapter;
    private DatabaseAdapter databaseAdapter;

    private static boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        databaseAdapter = new DatabaseAdapter(RecipesActivity.this);

        Bundle extras = getIntent().getExtras();
        String filter = extras.getString("filterKey");
        mode = extras.getBoolean("favouriteModeKey", false);

        mRecycleView = findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(
                2, GridSpacingItemDecoration.ConvertDpToPixels(this, 10), true));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        if(mode){
            getFavouriteRecipesFromDb();
        }
          else {
            getRecipesFromJSON(filter);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if(mode) {
            getFavouriteRecipesFromDb();
        }
    }

    private void getRecipesFromJSON(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(SD.getTag(), response.toString());
                try {
                    JSONArray recipesJSONArray = response.getJSONArray("meals");

                    for(int i = 0; i < recipesJSONArray.length(); i++)
                    {
                        JSONObject object = recipesJSONArray.getJSONObject(i);
                        recipes.add(new Recipe(
                                object.getString("strMeal"),
                                object.getString("strMealThumb"),
                                object.getString("idMeal")
                        ));

                    }

                    mRecipesAdapter = new RecipesAdapter(RecipesActivity.this, recipes);
                    mRecycleView.setAdapter(mRecipesAdapter);

                    mRecipesAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String id = recipes.get(position).getRecipeId();

                            Intent intent = new Intent(RecipesActivity.this, RecipeDetailsActivity.class);
                            intent.putExtra("recipeKey", SD.getRecipeById() + id);
                            RecipesActivity.this.startActivity(intent);
                        }
                    });

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
                Toast.makeText(RecipesActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFavouriteRecipesFromDb() {
        recipes = databaseAdapter.getAllFavouriteRecipesFromDb();
        if(recipes.size() == 0) {
            StaticMethods.setAlertDialog(RecipesActivity.this, "Alert",
                    "No favourite recipes added!", "OK");
        }
        else{
            mRecipesAdapter = new RecipesAdapter(RecipesActivity.this, recipes);
            mRecycleView.setAdapter(mRecipesAdapter);

            mRecipesAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String id = recipes.get(position).getRecipeId();
                    Intent favouriteRecipeIntent = new Intent(RecipesActivity.this, RecipeDetailsActivity.class);
                    favouriteRecipeIntent.putExtra("favouriteRecipeIdKey", id);
                    favouriteRecipeIntent.putExtra("favouriteDetailsModeKey", true);
                    startActivity(favouriteRecipeIntent);
                }
            });
        }

    }
}
