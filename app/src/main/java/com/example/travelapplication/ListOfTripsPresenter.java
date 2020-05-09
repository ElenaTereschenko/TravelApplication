package com.example.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfTripsPresenter extends AppCompatActivity {
    private ListOfTrips view;
    private Context context;
    private List<String> idTrips;
    private List<Trip> trips;

    private SharedPreferences preferences;
    private String token;


    private RecyclerView listOfTrips;
    private ListOfTripsAdapter listOfTripsAdapter;
    private FloatingActionButton fab;

    private String tripId;
    private String userId;
    private String nameTrip;
    private String descriptionTrip;
    private List<String> photosIdTrip;
    private List<String> placesIdTrip;
    private List<String> goodsIdTrip;
    private List<String> goalsIdTrip;
    private List<String> purchasesIdTrip;
    private Long fromDateTrip;
    private Long toDateTrip;
    private Date toDate;
    private Date fromDate;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public ListOfTripsPresenter(Context context, RecyclerView listOfTrips, FloatingActionButton fab){
        this.context = context;
        this.listOfTrips = listOfTrips;
        this.fab = fab;
    }

    public void setView(ListOfTrips view){
        this.view = view;
    }

    public void sendRequest(Context context) {

         preferences = PreferenceManager.getDefaultSharedPreferences(context);
         token = preferences.getString("token","");




        NetworkService.getInstance().getTravelAPI().getAllTrips(token).enqueue((new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.e("response code", "" + response.code());
                System.out.println(response.isSuccessful());
                idTrips = new ArrayList<>(response.body().size());
                trips = new ArrayList<>();
                Log.e("size",""+response.body().size());
                for( int i = 0; i < response.body().size();i++){
                    idTrips.add(i,response.body().get(i))  ;
                }
                trips = new ArrayList<>();
                if (idTrips.size() > 0) {


                    String[] params = new String[idTrips.size()];
                    params = idTrips.toArray(params);
                    try {
                        new SendGetRead().execute(params).get();
                    }
                    catch (Exception e) {
                        Log.e("exception",e.getMessage());
                    }


                }

                int i =trips.size();
                //Строим интерфейс

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                listOfTrips.setLayoutManager(layoutManager);


                listOfTrips.setHasFixedSize(true);
                listOfTripsAdapter = new ListOfTripsAdapter(trips);
                listOfTrips.setAdapter(listOfTripsAdapter);
                final Context context1 = context   ;

                listOfTrips.addOnItemTouchListener(
                        new RecyclerItemClickListener(context, listOfTrips, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(context1, TripCard.class);
                                intent.putExtra("trip",trips.get(position));
                                context.startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        })
                );

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(context,AddingTrip.class);
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {


            }
        }));
    }

    public class SendGetRead extends AsyncTask<String, Void, String> {




        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {



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

                purchasesIdTrip = new ArrayList<>();

                if (!result.isNull("purchaseIds")){
                    JSONArray purchases = result.getJSONArray("purchaseIds");
                    for (int i = 0; i < purchases.length(); i++){
                        purchasesIdTrip.add(purchases.getString(i));
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

                Trip trip = new Trip (id, userId, nameTrip, descriptionTrip, photosIdTrip, placesIdTrip, goodsIdTrip, goalsIdTrip,purchasesIdTrip, fromDate, toDate);
                return trip;
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }
}
