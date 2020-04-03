package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Locations").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(WeatherView.this,SearchCity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("My Cities").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(WeatherView.this,Activity_cities.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
