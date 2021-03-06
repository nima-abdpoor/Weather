package com.example.weather.mai;

import android.app.Notification;
import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.text.Html;
import android.view.View;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.example.weather.mai.MainActivity.DEFAULT_TEMP;
import static com.example.weather.mai.MainActivity.MyCity;

public class GettingData {
    NotificationManagerCompat notificationManager;
    Context context;

    private double longitude;
    private double latitude;
    private int weatherId;
    private String icon = "";
    private int tempAverage;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int humidity;
    private double speed;
    private double rain1h;
    private double snow1h;
    private String country;
    private int cityId;
    private String city;
    private String detail;

    Double lat = 0.0;
    Double lon = 0.0;

    public static final String KEY = "b34d97936eaadfa405d3b9b18db6a0ff";
    public static String URL = "https://api.openweathermap.org/data/2.5/weather?lat=%1$s&lon=%2$s&appid=" + KEY;


    public GettingData(Context context, Double lon, Double lat) {
        this.context = context;
        this.lon = lon;
        this.lat = lat;
        RequestData(URL);
    }

    public void RequestData(String uri) {
        uri = GetUrl(uri);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setCity(response.getString("name") + ", " + response.getJSONObject("sys").getString("country"));
                    setTempAverage(response.getJSONObject("main").getInt("temp"));
                    setIcon(response.getJSONArray("weather").getJSONObject(0).getString("icon"));
                    setDetail(response.getJSONArray("weather").getJSONObject(0).getString("description"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private String GetUrl(String uri) {
        uri = String.format(uri, lat, lon);
        return uri;
    }

    private void CityNotFound(VolleyError error) {
        if(hostAvailable("www.google.com",80)){
            WeatherView.city.setText("NO INTERNET");
            WeatherView.progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            WeatherView.city.setText("City Not Found");
            WeatherView.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public boolean hostAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        }
        catch (IOException ex){
            System.out.println(ex);
            return false;
        }
        catch (NetworkOnMainThreadException e) {
            WeatherView.city.setText("City Not Found");
            WeatherView.progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
    }


    public void SetView() {
        WeatherView.progressBar.setVisibility(View.INVISIBLE);
        WeatherView.city.setText(getCity());
        WeatherView.temp.setText(getTempAverage(DEFAULT_TEMP));
        WeatherView.detail.setText(getDetail());
        getIcon();
        SendNotification();
    }

    private void SendNotification() {
        notificationManager= NotificationManagerCompat.from(context);
        Notification notification=new NotificationCompat.Builder(context,MyCity)
                .setSmallIcon(getIcon(),10)
                .setContentTitle(getTempAverage(DEFAULT_TEMP)+" ("+detail+")")
                .setContentText(city)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1,notification);
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

    public int getIcon() {
        if (icon.contains("01") || icon.contains("02") || icon.contains("10")) {
            switch (icon) {
                case "01d":
                    WeatherView.stateicon.setImageResource(R.drawable.clearskyd);
                    return R.drawable.clearskyd;
                case "01n":
                    WeatherView.stateicon.setImageResource(R.drawable.clearskyn);
                    return R.drawable.clearskyn;
                case "02d":
                    WeatherView.stateicon.setImageResource(R.drawable.fewcloudsd);
                    return R.drawable.clearskyn;
                case "02n":
                    WeatherView.stateicon.setImageResource(R.drawable.fewcloudsn);
                    return R.drawable.fewcloudsn;
                case "10d":
                    WeatherView.stateicon.setImageResource(R.drawable.raind);
                    return R.drawable.raind;
                case "10n":
                    WeatherView.stateicon.setImageResource(R.drawable.rainn);
                    return R.drawable.rainn;
            }
        } else {
            icon = icon.substring(0, 2);
            switch (icon) {
                case "03":
                    WeatherView.stateicon.setImageResource(R.drawable.scatterdcloudsd);
                    return R.drawable.scatterdcloudsd;
                case "04":
                    WeatherView.stateicon.setImageResource(R.drawable.brokencloudsd);
                    return R.drawable.brokencloudsd;
                case "09":
                    WeatherView.stateicon.setImageResource(R.drawable.showerraind);
                    return R.drawable.showerraind;
                case "11":
                    WeatherView.stateicon.setImageResource(R.drawable.thunderstormd);
                    return R.drawable.thunderstormd;
                case "13":
                    WeatherView.stateicon.setImageResource(R.drawable.snow);
                    return R.drawable.snow;
                case "50":
                    WeatherView.stateicon.setImageResource(R.drawable.mist);
                    return R.drawable.mist;
            }
        }
        return 0;
    }

    public String getTempAverage(String type) {
        switch (type) {
            case "Kelvin":
                return (String.valueOf(tempAverage));
            case "Celsius":
                return (String.valueOf((tempAverage - 273)) + Html.fromHtml("&#8451;"));
            case "Fahrenheit":
                return String.valueOf((((tempAverage - 273) * 1.8) + 32));
            default:
                break;
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
        switch (city) {
            case "Tehran":
                // WeatherView.cardView.setBackgroundResource(R.drawable.damavand);
                break;
            default:
                break;
        }
        return city;
    }

    public String getDetail() {
        return detail;
    }
}
