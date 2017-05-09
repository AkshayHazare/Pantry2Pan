
package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("8b260cbb11b9d86b82cc8af7074bf5a9b775464c")
            .clientKey("88be0047eda77b9182bb2bc720481e01e4804957")
            .server("http://ec2-54-200-137-68.us-west-2.compute.amazonaws.com:80/parse/")
            .build()
    );


    //ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
