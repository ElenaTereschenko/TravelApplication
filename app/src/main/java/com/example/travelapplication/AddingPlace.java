package com.example.travelapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class AddingPlace extends AppCompatActivity {

    private PlacesClient placesClient;
    private EditText name;
    private EditText adress;
    private TextView date;
    private DatePickerDialog.OnDateSetListener picker;
    private Date dateDate;
    private Button doneButton;

    private SharedPreferences preferences;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_place);

        adress = findViewById(R.id.editText_addingPlace_adress);
        date = findViewById(R.id.textView_addingPlace_date);
        name = findViewById(R.id.editText_addingPlace_name);
        doneButton = findViewById(R.id.button_addingPlace_done);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                DatePickerDialog dialog = new DatePickerDialog(AddingPlace.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,picker,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        picker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int realmonth = month + 1;
                Log.d("Calendar",year + "/" + realmonth + "/" + dayOfMonth);
                String dateString= dayOfMonth + "/" + realmonth  + "/" + year;
                date.setText(dateString);
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                dateDate = cal.getTime();
            }
        };

        String apiKey = "AIzaSyBIbr2sXiQIdSiSHVVf1S0nWGlAWzOiE74";

        Locale aLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();


        if (!Places.isInitialized()){
            Places.initialize(this, apiKey, aLocale);
        }

        placesClient = Places.createClient(this);


        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_addingPlace_placesGoogle);

        autocompleteSupportFragment.setHint("");



        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                adress.setText(place.getAddress());
                name.setText(place.getName());
            }



            @Override
            public void onError(@NonNull Status status) {
                autocompleteSupportFragment.setText(status.getStatusMessage());
            }
        });


        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userId = preferences.getString("userId", "");

        doneButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Realm.init(v.getContext());
                Realm mRealm = ((TravelApplication) getApplication()).getRealm();

                mRealm.beginTransaction();
                PlaceRealm placeRealm = mRealm.createObject(PlaceRealm.class);
                placeRealm.setUserId(userId);
                placeRealm.setName(name.getText().toString());
                placeRealm.setAdress(adress.getText().toString());
                placeRealm.setDateTicks(dateDate.toString());
                placeRealm.setVisited(false);
                mRealm.commitTransaction();

                Intent intent = new Intent(AddingPlace.this,PlacesTrip.class);
                startActivity(intent);
            }
        });
    }
}
