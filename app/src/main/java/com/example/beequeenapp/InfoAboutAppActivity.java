/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-07.
 *
 ******************************************************************************/

package com.example.beequeenapp;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class InfoAboutAppActivity extends AppCompatActivity {

    TextView allInfos;
    Context context = MainActivity.getContext();
    final String InfoAboutMainPanel = context.getString(R.string.MainPanelInfo);
    final String InfoAboutGroupFarm = context.getString(R.string.InfoAboutGroupFarm);
    final String InfoAboutUnitFarm = context.getString(R.string.InfoAboutUnitFarm);
    final String InfoAboutColors = context.getString(R.string.InfoAboutColors);
    final String InfoAboutMainPanelTitle = context.getString(R.string.MainPanelInfoTitle);
    final String InfoAboutGroupFarmTitle = context.getString(R.string.InfoAboutGroupFarmTitle);
    final String InfoAboutUnitFarmTitle = context.getString(R.string.InfoAboutUnitFarmTitle);
    final String InfoAboutColorsTitle = context.getString(R.string.InfoAboutColorsTitle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        allInfos = findViewById(R.id.InfosAboutAppID);
        allInfos.setTextSize(16);
        allInfos.setText(InfoAboutMainPanelTitle + "\n" + InfoAboutMainPanel + "\n\n\n" +
            InfoAboutGroupFarmTitle + "\n" + InfoAboutGroupFarm + "\n\n\n" +
            InfoAboutUnitFarmTitle  + "\n" + InfoAboutUnitFarm + "\n\n\n" +
                InfoAboutColorsTitle + "\n" + InfoAboutColors);
    }
}
