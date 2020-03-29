package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherView extends AppCompatActivity {
    static TextView temp,city,detail;
    static ImageView stateicon;
    static CardView cardView;


    public WeatherView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        VerifyingViewItems();
        setViewItems();
    }

    public void VerifyingViewItems() {
        city =findViewById(R.id.city);
        stateicon =findViewById(R.id.icon);
        temp =findViewById(R.id.temp);
        cardView =findViewById(R.id.card_view);
        detail =findViewById(R.id.detail);
    }

    private void setViewItems() {
        new GettingData(this);
    }


}
