/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-19
 *
 ******************************************************************************/

package com.example.beequeenapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QueenOperationsActivity extends AppCompatActivity {

    TextView choosenKindOfQueenTV, choosenHowToAddQueenMethodTV, weedingHiveNumber, processToDoTVQ,
        dateProcessToDoTVQ, nextProcessTVQ, dateDisplay, timeDisplay, QueenFarmNameTV, processInfoTVQ,
        nextProcessDateTVQ;
    Button saveChangesButtonQ;
    String nameStrQ, numberStrQ, dateStrQ, stepStrQ,  kindOfQueenToFarmStr, howToAddQueenMethodStr,
        processInfoQStr, nextProcessQStr, processInfoDescriptionStr, dateProcessToDoQStr,
        nextProcessDateQStr;
    int posQ;
    InfoQueen InfoQueen;
    CheckBox checkBoxText;
    RadioGroup inseminationTypeRadioGroup;
    RadioButton insNaturallyRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queen_operations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adding XML objects
        choosenKindOfQueenTV = findViewById(R.id.getKindOfQueenToFarmID);
        choosenHowToAddQueenMethodTV = findViewById(R.id.getHowToAddQueenMethodID);
        QueenFarmNameTV = findViewById(R.id.getQueenFarmNameID);
        weedingHiveNumber = findViewById(R.id.getWeedingHiveNumberID);
        processToDoTVQ = findViewById(R.id.processToDoIDQ);
        dateProcessToDoTVQ = findViewById(R.id.dateProcessToDoIDQ);
        nextProcessTVQ = findViewById(R.id.nextProcessIDQ);
        dateDisplay = findViewById(R.id.dateQueenOperationsID);
        timeDisplay = findViewById(R.id.timeQueenOperationsID);
        processInfoTVQ = findViewById(R.id.processInfoIDQO);
        nextProcessDateTVQ = findViewById(R.id.nextProcessDateIDQ);
        saveChangesButtonQ = findViewById(R.id.saveChangesIDQO);
        checkBoxText = findViewById(R.id.checkboxQOID);
        inseminationTypeRadioGroup = findViewById(R.id.radioGroupOperationsID);
        insNaturallyRadioButton = findViewById(R.id.radioButtonInsNaturalyID);

        //Getting intent from listView and casting it for an InfoQueen object
        InfoQueen = (InfoQueen)getIntent().getExtras().get("OBJQ");
        posQ = getIntent().getExtras().getInt("POSQ");

        //setting current date and time display
        DisplayTimeAndDate x = new DisplayTimeAndDate(timeDisplay, dateDisplay);
        x.displayDateMethod();
        x.displayTimeMethod();

        //setting methods info, right step info and description
        InfoQueen.MethodChanger(InfoQueen.getStepQ());
        chooseRightStepQ(InfoQueen.getStepQ());
        setButtonVisibilityIfCheckBoxIsVisible();

        //getting basic info from item from listView and adding it to variables
        nameStrQ = InfoQueen.getQueenFarmNameInfoQ();
        numberStrQ = InfoQueen.getNumberQ();
        dateStrQ = InfoQueen.getDateInfoQ();
        stepStrQ = InfoQueen.getStepQ();
        kindOfQueenToFarmStr = InfoQueen.getKindOfQueenToFarm();
        howToAddQueenMethodStr = InfoQueen.getHowToAddQueenToFarm();
        processInfoQStr = InfoQueen.getProcessInfo();
        nextProcessQStr = InfoQueen.getNextProcessInfo();
        processInfoDescriptionStr = InfoQueen.getProcessDescriptionInfo();

        //setting info for TVs and other objects
        QueenFarmNameTV.setText(nameStrQ);
        weedingHiveNumber.setText(numberStrQ);
        dateProcessToDoTVQ.setText(dateStrQ);
        choosenKindOfQueenTV.setText(kindOfQueenToFarmStr);
        choosenHowToAddQueenMethodTV.setText(howToAddQueenMethodStr);
        processToDoTVQ.setText(processInfoQStr);
        nextProcessDateTVQ.setText(nextProcessDateQStr);
        processInfoTVQ.setText(processInfoDescriptionStr);
        nextProcessTVQ.setText(nextProcessQStr);

        //setting savingChangesButton enabled if checkBox isChecked
        checkBoxText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxText.isChecked()==true  && inseminationTypeRadioGroup
                    .getVisibility() == View.VISIBLE && insNaturallyRadioButton.isChecked())
                {
                    saveChangesButtonQ.setEnabled(true);
                }
                else if(checkBoxText.isChecked()==true && inseminationTypeRadioGroup
                    .getVisibility()==View.GONE)
                {
                    saveChangesButtonQ.setEnabled(true);
                }
                else
                {
                    saveChangesButtonQ.setEnabled(false);
                }
            }
        });

        //setting saveChangesButton onCheckChanged
        inseminationTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkBoxText.getVisibility()==View.VISIBLE && checkBoxText.isChecked())
                    saveChangesButtonQ.setEnabled(true);
                else{
                    saveChangesButtonQ.setEnabled(false);
                }
            }
        });
        //saving data in BasicFarmActivity ListView
        saveChangesButtonQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String name = nameStrQ;
                String date = nextProcessDateQStr;
                String step = stepStrQ;
                String number = numberStrQ;

                intent.putExtra("QO_NAME", name);
                intent.putExtra("QO_DATE", date);
                intent.putExtra("QO_STEP", step);
                intent.putExtra("QO_NUMBER", number);
                intent.putExtra("QO_POSITION", posQ);
                setResult(AddingFarmActivity.RESULT_OK, intent);
                if(InfoQueen.getStepQ().contains("Koniec") || InfoQueen.getNextProcessInfo()
                    .contains("Koniec")) {
                    alertQ();
                }
                else {
                    finish();
                }
            }

        });
    }

    //setting saveChangesButton visibility
    private void setButtonVisibilityIfCheckBoxIsVisible()
    {
        if(checkBoxText.getVisibility()==View.VISIBLE || inseminationTypeRadioGroup
            .getVisibility()!=View.GONE)
        {
            saveChangesButtonQ.setEnabled(false);
        }
        else
        {
            saveChangesButtonQ.setEnabled(true);
        }
    }

    //changing dates for not inseminated queens type
    private void dateChangesNotIns()
    {
        nextProcessDateQStr ="brak";
    }

    //changing dates for queens put to hive as larva
    private void dateChangesInsLarvAdded(int days)
    {
        if (InfoQueen.getStepQ().equals("1muNW") ||
            InfoQueen.getStepQ().equals("4muNW") ||
            InfoQueen.getStepQ().equals("5muNW"))
        {
            dateProcessToDoTVQ.setText(InfoQueen.getDateInfoQ());
            nextProcessDateQStr = "brak";
        }
        else if (InfoQueen.getStepQ().equals("Koniec hodowli (muNW)"))
        {
            dateProcessToDoTVQ.setText("brak");
            nextProcessDateQStr = "brak";
        }
        else {
            dateChangesInMethods(days);
        }
    }

    //changing dates for queens put to hive after born
    private void dateChangesInsAfterBorn(int days)
    {
        if (InfoQueen.getStepQ().equals("1muW") ||
            InfoQueen.getStepQ().equals("2muW") ||
            InfoQueen.getStepQ().equals("4muW") ||
            InfoQueen.getStepQ().equals("5muW"))
        {
            dateProcessToDoTVQ.setText(InfoQueen.getDateInfoQ());
            nextProcessDateQStr = "brak";
        }
        else if (InfoQueen.getStepQ().equals("Koniec hodowli (muNW)"))
        {
            dateProcessToDoTVQ.setText("brak");
            nextProcessDateQStr = "brak";
        }
        else {
            dateChangesInMethods(days);
        }
    }

    //changing dates. Used in dateChanges method
    private void dateChangesInMethods(int days)
    {
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String data3 = InfoQueen.getDateInfoQ();
        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(data3);
            calendar2.setTime(date1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar2.add(Calendar.DAY_OF_YEAR, days);
        dateProcessToDoTVQ.setText(data3);
        nextProcessDateQStr = dateFormat.format(calendar2.getTime());
    }

    //setting confirmation button if current date and processToDo date are the same.
    // Implemented, not active yet.
    private void compareDatesToSetButton()
    {
        if(dateDisplay.getText().toString().equals(dateStrQ))
            saveChangesButtonQ.setEnabled(true);
        else
            saveChangesButtonQ.setEnabled(false);
    }

    //alertbox after finished farm
    private void alertQ()
    {
        AlertDialog.Builder dialog = new AlertDialog
                .Builder(QueenOperationsActivity.this);
        dialog.setTitle("Zakończenie hodowli");
        dialog.setMessage("Gratululacje! \n\n Hodowla zakończona sukcesem, matka " +
                "gotowa do użytku!\n\n " +
                "Jeśli chcesz usunąć tą hodowlę, wróć do listy wszystkich hodowli, " +
                "przytrzymaj dłużej jej pozycję na liście, a następnie usuń!");
        dialog.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }

    //changing info of selected farm in user panel (enabling checkbox if required) and next step
    private void chooseRightStepQ(String sendStepHere)
    {
        //for not inseminated queens
        if (sendStepHere.equals("1mn")) {
            InfoQueen.step1mn();
            InfoQueen.setStepQ("2mn");
            checkBoxText.setText("Matka się wygryzła");
            dateChangesNotIns();
        } else if (sendStepHere.equals("2mn")) {
            InfoQueen.step2mn();
            InfoQueen.setStepQ("Koniec hodowli (mn)");
            checkBoxText.setText("Matka oznakowana");
            dateChangesNotIns();
        } else if (sendStepHere.equals("Koniec hodowli (mn)")) {
            checkBoxText.setVisibility(View.INVISIBLE);
            dateChangesNotIns();
            InfoQueen.koniecmn();
        }

        //for inseminated queens added as larv
        if(sendStepHere.equals("1muNW")){
            InfoQueen.step1muNW();
            dateChangesInsLarvAdded(InfoQueen.getDays());
            InfoQueen.setStepQ("2muNW");
            checkBoxText.setText("Ulik weselny gotowy!");
        }
        else if(sendStepHere.equals("2muNW"))
        {
            checkBoxText.setVisibility(View.INVISIBLE);
            InfoQueen.step2muNW();
            dateChangesInsLarvAdded(InfoQueen.getDays());
            InfoQueen.setStepQ("3muNW");
            checkBoxText.setText("Matecznik poddany!");
        }
        else if(sendStepHere.equals("3muNW"))
        {
            checkBoxText.setVisibility(View.INVISIBLE);
            InfoQueen.step3muNW();
            dateChangesInsLarvAdded(InfoQueen.getDays());
            InfoQueen.setStepQ("4muNW");
        }
        else if(sendStepHere.equals("4muNW"))
        {
            InfoQueen.step4muNW();
            dateChangesInsLarvAdded(InfoQueen.getDays());
            InfoQueen.setStepQ("5muNW");
            checkBoxText.setText("Matka zaczęła czerwić!");
        }
        else if(sendStepHere.equals("5muNW"))
        {
            InfoQueen.step5muNW();
            dateChangesInsLarvAdded(InfoQueen.getDays());
            InfoQueen.setStepQ("Koniec hodowli (muNW)");
            checkBoxText.setText("Matka wyłapana!");
        }
        else if (sendStepHere.equals("Koniec hodowli (muNW)")) {
            checkBoxText.setVisibility(View.INVISIBLE);
            dateChangesNotIns();
            InfoQueen.koniecmuNW();
        }

        //for inseminated queens added after born
        if(sendStepHere.equals("1muW")){
            InfoQueen.step1muW();
            inseminationTypeRadioGroup.setVisibility(View.VISIBLE);
            dateChangesInsAfterBorn(InfoQueen.getDays());
            InfoQueen.setStepQ("2muW");
            checkBoxText.setText("Matka wygryzła się!");
        }
        else if(sendStepHere.equals("2muW"))
        {
            InfoQueen.step2muW();
            dateChangesInsAfterBorn(InfoQueen.getDays());
            InfoQueen.setStepQ("3muW");
            checkBoxText.setText("Ulik weselny gotowy!");
        }
        else if(sendStepHere.equals("3muW"))
        {
            checkBoxText.setVisibility(View.INVISIBLE);
            InfoQueen.step3muW();
            dateChangesInsAfterBorn(InfoQueen.getDays());
            InfoQueen.setStepQ("4muW");
        }
        else if(sendStepHere.equals("4muW"))
        {
            InfoQueen.step4muW();
            dateChangesInsAfterBorn(InfoQueen.getDays());
            InfoQueen.setStepQ("5muW");
            checkBoxText.setText("Matka zaczęła czerwić!");
        }
        else if(sendStepHere.equals("5muW"))
        {
            InfoQueen.step5muW();
            dateChangesInsAfterBorn(InfoQueen.getDays());
            InfoQueen.setStepQ("Koniec hodowli (muNW)");
            checkBoxText.setText("Matka wyłapana!");
        }
        else if (sendStepHere.equals("Koniec hodowli (muNW)")) {
            checkBoxText.setVisibility(View.INVISIBLE);
            dateChangesNotIns();
            InfoQueen.koniecmuW();
        }
    }
}
