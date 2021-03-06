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
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GoalsTrip extends AppCompatActivity {
    public List<Goal> goals;

    private String id;
    private String userId;
    private String name;
    private String description;
    private boolean isDone;


    private SharedPreferences preferences;
    private String token;

    private RecyclerView listOfGoals;
    private GoalsTripAdapter listOfGoalsAdapter;
    private Button doneButton;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_trip);

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");
            goals = new ArrayList<>();

            try {
                if (trip.getGoalsId().size() > 0) {

                    String[] params = new String[trip.getGoalsId().size()];
                    params = trip.getGoalsId().toArray(params);
                    new GoalsTrip.SendGetRead().execute(params).get();

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            trip = new Trip();
        }

        doneButton = findViewById(R.id.button_goalsTrip_done);
        fab = findViewById(R.id.fab_goalsTrip);

        listOfGoals = findViewById(R.id.recycleview_goalsTrip_goalsTrip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfGoals.setLayoutManager(layoutManager);

        listOfGoals.setHasFixedSize(true);
        listOfGoalsAdapter = new GoalsTripAdapter(goals,true);
        listOfGoals.setAdapter(listOfGoalsAdapter);

        final Context context = getBaseContext();
        listOfGoals.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), listOfGoals, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        goals.get(position).setDone(!goals.get(position).isDone());
                       listOfGoalsAdapter.updateDate(goals);
                       listOfGoalsAdapter.notifyDataSetChanged();

                        String[] params = new String[1];
                        params [0] = "" + position;
                        try {
                            new GoalsTrip.SendPostUpsert().execute(params).get();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        listOfGoalsAdapter.changeVisibility(false);
                        listOfGoalsAdapter.notifyDataSetChanged();
                        doneButton.setVisibility(View.VISIBLE);

                    }
                })
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GoalsTrip.this,AddingGoal.class);
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
                preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                token = preferences.getString("token", "");

                for (String id : ids){
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/goal/read" + "?id=" + id + "&token=" + token);

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
                        Goal goal = doGoalFromJSON(sb.toString(), id);

                        if(goal!=null){
                            goals.add(goal);
                        }

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}

    public class SendPostUpsert extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                for (String id : ids){
                    Goal goal = goals.get(parseInt(id));
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/goal/upsert" + "?token=" + token);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("Id", goal.getId());
                    postDataParams.put("IsDone",goal.isDone());
                    postDataParams.put("UserId",goal.getUserId());
                    postDataParams.put("Name",goal.getName());
                    postDataParams.put("Description",goal.getDescription());
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

                        break;

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}


    private Goal doGoalFromJSON(String resultString,String id ){
        try {
            JSONObject result = new JSONObject(resultString);

            userId = result.getString("userId");
            name = result.getString("name");
            description = result.getString("description");
            isDone = result.getBoolean("isDone");



            //Добавляем цель

            Goal goal = new Goal (id, userId, name, description, isDone);

            return goal;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
