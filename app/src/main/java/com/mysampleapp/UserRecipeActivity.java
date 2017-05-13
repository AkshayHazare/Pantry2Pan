package com.mysampleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class UserRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserRecipeActivity";

    private ArrayList<Data> listData;
    private ArrayList<Recipe> recipeData;
    DatabaseHelper mdatabaseHelper = new DatabaseHelper(this);
    RecipeHelper recipeHelper = new RecipeHelper();
    private ListView recipeListView;
    Button backButton;
    ImageView image;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipe);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recipeListView = (ListView) findViewById(R.id.recipeListView);


        Log.d(TAG, "Populate Recipe List View: Displaying data in the ListView.");

        listData = mdatabaseHelper.getData();

        recipeData = recipeHelper.getRecipe(listData);


        //create the list adapter and set the adapter
        CustomAdapter customadapter = new CustomAdapter();
        recipeListView.setAdapter(customadapter);

        backButton = (Button) findViewById(R.id.backbuttonrecipe);
        backButton.setOnClickListener(this);


        //set an onItemClickListener to the ListView
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String name = recipeData.get(i).getName();
                String ingredients = recipeData.get(i).getIngredients();
                String cookTime = "";
                int time= new Integer(recipeData.get(i).getCook_time());
                if (time <= 0 ) {
                    cookTime = ("Not specified");
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
                        cookTime = (hourString + " hours");
                    }
                    if (mins != 0) {
                        cookTime = (cookTime + " " + minString + " minutes");
                    }
                    if (secs != 0) {
                        cookTime = (cookTime  + " " + secString + " seconds");
                    }
                }


                String sourceURL = recipeData.get(i).getSourceURL();
                String image = recipeData.get(i).getImageURL();
                String cuisine = recipeData.get(i).getCuisine();

                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Intent editScreenIntent = new Intent(getApplicationContext(), UserChosenRecipeActivity.class);
                editScreenIntent.putExtra("recipeName", name);
                editScreenIntent.putExtra("ingredients", ingredients);
                editScreenIntent.putExtra("cookTime", cookTime);
                editScreenIntent.putExtra("sourceURL", sourceURL);
                editScreenIntent.putExtra("smallImageUrls",image);
                editScreenIntent.putExtra("cuisine", cuisine);

                startActivity(editScreenIntent);

            }
        });



    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backbuttonrecipe) {
            Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
            startActivity(intent);
        }
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recipeData.size();
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
            view = getLayoutInflater().inflate(R.layout.recipe_custom_layout, null);

            image = (ImageView) view.findViewById(R.id.imageView);
            TextView nameView = (TextView) view.findViewById(R.id.nameView);
            TextView cooktimeView = (TextView) view.findViewById(R.id.cooktimeView);
            Bitmap bitmap=recipeData.get(i).getImage();
            try
            {
                Log.i("Image",recipeData.get(i).getImage().toString());

                nameView.setText(recipeData.get(i).getName());

                cooktimeView.setText("");

                int time=new Integer(recipeData.get(i).getCook_time());

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


            }
            catch (Exception e){};
            return view;
        }
    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap> {

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

