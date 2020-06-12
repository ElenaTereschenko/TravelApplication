package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private Button button_register_register;
    private EditText editText_register_username;
    private EditText editText_register_password;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);
        registerPresenter.setView(this);


        editText_register_username = (EditText) findViewById(R.id.editText_register_username);
        editText_register_password = (EditText) findViewById(R.id.editText_register_password);

        button_register_register = (Button) findViewById(R.id.button_register_register);

        button_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int res = registerPresenter.clicked(editText_register_username.getText().toString(), editText_register_password.getText().toString());
                if (res == 1){
                    //Переход к поездкам
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
