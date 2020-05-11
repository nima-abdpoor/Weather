package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.CityDbHelper;

import static com.example.weather.GettingData.key;
import static com.example.weather.MainActivity.DEFAULT_TEMP;

public class OtherCities extends AppCompatActivity {
    ProgressBar progressBar;
    ViewPager viewPager;
    List<String> MyCities;
    List<Fragment> fragments;
    MyPagerAdapter adapter;
    RequestQueue requestQueue;
    JsonObjectRequest request;
    ImageButton home,search,other;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_cities);
        VeryfingViewItems();
        setonclickforicons();
        progressBar.setVisibility(View.VISIBLE);
        Init();
    }

    private void VeryfingViewItems() {
        progressBar=findViewById(R.id.progress_bar);
        viewPager=findViewById(R.id.view_pager);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        other=findViewById(R.id.other);
    }

    private void Init() {
        requestQueue =Volley.newRequestQueue(this);
        RequestData();
        adapter=new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

   /* @Override
    protected void onResume() {
        RequestData();
        super.onResume();
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                RequestData();
                handler.postDelayed(this,10 *60*1000);
            }
        });
    }
*/
    public void RequestData() {
        fragments=new ArrayList<>();
        fragments.clear();
        String uri=PrepareUri();
            request = new JsonObjectRequest(Request.Method.GET, uri, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressBar.setVisibility(View.INVISIBLE);
                                int cnt=response.getInt("cnt");
                                JSONArray jsonArray=response.getJSONArray("list");
                                for (int i=0;i<cnt;++i){
                                    JSONObject res=jsonArray.getJSONObject(i);
                                    Bundle args=new Bundle();
                                    args.putString("city",res.getString("name")+
                                            ", "+res.getJSONObject("sys").getString("country"));
                                    args.putString("tempA", String.valueOf(res.getJSONObject("main").getInt("temp")));
                                    args.putString("icon",res.getJSONArray("weather").getJSONObject(0).getString("icon"));
                                    args.putString("description",res.getJSONArray("weather").getJSONObject(0).getString("description"));
                                    fragments.add(WeatherFragment.newInstance(args));
                                }
                                UpdateDisplay();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (fragments.size() == 0){
                        Toast.makeText(OtherCities.this,"Please Add City",Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
            requestQueue.add(request);
    }

    private void UpdateDisplay(){
        adapter=new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }
    private String PrepareUri() {
        MyCities=new ArrayList<>();
        CityDbHelper dbHelper=new CityDbHelper(this);
        MyCities.addAll(dbHelper.GetMyCitiesId());
        String[] cities;
        cities= MyCities.toArray(new String[0]);
        StringBuilder urlResult=new StringBuilder();
        urlResult.append("http://api.openweathermap.org/data/2.5/group?id=");
        for (int i = 0; i < cities.length; i++) {
            urlResult.append(cities[i]);
            if (i != (cities.length)-1){
                urlResult.append(",");
            }
        }
        urlResult.append("&APPID="+key);
        return String.valueOf(urlResult);
    }
    private void setonclickforicons() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OtherCities.this,WeatherView.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OtherCities.this,SearchCity.class);
                startActivity(intent);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
    }
    private void refreshPage() {
        Init();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(OtherCities.this,WeatherView.class);
        startActivity(intent);
    }
}

    class MyPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public MyPagerAdapter(FragmentManager fm, List<Fragment> frag) {
        super(fm);
        this.fragments=frag;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    }
