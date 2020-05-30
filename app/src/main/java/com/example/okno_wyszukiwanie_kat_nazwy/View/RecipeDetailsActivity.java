package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.Recipe;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.DatabaseAdapter;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.SD;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.StaticMethods;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ImageView mRecipeImageView;
    private Button mFavouritesButton, mYoutubeButton;
    private TextView mNameTextView, mCategoryTextView, mAreaTextView,
            mInstructionsTextView, mIngredientsTextView, mMeasuresTextView;
    Recipe recipe;

    DatabaseAdapter databaseAdapter;

    ScrollView mScrollView;
    ViewTreeObserver mViewTreeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Bundle extras = getIntent().getExtras();
        String recipeUrl = extras.getString("recipeKey");
        String recipeId = extras.getString("favouriteRecipeIdKey");
        boolean mode = extras.getBoolean("favouriteDetailsModeKey", false);

        databaseAdapter = new DatabaseAdapter(RecipeDetailsActivity.this);

        mRecipeImageView = findViewById(R.id.recipeImageView);
        mCategoryTextView = findViewById(R.id.categoryTextView);
        mAreaTextView = findViewById(R.id.areaTextView);
        mInstructionsTextView = findViewById(R.id.instructionsTextVew);
        mIngredientsTextView = findViewById(R.id.ingredientsTextView);
        mMeasuresTextView = findViewById(R.id.measuresTextView);
        mNameTextView = findViewById(R.id.nameTextView);
        mFavouritesButton = findViewById(R.id.favouritesButton);
        mYoutubeButton = findViewById(R.id.youtubeButton);

        mScrollView = findViewById(R.id.scroll_view);
        mViewTreeObserver = mScrollView.getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int measuresTextViewHeight = mMeasuresTextView.getHeight();
                int ingredientsTextViewHeight = mIngredientsTextView.getHeight();

                if(ingredientsTextViewHeight > measuresTextViewHeight) {
                    mMeasuresTextView.setHeight(ingredientsTextViewHeight);
                }
                else {
                    mIngredientsTextView.setHeight(measuresTextViewHeight);
                }
            }
        });

        if(mode) {
            getRecipeDetailsFromDb(recipeId);
        }
        else{
            getRecipeFromJSON(recipeUrl);
        }

        mYoutubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pierwszy sposób
                //Intent youtubeIntent = new Intent(RecipeDetailsActivity.this, YoutubeActivity.class);
                // youtubeIntent.putExtra("youtubeKey", recipe.getYoutubeUrl());
                // RecipeDetailsActivity.this.startActivity(youtubeIntent);

                //Drugi sposób
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getYoutubeUrl())));
            }
        });

        mFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(databaseAdapter.getRecipeIdsFromDb().contains(recipe.getRecipeId())) {
                    boolean isDeleted = databaseAdapter.deleteRecipeFromFavouritesById(recipe.getRecipeId());
                    if (isDeleted == true) {
                        Toast.makeText(RecipeDetailsActivity.this, "Recipe was deleted from favourites!", Toast.LENGTH_SHORT).show();
                        setFavouritesButtonIconColor("#ffffff");
                    } else {
                        Toast.makeText(RecipeDetailsActivity.this, "Cannot delete recipe from favourites!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String recipeIngredients = StaticMethods.convertStringArrayListToString(recipe.getIngredients());
                    String recipeMeasures = StaticMethods.convertStringArrayListToString(recipe.getMeasures());
                    boolean isInserted = databaseAdapter.insertFavouriteRecipeToDb(recipe.getRecipeId(), recipe.getRecipeName(), recipe.getCategoryName(),
                            recipe.getAreaName(), recipe.getInstrutions(), recipe.getRecipeImage(), recipe.getYoutubeUrl(),
                            recipeIngredients, recipeMeasures);

                    if (isInserted == true) {
                        Toast.makeText(RecipeDetailsActivity.this, "Recipe was added to favourites!", Toast.LENGTH_SHORT).show();
                        setFavouritesButtonIconColor("#ffff00");
                    } else {
                        Toast.makeText(RecipeDetailsActivity.this, "Cannot add recipe to favourites!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void getRecipeFromJSON(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

           // @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(SD.getTag(), response.toString());
                try {
                    JSONArray recipeJSONArray = response.getJSONArray("meals");

                    for(int i = 0; i < recipeJSONArray.length(); i++)
                    {
                        JSONObject object = recipeJSONArray.getJSONObject(i);
                        recipe = new Recipe(
                                object.getString("idMeal"),
                                object.getString("strMeal"),
                                object.getString("strCategory"),
                                object.getString("strArea"),
                                object.getString("strInstructions"),
                                object.getString("strMealThumb"),
                                object.getString("strYoutube"),
                                StaticMethods.getRecipeIngredients(recipeJSONArray, object),
                                StaticMethods.getRecipeMeasures(recipeJSONArray, object)
                        );

                    }


                    if(databaseAdapter.getRecipeIdsFromDb().contains(recipe.getRecipeId())) {
                       // mFavouritesButton.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffff00")));
                        setFavouritesButtonIconColor("#ffff00");
                    }

                    StaticMethods.removeEmptyStringsAndNulls(recipe.getIngredients());
                    StaticMethods.removeEmptyStringsAndNulls(recipe.getMeasures());

                    setComponentFeatures();

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
                Toast.makeText(RecipeDetailsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRecipeDetailsFromDb(String id) {
        recipe = databaseAdapter.getFavouriteRecipeDetailsById(id);
        setFavouritesButtonIconColor("#ffff00");
        StaticMethods.removeEmptyStringsAndNulls(recipe.getIngredients());
        StaticMethods.removeEmptyStringsAndNulls(recipe.getMeasures());
        setComponentFeatures();
    }


    private void setFavouritesButtonIconColor(String hexValue) {
        Drawable drawable = ContextCompat.getDrawable(RecipeDetailsActivity.this, R.drawable.favourites_btn);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable.mutate(), Color.parseColor(hexValue));
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mFavouritesButton.setCompoundDrawables(drawable, null, null, null);
    }

    private void setComponentFeatures() {
        Picasso.get().load(recipe.getRecipeImage()).into(mRecipeImageView);
        mNameTextView.setText(recipe.getRecipeName());
        mCategoryTextView.setText(recipe.getCategoryName());
        mAreaTextView.setText(recipe.getAreaName());
        mInstructionsTextView.setText(recipe.getInstrutions());
        ArrayList<Integer> measuresIntList = new ArrayList<>();
        ArrayList<String> ingredientsString = recipe.getIngredients();
        if(StaticMethods.getScreenWidth() <= 480 || StaticMethods.getScreenHeight() <= 800)
        {
            int count = 1;
            for(int i = 0; i < recipe.getMeasures().size(); i++)
            {
                if(recipe.getMeasures().get(i).length() > 23 && recipe.getMeasures().get(i).length() < 46)
                {
                    measuresIntList.add(i + count);
                    ++count;
                }

                if(recipe.getMeasures().get(i).length() > 46)
                {
                    measuresIntList.add(i + count);
                    ++count;
                    measuresIntList.add(i + count);
                    ++count;
                }
            }

            if(measuresIntList.size()>0)
            {
                for(int i = 0; i < measuresIntList.size(); i++)
                {
                    ingredientsString.add(measuresIntList.get(i), "");
                }
            }

        }
        else {
            int count = 1;
            for(int i = 0; i < recipe.getMeasures().size(); i++)
            {
                if(recipe.getMeasures().get(i).length() > 29 && recipe.getMeasures().get(i).length() < 49)
                {
                    measuresIntList.add(i + count);
                    ++count;
                }

                if(recipe.getMeasures().get(i).length() > 49)
                {
                    measuresIntList.add(i + count);
                    ++count;
                    measuresIntList.add(i + count);
                    ++count;
                }
            }

            if(measuresIntList.size()>0)
            {
                for(int i = 0; i < measuresIntList.size(); i++)
                {
                    ingredientsString.add(measuresIntList.get(i), "");
                }
            }

        }
        StaticMethods.displayArrayListInTextView("•",ingredientsString, mIngredientsTextView);
        StaticMethods.displayArrayListInTextView(":",recipe.getMeasures(), mMeasuresTextView);
    }
}
