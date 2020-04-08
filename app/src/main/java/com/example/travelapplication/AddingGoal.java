package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

import static java.lang.Integer.parseInt;

public class AddingGoal extends AppCompatActivity {

    private Button doneButton;
    private EditText editText_addingGoal_name;
    private String name;
    private String tripId;
    List<String> goalsId;
    private String idGoal;

    private SharedPreferences preferences;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_goal);

        tripId="";

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");


        doneButton = findViewById(R.id.button_addingGoal_done);
        editText_addingGoal_name = findViewById(R.id.editText_addingGoal_name);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                name = editText_addingGoal_name.getText().toString();
                goalsId= new ArrayList<>();
                try {
                    tripId = trip.getId();
                    goalsId = trip.getGoalsId();
                    new AddingGoal.SendPostUpsert().execute().get();
                    goalsId.add(idGoal);
                    trip.setGoalsId(goalsId);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddingGoal.this,GoalsTrip.class);
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
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/goal/upsertwithtripid" + "?token=" + token);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("IsDone",false);
                    postDataParams.put("Name",name);
                    postDataParams.put("TripId",tripId);
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

                        idGoal = result.getString("id");



                    }else {
                        return new String("false : " + responseCode);
                    }




            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}
}
