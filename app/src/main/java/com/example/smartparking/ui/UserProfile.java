package com.example.smartparking.ui;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends FragmentActivity implements View.OnClickListener {
    @BindView(R.id.cardParking) MaterialCardView cardParking;
    @BindView(R.id.contactus) MaterialCardView contactus;
    GridView gridView;
    Button button2;
    private List<BlockResponse> blockRespons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        cardParking.setOnClickListener(this);
        contactus.setOnClickListener(this);
        gridView = findViewById(R.id.gridview);
        button2 = findViewById(R.id.button2);
        getAllBlocks();
    }

    public void getAllBlocks() {
        Call<List<BlockResponse>> imagesResponse = ApiClient.getInterface().getAllImages();
        imagesResponse.enqueue(new Callback<List<BlockResponse>>() {
            @Override
            public void onResponse(Call<List<BlockResponse>> call, Response<List<BlockResponse>> response) {
                if (response.isSuccessful()) {
                    String message = "Request successful";
                    Toast.makeText(UserProfile.this, message, Toast.LENGTH_LONG).show();
                    blockRespons = response.body();
                    UserProfile.CustomAdapter customAdapter = new UserProfile.CustomAdapter(blockRespons, UserProfile.this);
                    gridView.setAdapter(customAdapter);

                } else {
                    String message = "an error occured try again later..";
                    Toast.makeText(UserProfile.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<BlockResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(UserProfile.this, message, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == cardParking) {
            Intent intent = new Intent(UserProfile.this, ParkingList.class);
            startActivity(intent);

        }
        if (v == contactus) {
            Intent intent = new Intent(UserProfile.this, ContactUsActivity.class);
            startActivity(intent);
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private List<BlockResponse> blockResponseList;
        private Context context;
        private LayoutInflater layoutInflater;


        public CustomAdapter(List<BlockResponse> blockResponseList, Context context) {
            this.blockResponseList = blockResponseList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return blockResponseList.size();
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
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row, parent, false);

                TextView block = convertView.findViewById(R.id.block);
                TextView location = convertView.findViewById(R.id.location);
                TextView slot = convertView.findViewById(R.id.slot);
                Button button2 = convertView.findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(UserProfile.this, ReservationActivity.class));
                    }
                });
                JsonObject loc = blockResponseList.get(position).getLocation();
                location.setText("Location: " + " " + loc.get("name"));
                slot.setText("Slots:" + " " + blockResponseList.get(position).getNumber_of_slots());
                block.setText("Block Code:" + " " + blockResponseList.get(position).getBlock_code());

//                GlideApp.with(context).load(blockResponseList.get(position).getBlock_photo()).into(imageView);
            }
            return convertView;
        }
    }
}