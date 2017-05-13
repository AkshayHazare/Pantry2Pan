package com.mysampleapp;

import android.graphics.Bitmap;

public class Recipe {

    public String name;
    public Bitmap image;
    public String ingredients;
    public String cook_time;
    public String sourceURL;
    public String cuisine;

    public String getName(){
        return name;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getCook_time(){
        return cook_time;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getSourceURL(){
        return sourceURL;
    }

    public String getCuisine(){
        return cuisine;
    }


}
