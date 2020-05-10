package com.example.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlacesTripPresenter {
    private PlacesTrip view;
    private Context context;

    public List<Place> places;
    private String id;
    private String userId;
    private String name;
    private String adress;
    private String description;
    private Long dateLong;
    private Date date;
    private boolean isVisited;
    private List<String> photos;
    private SharedPreferences preferences;
    private String token;

    private RecyclerView listOfPlaces;
    private PlacesTripAdapter listOfPlacesAdapter;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public PlacesTripPresenter(Context context){
        this.context = context;
    }

    public void setView (PlacesTrip view){
        this.view = view;
    }

    public void uploadData(Intent intent, RecyclerView listOfPlaces){
        Bundle arguments = intent.getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");
            places = new ArrayList<>();

            try {
                if (trip.getPlacesID().size() > 0) {

                    String[] params = new String[trip.getPlacesID().size()];
                    params = trip.getPlacesID().toArray(params);
                    new SendGetRead().execute(params).get();

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            trip = new Trip();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        listOfPlaces.setLayoutManager(layoutManager);

        listOfPlaces.setHasFixedSize(true);
        listOfPlacesAdapter = new PlacesTripAdapter(places);
        listOfPlaces.setAdapter(listOfPlacesAdapter);
    }

    public class SendGetRead extends AsyncTask<String, Void, String> {




        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                token = preferences.getString("token", "");

                for (String id : ids){
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/place/read" + "?id=" + id + "&token=" + token);

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
                        Place place = doPlaceFromJSON(sb.toString(), id);

                        if(place!=null){
                            places.add(place);
                        }

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}

    private Place doPlaceFromJSON(String resultString,String id ){
        try {
            JSONObject result = new JSONObject(resultString);

            userId = result.getString("userId");
            name = result.getString("name");
            adress = result.getString("adress");
            description = result.getString("description");

            date = null;

            if ( !result.isNull("date")) {
                dateLong = result.getLong("date");
                date= new Date((dateLong - TICKS_AT_EPOCH)/TICKS_PER_MILLISECOND);
            }

            isVisited = result.getBoolean("isVisited");

            photos = new ArrayList<>();

            if ( !result.isNull("photos")) {
                JSONArray photosJS = result.getJSONArray("photos");
                for (int i = 0; i < photosJS.length(); i++) {
                    photos.add(photosJS.getString(i));
                }
            }


            //Добавляем место

            Place place = new Place (id, userId, name, adress, description, date, isVisited, photos);

            return place;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
