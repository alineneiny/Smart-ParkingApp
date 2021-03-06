package com.example.smartparking.ui;

import android.app.ProgressDialog;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.R;
import com.example.smartparking.models.RegisterRequest;
import com.example.smartparking.models.RegisterResponse;
import com.example.smartparking.services.ApiClient;
import com.example.smartparking.storage.SharedPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.createUserButton) Button mCreateUserButton;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.lname) EditText lname;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.fname) EditText fname;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginTextView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    Animation topAnim, bottomAnim;
    private ProgressDialog mAuthProgressDialog;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        linearLayout.setAnimation(bottomAnim);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        createAuthProgressDialog();
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginTextView) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == mCreateUserButton) {
            saveRegister(addUser());
        }
    }
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Please wait while signing up......");
        mAuthProgressDialog.setCancelable(true);
    }
    public RegisterRequest addUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username.getText().toString());
        registerRequest.setPassword(mPasswordEditText.getText().toString());
        registerRequest.setFirst_name(fname.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setLast_name(lname.getText().toString());

        return registerRequest;
    }

    public void saveRegister(RegisterRequest registerRequest){
        mAuthProgressDialog.show();
        Call<RegisterResponse> registerResponseCall = ApiClient.getRegisterService().saveRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()){
                    mAuthProgressDialog.dismiss();
                    sharedPreferenceManager.saveUser(registerResponse.getUser());
                    Toast.makeText(RegisterActivity.this, "Welcome...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, UserProfile.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    mAuthProgressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                mAuthProgressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Register unsuccessful"+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}