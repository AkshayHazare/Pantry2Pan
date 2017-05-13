package com.mysampleapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class RecipeHelper {


    ArrayList<Recipe> recipeData = new ArrayList<>();
    ArrayList<Recipe> recipe = new ArrayList<>();

    //String url = ("http://api.yummly.com/v1/api/recipes?_app_id=6aa3b3c5&_app_key=98f091eede210875e3db43249b670de4");
    String url = ("http://yummly-env1.8ez2bxu5si.us-east-1.elasticbeanstalk.com/pull_recipes/?pantry_items=");
    Drawable drawable;
    public ArrayList<Recipe> getRecipe(ArrayList<Data> listData)
    {
        //url += "&q=";
        for (int i = 0; i < listData.size(); i++) {
            if (i == listData.size() - 1) {
                url += listData.get(i).getName();
            } else {
                url += listData.get(i).getName();
                url += ",%20";
                //url += "+";
            }
        }
       // url += "&maxResult=2";
        Log.i("Final call ", url);

        try {
            String json = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
            JSONArray arr = new JSONArray(json);
            //JSONObject jsonObject = new JSONObject(json);
            //String match = jsonObject.getString("matches");
            //JSONArray arr = new JSONArray(match);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonPart =  arr.getJSONObject(i);
                JSONObject jsonObject = new JSONObject(jsonPart.getString("_source"));
                String url = (jsonObject.getString("smallImageUrls"));
                String  newImageString = parseImage(url);
                Bitmap bmp = BitmapFactory.decodeStream(new URL(newImageString).openConnection().getInputStream());

                if(bmp!=null){
                    Recipe recipeEntity = new Recipe();
                    recipeEntity.name = jsonObject.getString("recipeName");
                    recipeEntity.cook_time = jsonObject.getString("totalTimeInSeconds");
                    recipeEntity.cuisine = jsonObject.getString("cuisine");
                    recipeEntity.ingredients = parseIngredientsList(jsonObject.getString("ingredients")).toString();
                    recipeEntity.sourceURL =  parseImage(jsonObject.getString("sourceURL")).toString();
                    recipeEntity.image = bmp;
                    Log.i("JSONobj",recipeEntity.toString());
                    recipeData.add(recipeEntity);
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return recipeData;
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    private ArrayList<String> parseIngredientsList(String ingredientString) {
        ArrayList<String> ingredientList = new ArrayList<>();
        String stringFragment = "";

        for (int i = 0; i < ingredientString.length(); ++i) {

            if (ingredientString.charAt(i) == '[' ||
                    ingredientString.charAt(i) == '\"')   {
                continue;
            }

            else if (ingredientString.charAt(i) == ','
                    || ingredientString.charAt(i) == ']') {
                ingredientList.add(stringFragment);
                stringFragment = "";
            }

            else {
                stringFragment = stringFragment + ingredientString.charAt(i);
            }
        }

        return ingredientList;
    }

    // Parse image string
    private String parseImage(String imageString) {

        String stringFragment = "";

        for (int i = 0; i < imageString.length(); ++i) {

            if (imageString.charAt(i) == '\"' || imageString.charAt(i) == '[') {
                continue;
            }

            else if (imageString.charAt(i) == ','
                    || imageString.charAt(i) == ']') {
                break;
            }

            else {
                stringFragment = stringFragment + imageString.charAt(i);
            }
        }
        String finalFragment = "";
        int count = 0;
        boolean shouldRemove = true;
        for (int i = 0; i < stringFragment.length(); ++i) {

            if (count == 2) {
                shouldRemove = false;
            }

            if (stringFragment.charAt(i) == 's' && shouldRemove) {
                continue;
            }

            if (stringFragment.charAt(i) == '\\') {
                continue;
            }

            if (stringFragment.charAt(i) == '/') {
                count++;
            }

            finalFragment = finalFragment + stringFragment.charAt(i);
        }

        return finalFragment;
    }

}









