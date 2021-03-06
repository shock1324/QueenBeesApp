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

public class AddingQueenFarmActivity extends AppCompatActivity {
    TextView choosenMethodStep1Q, choosenMethodStep2Q, dateDisplay, timeDisplay;
    EditText QueenName, WeedingBeehiveNumber;
    Button startQueenFarmButton;
    String currentDateQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_queen_farm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //adding all used objs
        choosenMethodStep1Q = findViewById(R.id.PickedMethodStep1ID);
        choosenMethodStep2Q = findViewById(R.id.PickedMethodStep2ID);
        QueenName = findViewById(R.id.QueenNumberNameID);
        WeedingBeehiveNumber = findViewById(R.id.WeedingHiveNumberID);
        timeDisplay = findViewById(R.id.timeMainAddingQueenFarmID);
        dateDisplay = findViewById(R.id.dateMainAddingQueenFarmID);
        startQueenFarmButton = findViewById(R.id.startQueenFarmID);

        //setting method
        choosenMethodStep1Q.setText(getIntent().getStringExtra("Q_MethodStep1"));
        choosenMethodStep2Q.setText(getIntent().getStringExtra("Q_MethodStep2"));

        //setting current date and time display
        DisplayTimeAndDate x = new DisplayTimeAndDate(timeDisplay, dateDisplay);
        x.displayDateMethod();
        x.displayTimeMethod();

        /*Sending back values to MainActivity*/
        startQueenFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QueenName.getText().toString().isEmpty() || WeedingBeehiveNumber.getText()
                    .toString().isEmpty()) {
                    Toast.makeText(AddingQueenFarmActivity.this,
                    "Uzupełnij pola wszystkie pola!", Toast.LENGTH_LONG);
                }
                else {
                    Intent intent = new Intent();
                    String name = QueenName.getText().toString();
                    String number = WeedingBeehiveNumber.getText().toString();
                    String method1 = choosenMethodStep1Q.getText().toString();
                    String method2 = choosenMethodStep2Q.getText().toString();
                    String date;
                    String step;

                    if (method1.equals("Wychów matki nieunasienionej") && method2.equals("brak"))
                    {
                        step = "1mn";
                        date = "brak";
                    }
                    else if (method1.equals("Wychów matki unasienionej") &&
                        method2.equals("Poddanie do ulika weselnego \n niewygryzionego matecznika"))
                    {
                        step = "1muNW";
                        date = dateDisplay.getText().toString();
                    }
                    else
                    {
                        step = "1muW";
                        date = "brak";
                    }
                    intent.putExtra("Q_NAME", name);
                    intent.putExtra("Q_NUMBER", number);
                    intent.putExtra("Q_DATE", date);
                    intent.putExtra("Q_STEP", step);
                    setResult(AddingQueenFarmActivity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
