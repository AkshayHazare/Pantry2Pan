package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.ParseAnalytics;


public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener{

    public void showUserPantry() {

        Intent intent = new Intent(getApplicationContext(), ListDataActivity.class);
        startActivity(intent);
    }

    public void showUserRecipe() {

        Intent intent = new Intent(getApplicationContext(), UserRecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.recipeButton) {
            showUserRecipe();
        }
        else if(view.getId()== R.id.pantryButton){
            showUserPantry();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        setTitle("Pantry2Pan");
        Button recipeButton = (Button) findViewById(R.id.recipeButton);
        recipeButton.setOnClickListener(this);

        Button pantryButton = (Button) findViewById(R.id.pantryButton);
        pantryButton.setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


}