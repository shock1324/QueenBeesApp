/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-19
 *
 ******************************************************************************/

package com.example.beequeenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddingFarmActivity extends AppCompatActivity {
    TextView choosenMethod, dateDisplay, timeDisplay;
    EditText beeFamilyName;
    Button startButton;
    String currentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Adding XML objects
        choosenMethod = findViewById(R.id.PickedMethodStep1ID);
        beeFamilyName = findViewById(R.id.WeedingHiveNumberID);
        timeDisplay = findViewById(R.id.timeMainAddingFarmID);
        dateDisplay = findViewById(R.id.dateMainAddingFarmID);
        startButton = findViewById(R.id.startID);

        //setting method
        choosenMethod.setText(getIntent().getStringExtra("METHOD"));

        //setting current date and time display
        DisplayTimeAndDate x = new DisplayTimeAndDate(timeDisplay, dateDisplay);
        x.displayDateMethod();
        x.displayTimeMethod();

        //Sending back values to MainActivity
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(beeFamilyName.getText().toString().isEmpty()) {
                    Toast.makeText(AddingFarmActivity.this, "Wprowadź nazwę!",
                            Toast.LENGTH_LONG);
                }
                else {
                    Intent intent = new Intent();
                    String name = beeFamilyName.getText().toString();
                    String date = dateDisplay.getText().toString();
                    String method = choosenMethod.getText().toString();
                    String step = "1";
                    intent.putExtra("METHOD2", method);
                    intent.putExtra("NAME", name);
                    intent.putExtra("DATE", date);
                    intent.putExtra("STEP", step);
                    setResult(AddingFarmActivity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}





