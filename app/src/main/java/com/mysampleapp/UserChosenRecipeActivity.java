package com.mysampleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class UserChosenRecipeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chosen_recipe);
        backbutton = (Button) findViewById(R.id.chosenRecipeBackButton);

        Intent receivedIntent = getIntent();
        String recipeName = receivedIntent.getStringExtra("recipeName");
        String str_ingredients = receivedIntent.getStringExtra("ingredients");
        String cuisine = receivedIntent.getStringExtra("cuisine");
        String srcUrl = receivedIntent.getStringExtra("sourceURL");
        String image = receivedIntent.getStringExtra("smallImageUrls");
        String cookTime = receivedIntent.getStringExtra("cookTime");

        final TextView recipeTitle = (TextView) findViewById(R.id.nameTextView);
        final TextView ingredientString = (TextView) findViewById(R.id.ingredients_list);
        final TextView cookingTime = (TextView) findViewById(R.id.cooktimeTextView);
        final TextView recipeLink = (TextView) findViewById(R.id.src_Url);
        final ImageView imageView = (ImageView) findViewById(R.id.selectRecipeImage);
        final TextView cuis = (TextView) findViewById(R.id.cuisineTextView);
        String[] ingredient_list = str_ingredients.split(",");
        String[] cuisinelist = cuisine.split("");
        recipeTitle.setText(recipeName);
        String ingredientList = "Ingredients are : " + "\n";

        for (int i = 1; i < ingredient_list.length - 1; i++) {
            if (i == ingredient_list.length - 2) {
                ingredientList += (i + ".");
                ingredientList += ingredient_list[i];
            } else {
                ingredientList += (i + ".");
                ingredientList += ingredient_list[i] + "\n";
            }
        }
        ingredientString.setText(ingredientList);
        cookingTime.setText("Time to Cook : " +"\n"+ cookTime);
        recipeLink.setText("Recipe can be found at : "+"\n"+srcUrl);
        Linkify.addLinks(recipeLink, Linkify.WEB_URLS);

        String c = "Cuisine is : ";
        if(cuisinelist[3].equals("u")){
            for (int i = 5; i < cuisinelist.length - 3; i++) {
                c += cuisinelist[i];
            }
        }
        else {
            for (int i = 3; i < cuisinelist.length - 2; i++)
            {
                c += cuisinelist[i];
            }
        }
        cuis.setText(c);
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(new URL(image).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);
        backbutton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.chosenRecipeBackButton) {
            Intent intent = new Intent(getApplicationContext(), UserRecipeActivity.class);
            startActivity(intent);
        }
    }

}