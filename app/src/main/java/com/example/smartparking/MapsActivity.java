package com.example.smartparking;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.smartparking.ui.ReservationActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // 5 Meters
    private LocationListener locationListener;
    private LocationManager locationManager;


    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });

// Add a marker in Sydney and move the camera
        LatLng Kigali = new LatLng(-1.9294217, 29.9871581);
        mMap.addMarker(new MarkerOptions().position(Kigali).title("KIGALI"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kigali, 1400));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Kigali));
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    latLng = new LatLng(-1.9531, 30.0931);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Kigali Heights Parking"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    latLng = new LatLng(-1.9555493, 30.1021022);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Auca"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}

