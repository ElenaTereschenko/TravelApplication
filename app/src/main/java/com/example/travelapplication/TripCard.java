package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TripCard extends AppCompatActivity {
    public TextView nameTrip;
    public TextView periodTrip;
    public TextView descriptionTrip;
    public TextView countPlaces;
    public TextView countGoals;
    public TextView countGood;
    public TextView countPurchase;
    public TextView countPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_card);
        nameTrip = findViewById(R.id.textView_tripCard_nameTrip);
        periodTrip = findViewById(R.id.textView_tripCard_period);
        descriptionTrip = findViewById(R.id.textView_tripCard_description);
        countPlaces = findViewById(R.id.textView_tripCard_countPlaces);
        countGoals = findViewById(R.id.textView_tripCard_countGoals);
        countGood = findViewById(R.id.textView_tripCard_countGood);
        countPurchase = findViewById(R.id.textView_tripCard_countPurchase);
        countPhotos = findViewById(R.id.textView_tripCard_countPhotos);

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");
            nameTrip.setText(trip.getName());
            periodTrip.setText(trip.getPeriod());
            if(trip.getDescription().equals("null")){
                descriptionTrip.setText("Добавьте описание поездки");
            }
            else {
                descriptionTrip.setText(trip.getDescription());
            }
            countPlaces.setText("" + trip.getPlacesID().size());
            countGoals.setText("" + trip.getGoalsId().size());
            countGood.setText("" + trip.getGoodsId().size());
            countPurchase.setText("" + trip.getGoodsId().size());
            countPhotos.setText("" + trip.getPhotosId().size());
        }


    }
}
