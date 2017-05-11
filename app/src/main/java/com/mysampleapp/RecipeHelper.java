package com.mysampleapp;

import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class RecipeHelper {
    ArrayList<Recipe> recipeData = new ArrayList<>();
    ArrayList<Recipe> recipe = new ArrayList<>();
    JSONArray arr;
    //String url = ("http://api.yummly.com/v1/api/recipes?_app_id=6aa3b3c5&_app_key=98f091eede210875e3db43249b670de4");
     String url = ("http://yummly-env1.8ez2bxu5si.us-east-1.elasticbeanstalk.com/pull_recipes/?pantry_items=");
    // http://yummly-env1.8ez2bxu5si.us-east-1.elasticbeanstalk.com/pull_recipes/?pantry_items=chicken,%20avacado,%20basil,%20spinach


    public ArrayList<Recipe> getRecipe(ArrayList<Data> listData) {
      //  url += "&q=";
        for (int i = 0; i < listData.size(); i++) {
            if (i == listData.size() - 1) {
                url += listData.get(i).getName();
            } else {
                url += listData.get(i).getName();
                url += ",%20";
               // url += "+";
            }
        }
      //  url += "&maxResult=2";

        Log.i("Final call ", url);


        final Thread thread = new Thread(new Runnable() {

            public void run() {
                Looper.prepare();
                Looper.loop();
                try {
                    String json = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();

                    JSONArray arr1 = new JSONArray(json);
                    // JSONObject jsonObject = new JSONObject(out);
                    // String match = jsonObject.getString("_source");
                    // arr = new JSONArray(match);
                    for (int i = 0; i < arr1.length(); i++) {
                        JSONObject jsonPart = arr1.getJSONObject(i);
                        JSONObject jsonObject = new JSONObject(jsonPart.getString("_source"));

                        Recipe recipeEntity = new Recipe();
                        recipeEntity.name = jsonObject.getString("recipeName");
                        recipeEntity.cook_time = jsonObject.getString("totalTimeInSeconds");
                        recipeEntity.ingredients = jsonObject.getString("ingredients");
                     //   recipeEntity.url= jsonObject.getString("smallImageUrls");
                        recipeData.add(recipeEntity);

                        Log.i("123", recipeData.get(i).getName().toString());
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
//        Log.i("321",recipeData.get(0).getName().toString());
        return recipeData;
    }
}

//                  URL imageurl = new URL(jsonPart.getString("smallImageUrls"));

//                  Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());

//                  recipeEntity.setImage(bitmap);








