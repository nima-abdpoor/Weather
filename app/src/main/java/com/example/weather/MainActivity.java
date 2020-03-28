package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static String  [] TEMP_TYPE = new String[]{"Kelvin","Celsius","Fahrenheit"};
    public static String DEFAULT_TEMP=TEMP_TYPE[2];
    static EditText city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VerifingViewItems();
    }

    private void VerifingViewItems() {
        city=findViewById(R.id.getcity);
    }


    public void set(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherView=new Intent(MainActivity.this,WeatherView.class);
                startActivity(weatherView);
            }
        });
    }

}
