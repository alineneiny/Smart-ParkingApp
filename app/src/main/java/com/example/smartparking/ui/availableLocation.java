package com.example.smartparking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.R;
import com.example.smartparking.models.LocationResponse;
import com.example.smartparking.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class availableLocation extends AppCompatActivity {
    GridView gridView;
    private List<LocationResponse> locationresponses =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_location);
        gridView =findViewById(R.id.gridview1);
        getAllLocation();
    }
    public  void  getAllLocation(){
        Call<List<LocationResponse>> locationresponse = ApiClient.getInterfac().getAllLocation();
        locationresponse.enqueue(new Callback<List<LocationResponse>>() {
            @Override
            public void onResponse(Call<List<LocationResponse>> call, Response<List<LocationResponse>> response) {
                if(response.isSuccessful()){
                    String message ="Request successful";
                    Toast.makeText(availableLocation.this,message,Toast.LENGTH_LONG).show();
                    locationresponses=response.body();
                    availableLocation.CustomAdapter customAdapter = new availableLocation.CustomAdapter(locationresponses,availableLocation.this);
                    gridView.setAdapter(customAdapter);

                }
                else{
                    String message ="an error occured try again later..";
                    Toast.makeText(availableLocation.this,message,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<LocationResponse>> call, Throwable t) {
                String message =t.getLocalizedMessage();
                Toast.makeText(availableLocation.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }
    public class CustomAdapter extends BaseAdapter {
        private List<LocationResponse>locationlist;
        private Context context;
        private LayoutInflater layoutInflater;


        public CustomAdapter(List<LocationResponse> locationlist, Context context) {
            this.locationlist = locationlist;
            this.context = context;
            this.layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return locationlist.size();
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
                convertView=layoutInflater.inflate(R.layout.row1,parent,false);

//              ImageView imageView = convertView.findViewById(R.id.imageView);
                TextView name=convertView.findViewById(R.id.name);


                name.setText(locationlist.get(position).getName());

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(availableLocation.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

//              Glide.with(context).load(locationlist.get(position).getLocation_pic()).into(imageView);
            }
            return convertView;
        }
    }
}