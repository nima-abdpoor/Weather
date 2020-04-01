package com.example.weather;

import android.app.Application;

import data.AssetDatabaseHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                new AssetDatabaseHelper(getApplicationContext()).checkDb();
            }
        });
        thread.start();
        super.onCreate();
    }
}
