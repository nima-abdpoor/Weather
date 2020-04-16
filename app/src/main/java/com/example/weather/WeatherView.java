package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WeatherView extends AppCompatActivity {
    static TextView temp,city,detail;
    static ImageView stateicon;
    static CardView cardView;
    static ProgressBar progressBar;

    Double lon= 0.0 ;
    Double lat= 0.0;

    public WeatherView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetingLocation();
        VerifyingViewItems();
        setViewItems();
    }

    private void GetingLocation() {
        setContentView(R.layout.activity_weather_view);
        GetLocation getLocation=new GetLocation(this);
        lat=getLocation.getLatitude();
        lon=getLocation.getLongitude();
        if (lon == 0.0 && lat ==0.0){
            GetingLocation();
        }
    }

    public void VerifyingViewItems() {
        city =findViewById(R.id.city);
        stateicon =findViewById(R.id.icon);
        temp =findViewById(R.id.temp);
        cardView =findViewById(R.id.card_view);
        detail =findViewById(R.id.detail);
        progressBar=findViewById(R.id.progress_bar);
    }

    private void setViewItems() {
        progressBar.setVisibility(View.VISIBLE);
        new GettingData(this,lon,lat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Locations").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("salamgoh","");
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
        menu.add("All Cities").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(WeatherView.this,OtherCities.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
