package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_mainactivity_login;
    Button button_mainactivity_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_mainactivity_login = (Button) findViewById(R.id.button_mainactivity_login);
        button_mainactivity_register = findViewById(R.id.button_mainactivity_register);

        button_mainactivity_login.setOnClickListener(this);

        button_mainactivity_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_mainactivity_register:
                        Intent intent = new Intent(getBaseContext(), Register.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_mainactivity_login:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
