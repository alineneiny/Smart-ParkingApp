package com.example.smartparking.ui;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.smartparking.models.LoginRequest;
import com.example.smartparking.models.ReceiptRequest;
import com.example.smartparking.services.ApiClient;
import com.example.smartparking.storage.SharedPreferenceManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Receipt extends AppCompatActivity {

    @BindView(R.id.receiptView)
    GridView receiptView;
    SharedPreferenceManager sharedPreferenceManager;

    private List<ReceiptResponse> receiptResponse = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        ButterKnife.bind(this);

        getAllReceipts(userReceipt());
    }
    public ReceiptRequest userReceipt(){
        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setId(sharedPreferenceManager.getUser().getId());
        return receiptRequest;
    }
    public void getAllReceipts(ReceiptRequest receiptRequest) {
        Call<List<ReceiptResponse>> receiptsResponse = ApiClient.getReservationService().getAllReceipts(receiptRequest);
        receiptsResponse.enqueue(new Callback<List<ReceiptResponse>>() {
            @Override
            public void onResponse(Call<List<ReceiptResponse>> call, Response<List<ReceiptResponse>> response) {
                if (response.isSuccessful()) {
                    receiptResponse = response.body();
                    Receipt.CustomAdapter customAdapter = new Receipt.CustomAdapter(receiptResponse, Receipt.this);
                    receiptView.setAdapter(customAdapter);

                } else {
                    String message = "an error occured try again later..";
                    Toast.makeText(Receipt.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<ReceiptResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(Receipt.this, message, Toast.LENGTH_LONG).show();

            }
        });
    }

    public class CustomAdapter extends BaseAdapter {
        private List<ReceiptResponse> receiptResponseList;
        private Context context;
        private LayoutInflater layoutInflater;


        public CustomAdapter(List<ReceiptResponse> receiptResponseList, Context context) {
            this.receiptResponseList = receiptResponseList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return receiptResponseList.size();
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
                convertView = layoutInflater.inflate(R.layout.receipt, parent, false);

                TextView block = convertView.findViewById(R.id.block);
                TextView entryTime = convertView.findViewById(R.id.entry_time);
                TextView exitTime = convertView.findViewById(R.id.exit_time);
                TextView bDate = convertView.findViewById(R.id.date);
                TextView plateNo = convertView.findViewById(R.id.plate);
                Button checkoutBtn = convertView.findViewById(R.id.checkout_btn);
                checkoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        startActivity(new Intent(Receipt.this, ReservationActivity.class));
                    }
                });
                block.setText("Block Code:" + " " + receiptResponseList.get(position).getParking_block());
                entryTime.setText("Entry Time:" + " " + receiptResponseList.get(position).getEntry_time());
                exitTime.setText("Exit Time:" + " " + receiptResponseList.get(position).getExit_time());
                bDate.setText("Booking Date:" + " " + receiptResponseList.get(position).getBooking_date());
                plateNo.setText("Plate NO:" + " " + receiptResponseList.get(position).getPlate_No());

            }
            return convertView;
        }
    }
}