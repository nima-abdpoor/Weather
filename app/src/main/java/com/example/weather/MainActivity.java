package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.CityDbHelper;


public class MainActivity extends AppCompatActivity {
    public static String  [] TEMP_TYPE = new String[]{"Kelvin","Celsius","Fahrenheit"};
    public static String DEFAULT_TEMP=TEMP_TYPE[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        getLocation();
    }

    public void getLocation(){
        GetLocation GetLocation =  new GetLocation(this);
        if(GetLocation.canGetLocation()){
            double lat = GetLocation.getLatitude();
            double lon = GetLocation.getLongitude();
            Intent weatherView=new Intent(MainActivity.this,WeatherView.class);
            weatherView.putExtra("lon",lon);
            weatherView.putExtra("lat",lat);
            startActivity(weatherView);
        } else {
           GetLocation.showGpsAlertDialog();
        }
    }


    private void checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100){
            Log.i("testrequest","true");
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permission Denied To Access Location",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
