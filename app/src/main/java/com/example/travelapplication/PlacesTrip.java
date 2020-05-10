package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;



public class PlacesTrip extends AppCompatActivity {

    private RecyclerView listOfPlaces;
    private PlacesTripPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_trip);

        listOfPlaces = findViewById(R.id.recycleview_placesTrip_placesTrip);

        presenter = new PlacesTripPresenter(getBaseContext());
        presenter.setView(this);
        presenter.uploadData(getIntent(), listOfPlaces);
    }

}
