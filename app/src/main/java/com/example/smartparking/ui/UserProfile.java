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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.R;
import com.example.smartparking.services.ApiClient;
import com.example.smartparking.storage.SharedPreferenceManager;
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
    @BindView(R.id.logoutBtn) ImageView logoutBtn;
    @BindView(R.id.contactus) MaterialCardView contactus;
    @BindView(R.id.receipts) MaterialCardView receipts;
    @BindView(R.id.gridview) GridView gridView;
    @BindView(R.id.full_name) TextView full_name;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.release) MaterialCardView releaseCar;

    SharedPreferenceManager sharedPreferenceManager;
    private List<BlockResponse> blockResponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        cardParking.setOnClickListener(this);
        contactus.setOnClickListener(this);
        releaseCar.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        receipts.setOnClickListener(this);
        full_name.setText(sharedPreferenceManager.getUser().getUsername()+", "+sharedPreferenceManager.getUser().getLast_name());
        email.setText(sharedPreferenceManager.getUser().getEmail());
        if(sharedPreferenceManager.getUser().getRole()==true){
            releaseCar.setVisibility(View.VISIBLE);
            contactus.setVisibility(View.GONE);
        }else {
            releaseCar.setVisibility(View.GONE);
            contactus.setVisibility(View.VISIBLE);
        }
        getAllBlocks();
    }

    public void getAllBlocks() {
        Call<List<BlockResponse>> imagesResponse = ApiClient.getInterface().getAllImages();
        imagesResponse.enqueue(new Callback<List<BlockResponse>>() {
            @Override
            public void onResponse(Call<List<BlockResponse>> call, Response<List<BlockResponse>> response) {
                if (response.isSuccessful()) {
                    blockResponse = response.body();
                    UserProfile.CustomAdapter customAdapter = new UserProfile.CustomAdapter(blockResponse, UserProfile.this);
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
        if (v == receipts) {
            Intent intent = new Intent(UserProfile.this, Receipt.class);
            startActivity(intent);
        }
        if (v == logoutBtn) {
            sharedPreferenceManager.logout();
            Intent intent = new Intent(UserProfile.this, LoginActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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

            }
            return convertView;
        }
    }
}