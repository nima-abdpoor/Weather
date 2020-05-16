package com.example.weather.mai;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class GetLocation extends Service implements LocationListener {

    private Context context;
    private LocationManager locationManager;

    private Location location;
    private double latitude = 0.0;
    private double longitude = 0.0;

    private Boolean DialogState=false;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;


    public static final int TIME_BW_UPDATES = 1000 * 10; // 10 seconds
    public static final int MIN_DISTANCE_FOR_UPDATE = 10;     // 10 meter

    public GetLocation(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() throws SecurityException {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled) {
            canGetLocation = false;
            // no provider enabled
        } else {
            canGetLocation = true;
            // first , get location using network provider
            if (!hasPermissions()) {
                location = null;
                return location;
            }
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        TIME_BW_UPDATES,
                        MIN_DISTANCE_FOR_UPDATE,
                        this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

            if (isGpsEnabled && location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        TIME_BW_UPDATES, MIN_DISTANCE_FOR_UPDATE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }

        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {

        return this.canGetLocation;
    }

    public boolean hasPermissions() {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    public Boolean showGpsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS")
                .setMessage("GPS is not enabled. Do you want to go to Settings menu?")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        DialogState=true;
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                        DialogState=true;

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        DialogState=false;
                    }
                });
        builder.show();
        return DialogState;
    }

    public void stopUsingGps() throws SecurityException {
        if (locationManager != null && hasPermissions()) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.i(GetLocation.class.getSimpleName(),
                    "lat : " + location.getLatitude() + ", lon = " + location.getLongitude());
        } else {
            Log.i(GetLocation.class.getSimpleName(), "location = null");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(GetLocation.class.getSimpleName(), "provider enabled : " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(GetLocation.class.getSimpleName(), "provider disabled : " + provider);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
