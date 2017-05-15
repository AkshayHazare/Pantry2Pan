package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class UserPantryActivity extends AppCompatActivity {

    private static final String TAG = "UserPantryActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText,editText1,editText2,editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add2_pantry);
        setTitle("Pantry2Pan");
        Log.i("Pantry Page", "Successfully loaded");
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


        editText = (EditText) findViewById(R.id.nameEditText);
        editText1 = (EditText) findViewById(R.id.typeEditText);
        editText2 = (EditText) findViewById(R.id.quantityEditText);
        editText3 = (EditText) findViewById(R.id.expiryEditText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);

        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();
                String newType = editText1.getText().toString();
                String newQuantity = editText2.getText().toString();
                String newExpiry = editText3.getText().toString();
                if (editText.length() != 0 && editText1.length() !=0 && editText2.length() !=0 && editText3.length() !=0) {
                    AddData(newItem,newType,newQuantity,newExpiry,userId);
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                } else {
                    toastMessage("One or more missing text field!");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newItem, String newType, String newQuantity, String newExpiry, String userId) {
        Log.i(TAG, "addData: Adding " + newItem + newType + newQuantity + newExpiry +" to Database");
        boolean insertData = mDatabaseHelper.addData(newItem,newType,newQuantity,newExpiry,userId);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
