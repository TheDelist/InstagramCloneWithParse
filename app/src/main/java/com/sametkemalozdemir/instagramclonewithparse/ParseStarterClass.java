package com.sametkemalozdemir.instagramclonewithparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(applicationId)//change this (String)
                // if desired
                .clientKey("clientKey")//change this
                .server("https://parseapi.back4app.com/")
                .build());

    }
}
