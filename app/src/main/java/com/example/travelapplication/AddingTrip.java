package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddingTrip extends AppCompatActivity {

    private EditText name;
    private TextView toDate;
    private TextView fromDate;
    private DatePickerDialog.OnDateSetListener pickerTo;
    private DatePickerDialog.OnDateSetListener pickerFrom;
    private Button doneButton;

    private Date toDateDate;
    private Date fromDateDate;
    private String nameTrip;
    private Trip trip;

    private SharedPreferences preferences;
    private String token;

    private static final long  TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    private String tripId;
    private String userId;
    private String descriptionTrip;
    private List<String> photosIdTrip;
    private List<String> placesIdTrip;
    private List<String> goodsIdTrip;
    private List<String> goalsIdTrip;
    private List<String> purchasesIdTrip;
    private Long fromDateTrip;
    private Long toDateTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_trip);

        name = findViewById(R.id.editText_addingTrip_name);
        toDate = findViewById(R.id.textView_addingTrip_toDate);
        fromDate = findViewById(R.id.textView_addingTrip_fromDate);
        doneButton = findViewById(R.id.button_addingTrip_done);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                DatePickerDialog dialog = new DatePickerDialog(AddingTrip.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,pickerFrom,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        pickerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int realmonth = month + 1;
                Log.d("Calendar",year + "/" + realmonth + "/" + dayOfMonth);
                String date= dayOfMonth + "/" + realmonth  + "/" + year;
                fromDate.setText(date);
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                fromDateDate = cal.getTime();
            }
        };

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                DatePickerDialog dialog = new DatePickerDialog(AddingTrip.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,pickerTo,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        pickerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int realmonth = month + 1;
                Log.d("Calendar",year + "/" + realmonth + "/" + dayOfMonth);
                String date= dayOfMonth + "/" + realmonth  + "/" + year;
                toDate.setText(date);
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,dayOfMonth);
                toDateDate = cal.getTime();

            }
        };

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                try {
                    nameTrip = name.getText().toString();
                    new AddingTrip.SendPostUpsert().execute().get();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddingTrip.this,ListOfTrips.class);
                intent.putExtra("trip",trip);
                startActivity(intent);
            }
        });
    }

    public class SendPostUpsert extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                token = preferences.getString("token", "");

                //Формируем запрос
                URL url = new URL("http://travelapp.fun/api/trip/upsert" + "?token=" + token);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Name",nameTrip);
                if (fromDateDate!= null){
                    Long ticksFrom = fromDateDate.getTime()*TICKS_PER_MILLISECOND+ TICKS_AT_EPOCH ;

                    postDataParams.put("FromDate",ticksFrom);
                }
                if (toDateDate!= null){
                    Long ticksTo = toDateDate.getTime() *TICKS_PER_MILLISECOND + TICKS_AT_EPOCH ;
                    postDataParams.put("ToDate",ticksTo);
                }

                Log.e("token",token);
                Log.e("params",postDataParams.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(postDataParams.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();

                Log.e("responce code","" + responseCode);



                if (responseCode == HttpURLConnection.HTTP_OK ) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null){
                        sb.append(line);
                        break;
                    }

                    in.close();

                    //Парсим JSON

                    trip = doTripFromJSON(sb.toString());




                }else {
                    return new String("false : " + responseCode);
                }




            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}

    private Trip doTripFromJSON(String resultString ){
        try {
            JSONObject result = new JSONObject(resultString);

            userId = result.getString("userId");
            tripId = result.getString("id");
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
                fromDateDate = new Date((fromDateTrip - TICKS_AT_EPOCH)/TICKS_PER_MILLISECOND);
            }

            if ( !result.isNull("toDate")) {
                toDateTrip = result.getLong ("toDate");
                toDateDate = new Date((toDateTrip - TICKS_AT_EPOCH)/TICKS_PER_MILLISECOND);
            }


            //Добавляем поездки

            Trip trip = new Trip (tripId, userId, nameTrip, descriptionTrip, photosIdTrip, placesIdTrip, goodsIdTrip, goalsIdTrip,purchasesIdTrip, fromDateDate, toDateDate);
            return trip;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
