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
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QueenFarmMethodPickActivity extends AppCompatActivity {

    TextView QueenFarmMethodInfo;
    RadioGroup QueenFarmMethodChoice, QueenFarmWeedingBeehiveChoice;
    Button QueenGoNextButton;
    String textOfQueenFarmMethod;
    String textOfLarvOrQueen;

    public static Context context = MainActivity.getContext();

    //Variables for methodInfos
    String MNU = context.getResources().getString(R.string.QueenMethodNotinseminated);
    String MU = context.getResources().getString(R.string.QueenMethodInseminated);
    String Larv = context.getResources().getString(R.string.QueenMethodLarv);
    String Queen = context.getResources().getString(R.string.QueenMethodQueen);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queen_farm_method_pick);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QueenFarmMethodInfo = findViewById(R.id.QueenMethodInfoID);
        QueenFarmMethodChoice = findViewById(R.id.radioGroupIDQueenStep1);
        QueenFarmWeedingBeehiveChoice = findViewById(R.id.radioGroupIDQueenStep2);
        QueenGoNextButton = findViewById(R.id.QueenNextID);

        final RadioButton method_1 = findViewById(R.id.radioButton1QueenStep1);
        final RadioButton method_2 = findViewById(R.id.radioButton2QueenStep1);

        QueenFarmMethodChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1QueenStep1:
                        QueenFarmWeedingBeehiveChoice.setVisibility(View.INVISIBLE);
                        QueenFarmWeedingBeehiveChoice.clearCheck(); //clearing 2nd radioGroup
                        QueenFarmMethodInfo.setText(MNU);
                        textOfLarvOrQueen = "brak";
                        textOfQueenFarmMethod = method_1.getText().toString();
                        QueenFarmWeedingBeehiveChoice.setVisibility(View.INVISIBLE);
                        QueenGoNextButton.setEnabled(true);
                        break;
                    case R.id.radioButton2QueenStep1:
                        QueenFarmWeedingBeehiveChoice.setVisibility(View.VISIBLE);
                        QueenFarmMethodInfo.setText(MU); //setting value
                        textOfLarvOrQueen = null;
                        QueenGoNextButton.setEnabled(false);
                        textOfQueenFarmMethod = method_2.getText().toString();
                        break;
                }
            }
        });

        final RadioButton method2_1 = findViewById(R.id.radioButton1QueenStep2);
        final RadioButton method2_2 = findViewById(R.id.radioButton2QueenStep2);

        QueenFarmWeedingBeehiveChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1QueenStep2:
                        QueenFarmMethodInfo.setText(MU  +"\n \n" + Larv); //setting value
                        textOfLarvOrQueen = method2_1.getText().toString();
                        QueenGoNextButton.setEnabled(true);
                        break;
                    case R.id.radioButton2QueenStep2:
                        QueenFarmMethodInfo.setText(MU +"\n \n" + Queen); //setting value
                        textOfLarvOrQueen = method2_2.getText().toString();
                        QueenGoNextButton.setEnabled(true);
                        break;
                }
            }
        });

        QueenGoNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueenFarmMethodPickActivity.this,
                    AddingQueenFarmActivity.class);
                intent.putExtra("Q_MethodStep1", textOfQueenFarmMethod);
                intent.putExtra("Q_MethodStep2", textOfLarvOrQueen);
                startActivityForResult(intent, 11);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 11 && resultCode == RESULT_OK)
        {
            setResult(QueenFarmMethodPickActivity.RESULT_OK, data);
            finish();
        }
    }



}
