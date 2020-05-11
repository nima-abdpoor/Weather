package com.example.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {

    public static String  [] TEMP_TYPE = new String[]{"Kelvin","Celsius","Fahrenheit"};
    public static String DEFAULT_TEMP=TEMP_TYPE[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        else {
            Intent intent = new Intent(MainActivity.this, WeatherView.class);
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100){
            Log.i("testrequest","true");
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(MainActivity.this,WeatherView.class);
                startActivity(intent);
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permission Denied To Access Location",Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
    }


}
