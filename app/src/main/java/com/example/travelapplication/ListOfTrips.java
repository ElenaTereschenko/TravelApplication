package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public List<Trip> trips;
    private SharedPreferences preferences;
    private String token;

    private RecyclerView listOfTrips;
    private ListOfTripsAdapter listOfTripsAdapter;

    private String tripId;
    private String userId;
    private String nameTrip;
    private String descriptionTrip;
    private List<String> phototsIdTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_trips);

        try {
            //Получаем ID всeх поездок
            new SendGetGetAll().execute().get();
        }catch (Exception e){
            String s = e.getMessage();
        }


        //Получаем поездки по ID
        trips = new ArrayList<>();
        if (idTrips.size() > 0){
            for (int i =0; i < idTrips.size(); i++){
                tripId = idTrips.get(i);
                new SendGetRead().execute();
            }
        }

        //Строим интерфейс
        listOfTrips = findViewById(R.id.recycleview_listOfTrips_listOfTrips);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfTrips.setLayoutManager(layoutManager);

        listOfTrips.setHasFixedSize(true);
        listOfTripsAdapter = new ListOfTripsAdapter(trips.size());
        listOfTrips.setAdapter(listOfTripsAdapter);
    }

    public class SendGetGetAll extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {

                //Получаем токен
                 preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                 token = preferences.getString("token", "");

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

    public class SendGetRead extends AsyncTask<String, Void, String>{
        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {

                //Получаем токен
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                //Формируем запрос
                URL url = new URL("http://travelapp.fun/api/trip/read" + "?id=" + tripId + "&token=" + token);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");

                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK ) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();

                    //Парсим JSON

                    JSONObject result = new JSONObject(sb.toString());

                    userId = result.getString("userId");
                    nameTrip = result.getString("name");
                    descriptionTrip = result.getString("textField");
                    phototsIdTrip = new ArrayList<>();

                    if (result.getJSONArray("photos")!= null) {
                        JSONArray photos = result.getJSONArray("photos");
                        for (int i = 0; i < photos.length(); i++) {
                            phototsIdTrip.add(photos.getString(i));
                        }
                    }

                    //Добавляем поездки

                    Trip trip = new Trip (tripId, userId, nameTrip, descriptionTrip, phototsIdTrip);
                    trips.add(trip);

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
