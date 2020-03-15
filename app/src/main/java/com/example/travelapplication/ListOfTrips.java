package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
    private Long fromDateTrip;
    private Long toDateTrip;
    private Date toDate;
    private Date fromDate;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;


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

                    String[] params = new String[idTrips.size()];
                    params = idTrips.toArray(params);
                    new SendGetRead().execute(params).get();

            }
        }catch (Exception e){
        String s = e.getMessage();
    }

        int i =trips.size();
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




        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                for (String id : ids){
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/trip/read" + "?id=" + id + "&token=" + token);

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
                        Trip trip = doTripFromJSON(sb.toString(), id);

                        if(trip!=null){
                            trips.add(trip);
                        }

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }

        private Trip doTripFromJSON(String resultString,String id ){
            try {
                JSONObject result = new JSONObject(resultString);

                userId = result.getString("userId");
                nameTrip = result.getString("name");
                descriptionTrip = result.getString("textField");
                photosIdTrip = new ArrayList<>();

                if ( !result.isNull("photoIds")) {
                    JSONArray photos = result.getJSONArray("photoIds");
                    for (int i = 0; i < photos.length(); i++) {
                        photosIdTrip.add(photos.getString(i));
                    }
                }

                placesIdTrip = new ArrayList<>();

                if (!result.isNull("placeIds")) {
                    JSONArray places = result.getJSONArray("placeIds");
                    for (int i = 0; i < places.length(); i++) {
                        placesIdTrip.add(places.getString(i));
                    }
                }

                goodsIdTrip = new ArrayList<>();


                if (!result.isNull("goodsIds") ) {
                    JSONArray goods = result.getJSONArray("goodIds");
                    for (int i = 0; i < goods.length(); i++) {
                        goodsIdTrip.add(goods.getString(i));
                    }
                }

                goalsIdTrip = new ArrayList<>();

                if (!result.isNull("goalIds")) {
                    JSONArray goals = result.getJSONArray("goalIds");
                    for (int i = 0; i < goals.length(); i++) {
                        goalsIdTrip.add(goals.getString(i));
                    }
                }

                fromDate = null;
                toDate = null;

                if ( !result.isNull("fromDate")) {
                    fromDateTrip = result.getLong("fromDate");
                    fromDate = new Date((fromDateTrip - TICKS_AT_EPOCH)/TICKS_PER_MILLISECOND);
                }

                if ( !result.isNull("toDate")) {
                    toDateTrip = result.getLong ("toDate");
                    toDate = new Date((toDateTrip - TICKS_AT_EPOCH)/TICKS_PER_MILLISECOND);
                }


                //Добавляем поездки

                Trip trip = new Trip (id, userId, nameTrip, descriptionTrip, photosIdTrip, placesIdTrip, goodsIdTrip, goalsIdTrip,fromDate, toDate);
                return trip;
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }
}
