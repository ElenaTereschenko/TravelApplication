package com.example.travelapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter extends AppCompatActivity {
    private Login view;
    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    public void setView(Login view) {
        this.view = view;
    }

    public int clicked(String username, String password) {
        try {
            sendRequest(username, password);
            return 1;

        } catch (Exception ex) {
            Log.e("Exception", new String("Exception: " + ex.getMessage()));
            return 2;
        }

    }

    public void sendRequest(String username, String password) {
        LoginBody loginBody = new LoginBody();
        loginBody.email = username;
        loginBody.password = password;


        NetworkService.getInstance().getTravelAPI().postLogin(loginBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String userId = response.body().userId;
                String token = response.body().token;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getBaseContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userId", userId);
                editor.putString("token", token);
                editor.apply();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}

