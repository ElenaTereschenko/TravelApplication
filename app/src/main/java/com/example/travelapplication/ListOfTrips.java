package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListOfTrips extends AppCompatActivity {

    public List<String> idTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_trips);

        //Получаем ID всeх поездок
        new SendGetRequest().execute();

        //Получаем поездки по ID

    }

    public class SendGetRequest extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {

                //Получаем токен
                SharedPreferences preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                String token = preferences.getString("token", "");

                //Формируем запрос
                URL url = new URL("http://travelapp.fun/api/trip/getall"+"?token="+token);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");

                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode==405) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();

                    //Парсим JSON

                    JSONArray result = new JSONArray(sb.toString());
                    idTrips = new ArrayList<>();

                    for (int i = 0; i < result.length(); i++){
                        idTrips.add(result.getString(i));
                    }

                    return "";
                } else {
                    return new String("false : " + responseCode);
                }


            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }
        }
    }
}
