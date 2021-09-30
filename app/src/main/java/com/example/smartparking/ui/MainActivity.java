package com.example.smartparking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.R;
import com.example.smartparking.services.ApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // 5 Meters
    private LocationListener locationListener;
    private LocationManager locationManager;


    private LatLng latLng;
    GridView gridView;
    Button button2;
    private List<ImageResponse> imageResponses =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView =findViewById(R.id.gridview);
        button2=findViewById(R.id.button2);
        getAllImages();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);}



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });

// Add a marker in Sydney and move the camera
        LatLng Kigali = new LatLng(-1.9294217, 29.9871581);
        mMap.addMarker(new MarkerOptions().position(Kigali).title("Your Location"));
        float zoomLevel = 12.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kigali, zoomLevel));
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    latLng = new LatLng(-1.945, 30.0561);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Kigali DownTown Building"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    latLng = new LatLng(-1.9555493, 30.1021022);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Auca"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                }
                catch (SecurityException e){
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
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }


    public  void  getAllImages(){
        Call<List<ImageResponse>>imagesResponse = ApiClient.getInterface().getAllImages();
        imagesResponse.enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call, Response<List<ImageResponse>> response) {
                if(response.isSuccessful()){
                    String message ="Request successful";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    imageResponses=response.body();
                    CustomAdapter customAdapter = new CustomAdapter(imageResponses,MainActivity.this);
                    gridView.setAdapter(customAdapter);

                }
                else{
                    String message ="an error occured try again later..";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {
                String message =t.getLocalizedMessage();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }
    public class CustomAdapter extends BaseAdapter{
        private List<ImageResponse>imageResponseList;
        private Context context;
        private LayoutInflater layoutInflater;


        public CustomAdapter(List<ImageResponse> imageResponseList, Context context) {
            this.imageResponseList = imageResponseList;
            this.context = context;
            this.layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imageResponseList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.row,parent,false);

                TextView block=convertView.findViewById(R.id.block);
//               TextView location= convertView.findViewById(R.id.location);
                TextView slot= convertView.findViewById(R.id.slot);
                Button button2 =convertView.findViewById(R.id.button2);
                TextView Locationname=convertView.findViewById(R.id.lname);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ReservationActivity.class));
                    }
                });

//               location.setText( "Location"+" "+imageResponseList.get(position).getLocation());
                Locationname.setText("Location Name:"+imageResponseList.get(position).getLocation_name());
//
                slot.setText("Number of Slot:"+" "+imageResponseList.get(position).getNumber_of_slots());
                block.setText("Block Code:"+" "+imageResponseList.get(position).getBlock_code());

//                GlideApp.with(context).load(imageResponseList.get(position).getBlock_photo()).into(imageView);
            }
            return convertView;
        }
    }

}