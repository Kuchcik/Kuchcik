package com.example.okno_wyszukiwanie_kat_nazwy.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okno_wyszukiwanie_kat_nazwy.Interfaces.OnItemClickListener;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Recipe;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private Context context;
    private ArrayList<Recipe> meals;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder {
        public TextView mRecipeNameTextView;
        public ImageView mThumbnailImageView;


        public RecipesViewHolder(@NonNull View itemView, final OnItemClickListener listener ) {
            super(itemView);

            mRecipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            mThumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.meals = recipes;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipe_card_view, parent, false);

        RecipesViewHolder recipesViewHolder = new RecipesViewHolder(itemView, mOnItemClickListener);

        return recipesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipe recipe = meals.get(position);
        holder.mRecipeNameTextView.setText(recipe.getRecipeName());
        Picasso.get().load(recipe.getRecipeImage()).into(holder.mThumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

}
