package com.example.weather;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import data.CityDbHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(!preferences.getBoolean("firstTime",false)){
                    CityDbHelper dbHelper=new CityDbHelper(getApplicationContext());
                    dbHelper.InitContents();
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("firstTime",true);
                    editor.commit();
                }
            }
        });
        thread.start();
        Thread DeleteUnUsedInfo =new Thread(new Runnable() {
            @Override
            public void run() {
               CityDbHelper dbHelper =new CityDbHelper(getApplicationContext());
               dbHelper.DeleteUnUsedInfo();
            }
        });
        super.onCreate();
    }
}