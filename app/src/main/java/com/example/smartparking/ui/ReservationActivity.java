package com.example.smartparking.ui;

import static java.lang.String.*;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.smartparking.R;
import com.example.smartparking.models.ReservationRequest;
import com.example.smartparking.models.ReservationResponse;
import com.example.smartparking.services.ApiClient;
import com.example.smartparking.storage.SharedPreferenceManager;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    @BindView(R.id.editTextEntryDate) EditText entryDate;
    @BindView(R.id.editTextExitTime) EditText exitTime;
    @BindView(R.id.editTextEntryTime) EditText entryTime;
    @BindView(R.id.editPlateNo) EditText plateNo;
    @BindView(R.id.reservationButton) Button reservationButton;
    Date date1;
    Date date2;
    private int mHour, mMinute,mSecond,mYear, mMonth, mDay;
    String format;
    long dMinutes;
    @BindView(R.id.image3)
    ImageView image;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    Animation topAnim, bottomAnim;
    AwesomeValidation awesomeValidation;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        ButterKnife.bind(this);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image.setAnimation(topAnim);
        linearLayout.setAnimation(bottomAnim);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        // add validations
        awesomeValidation.addValidation(this,R.id.plate,"{7}",R.string.invalid_plate_No);
        awesomeValidation.addValidation(this,R.id.date, RegexTemplate.NOT_EMPTY,R.string.invalid_booking);
        entryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String sDate=year+ "-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                entryDate.setText(sDate);
                                
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        entryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond=c.get(Calendar.SECOND);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }
                                    entryTime.setText(hourOfDay + ":" + minute + ":" + mSecond);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        exitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond=c.get(Calendar.SECOND);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReservationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }

                                exitTime.setText(hourOfDay + ":" + minute + ":" + mSecond);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate() ) {
                    PayBooking();
                }
            }
        });


    }

    public ReservationRequest addReservation(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            date1 = format.parse(valueOf(entryTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = format.parse(valueOf(exitTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dMinutes= date1.getTime() - date2.getTime();
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setUser_id(sharedPreferenceManager.getUser().getId());
        reservationRequest.setParking_block(1);
        reservationRequest.setBooking_date(entryDate.getText().toString());
        reservationRequest.setEntry_time(entryTime.getText().toString());
        reservationRequest.setExit_time(exitTime.getText().toString());
        reservationRequest.setDuration_in_minutes(60);
        reservationRequest.setAmount(100);
        reservationRequest.setPlate_No(plateNo.getText().toString());
        return reservationRequest;
    }
    public void PayBooking(){
        new RaveUiManager(ReservationActivity.this).setAmount(100)
                .setCurrency("RWF")
                .setEmail(sharedPreferenceManager.getUser().getEmail())
                .setfName(sharedPreferenceManager.getUser().getLast_name())
                .setlName(sharedPreferenceManager.getUser().getFirst_name())
                .setNarration("parking")
                .setPublicKey("FLWPUBK_TEST-ffca2fc845cb11d844177c7456441df5-X")
                .setEncryptionKey("FLWSECK_TEST47ebc4968736")
                .setTxRef(System.currentTimeMillis()+"ref")
                .setPhoneNumber("+250781207615", true)
                .acceptAccountPayments(false)
                .acceptCardPayments(true)
                .acceptMpesaPayments(false)
                .acceptAchPayments(false)
                .acceptGHMobileMoneyPayments(false)
                .acceptUgMobileMoneyPayments(false)
                .acceptZmMobileMoneyPayments(false)
                .acceptRwfMobileMoneyPayments(true)
                .acceptSaBankPayments(false)
                .acceptUkPayments(false)
                .acceptBankTransferPayments(false)
                .acceptUssdPayments(false)
                .acceptBarterPayments(false)
                .acceptFrancMobileMoneyPayments(false)
                .allowSaveCardFeature(false)
                .onStagingEnv(false)
                .withTheme(R.style.MyCustomTheme)
                .initialize();
    }
    public void saveReservation(ReservationRequest reservationRequest){
        Call<ReservationResponse> reservationResponseCall = ApiClient.getReservationService().saveReservation(reservationRequest);
        reservationResponseCall.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful()){

                    Toast.makeText(ReservationActivity.this, "Booking successful", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ReservationActivity.this,UserProfile.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ReservationActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                Toast.makeText(ReservationActivity.this, "Booking unsuccessful"+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                saveReservation(addReservation());
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " , Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}