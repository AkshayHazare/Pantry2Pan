package com.mysampleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class UserRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserRecipeActivity";

    private ArrayList<Data> listData;
    private ArrayList<Recipe> recipeData;
    DatabaseHelper mdatabaseHelper = new DatabaseHelper(this);
    RecipeHelper recipeHelper = new RecipeHelper();
    private ListView recipeListView;
    Button backButton;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipe);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        CognitoCachingCredentialsProvider credentials = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "040667233965",
                "Cognito_TestDynamo",
                //"us-east-1:314e3462-971d-459c-bd79-edbb6838933c",
                "arn:aws:iam::040667233965:role/Cognito_testDynamoUnauth_Role",
                "arn:aws:iam::040667233965:role/Cognito_testDynamoAuth_Role",
                Regions.US_EAST_1
                //"1olub3i713t45k3ot6hptvnj5m",
                //"124um0osvif397qh81k78ajhhiqsffo123dr14rsh0nli2kkffuo"
        );

        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(),
                "us-east-1:314e3462-971d-459c-bd79-edbb6838933c",
                "1olub3i713t45k3ot6hptvnj5m",
                "124um0osvif397qh81k78ajhhiqsffo123dr14rsh0nli2kkffuo"
        );

        CognitoUser user = userPool.getCurrentUser();
        final String userId = user.getUserId();

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
                int time = new Integer(recipeData.get(i).getCook_time());
                if (time <= 0) {
                    cookTime = ("Not specified");
                } else {
                    int temp_time = time;
                    int hours = temp_time / 3600;
                    temp_time -= hours * 3600;
                    int mins = temp_time / 60;
                    temp_time -= mins * 60;
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
                        cookTime = (cookTime + " " + secString + " seconds");
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
                editScreenIntent.putExtra("smallImageUrls", image);
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

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView nameView = (TextView) view.findViewById(R.id.nameView);
            TextView cooktimeView = (TextView) view.findViewById(R.id.cooktimeView);

            try {

                nameView.setText(recipeData.get(i).getName());
                cooktimeView.setText("");
                int time = new Integer(recipeData.get(i).getCook_time());
                if (time <= 0) {
                    cooktimeView.setText(cooktimeView.getText() + "Not specified");
                } else {
                    int temp_time = time;
                    int hours = temp_time / 3600;
                    temp_time -= hours * 3600;
                    int mins = temp_time / 60;
                    temp_time -= mins * 60;
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
                String imgurl = parseImage(recipeData.get(i).getImageURL());
                Picasso.with(getApplicationContext()).load(imgurl).into(imageView);
              // getBitmap(imageView,image);
            }
            catch (Exception e){}
            return view;
        }
    }
    public void getBitmap(ImageView image,String url) throws IOException {
        String img = parseImage(url);
        Bitmap bitmap;
        try {
            URL newurl = new URL(parseImage(url));
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            image.setImageBitmap(bitmap);
            Log.i("URL is ", String.valueOf(bitmap));
        }
        catch (Exception e){}
    }


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
