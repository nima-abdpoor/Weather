package com.example.weather;



import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;

public class ManageCurrentLocation {
    private List<Double> coordinates=new ArrayList<>();
    private double lat=0.0;
    private double lon=0.0;
    private Context context;
    public ManageCurrentLocation(Context context){
        this.context=context;
    }
    public List<Double> getcoordinates(){
        CityDbHelper cityDbHelper=new CityDbHelper(context);
        if(cityDbHelper.cangetcurrentcity()){
            coordinates=getfromdatabase();
        }
        else {
            coordinates=getfromgps();
        }
        return coordinates;
    }

    private List<Double> getfromdatabase() {
        CityDbHelper cityDbHelper=new CityDbHelper(context);
        coordinates =cityDbHelper.GetCurrentCity();
        if (coordinates.get(0)==0.0 || coordinates.get(1)==0.0){
            coordinates= getfromgps();
        }
        return coordinates;
    }

    private List<Double> getfromgps() {
        CityDbHelper cityDbHelper=new CityDbHelper(context);
        GetLocation getLocation=new GetLocation(context);
        lat=getLocation.getLatitude();
        lon=getLocation.getLongitude();
        if(lon == 0.0 || lat== 0.0){
            lat=getLocation.getLatitude();
            lon=getLocation.getLongitude();
        }
        cityDbHelper.SetCurrentCity(lat,lon);
        coordinates.add(lat);
        coordinates.add(lon);
        return coordinates;
    }
}

