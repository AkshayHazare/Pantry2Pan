package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobilehelper.auth.IdentityManager;
import com.amazonaws.regions.Regions;


public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper mdb ;
    private static final String TAG = "HomeActivity";
    final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
    private IdentityManager identityManager = awsMobileClient.getIdentityManager();



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
        if (view.getId() == R.id.pantryButton) {
            showUserPantry();
        }
        if (view.getId() == R.id.logoutbutton) {

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
        Log.d(TAG, "logoutbutton was clicked");

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
        String userId = user.getUserId();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(" Hi "+userId.toUpperCase());

        DatabaseHelper mdb = new DatabaseHelper(this);



    }






}


