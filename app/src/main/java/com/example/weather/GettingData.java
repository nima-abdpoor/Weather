package com.example.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

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

public class GettingData extends AsyncTask<String ,String ,String>{
    Context context;

    private double longitude ;
    private double latitude;
    private int weatherId;
    private String icon="";
    private double tempAverage ;
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

    public static final String key="b34d97936eaadfa405d3b9b18db6a0ff";
    public static String URL="https://api.openweathermap.org/data/2.5/weather?q=%s&appid="+key;

    public GettingData(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... params) {
        city= String.valueOf(MainActivity.city.getText());
        RequestData(URL,getCity());
        return "";
    }


    @Override
    protected void onPostExecute(String result) {

    }


    public void RequestData(String uri, String city) {
        uri =String.format(Locale.getDefault(),uri,city);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setCity(response.getString("name"));
                    setTempAverage(response.getJSONObject("main").getDouble("temp"));
                    SetView();
                } catch (JSONException e) {
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
    private void CityNotFound(VolleyError error){
            WeatherView.city.setText("city not found");
    }



    public void SetView(){
        WeatherView.city.setText(getCity());
        WeatherView.temp.setText(String.valueOf(getTempAverage(DEFAULT_TEMP)));
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

    public void setTempAverage(double tempAverage) {
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
        return icon;
    }

    public double getTempAverage(String type) {
        switch (type){
            case  "Kelvin":
                return tempAverage;
            case "Celsius":
                return ((int)tempAverage-273);
            case "Fahrenheit":
                return ((int)((tempAverage-273)*1.8)+32);
            default:break;
        }
        return tempAverage;
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
                WeatherView.cardView.setBackgroundResource(R.drawable.damavand);
                break;
            default:break;
        }
        return city;
    }
}
