package com.example.smartparking.ui;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartparking.R;
import com.example.smartparking.models.LoginRequest;
import com.example.smartparking.models.LoginResponse;
import com.example.smartparking.services.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.passwordLoginButton) Button mPasswordLoginButton;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.image1)
    ImageView image;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.registerTextView) TextView mRegisterTextView;
    Animation topAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mRegisterTextView.setOnClickListener(this);
        mPasswordLoginButton.setOnClickListener(this);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image.setAnimation(topAnim);
        linearLayout.setAnimation(bottomAnim);
    }
    @Override
    public void onClick(View view) {
        if (view == mRegisterTextView) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == mPasswordLoginButton) {
            intoUser(loginUser());
        }
    }
    public LoginRequest loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(mPassword.getText().toString());
        return loginRequest;
    }
    public void intoUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getLogin().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login unsuccessful"+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}