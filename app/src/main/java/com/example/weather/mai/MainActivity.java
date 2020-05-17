package com.example.weather.mai;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.weather.R;


public class MainActivity extends AppCompatActivity {
    public static final String MyCity ="MyCity";
    public static String  [] TEMP_TYPE = new String[]{"Kelvin","Celsius","Fahrenheit"};
    public static String DEFAULT_TEMP=TEMP_TYPE[1];
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateNotificationChannels();
        checkPermissions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CreateNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mycity =new NotificationChannel(MyCity,MyCity, NotificationManager.IMPORTANCE_DEFAULT);
            mycity.setDescription("جونی ننت دستش نزن");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(mycity);
        }
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
