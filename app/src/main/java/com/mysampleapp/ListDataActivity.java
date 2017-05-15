package com.mysampleapp;

import android.content.Intent;
import android.database.Cursor;
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

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.joda.time.Days.daysBetween;

//import static com.amazonaws.services.cognitoidentityprovider.model.AttributeDataType.DateTime;


public class ListDataActivity extends AppCompatActivity  {

    int[] IMAGES = {R.drawable.meat,R.drawable.fruits,R.drawable.dairy,R.drawable.vegetables,
            R.drawable.poultry,R.drawable.spices_condiments,R.drawable.fish,R.drawable.milk,R.drawable.grains,R.drawable.bread,R.drawable.empty};

    private static final String TAG = "ListDataActivity";
    public ArrayList<Data> listData;
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private Button addButton,backbutton;
    Date d =  new Date();
    DateTime jd = new DateTime(d);
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
        setContentView(R.layout.activity_user_pantry);
        mListView = (ListView) findViewById(R.id.pantryListView);
        mDatabaseHelper = new DatabaseHelper(this);
        addButton = (Button) findViewById(R.id.addButton);
        backbutton = (Button) findViewById(R.id.pantrybackbutton);

        //get the data and append to a list
        listData = mDatabaseHelper.getData();


        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        //create the list adapter and set the adapter
        CustomAdapter customadapter = new CustomAdapter();
        mListView.setAdapter(customadapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = listData.get(i).getName();
                String type = listData.get(i).getType();
                String quantity = listData.get(i).getQuantity();
                String expiry = listData.get(i).getExpiry();
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Cursor data = mDatabaseHelper.getItemID(name);//get the id associated with that name

                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(getApplicationContext(), EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);
                    editScreenIntent.putExtra("type", type);
                    editScreenIntent.putExtra("quantity", quantity);
                    editScreenIntent.putExtra("expiry", expiry);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserPantryActivity.class);
                startActivity(intent);
            }


        });
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(intent);
            }


        });
    }




        private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listData.size();
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
            view = getLayoutInflater().inflate(R.layout.custom_layout, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView nameView = (TextView) view.findViewById(R.id.nameView);
            TextView typeView = (TextView) view.findViewById(R.id.typeView);
            TextView quantityView = (TextView) view.findViewById(R.id.quantityView);
            TextView expiryView = (TextView) view.findViewById(R.id.expiryView);
            TextView expirypromptView = (TextView) view.findViewById(R.id.expiryprompt);


            nameView.setText(listData.get(i).getName());
            typeView.setText(listData.get(i).getType());
            quantityView.setText(listData.get(i).getQuantity());
            expiryView.setText(listData.get(i).getExpiry());


            try {
                Date expirydate = new SimpleDateFormat("MM/dd/yyyy").parse(listData.get(i).getExpiry());
                DateTime j_expiry = new DateTime(expirydate);
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();

                if(daysBetween(jd.toLocalDate(),j_expiry.toLocalDate()).getDays()  <= 3){
                    expirypromptView.setText("Expiring in " + daysBetween(jd.toLocalDate(),j_expiry.toLocalDate()).getDays()+" days" );
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int j = getImage(typeView.getText().toString().toLowerCase());
            if (j != 0) {
                imageView.setImageResource(IMAGES[j - 1]);
            } else {
                Log.d(TAG, "Not a Recognizable Type");
            }


            return view;
        }
    }



    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public int getImage(String typeView) {

        if (typeView.toLowerCase().equals("meat")) {
            return 1;
        }
        if (typeView.toLowerCase().equals("fruits")) {
            return 2;
        }
        if (typeView.toLowerCase().equals("dairy")) {
            return 3;
        }
        if (typeView.toLowerCase().equals("vegetables")) {
            return 4;
        }
        if (typeView.toLowerCase().equals("poultry")) {
            return 5;
        }
        if (typeView.toLowerCase().equals("spices_condiments")) {
            return 6;
        }
        if (typeView.toLowerCase().equals("fish")) {
            return 7;
        }
        if (typeView.toLowerCase().equals("milk")) {
            return 8;
        }
        if (typeView.toLowerCase().equals("grains")) {
            return 9;
        }
        if (typeView.toLowerCase().equals("bread")){
            return 10;
        }
        else {
            return 11;
        }
    }
}