package com.example.weather;

import android.content.Context;
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

import static com.example.weather.MainActivity.DEFAULT_TEMP;

public class WeatherFragment extends Fragment {
    static TextView City,temp, Detail;
    static ImageView Icon;

     private String ID="";

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

    private static String URL="https://api.openweathermap.org/data/2.5/weather?" +
            "id=%s&appid=b34d97936eaadfa405d3b9b18db6a0ff";

    public static WeatherFragment newInstance(Bundle args) {
        WeatherFragment fragment =new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        setCity(args.getString("city"));
        setTempAverage(Integer.parseInt(args.getString("tempA")));
        setDetail(args.getString("description"));
        setIcon(args.getString("icon"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.card_weather,container,false);
        City =view.findViewById(R.id.city);
        temp=view.findViewById(R.id.temp);
        Detail =view.findViewById(R.id.detail);
        Icon =view.findViewById(R.id.icon);
        SetView();
        return view;
    }



    private void SetView(){
        City.setText(getCity());
        temp.setText(getTempAverage(DEFAULT_TEMP));
        Detail.setText(getDetail());
        getIcon();
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
