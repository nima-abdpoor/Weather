package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class WeatherView extends AppCompatActivity {
    static TextView temp,city,detail;
    static ImageView stateicon;
    static CardView cardView;
    static ProgressBar progressBar;
    ImageButton home,search,other;

    Double lon= 0.0 ;
    Double lat= 0.0;

    public WeatherView() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VerifyingViewItems();
        setViewItems();
        setonclickforicons();
        GettingLocation();
    }

    private void setonclickforicons() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeatherView.this,SearchCity.class);
                startActivity(intent);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeatherView.this,OtherCities.class);
                startActivity(intent);
            }
        });
    }

    private void GettingLocation() {
        List<Double> list;
        ManageCurrentLocation currentLocation=new ManageCurrentLocation(this);
        list=currentLocation.getcoordinates();
        lat=list.get(0);
        lon=list.get(1);
    }

    private void refreshPage() {
        setViewItems();
    }

    public void VerifyingViewItems() {
        city =findViewById(R.id.city);
        stateicon =findViewById(R.id.icon);
        temp =findViewById(R.id.temp);
        cardView =findViewById(R.id.card_view);
        detail =findViewById(R.id.detail);
        progressBar=findViewById(R.id.progress_bar);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        other=findViewById(R.id.other);
    }
    private void setViewItems() {
        home.setBackgroundResource(R.drawable.homeblue);
        search.setBackgroundResource(R.drawable.search);
        other.setBackgroundResource(R.drawable.language);
        progressBar.setVisibility(View.VISIBLE);
        new GettingData(this,lon,lat);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
