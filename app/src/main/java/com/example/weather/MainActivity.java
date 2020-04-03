package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.CityDbHelper;


public class MainActivity extends AppCompatActivity {
    public static String  [] TEMP_TYPE = new String[]{"Kelvin","Celsius","Fahrenheit"};
    public static String DEFAULT_TEMP=TEMP_TYPE[1];
    static EditText city;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
               // putcitytodatabase();
            }
        });
        thread.start();
        setContentView(R.layout.activity_main);
        VerifingViewItems();
    }

    private void VerifingViewItems() {
        city=findViewById(R.id.getcity);
        button=findViewById(R.id.button);
        set();
    }


    public void set() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherView=new Intent(MainActivity.this,WeatherView.class);
                startActivity(weatherView);
            }
        });
    }

    private void putcitytodatabase() {
        CityDbHelper cityDbHelper=new CityDbHelper(this);
    }

}
