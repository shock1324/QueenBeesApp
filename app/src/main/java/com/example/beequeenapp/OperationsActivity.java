/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-07.
 *
 ******************************************************************************/

package com.example.beequeenapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperationsActivity extends AppCompatActivity {

    CheckBox warmer;
    TextView choosenMethodTV, processToDoTV, dateProcessToDoTV, nextProcessTV, currDateTV,
        farmNameTV, processInfoTV, nextProcessDateTV;
    Button saveChangesButton;
    String nameStr, methodStr, dateStr, stepStr, stepStr2;
    int pos;
    Info ItemInfo;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Adding XML objects
        currDateTV = findViewById(R.id.currDate3ID);
        choosenMethodTV = findViewById(R.id.getKindOfQueenToFarmID);
        farmNameTV = findViewById(R.id.getWeedingHiveNumberID);
        processToDoTV = findViewById(R.id.processToDoIDQ);
        dateProcessToDoTV = findViewById(R.id.dateProcessToDoIDQ);
        nextProcessTV = findViewById(R.id.nextProcessIDQ);
        nextProcessDateTV = findViewById(R.id.nextProcessDateIDQ);
        processInfoTV = findViewById(R.id.processInfoID);
        saveChangesButton = findViewById(R.id.saveChangesID);
        warmer = findViewById(R.id.warmerID);

        //getting intent from BasicFarmActivity list
        getIntentFromBasicFarmActivity();

        //setting current date
        MainActivity.currentDate(currDateTV);

        //setting item step values
        chooseRightStepAndSetDate(stepStr);

        //setting days and step name if warmer checkbox is enabled or disabled
        if (warmer.isEnabled()) {
            warmer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        ItemInfo.setDays(4);
                        stepStr = "4ciep";
                    }
                    else {
                        ItemInfo.setDays(7);
                        stepStr = "4";
                    }
                    dateChanges(ItemInfo.getDays());
                }
            });
        }
        else
        {
            dateChanges(ItemInfo.getDays());
        }

        //setting info for textViews in activity
        choosenMethodTV.setText(methodStr);
        farmNameTV.setText(nameStr);
        processToDoTV.setText(ItemInfo.getProcessInfo());
        nextProcessTV.setText(ItemInfo.getNextProcessInfo());
        processInfoTV.setText(ItemInfo.getProcessDescriptionInfo());

        //saving data in main panel - BasicFarmActivity
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String name = farmNameTV.getText().toString();
                String date = nextProcessDateTV.getText().toString();
                String step = stepStr;
                String method = choosenMethodTV.getText().toString();

                intent.putExtra("O_NAME", name);
                intent.putExtra("O_DATE", date);
                intent.putExtra("O_STEP", step);
                intent.putExtra("O_METHOD", method);
                intent.putExtra("O_POSITION", pos);
                setResult(AddingFarmActivity.RESULT_OK, intent);
                if(ItemInfo.getStep().contains("Koniec") || ItemInfo.getNextProcessInfo()
                    .contains("Koniec")) {
                    alert();
                }
                else {
                    finish();
                }
            }
            private void alert()
            {
                AlertDialog.Builder dialog = new AlertDialog
                    .Builder(OperationsActivity.this);
                dialog.setTitle("Zakończenie hodowli");
                dialog.setMessage("Gratululacje! \n\n Hodowla zakończona sukcesem! \n\n " +
                        "Posiadasz teraz materiał (w postaci zaizolowanych mateczników) do " +
                        "dalszej hodowli matek (hodowla jednostkowa)" +
                        "Aby usunąć tą hodowlę, wróć do listy wszystkich hodowli, " +
                        "przytrzymaj dłużej jej pozycję na liście, a następnie usuń!");
                dialog.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.show();
            }
        });
    }

    //getting intent from BasicFarmActivity list
    private void getIntentFromBasicFarmActivity(){
        ItemInfo = (Info) getIntent().getExtras().get("OBJ");
        nameStr = ItemInfo.getFarmNameInfo();
        methodStr = ItemInfo.getChoosenMethodInfo();
        dateStr = ItemInfo.getDateInfo();
        stepStr = ItemInfo.getStep();
        stepStr2 = ItemInfo.getStep();
        pos = getIntent().getExtras().getInt("POSITION");
    }
    //changing dates
    private void dateChanges(int days)
    {
        if (ItemInfo.getStep().equals("6") || ItemInfo.getStep().equals("6ciep"))
        {
            dateProcessToDoTV.setText(dateStr);
            nextProcessDateTV.setText("brak");
        }
        else if (ItemInfo.getStep().equals("Koniec hodowli"))
        {
            dateProcessToDoTV.setText("brak");
            nextProcessDateTV.setText("brak");
        }
        else {
            dateChangesInMethods(days);
        }
    }
    //changing dates. Used in dateChanges method
    private void dateChangesInMethods(int days)
    {
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat formatDaty = new SimpleDateFormat("dd-MM-yyyy");
        String data3 = dateStr;
        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(data3);
            calendar2.setTime(date1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar2.add(Calendar.DAY_OF_YEAR, days);
        dateProcessToDoTV.setText(data3);
        nextProcessDateTV.setText(formatDaty.format(calendar2.getTime()));
    }
    //setting confirmation button if current date and processToDo date are the same. Implemented, not active yet.
    private void compareDatesToSetButton()
    {
        if(currDateTV.getText().toString().equals(dateStr))
            saveChangesButton.setEnabled(true);
        else
            saveChangesButton.setEnabled(false);
    }
    //changing info of selected farm in user panel (enabling checkbox if required) and next step
    private void chooseRightStepAndSetDate(String sendStepHere)
    {
        if (sendStepHere.equals("1")) {
            ItemInfo.step1();
            stepStr = "2";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("2")) {
            ItemInfo.step2();
            stepStr = "3";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("3")) {
            warmer.setVisibility(View.VISIBLE);
            warmer.setEnabled(true);
            ItemInfo.step3();
            stepStr = "4";
            dateChanges(ItemInfo.getDays());
        }
        //warmer
        else if (sendStepHere.equals("4")) {
            ItemInfo.step4();
            stepStr = "5";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("5")) {
            ItemInfo.step5();
            stepStr = "6";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("6")) {
            ItemInfo.step6();
            stepStr = "Koniec hodowli";
            dateChanges(ItemInfo.getDays());
        }
        //no warmer
        else if (sendStepHere.equals("4ciep")) {

            ItemInfo.step4warm();
            stepStr = "5ciep";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("5ciep")) {

            ItemInfo.step5warm();
            stepStr = "6ciep";
            dateChanges(ItemInfo.getDays());
        } else if (sendStepHere.equals("6ciep")) {

            ItemInfo.step6warm();
            stepStr = "Koniec hodowli";
            dateChanges(ItemInfo.getDays());
        }
    }
}
