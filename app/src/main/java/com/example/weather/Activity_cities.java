package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;

public class Activity_cities extends AppCompatActivity {
    RecyclerView recyclerView;
    CitiesAdded cityAdded;
    CityDbHelper dbHelper;
    List<String> MyCities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        VerifingViewItems();
        MyCities=new ArrayList<>();
        dbHelper=new CityDbHelper(this);
        String [] salam = dbHelper.GetMyCitiesId().toArray(new String[0]);
        for(String s:salam)
        MyCities.add(s);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityAdded =new CitiesAdded(MyCities,this);
        recyclerView.setAdapter(cityAdded);
    }

    private void VerifingViewItems() {
        recyclerView=findViewById(R.id.city_recycler_view);
    }
}
