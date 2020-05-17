package com.example.weather.mai;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

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

    private void refreshPage() {
        setData();
    }

    private void VerifingViewItems() {
        recyclerView=findViewById(R.id.city_recycler_view);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        other=findViewById(R.id.other);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Activity_cities.this,WeatherView.class);
        startActivity(intent);
    }
}
