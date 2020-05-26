package com.example.okno_wyszukiwanie_kat_nazwy.Model;

import java.util.ArrayList;

public class Recipe {
    private String recipeId;
    private String recipeName;
    private String categoryName;
    private String areaName;
    private String instructions;
    private String recipeImage;
    private String youtubeUrl;
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> measures = new ArrayList<>();

    public Recipe(String recipeId, String recipeName, String categoryName, String areaName, String instructions,
                  String recipeImage, String youtubeUrl, ArrayList<String> ingredients, ArrayList<String> measures) {

        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.categoryName = categoryName;
        this.areaName = areaName;
        this.instructions = instructions;
        this.recipeImage = recipeImage;
        this.youtubeUrl = youtubeUrl;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public Recipe(){

    }

    public Recipe(String recipeName, String recipeImage, String recipeId) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.recipeId = recipeId;
    }

    // Getter Methods

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getInstrutions() {
        return instructions;
    }

    public String getRecipeImage() {
        return recipeImage;
    }


    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getMeasures() {
        return measures;
    }

    // Setter Methods

    public void setRecipeId( String recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName( String recipeName ) {
        this.recipeName = recipeName;
    }


    public void setCategory( String categoryName ) {
        this.categoryName = categoryName;
    }

    public void setAreaName( String areaName ) {
        this.areaName = areaName;
    }

    public void setInstructions( String instructions ) {
        this.instructions = instructions;
    }

    public void setRecipeImage( String recipeImage ) {
        this.recipeImage= recipeImage;
    }


    public void setYoutube( String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public void setIngredients (ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures (ArrayList<String> measures) {
        this.measures = measures;
    }

}

