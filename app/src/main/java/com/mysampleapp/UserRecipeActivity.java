package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserRecipeActivity extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    private static final String TAG = "UserRecipeActivity";

//    public ArrayList<Data> listData;
//    private ArrayList<Recipe> recipeData = new ArrayList<>();

    private ListView recipeListView;

    String url = ("http://api.yummly.com/v1/api/recipes?_app_id=6aa3b3c5&_app_key=98f091eede210875e3db43249b670de4&q=chicken&maxResult=2");

    List<HashMap<String, String>> mRecipeList = new ArrayList<>();

    private static final String KEY_NAME = "recipeName";
    private static final String KEY_COOK_TIME = "totalTimeInSeconds";
    private static final String KEY_INGREDIENTS = "ingredients";

   // DatabaseHelper mdatabaseHelper = new DatabaseHelper(this);
  //  RecipeHelper recipeHelper = new RecipeHelper();



    // String url = ("http://yummly-env1.8ez2bxu5si.us-east-1.elasticbeanstalk.com/pull_recipes/?pantry_items=");
    // http://yummly-env1.8ez2bxu5si.us-east-1.elasticbeanstalk.com/pull_recipes/?pantry_items=chicken,%20avacado,%20basil,%20spinach

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipe);

        recipeListView = (ListView) findViewById(R.id.list_view);
        //listData = mdatabaseHelper.getData();
      //  url += "&q=";
//        for (int i = 0; i < listData.size(); i++) {
//            if (i == listData.size() - 1) {
//                url += listData.get(i).getName();
//            } else {
//                url += listData.get(i).getName();
//              //  url += ",%20";
//                 url += "+";
//            }
//        }
        //url += "&maxResult=2";

        Log.d(TAG, "Populate Recipe List View: Displaying data in the ListView.");

        // recipeData=recipeHelper.getRecipe(listData);

        recipeListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(url);
    }

    @Override
    public void onLoaded(List<Recipe> recipeList) {
        for (Recipe recipes : recipeList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_NAME, recipes.getName());
            map.put(KEY_COOK_TIME, recipes.getCook_time());
            map.put(KEY_INGREDIENTS, recipes.getIngredients());

            mRecipeList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError(){
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
        Log.i("error","ERROR");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        String name = mRecipeList.get(i).get(KEY_NAME);
        String ingredients = mRecipeList.get(i).get(KEY_INGREDIENTS);
        String cookTime = mRecipeList.get(i).get(KEY_COOK_TIME);
        Log.d(TAG, "onItemClick: You Clicked on " + name);

        Intent editScreenIntent = new Intent(getApplicationContext(), UserChosenRecipeActivity.class);
        editScreenIntent.putExtra(KEY_NAME, name);
        editScreenIntent.putExtra(KEY_INGREDIENTS, ingredients);
        editScreenIntent.putExtra(KEY_COOK_TIME, cookTime);
        startActivity(editScreenIntent);

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {

        ListAdapter listadapter = new SimpleAdapter(UserRecipeActivity.this, mRecipeList,R.layout.list_item,
                new String[] {KEY_NAME, KEY_INGREDIENTS, KEY_COOK_TIME},
                new int[] {R.id.nameView,R.id.ingredientsView, R.id.cooktimeView});
        recipeListView.setAdapter(listadapter);
        //CustomAdapter customadapter = new CustomAdapter();
        //recipeListView.setAdapter(customadapter);

    }
    /*private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mRecipeList.size();
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

            // ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView nameView = (TextView) view.findViewById(R.id.recipetextView);
            TextView cooktimeView = (TextView) view.findViewById(R.id.cooktimeTextView);
            TextView ingredientsView = (TextView) view.findViewById(R.id.ingredientsView);

            nameView.setText(mRecipeList.get(i).get(KEY_NAME));
            Log.i("namwView: ", (String) nameView.getText());
            cooktimeView.setText(mRecipeList.get(i).get(KEY_COOK_TIME));
            ingredientsView.setText(mRecipeList.get(i).get(KEY_INGREDIENTS));

            // imageView.setImageBitmap(recipeData.get(i).getImage());

            return view;
            */

    }



