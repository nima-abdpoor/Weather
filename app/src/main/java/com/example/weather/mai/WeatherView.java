package com.example.weather.mai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.weather.Forecast.retrofit.Forecast;
import com.example.weather.Forecast.retrofit.JsonPlaceHolderAPI;
import com.example.weather.Forecast.retrofit.weather;
import com.example.weather.R;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherView extends AppCompatActivity {
    static TextView temp,city,detail;
    static ImageView stateicon;
    static CardView cardView;
    static ProgressBar progressBar;
    ImageButton home,search,other;
    GetLocation GetLocation;

    int[] forecastargs={1,9,17,25,33};

    Double lon= 0.0 ;
    Double lat= 0.0;

    public WeatherView() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        VerifyingViewItems();
        setViewItems();
        setonclickforicons();
        Forecast();
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
        setViewItems();
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
        progressBar.setVisibility(View.VISIBLE);
        new GettingData(this,lon,lat);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void showGpsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS")
                .setMessage("GPS is not enabled. Do you want to go to Settings menu?")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        Intent intent=new Intent(WeatherView.this,OtherCities.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }
    private void Forecast() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonapi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<Forecast> call = jsonapi.getfrorecast((long) 524901,"b34d97936eaadfa405d3b9b18db6a0ff");
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast =response.body();
                for (int i=0;i<forecastargs.length;++i){
                    String icon =forecast.getList().get(forecastargs[i]).getWeather().get(0).getIcon();
                    double temp =forecast.getList().get(forecastargs[i]).getMain().getTemp();
                    String description =forecast.getList().get(forecastargs[i]).getWeather().get(0).getDescription();
                    String date= String.valueOf(forecast.getList().get(forecastargs[i]).getDtTxt());
                    Log.i("testforforecast","icon: "+icon+" | temp: "+temp+" | description: "+description+" | "+date.toString()+"\n\n");
                }
            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.i("afdhka",t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation();
            }
        },1000);
    }

    public void getLocation(){
        GetLocation =new GetLocation(this);
        if(!GetLocation.canGetLocation()){
            showGpsAlertDialog();
        } else {
            GettingLocation();
        }
    }
}
