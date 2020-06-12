package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;




public class PlacesTrip extends AppCompatActivity {

    private RecyclerView listOfPlaces;
    private PlacesTripPresenter presenter;
    private FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_trip);

        listOfPlaces = findViewById(R.id.recycleview_placesTrip_placesTrip);

        fab = findViewById(R.id.fab_placesTrip);

        Realm mRealm = ((TravelApplication) getApplication()).getRealm();

        presenter = new PlacesTripPresenter(getBaseContext(), mRealm, fab);
        presenter.setView(this);




        presenter.uploadData(getIntent(), listOfPlaces);
    }

}
