package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddingGood extends AppCompatActivity {

    private Button doneButton;
    private EditText editText_addingGood_name;
    private EditText editText_addingGood_count;
    private String name;
    private int count;
    private String tripId;
    List<String> goodsId;
    private String idGood;

    private SharedPreferences preferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_good);

        tripId="";

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");


            doneButton = findViewById(R.id.button_addingGood_done);
            editText_addingGood_name = findViewById(R.id.editText_addingGood_name);


            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    name = editText_addingGood_name.getText().toString();

                    try {
                        count = Integer.parseInt(editText_addingGood_count.getText().toString());
                    }
                    catch (Exception e){
                        count = 1;
                    }

                    goodsId= new ArrayList<>();
                    try {
                        tripId = trip.getId();
                        goodsId = trip.getGoodsId();
                        new AddingGood.SendPostUpsert().execute().get();
                        goodsId.add(idGood);
                        trip.setGoodsId(goodsId);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(AddingGood.this,GoodsTrip.class);
                    intent.putExtra("trip", trip);
                    startActivity(intent);
                }
            });}
    }


    public class SendPostUpsert extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                token = preferences.getString("token", "");
                String userId = preferences.getString("userId","");

                //Формируем запрос
                URL url = new URL("http://travelapp.fun/api/good/upsertwithtripid" + "?token=" + token);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("UserId", userId);
                postDataParams.put("IsTook",false);
                postDataParams.put("Name",name);
                postDataParams.put("TripId",tripId);
                postDataParams.put("Count",count);
                postDataParams.put("LastUpdate", 0);
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

                    JSONObject result = new JSONObject(sb.toString());

                    idGood = result.getString("id");



                }else {
                    return new String("false : " + responseCode);
                }




            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}
}
