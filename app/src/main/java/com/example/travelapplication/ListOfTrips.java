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
    private List<String> photosIdTrip;
    private List<String> placesIdTrip;
    private List<String> goodsIdTrip;
    private List<String> goalsIdTrip;
    private int fromDateTrip;
    private int toDateTrip;


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


        try {
            //Получаем поездки по ID
            trips = new ArrayList<>();
            if (idTrips.size() > 0) {
                for (int i = 0; i < idTrips.size(); i++) {
                    new SendGetRead(idTrips.get(i)).execute();
                }
            }
        }catch (Exception e){
        String s = e.getMessage();
    }

    //Строим интерфейс
        listOfTrips = findViewById(R.id.recycleview_listOfTrips_listOfTrips);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfTrips.setLayoutManager(layoutManager);

        listOfTrips.setHasFixedSize(true);
        listOfTripsAdapter = new ListOfTripsAdapter(trips);
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

        private String id;

        public SendGetRead (String id){
            this.id= id;
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {


                //Получаем токен
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                //Формируем запрос
                URL url = new URL("http://travelapp.fun/api/trip/read" + "?id=" + id + "&token=" + token);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");

                connection.setDoInput(true);




                int responseCode = connection.getResponseCode();

                if (id.equals("0e1f9a8f-ca4b-49be-b00a-1076b0cb73fc")){
                    int i = 0 ;
                }

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

                    userId = result.getString("UserId");
                    nameTrip = result.getString("Name");
                    descriptionTrip = result.getString("TextField");
                    photosIdTrip = new ArrayList<>();

                    if (result.getJSONArray("PhotosId")!= null) {
                        JSONArray photos = result.getJSONArray("PhotosId");
                        for (int i = 0; i < photos.length(); i++) {
                            photosIdTrip.add(photos.getString(i));
                        }
                    }

                    placesIdTrip = new ArrayList<>();

                    if (result.getJSONArray("PlacesId")!= null) {
                        JSONArray places = result.getJSONArray("PlacesId");
                        for (int i = 0; i < places.length(); i++) {
                            placesIdTrip.add(places.getString(i));
                        }
                    }

                    goodsIdTrip = new ArrayList<>();

                    if (result.getJSONArray("GoodsId")!= null) {
                        JSONArray goods = result.getJSONArray("GoodsId");
                        for (int i = 0; i < goods.length(); i++) {
                            goodsIdTrip.add(goods.getString(i));
                        }
                    }

                    goalsIdTrip = new ArrayList<>();

                    if (result.getJSONArray("GoalsId")!= null) {
                        JSONArray goals = result.getJSONArray("GoalsId");
                        for (int i = 0; i < goals.length(); i++) {
                            goalsIdTrip.add(goals.getString(i));
                        }
                    }

                    /*
                    fromDateTrip = result.getInt("FromDate");
                    toDateTrip = result.getInt ("ToDate");
                    */
                    //Добавляем поездки

                    Trip trip = new Trip (id, userId, nameTrip, descriptionTrip, photosIdTrip, placesIdTrip, goodsIdTrip, goalsIdTrip,fromDateTrip, toDateTrip);
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
