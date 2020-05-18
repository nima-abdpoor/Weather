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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.weather.Forecast.retrofit.Forecast;
import com.example.weather.Forecast.retrofit.JsonPlaceHolderAPI;
import com.example.weather.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weather.mai.GettingData.KEY;

public class WeatherView extends AppCompatActivity {
    static TextView temp, city, detail;
    TextView time;
    TextView[] fivedaystemp = new TextView[5];
    TextView[] weektemps = new TextView[5];
    TextView[] fivedaysdetail = new TextView[5];
    TextView[] weekdetail = new TextView[5];
    TextView[] fivedaystime = new TextView[5];
    TextView[] weektime = new TextView[5];
    ImageView[] fivedaysicon = new ImageView[5];
    ImageView[] weekicon = new ImageView[5];

    static ImageView stateicon;
    static CardView cardView;
    static ProgressBar progressBar;
    ImageButton home, search, other;
    GetLocation GetLocation;

    public static int[] fivedaysforecastargs = {2, 10, 18, 26, 34};
    public static int[] weekforecastargs = {3, 4, 5, 6, 7};

    Double lon = 0.0;
    Double lat = 0.0;

    public WeatherView() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        VerifyingViewItems();
        setViewItems();
    }


    private void GettingLocation() {
        List<Double> list;
        ManageCurrentLocation currentLocation = new ManageCurrentLocation(this);
        list = currentLocation.getcoordinates();
        lat = list.get(0);
        lon = list.get(1);
        FivedaysForecast(lat, lon);
        WeekForecast(lat, lon);
        setViewItems();
    }

    private void refreshPage() {
        setViewItems();
    }

    public void VerifyingViewItems() {
        city = findViewById(R.id.city);
        stateicon = findViewById(R.id.icon);
        temp = findViewById(R.id.temp);
        cardView = findViewById(R.id.card_view);
        detail = findViewById(R.id.detail);
        progressBar = findViewById(R.id.progress_bar);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        other = findViewById(R.id.other);
        time = findViewById(R.id.time);
        forecastverifing();
        forecastweek();
    }

    private void forecastverifing() {
        fivedaystemp[0] = findViewById(R.id.temp_1);
        fivedaystemp[1] = findViewById(R.id.temp_2);
        fivedaystemp[2] = findViewById(R.id.temp_3);
        fivedaystemp[3] = findViewById(R.id.temp_4);
        fivedaystemp[4] = findViewById(R.id.temp_5);
        fivedaystime[0] = findViewById(R.id.time_1);
        fivedaystime[1] = findViewById(R.id.time_2);
        fivedaystime[2] = findViewById(R.id.time_3);
        fivedaystime[3] = findViewById(R.id.time_4);
        fivedaystime[4] = findViewById(R.id.time_5);
        fivedaysdetail[0] = findViewById(R.id.detail_1);
        fivedaysdetail[1] = findViewById(R.id.detail_2);
        fivedaysdetail[2] = findViewById(R.id.detail_3);
        fivedaysdetail[3] = findViewById(R.id.detail_4);
        fivedaysdetail[4] = findViewById(R.id.detail_5);
        fivedaysicon[0] = findViewById(R.id.icon1);
        fivedaysicon[1] = findViewById(R.id.icon2);
        fivedaysicon[2] = findViewById(R.id.icon3);
        fivedaysicon[3] = findViewById(R.id.icon4);
        fivedaysicon[4] = findViewById(R.id.icon5);
    }

    private void forecastweek() {
        weektime[0] = findViewById(R.id.time1);
        weektime[1] = findViewById(R.id.time2);
        weektime[2] = findViewById(R.id.time3);
        weektime[3] = findViewById(R.id.time4);
        weektime[4] = findViewById(R.id.time5);
        weektemps[0] = findViewById(R.id.temp1);
        weektemps[1] = findViewById(R.id.temp2);
        weektemps[2] = findViewById(R.id.temp3);
        weektemps[3] = findViewById(R.id.temp4);
        weektemps[4] = findViewById(R.id.temp5);
        weekdetail[0] = findViewById(R.id.detail1);
        weekdetail[1] = findViewById(R.id.detail2);
        weekdetail[2] = findViewById(R.id.detail3);
        weekdetail[3] = findViewById(R.id.detail4);
        weekdetail[4] = findViewById(R.id.detail5);
        weekicon[0] = findViewById(R.id.icon_1);
        weekicon[1] = findViewById(R.id.icon_2);
        weekicon[2] = findViewById(R.id.icon_3);
        weekicon[3] = findViewById(R.id.icon_4);
        weekicon[4] = findViewById(R.id.icon_5);
    }

    private void setViewItems() {
        progressBar.setVisibility(View.VISIBLE);
        new GettingData(this, lon, lat);
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
                        Intent intent = new Intent(WeatherView.this, OtherCities.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    private void FivedaysForecast(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonapi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<Forecast> call = jsonapi.getfrorecast(lat, lon, KEY);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast = response.body();
                for (int i = 0; i < fivedaysforecastargs.length; ++i) {
                    getIcon(forecast.getList().get(fivedaysforecastargs[i]).getWeather().get(0).getIcon(), i, true);
                    fivedaystemp[i].setText(forecast.getList().get(fivedaysforecastargs[i]).getMain().getTemp());
                    fivedaysdetail[i].setText(forecast.getList().get(fivedaysforecastargs[i]).getWeather().get(0).getDescription().replace(" ", "\n"));
                    fivedaystime[i].setText(forecast.getList().get(fivedaysforecastargs[i]).getDtTxt().substring(5, 10));
                    //Log.i("testforforecast", forecast.getList().get(forecastargs[i]).getDtTxt().substring(11,16));
                }
                time.setText(forecast.getList().get(fivedaysforecastargs[1]).getDtTxt().substring(11, 16));
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.i("afdhka", t.getMessage());
            }
        });
    }

    private void WeekForecast(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonapi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<Forecast> call = jsonapi.getfrorecast(lat, lon, KEY);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast forecast = response.body();
                for (int i = 0; i < weekforecastargs.length; ++i) {
                    getIcon(forecast.getList().get(weekforecastargs[i]).getWeather().get(0).getIcon(), i, false);
                    weektemps[i].setText(forecast.getList().get(weekforecastargs[i]).getMain().getTemp());
                    weekdetail[i].setText(forecast.getList().get(weekforecastargs[i]).getWeather().get(0).getDescription().replace(" ", "\n"));
                    weektime[i].setText(forecast.getList().get(weekforecastargs[i]).getDtTxt().substring(11, 16));
                    Log.i("testforforecast", forecast.getList().get(weekforecastargs[i]).getDtTxt().substring(11, 16));
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Other").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(WeatherView.this, OtherCities.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("Add").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(WeatherView.this, SearchCity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void getLocation() {
        GetLocation = new GetLocation(this);
        if (!GetLocation.canGetLocation()) {
            showGpsAlertDialog();
        } else {
            GettingLocation();
        }
    }

    public void getIcon(String icon, int i, boolean b) {
        if (b) {
            if (icon.contains("01") || icon.contains("02") || icon.contains("10")) {
                switch (icon) {
                    case "01d":
                        fivedaysicon[i].setImageResource(R.drawable.clearskyd);
                    case "01n":
                        fivedaysicon[i].setImageResource(R.drawable.clearskyn);
                    case "02d":
                        fivedaysicon[i].setImageResource(R.drawable.fewcloudsd);
                    case "02n":
                        fivedaysicon[i].setImageResource(R.drawable.fewcloudsn);
                    case "10d":
                        fivedaysicon[i].setImageResource(R.drawable.raind);
                    case "10n":
                        fivedaysicon[i].setImageResource(R.drawable.rainn);
                }
            } else {
                icon = icon.substring(0, 2);
                switch (icon) {
                    case "03":
                        fivedaysicon[i].setImageResource(R.drawable.scatterdcloudsd);
                    case "04":
                        fivedaysicon[i].setImageResource(R.drawable.brokencloudsd);
                    case "09":
                        fivedaysicon[i].setImageResource(R.drawable.showerraind);
                    case "11":
                        fivedaysicon[i].setImageResource(R.drawable.thunderstormd);
                    case "13":
                        fivedaysicon[i].setImageResource(R.drawable.snow);
                    case "50":
                        fivedaysicon[i].setImageResource(R.drawable.mist);
                }
            }
        } else {
            if (icon.contains("01") || icon.contains("02") || icon.contains("10")) {
                switch (icon) {
                    case "01d":
                        weekicon[i].setImageResource(R.drawable.clearskyd);
                    case "01n":
                        weekicon[i].setImageResource(R.drawable.clearskyn);
                    case "02d":
                        weekicon[i].setImageResource(R.drawable.fewcloudsd);
                    case "02n":
                        weekicon[i].setImageResource(R.drawable.fewcloudsn);
                    case "10d":
                        weekicon[i].setImageResource(R.drawable.raind);
                    case "10n":
                        weekicon[i].setImageResource(R.drawable.rainn);
                }
            } else {
                icon = icon.substring(0, 2);
                switch (icon) {
                    case "03":
                        weekicon[i].setImageResource(R.drawable.scatterdcloudsd);
                    case "04":
                        weekicon[i].setImageResource(R.drawable.brokencloudsd);
                    case "09":
                        weekicon[i].setImageResource(R.drawable.showerraind);
                    case "11":
                        weekicon[i].setImageResource(R.drawable.thunderstormd);
                    case "13":
                        weekicon[i].setImageResource(R.drawable.snow);
                    case "50":
                        weekicon[i].setImageResource(R.drawable.mist);
                }
            }
        }
    }
}
