package com.mysampleapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoadJSONTask extends AsyncTask<String, Void, Response> {

    public LoadJSONTask(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onLoaded(List<Recipe> recipeList);

        void onError();
    }

    private Listener mListener;

    @Override
    protected Response doInBackground(String... strings) {
        try {

            String stringResponse = loadJSON(strings[0]);
            Gson gson = new Gson();
            Log.i("GSON",gson.fromJson(stringResponse, Response.class).toString());
            return gson.fromJson(stringResponse, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {

        if (response != null) {

            mListener.onLoaded(response.getRecipes());

        } else {

            mListener.onError();
        }
    }

    private String loadJSON(String jsonURL) throws IOException {
        String url = ("http://api.yummly.com/v1/api/recipes?_app_id=6aa3b3c5&_app_key=98f091eede210875e3db43249b670de4");
        url += "&q=";
        for (int i = 0; i < listData.size(); i++) {
            if (i == listData.size() - 1) {
                url += listData.get(i).getName();
            } else {
                url += listData.get(i).getName();
                // url += ",%20";
                url += "+";
            }
        }
        url += "&maxResult=2";

        Log.i("Final call ", url);

        return response.toString();
    }
}
