package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete,btnView;
    private EditText editable_item,editText1,editText2,editText3;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;
    private String selectedType;
    private String selectedQuantity;
    private String selectedExpiry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editpantry);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        editText1 = (EditText) findViewById(R.id.typeEditText);
        editText2 = (EditText) findViewById(R.id.quantityEditText);
        editText3 = (EditText) findViewById(R.id.expiryEditText);
        mDatabaseHelper = new DatabaseHelper(this);
        btnView = (Button) findViewById(R.id.btnView);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        selectedType = receivedIntent.getStringExtra("type");
        selectedQuantity = receivedIntent.getStringExtra("quantity");
        selectedExpiry = receivedIntent.getStringExtra("expiry");

        //set the text to show the current selected name
        editable_item.setText(selectedName);
        editText1.setText(selectedType);
        editText2.setText(selectedQuantity);
        editText3.setText(selectedExpiry);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String newType = editText1.getText().toString();
                String newQuantity = editText2.getText().toString();
                String newExpiry = editText3.getText().toString();

                if(!newType.equals("")){
                    mDatabaseHelper.updateType(newType,selectedID,selectedType);
                }else{
                    toastMessage("You must enter a Type");
                }
                if(!newQuantity.equals("")){
                    mDatabaseHelper.updateQuantity(newQuantity,selectedID,selectedQuantity);
                }else{
                    toastMessage("You must enter a Quantity");
                }
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                }else{
                    toastMessage("You must enter a Name");
                }
                if(!newExpiry.equals("")){
                    mDatabaseHelper.updateExpiry(newExpiry,selectedID,selectedExpiry);
                }else{
                    toastMessage("You must enter an Expiry");
                }
                toastMessage("Item Updated");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                toastMessage("removed from database");
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
























