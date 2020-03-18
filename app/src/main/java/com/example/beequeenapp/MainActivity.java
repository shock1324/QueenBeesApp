/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-07.
 *
 ******************************************************************************/

package com.example.beequeenapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
Button BasicFarmButton, QueenFarmListButton, CloseAppButton, InfoAboutApp;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        //Adding XML objects
        BasicFarmButton = findViewById(R.id.BasicFarmID);
        QueenFarmListButton = findViewById(R.id.QueenFarmButtonID);
        InfoAboutApp = findViewById(R.id.InfoAboutAppID);
        CloseAppButton = findViewById(R.id.CloseAppID);

        //Setting buttons listeners
        BasicFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                BasicFarmActivity.class));
            }
        });
        QueenFarmListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                QueenFarmListActivity.class));
            }
        });
        InfoAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                InfoAboutAppActivity.class));
            }
        });
        CloseAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
    //setting current date - used in other activities
    public static void currentDate(TextView x){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateStr = dateFormat.format(calendar.getTime());
        x.setText(currentDateStr);
    }

    public static Context getContext() {
        return mContext;
    }
}




