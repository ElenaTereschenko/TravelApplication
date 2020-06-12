package com.example.travelapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter extends AppCompatActivity {
    private Register view;
    private Context context;

    public RegisterPresenter(Context context) {
        this.context = context;
    }

    public void setView(Register view) {
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
        RegisterBody registerBody = new RegisterBody();
        registerBody.email = username;
        registerBody.password = password;
        registerBody.username = "Леночка";


        NetworkService.getInstance().getTravelAPI().postRegister(registerBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                try{
                String userId = response.body().userId;
                String token = response.body().token;
                Log.e("token",token);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getBaseContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userId", userId);
                editor.putString("token", token);
                editor.apply();}
                catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}

