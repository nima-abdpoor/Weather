package com.example.weather;

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

import com.example.weather.Forecast.Forecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherView extends AppCompatActivity {
    static TextView temp,city,detail;
    static ImageView stateicon;
    static CardView cardView;
    static ProgressBar progressBar;
    ImageButton home,search,other;
    GetLocation GetLocation;

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
        List<List<String>> res=new ArrayList<>();
        List<String> res2=new ArrayList<>();
        Forecast forecast=new Forecast(this);
        res = forecast.GetTodayForecast((long) 524901);
        Log.i("asjfsfhksajf", String.valueOf(res.size()));
        for (int i=0;i<res.size();++i){
            res2= res.get(i);
            for (int j=0;j<res2.size();++j){
                Log.i("asjfsfhksajf",res2.get(j));
            }
        }
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
