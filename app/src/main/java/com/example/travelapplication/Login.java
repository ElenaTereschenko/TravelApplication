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
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    private Button button_login_login;
    private EditText editText_login_username;
    private EditText editText_login_password;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_login_username = (EditText) findViewById(R.id.editText_login_username);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);

        button_login_login = (Button) findViewById(R.id.button_login_login);

        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    username = editText_login_username.getText().toString();
                    password = editText_login_password.getText().toString();

                    new SendPostRequest().execute();

                } catch (Exception ex){
                    String e = new String("Exception: " + ex.getMessage());
                }

            }

        });
    }

    public  class SendPostRequest extends AsyncTask<String,Void,String>{
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0){
            try{
                URL url = new URL("http://travelapp.fun/api/auth/login");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Email", username);
                postDataParams.put("Password",password);
                Log.e("params",postDataParams.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(postDataParams.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){
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

                    String userId = result.getString("userId");
                    String token = result.getString("token");

                    //Сохраняем в Shared Preferencies

                    SharedPreferences preferences = getSharedPreferences("TravelPrefs",MODE_PRIVATE);
                    preferences.edit().putString("userId",userId).commit();
                    preferences.edit().putString("token",token).commit();

                    //Переход к поездкам
                    Intent intent = new Intent(getBaseContext(), ListOfTrips.class);
                    startActivity(intent);
                    return "";
                }
                else{
                    return new String("false : " + responseCode);
                }


            } catch (Exception ex){
                return new String("Exception: " + ex.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            if (result != ""){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        }
    }

}
