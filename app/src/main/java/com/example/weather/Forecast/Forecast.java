package com.example.weather.Forecast;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class Forecast {

    public Forecast(Context context) {
        this.context = context;
    }

    Context context;
    private final int[] cntforfivedays = {1, 9, 17, 25, 33};
    private static String URL = "https://api.openweathermap.org/data/2.5/forecast?id=" +
            "%s&appid=b34d97936eaadfa405d3b9b18db6a0ff";
    List<List<String>> result=new ArrayList<>();
    List<List<String>> result2=new ArrayList<>();
    List<String> objects=new ArrayList<>();

    public List<List<String>> GetTodayForecast(Long ID) {
        URL = String.format(URL, ID);
        Request();
        Log.i("asdfasf", String.valueOf(getResult()));
        return result;
    }

    private String Request() {
        JsonObjectRequest request = new JsonObjectRequest(GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");
                    for (int i = 0; i < cntforfivedays.length; i++) {
                        JSONObject res = jsonArray.getJSONObject(cntforfivedays[i]);
                        objects.add(res.getJSONObject("main").getString("temp"));
                        objects.add(res.getJSONArray("weather").getJSONObject(0).getString("icon"));
                        objects.add(res.getJSONArray("weather").getJSONObject(0).getString("description"));
                        result.add(objects);
                    }
                    setResult(Collections.singletonList(result.get(2)));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error in forecast", error.getMessage());
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        return null;
    }

    private void setResult(List<List<String>> result) {
        this.result2=result;
    }

    public List<List<String>> getResult() {
        return result2;
    }
}
