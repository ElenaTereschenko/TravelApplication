package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PurchasesTrip extends AppCompatActivity {

    private SharedPreferences preferences;
    private String token;
    private Context context;
    List<String> categoryList;
    List<Purchase> purchases;

    private RecyclerView listOfPurchases;
    private PurchasesTripAdapter listOfPurchasesAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases_trip);
        context = this;

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");

            try {
                if (trip.getGoodsId().size() > 0) {
/*
                    String[] params = new String[trip.getGoodsId().size()];
                    params = trip.getGoodsId().toArray(params);
                    new GoodsTrip.SendGetRead().execute(params).get();
*/
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            trip = new Trip();
        }
        purchases = new ArrayList<>();
        //purchases.add(new Purchase("Нотная тетрадь","Стоимость: 12","Категория: Подарки"));
/*
        try {
            new SendGetRead().execute().get();
        }
        catch (Exception e){
            Log.e("Exception", e.getMessage());
        }*/

        fab = findViewById(R.id.fab_purchasesTrip);

        listOfPurchases = findViewById(R.id.recycleview_purchasesTrip_purchasesTrip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfPurchases.setLayoutManager(layoutManager);

        listOfPurchases.setHasFixedSize(true);
        listOfPurchasesAdapter = new PurchasesTripAdapter(purchases,true);
        listOfPurchases.setAdapter(listOfPurchasesAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PurchasesTrip.this,AddingPurchase.class);
                intent.putExtra("trip", trip);
                startActivity(intent);
            }
        });
    }

    public class SendGetRead extends AsyncTask<String, Void, String> {




        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
                token = preferences.getString("token", "");


                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/category/getAll" );

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



                        JSONArray result = new JSONArray(sb.toString());
                        Category category;
/*
                        for(int i = 0; i < result.length(); i++){
                            category = new Category(result.get(i))
                        }
*/
//                        if(!result.isNull())
/*
                        PlaceTrip place = doPlaceFromJSON(sb.toString(), id);

photosIdTrip = new ArrayList<>();

                if ( !result.isNull("photoIds")) {
                    JSONArray photos = result.getJSONArray("photoIds");
                    for (int i = 0; i < photos.length(); i++) {
                        photosIdTrip.add(photos.getString(i));
                    }
                }

                        if(place!=null){
                            places.add(place);
                        }
*/
                    } else {
                        return new String("false : " + responseCode);
                    }




            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}

}
