package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;

public class OtherCities extends AppCompatActivity {
    ViewPager viewPager;
    List<String> MyCities;
    List<Fragment> fragments;
    MyPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_cities);
        VeryfingViewItems();
        Init();
    }

    private void VeryfingViewItems() {
        viewPager=findViewById(R.id.view_pager);
    }

    private void Init() {

        MyCities=new ArrayList<>();
        fragments=new ArrayList<>();
        CityDbHelper dbHelper=new CityDbHelper(this);
        MyCities.addAll(dbHelper.GetMyCitiesId());
        String[] cities;
        cities= MyCities.toArray(new String[0]);
        for (int i=0;i<MyCities.size();++i) {
            fragments.add(WeatherFragment.newInstance(cities[i]));
        }
        adapter=new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
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
