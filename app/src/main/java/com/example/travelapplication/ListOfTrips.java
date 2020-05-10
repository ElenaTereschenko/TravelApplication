package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

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

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfTrips extends AppCompatActivity {

    public List<String> idTrips;
    public List<Trip> trips;
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

    private ListOfTripsPresenter listOfTripsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_trips);
        listOfTrips = findViewById(R.id.recycleview_listOfTrips_listOfTrips);
        fab = findViewById(R.id.fab_listOfTrips);

        Realm mRealm = ((TravelApplication)getApplication()).getRealm();

        listOfTripsPresenter = new ListOfTripsPresenter(this,mRealm, listOfTrips,fab);
        listOfTripsPresenter.sendRequest( this.getBaseContext());

    }
}
