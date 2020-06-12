package com.example.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddingPurchase extends AppCompatActivity {

    private Button doneButton;
    private EditText editText_addingPurchase_name;
    private EditText editText_addingPurchase_cost;
    private Spinner spinner_addingPurchase;


    private String name;
    private String cost;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_purchase);


        doneButton = findViewById(R.id.button_addingPurchase_done);
        editText_addingPurchase_name = findViewById(R.id.editText_addingPurchase_name);
        editText_addingPurchase_cost = findViewById(R.id.editText_addingPurchase_cost);
        spinner_addingPurchase = findViewById(R.id.spinner_Purchase);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                name = editText_addingPurchase_name.getText().toString();
                category = "Подарки";
                cost = editText_addingPurchase_cost.getText().toString();

                Purchase purchase = new Purchase(name,cost,category);
                Trip trip = new Trip();

                Intent intent = new Intent(AddingPurchase.this,PurchasesTrip.class);
                intent.putExtra("trip", trip);
                startActivity(intent);
            }
        });}


}
