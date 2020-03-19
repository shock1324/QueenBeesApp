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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QueenFarmListActivity extends AppCompatActivity {

    ListView QueenFarmList;
    TextView dateDisplay, timeDisplay;
    Button AddNewQueenFarmButton , DeleteAllFinishedFarms;
    ArrayList<InfoQueen> arrayListInfoQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queen_farm_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adding XML objects
        dateDisplay = findViewById(R.id.dateQueenFarmListID);
        timeDisplay = findViewById(R.id.timeQueenFarmListID);
        QueenFarmList = findViewById(R.id.QueenFarmListID);
        AddNewQueenFarmButton = findViewById(R.id.AddNewQueenFarmID);
        DeleteAllFinishedFarms = findViewById(R.id.DeleteAllBFinishedFarmsQID);

        //setting current date and time display
        DisplayTimeAndDate x = new DisplayTimeAndDate(timeDisplay, dateDisplay);
        x.displayDateMethod();
        x.displayTimeMethod();

        //creating new or loading previous arrayList with farms to QueenFarmList
        createNewArrayListOrLoadPreviousOneQ();

        //setting buttons listeners
        AddNewQueenFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QueenFarmListActivity.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Rozpoczynanie nowej hodowli")
                        .setMessage("Zakładając nową hodowlę jednostkową dla matki upewnij się, " +
                            "że posiadasz zaizolowane mateczniki z hodowli zbiorowej! \n\n" +
                            "Jeśli ich nie posiadasz, wróć do panelu z listą hodowli " +
                            "zbiorowych i rozpocznij hodowlę zbiorową w celu pozyskania" +
                            " mateczników do dalszego wychowu.")
                        .setPositiveButton("Kontynuuj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(
                                QueenFarmListActivity.this,
                                QueenFarmMethodPickActivity.class), 1);
                            }
                        })
                        .setNegativeButton("Wróć", null)
                        .show();
            }
        });

        QueenFarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                InfoQueen passingObj = (InfoQueen)parent.getItemAtPosition(position);
                if (passingObj.getStepQ().contains("Koniec"))
                {
                    new AlertDialog.Builder(QueenFarmListActivity.this)
                            .setTitle("Hodowla zakończona!")
                            .setMessage("Ta hodowla dobiegła już końca. \n\n " +
                                "Jeśli chcesz ją usunąć, przytrzymaj jej pozycję w " +
                                "liście wszystkich hodowli, a następnie usuń.")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    closeContextMenu();
                                }
                            })
                            .show();
                }
                else
                {
                    Intent intent = new Intent(QueenFarmListActivity.this,
                        QueenOperationsActivity.class);
                    intent.putExtra("OBJQ", passingObj);
                    intent.putExtra("POSQ", pos);
                    startActivityForResult(intent, 11);
                }
            }
        });

        QueenFarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                new AlertDialog.Builder(QueenFarmListActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Usuwanie hodowli")
                        .setMessage("Czy na pewno chcesz usunąć tą hodowlę? \n\n" +
                                "Wszystkie zmiany w danej hodowli zostaną utracone!")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayListInfoQ.remove(pos);
                                adapterViewQ().notifyDataSetChanged();
                                QueenFarmList.setAdapter(adapterViewQ());
                                saveData();
                            }
                        })
                        .setNegativeButton("Nie", null)
                        .show();
                return true;
            }
        });

        DeleteAllFinishedFarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(QueenFarmListActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Usuwanie zakończonych hodowli")
                        .setMessage("Czy na pewno chcesz usunąć wszystkie " +
                            "zakończone hodowle z listy?!")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < QueenFarmList.getAdapter().getCount(); i++){
                                    InfoQueen a = arrayListInfoQ.get(i);

                                    if(a.getStepQ().contains("Koniec"))
                                    {
                                        arrayListInfoQ.remove(a);
                                        adapterViewQ().notifyDataSetChanged();
                                        QueenFarmList.setAdapter(adapterViewQ());
                                        saveData();
                                        i--;
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Nie", null)
                        .show();
            }
        });
    }

    //getting data back from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            String name = data.getExtras().getString("Q_NAME");
            String number = data.getExtras().getString("Q_NUMBER");
            String date = data.getExtras().getString("Q_DATE");
            String step = data.getExtras().getString("Q_STEP");

            InfoQueen infoQ = new InfoQueen(name, date, number, step);
            arrayListInfoQ.add(infoQ);
            QueenFarmList.setAdapter(adapterViewQ());
            saveData();
        }
        if(requestCode == 11 && resultCode == RESULT_OK)
        {
            String name = data.getExtras().getString("QO_NAME");
            String date = data.getExtras().getString("QO_DATE");
            String number = data.getExtras().getString("QO_NUMBER");
            String step = data.getExtras().getString("QO_STEP");
            int pos = data.getExtras().getInt("QO_POSITION");

            InfoQueen info = new InfoQueen(name, date, number, step);
            arrayListInfoQ.set(pos,info);
            QueenFarmList.setAdapter(adapterViewQ());
            saveData();

            arrayListInfoQ = loadData();
            QueenFarmList.setAdapter(adapterViewQ());
        }
    }

    //saving arrayList data using json
    private void saveData(){
        SharedPreferences pref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayListInfoQ);
        editor.putString("ListToSaveQ", json);
        editor.apply();
    }

    //loading data to arrayList
    private ArrayList<InfoQueen> loadData(){
        SharedPreferences pref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("ListToSaveQ", null);
        Type type =new TypeToken<ArrayList<InfoQueen>>(){}.getType();
        ArrayList<InfoQueen> obj =gson.fromJson(json,type);
        return obj;
    }
    //creating new arrayList or loading previous one
    private void createNewArrayListOrLoadPreviousOneQ()
    {
        arrayListInfoQ = new ArrayList<InfoQueen>();
        arrayListInfoQ = loadData();
        if (arrayListInfoQ == null)
        {
            arrayListInfoQ = new ArrayList<InfoQueen>();
        }
        QueenFarmList.setAdapter(adapterViewQ());
    }

    //changing colors of listView items
    private InfoQueenListAdapter adapterViewQ()
    {
        InfoQueenListAdapter adapter = new InfoQueenListAdapter(QueenFarmListActivity.this,
                R.layout.listview2_layout, arrayListInfoQ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                InfoQueen infQ = arrayListInfoQ.get(position);
                String dateTday = dateDisplay.getText().toString();
                String farmProcessDate = infQ.getDateInfoQ();

                View view = super.getView(position, convertView, parent);

                if((dateTday.equals(farmProcessDate) || farmProcessDate.equals("brak")) &&
                    !infQ.getStepQ().contains("Koniec"))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.colorGreenForList));
                }
                else if(infQ.getStepQ().contains("Koniec"))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.colorGrayForList));
                }
                else {
                    try {
                        Date dateTdayParsed = new SimpleDateFormat("dd-MM-yyyy")
                            .parse(dateTday);
                        Date farmProcessDateParsed = new SimpleDateFormat("dd-MM-yyyy")
                            .parse(farmProcessDate);

                        if (farmProcessDateParsed.before(dateTdayParsed)) {
                            view.setBackgroundColor(getResources()
                                .getColor(R.color.colorRedForList));
                        } else if (farmProcessDateParsed.after(dateTdayParsed))
                        {
                            view.setBackgroundColor(getResources()
                                .getColor(R.color.colorYellowForList));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return view;
            }
        };
        return adapter;
    }
}
