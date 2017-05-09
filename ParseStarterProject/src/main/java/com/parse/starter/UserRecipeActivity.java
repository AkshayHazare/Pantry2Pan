package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class UserRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipe);
        setTitle("Pantry2Pan");
        Log.i("Recipe Page","Successfully loaded");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
