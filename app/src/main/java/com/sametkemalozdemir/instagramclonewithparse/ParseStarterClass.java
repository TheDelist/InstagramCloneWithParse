package com.sametkemalozdemir.instagramclonewithparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("YhVWepowkbXa1QxKQIEfHL41qSVYpYsMsmkZIqJM")
                // if desired
                .clientKey("J27jg3IvaiFLubDoVqthJCZaH3DiXyVcmBdqjuPw")
                .server("https://parseapi.back4app.com/")
                .build());

    }
}
