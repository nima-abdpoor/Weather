package com.example.weather;

import android.content.ContentValues;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.example.weather.MainActivity.DEFAULT_TEMP;

public class GettingData{
    Context context;

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

    Double lat=0.0;
    Double lon=0.0;

    public static final String key="b34d97936eaadfa405d3b9b18db6a0ff";
    public static String URL="https://api.openweathermap.org/data/2.5/weather?lat=%1$s&lon=%2$s&appid="+key;


    public GettingData(Context context,Double lon,Double lat){
        this.context=context;
        this.lon=lon;
        this.lat=lat;
        RequestData(URL);
    }

    public void RequestData(String uri) {
        uri=GetUrl(uri);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setCity(response.getString("name")+", "+response.getJSONObject("sys").getString("country"));
                    setTempAverage(response.getJSONObject("main").getInt("temp"));
                    setIcon(response.getJSONArray("weather").getJSONObject(0).getString("icon"));
                    setDetail(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                    SetView();
                    Log.i("salam",getCity());
                } catch (JSONException e) {
                    Log.i("solam",e.getMessage());
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CityNotFound(error);
            }
        }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private String GetUrl(String uri) {
        uri=String.format(uri,lat,lon);
        Log.i("sakdfjlsadkf",uri);
        return uri;
    }

    private void CityNotFound(VolleyError error){
            WeatherView.city.setText("city not found");
    }



    public void SetView(){
        WeatherView.progressBar.setVisibility(View.INVISIBLE);
        WeatherView.city.setText(getCity());
        WeatherView.temp.setText(getTempAverage(DEFAULT_TEMP));
        WeatherView.detail.setText(getDetail());
        getIcon();
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
                    WeatherView.stateicon.setImageResource(R.drawable.clearskyd);
                    break;
                case "01n":
                    WeatherView.stateicon.setImageResource(R.drawable.clearskyn);
                    break;
                case "02d":
                    WeatherView.stateicon.setImageResource(R.drawable.fewcloudsd);
                    break;
                case "02n":
                    WeatherView.stateicon.setImageResource(R.drawable.fewcloudsn);
                    break;
                case "10d":
                    WeatherView.stateicon.setImageResource(R.drawable.raind);
                    break;
                case "10n":
                    WeatherView.stateicon.setImageResource(R.drawable.rainn);
                    break;
            }
        }
        else {
            icon=icon.substring(0,2);
            switch (icon){
                case "03":
                    WeatherView.stateicon.setImageResource(R.drawable.scatterdcloudsd);
                    break;
                case "04":
                    WeatherView.stateicon.setImageResource(R.drawable.brokencloudsd);
                    break;
                case "09":
                    WeatherView.stateicon.setImageResource(R.drawable.showerraind);
                    break;
                case "11":
                    WeatherView.stateicon.setImageResource(R.drawable.thunderstormd);
                    break;
                case "13":
                    WeatherView.stateicon.setImageResource(R.drawable.snow);
                    break;
                case "50":
                    WeatherView.stateicon.setImageResource(R.drawable.mist);
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
               // WeatherView.cardView.setBackgroundResource(R.drawable.damavand);
                break;
            default:break;
        }
        return city;
    }
    public String getDetail() {
        return detail;
    }
}
