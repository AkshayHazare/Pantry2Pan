package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobilehelper.auth.IdentityManager;


public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "HomeActivity";
    final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
    private IdentityManager identityManager=awsMobileClient.getIdentityManager();

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
        if(view.getId() == R.id.recipeButton){
            showUserRecipe();
        }
        if(view.getId() == R.id.pantryButton){
            showUserPantry();
        }
        if(view.getId() == R.id.logoutbutton){

            identityManager.signOut();
            identityManager.signInOrSignUp(this, new SignInHandler());
            finish();
            return;
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

        Button logOutButton = (Button) findViewById(R.id.logoutbutton);
        logOutButton.setOnClickListener(this);
        Log.d(TAG,"logoutbutton was clicked");

    }


}