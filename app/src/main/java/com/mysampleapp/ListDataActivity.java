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

import java.util.ArrayList;


public class ListDataActivity extends AppCompatActivity {

    int[] IMAGES = {R.drawable.meat,R.drawable.fruits,R.drawable.dairy,R.drawable.vegetables,R.drawable.poultry,R.drawable.spices_condiments,R.drawable.empty};

    private static final String TAG = "ListDataActivity";
    public ArrayList<Data> listData;
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private Button addButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pantry);
        mListView = (ListView) findViewById(R.id.pantryListView);
        mDatabaseHelper = new DatabaseHelper(this);
        addButton = (Button) findViewById(R.id.addButton);

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
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(getApplicationContext(), EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    editScreenIntent.putExtra("type",type);
                    editScreenIntent.putExtra("quantity",quantity);
                    editScreenIntent.putExtra("expiry",expiry);
                    startActivity(editScreenIntent);
                }
                else{
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
    }
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listData.size() ;
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
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);

            ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
            TextView nameView =(TextView) view.findViewById(R.id.nameView);
            TextView typeView =(TextView) view.findViewById(R.id.typeView);
            TextView quantityView =(TextView) view.findViewById(R.id.quantityView);
            TextView expiryView =(TextView) view.findViewById(R.id.expiryView);

            nameView.setText(listData.get(i).getName());
            typeView.setText(listData.get(i).getType());
            quantityView.setText(listData.get(i).getQuantity());
            expiryView.setText(listData.get(i).getExpiry());

            int j=getImage(typeView.getText().toString().toLowerCase());
            if(j!=0){
                imageView.setImageResource(IMAGES[j-1]);
            }
            else {
                Log.d(TAG, "Not a Recognizable Type");
            }
            return view;
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public int getImage(String typeView){

        if(typeView.toLowerCase().equals("meat"))
        {
            return 1;
        }
        if(typeView.toLowerCase().equals("fruits"))
        {
            return 2;
        }
        if(typeView.toLowerCase().equals("dairy"))
        {
            return 3;
        }
        if(typeView.toLowerCase().equals("vegetables"))
        {
            return 4;
        }
        if(typeView.toLowerCase().equals("poultry"))
        {
            return 5;
        }
        if(typeView.toLowerCase().equals("spices_condiments"))
        {
            return 6;
        }
        else {
            return 7;
        }
    }

}
