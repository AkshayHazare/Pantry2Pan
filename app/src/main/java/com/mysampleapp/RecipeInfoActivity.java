package com.mysampleapp;


import android.os.AsyncTask;
import java.net.URL;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeInfoActivity extends AppCompatActivity implements View.OnClickListener{
    String stringURL;
    ImageView image;
    private Button backbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chosen_recipe);

        image = (ImageView) findViewById(R.id.imageView2);
        Intent receivedIntent = getIntent();
        String URL = receivedIntent.getStringExtra("SmallImageUrls");
        String recipeName = receivedIntent.getStringExtra("recipeName");
        String str_ingredients = receivedIntent.getStringExtra("ingredients");
        String cuisine = receivedIntent.getStringExtra("cuisine");
        String srcUrl = receivedIntent.getStringExtra("sourceURL");
        String cookTime = receivedIntent.getStringExtra("cookTime");

        final TextView recipeTitle = (TextView) findViewById(R.id.nameTextView);
        final TextView ingredientString = (TextView) findViewById(R.id.ingredients_list);
        final TextView cookingTime = (TextView) findViewById(R.id.cooktimeTextView);
        final TextView recipeLink = (TextView) findViewById(R.id.src_Url);
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

        new GetImage().execute(URL);

        backbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.chosenRecipeBackButton) {
            Intent intent = new Intent(getApplicationContext(), UserRecipeActivity.class);
            startActivity(intent);
        }
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
            String imageURL = strings[0];

            Log.i("imgURL: ", imageURL);

            try{

                InputStream input = new java.net.URL(imageURL).openStream();

                bitmap = BitmapFactory.decodeStream(input);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            image.setImageBitmap(result);

        }
    }

}


