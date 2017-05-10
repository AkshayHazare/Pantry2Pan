package com.mysampleapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class UserRecipeActivity extends AppCompatActivity {
    private static final String TAG = "UserRecipeActivity";
    ArrayList<Data> recipedata = new ArrayList<>();
    ElasticSearchHelper eElasticSearchHelper;
    private ListView recipeListView;
    private Button addButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipe);
        recipeListView = (ListView) findViewById(R.id.recipeListView);
        eElasticSearchHelper = new ElasticSearchHelper(this);
        setTitle("Pantry2Pan");
        Log.d(TAG, "Recipe Page - Successfully loaded");


        recipedata = eElasticSearchHelper.getData();
        //create the list adapter and set the adapter

        CustomAdapter customadapter = new CustomAdapter();
        recipeListView.setAdapter(customadapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = recipedata.get(i).getRecipeName();
                String cuisine = recipedata.get(i).getCuisine();
                String ingredients = recipedata.get(i).getIngredients();
                String cooktime = recipedata.get(i).getCooktime();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                // START THE NEW INTENT TO THE NEW PAGE:
                Intent editScreenIntent = new Intent(getApplicationContext(), EditDataActivity.class);
                editScreenIntent.putExtra("name", name);
                editScreenIntent.putExtra("cuisine", cuisine);
                editScreenIntent.putExtra("ingredients", ingredients);
                editScreenIntent.putExtra("cooktime", cooktime);
                startActivity(editScreenIntent);
            }
        });
    }
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recipedata.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = null;
            view = getLayoutInflater().inflate(R.layout.recipe_custom_layout, null);

            ImageView recipeimageView = (ImageView) view.findViewById(R.id.recipeimageView);
            TextView recipeNameView = (TextView) view.findViewById(R.id.nameTextView);
            TextView cuisineView = (TextView) view.findViewById(R.id.cuisineTextView);
            TextView cooktimeView = (TextView) view.findViewById(R.id.cooktimeTextView);
            TextView ingredientsview = (TextView) view.findViewById(R.id.ingredientsView);

            Drawable drawable;
            link = recipedata.get(i).getlink();
            drawable = LoadImageFromWebOperations(link);

            cooktimeView.setText("Time: ");
            int time = getCookTime();
            if (time <= 0 ) {
                cooktimeView.setText(cooktimeView.getText() + "Not specified");
            } else {
                int temp_time = time;
                int hours = temp_time/3600;
                temp_time -= hours*3600;
                int mins = temp_time/60;
                temp_time -= mins*60;
                int secs = temp_time;
                String hourString = "" + hours;
                String minString = "" + mins;
                String secString = "" + secs;
                if (hours != 0) {
                    cooktimeView.setText(cooktimeView.getText() + " " + hourString + " hours");
                }
                if (mins != 0) {
                    cooktimeView.setText(cooktimeView.getText() + " " + minString + " minutes");
                }
                if (secs != 0) {
                    cooktimeView.setText(cooktimeView.getText() + " " + secString + " seconds");
                }
            }

            String ingredientList = "Ingredients: ";
            for (int j = 0; j < ingredients.length; ++j) {
                if (j == ingredients.length-1) {
                    ingredientList += ingredients[j];
                } else {
                    ingredientList += ingredients[j] + ", ";
                }
            }
            ingredientsview.setText(ingredientList);

            recipeNameView.setText(recipedata.get(i).getName());
            cuisineView.setText(recipedata.get(i).getCuisine());

            recipeimageView.setImageDrawable(drawable);

            return view;
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}