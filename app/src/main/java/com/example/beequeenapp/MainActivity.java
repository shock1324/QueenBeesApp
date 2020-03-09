/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-01-14.
 *
 ******************************************************************************/

package com.example.beequeenapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
    public static Context getContext() {
        return mContext;
    }
}



