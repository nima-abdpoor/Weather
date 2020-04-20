package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;

public class Activity_cities extends AppCompatActivity {
    RecyclerView recyclerView;
    CitiesAdded cityAdded;
    CityDbHelper dbHelper;
    List<String> MyCities;
    ImageButton home,search,other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        VerifingViewItems();
        setViewItems();
        setonclickforicons();
        setData();
    }

    private void setData() {
        MyCities=new ArrayList<>();
        dbHelper=new CityDbHelper(this);
        String [] salam = dbHelper.GetMyCitiesId().toArray(new String[0]);
        for(String s:salam)
            MyCities.add(s);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityAdded =new CitiesAdded(MyCities,this);
        recyclerView.setAdapter(cityAdded);
    }

    private void setonclickforicons() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_cities.this,WeatherView.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_cities.this,OtherCities.class);
                startActivity(intent);
            }
        });
    }

    private void refreshPage() {
        setData();
    }

    private void VerifingViewItems() {
        recyclerView=findViewById(R.id.city_recycler_view);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        other=findViewById(R.id.other);
    }
    private void setViewItems() {
        home.setBackgroundResource(R.drawable.hime);
        search.setBackgroundResource(R.drawable.searchblue);
        other.setBackgroundResource(R.drawable.globe);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Activity_cities.this,WeatherView.class);
        startActivity(intent);
    }
}
