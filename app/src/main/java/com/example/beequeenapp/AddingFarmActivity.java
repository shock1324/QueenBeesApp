/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-01-14.
 *
 ******************************************************************************/

package com.example.beequeenapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class AddingFarmActivity extends AppCompatActivity {
    TextView choosenMethod, currDateTV;
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
        currDateTV = findViewById(R.id.currDate2ID);
        startButton = findViewById(R.id.startID);

        //setting method and date
        choosenMethod.setText(getIntent().getStringExtra("METHOD"));
        currentDate();

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
                    String date = currDateTV.getText().toString();
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
    //setting current date
    private void currentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());
        currDateTV.setText(currentDate);
    }
}





