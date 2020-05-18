package com.example.weather.mai;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.VolleyError;
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
import static com.example.weather.mai.MainActivity.DEFAULT_TEMP;
import static com.example.weather.mai.WeatherView.weekforecastargs;

public class WeatherFragment extends Fragment {
    static TextView City,temp, Detail;
    static ImageView Icon;
     private String ID="";
     String id ="";
    TextView time;
    TextView[] fivedaystemp = new TextView[5];
    TextView[] weektemps = new TextView[5];
    TextView[] fivedaysdetail = new TextView[5];
    TextView[] weekdetail = new TextView[5];
    TextView[] fivedaystime = new TextView[5];
    TextView[] weektime = new TextView[5];
    ImageView[] fivedaysicon = new ImageView[5];
    ImageView[] weekicon = new ImageView[5];

    private double longitude ;
    private double latitude;
    private int weatherId;
    private String icon="";
    private int tempAverage ;
    private double tempMin ;
    private double tempMax ;
    private int pressure ;
    private int humidity ;
    private double speed;
    private double rain1h;
    private double snow1h;
    private String country;
    private int cityId;
    private String city;
    private String detail;

    public static WeatherFragment newInstance(Bundle args) {
        WeatherFragment fragment =new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        WeekForecast(id);
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        setCity(args.getString("city"));
        setTempAverage(Integer.parseInt(args.getString("tempA")));
        setDetail(args.getString("description"));
        setIcon(args.getString("icon"));
        id = args.getString("id");
        WeekForecast(id);
        List<String> list = args.getStringArrayList("icons");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.card_weather,container,false);
        City =view.findViewById(R.id.city);
        temp=view.findViewById(R.id.temp);
        Detail =view.findViewById(R.id.detail);
        Icon =view.findViewById(R.id.icon);
        weektime[0] = view.findViewById(R.id.time1);
        weektime[1] = view.findViewById(R.id.time2);
        weektime[2] = view.findViewById(R.id.time3);
        weektime[3] = view.findViewById(R.id.time4);
        weektime[4] = view.findViewById(R.id.time5);
        weektemps[0] = view.findViewById(R.id.temp1);
        weektemps[1] = view.findViewById(R.id.temp2);
        weektemps[2] = view.findViewById(R.id.temp3);
        weektemps[3] = view.findViewById(R.id.temp4);
        weektemps[4] = view.findViewById(R.id.temp5);
        weekdetail[0] = view.findViewById(R.id.detail1);
        weekdetail[1] = view.findViewById(R.id.detail2);
        weekdetail[2] = view.findViewById(R.id.detail3);
        weekdetail[3] = view.findViewById(R.id.detail4);
        weekdetail[4] = view.findViewById(R.id.detail5);
        weekicon[0] = view.findViewById(R.id.icon_1);
        weekicon[1] = view.findViewById(R.id.icon_2);
        weekicon[2] = view.findViewById(R.id.icon_3);
        weekicon[3] = view.findViewById(R.id.icon_4);
        weekicon[4] = view.findViewById(R.id.icon_5);
        SetView();
        return view;
    }

    private void SetView(){
        City.setText(getCity());
        temp.setText(getTempAverage(DEFAULT_TEMP));
        Detail.setText(getDetail());
        getIcon();
    }
    private void WeekForecast(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonapi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<Forecast> call = jsonapi.getfrorecast(id, KEY);
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



    private void CityNotFound(VolleyError error){
        City.setText("city not found");
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTempAverage(int tempAverage) {
        this.tempAverage = tempAverage;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRain1h(double rain1h) {
        this.rain1h = rain1h;
    }

    public void setSnow1h(double snow1h) {
        this.snow1h = snow1h;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getIcon() {
        if(icon.contains("01")|| icon.contains("02")||icon.contains("10")){
            switch (icon){
                case "01d":
                    Icon.setImageResource(R.drawable.clearskyd);
                    break;
                case "01n":
                    Icon.setImageResource(R.drawable.clearskyn);
                    break;
                case "02d":
                    Icon.setImageResource(R.drawable.fewcloudsd);
                    break;
                case "02n":
                    Icon.setImageResource(R.drawable.fewcloudsn);
                    break;
                case "10d":
                    Icon.setImageResource(R.drawable.raind);
                    break;
                case "10n":
                    Icon.setImageResource(R.drawable.rainn);
                    break;
            }
        }
        else {
            icon=icon.substring(0,2);
            switch (icon){
                case "03":
                    Icon.setImageResource(R.drawable.scatterdcloudsd);
                    break;
                case "04":
                    Icon.setImageResource(R.drawable.brokencloudsd);
                    break;
                case "09":
                    Icon.setImageResource(R.drawable.showerraind);
                    break;
                case "11":
                    Icon.setImageResource(R.drawable.thunderstormd);
                    break;
                case "13":
                    Icon.setImageResource(R.drawable.snow);
                    break;
                case "50":
                    Icon.setImageResource(R.drawable.mist);
                    break;
            }
        }

        return icon;
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

    public String getTempAverage(String type) {
        switch (type){
            case  "Kelvin":
                return (String.valueOf(tempAverage));
            case "Celsius":
                return (String.valueOf((tempAverage-273))+ Html.fromHtml("&#8451;"));
            case "Fahrenheit":
                return String.valueOf((((tempAverage-273)*1.8)+32));
            default:break;
        }
        return String.valueOf(tempAverage);
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRain1h() {
        return rain1h;
    }

    public double getSnow1h() {
        return snow1h;
    }

    public String getCountry() {
        return country;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCity() {
        switch (city){
            case "Tehran":
                // .cardView.setBackgroundResource(R.drawable.damavand);
                break;
            default:break;
        }
        return city;
    }
    public String getDetail() {
        return detail;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
