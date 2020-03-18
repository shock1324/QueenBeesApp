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

public class BasicFarmActivity extends AppCompatActivity {
    ListView farmList;
    TextView currentDateTV;
    Button addFarmButton, DeleteAllFinishedFarms;
    ArrayList<Info> arrayListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_farm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //Adding XML objects
        currentDateTV = findViewById(R.id.dateMainBasicFarmActID);
        addFarmButton = findViewById(R.id.addFarmID);
        farmList = findViewById(R.id.farmListID);
        DeleteAllFinishedFarms = findViewById(R.id.DeleteAllFinishedFarmsID);

        //setting current date
        MainActivity.currentDate(currentDateTV);

        //creating new or loading previous arrayList with farms to farmList
        createNewArrayListOrLoadPreviousOne();

        //setting buttons listeners
        addFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BasicFarmActivity.this)
                    .setIcon(android.R.drawable.ic_input_add)
                    .setTitle("Rozpoczynanie nowej hodowli zbiorowej")
                    .setMessage("Zakładając nową hodowlę zbiorową decydujesz się na wychów matek" +
                        "w celu pozyskania zaizolowanych mateczników do daleszych hodowli " +
                        "(jednostkowych) w ulikach weselnych \n\n" +
                        "Czy na pewno chcesz kontynuować?")
                    .setPositiveButton("Kontynuuj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(BasicFarmActivity.this,
                        MethodPickActivity.class), 6);
                        }
                    })
                    .setNegativeButton("Wróć", null)
                    .show();
            }
        });

        farmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info passingObj = (Info)parent.getItemAtPosition(position);
                final int pos = position;
                if(passingObj.getStep().contains("Koniec"))
                {
                    new AlertDialog.Builder(BasicFarmActivity.this)
                            .setTitle("Hodowla zakończona!")
                            .setMessage("Ta hodowla dobiegła już końca.\n\nJeśli chcesz ją usunąć,"
                                + "przytrzymaj jej pozycję w liście wszystkich hodowli, " +
                                "a następnie usuń.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    closeContextMenu();
                                }
                            })
                            .show();
                }
                else{
                    Intent intent = new Intent(view.getContext(), OperationsActivity.class);
                    intent.putExtra("OBJ", passingObj);
                    intent.putExtra("POSITION", pos);
                    startActivityForResult(intent,7);
                }
            }
        });

        farmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                new AlertDialog.Builder(BasicFarmActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Usuwanie hodowli")
                        .setMessage("Czy na pewno chcesz usunąć tą hodowlę?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayListInfo.remove(pos);
                                adapterView().notifyDataSetChanged();
                                farmList.setAdapter(adapterView());
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

                new AlertDialog.Builder(BasicFarmActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Usuwanie zakończonych hodowli")
                        .setMessage("Czy na pewno chcesz usunąć wszystkie zakończone " +
                            "hodowle z listy?!")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < farmList.getAdapter().getCount(); i++){
                                    Info a = arrayListInfo.get(i);

                                    if(a.getStep().contains("Koniec"))
                                    {
                                        arrayListInfo.remove(a);
                                        adapterView().notifyDataSetChanged();
                                        farmList.setAdapter(adapterView());
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

        //showing current time in activity
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tTime = findViewById(R.id.timeMainBasicFarmActID);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdfT = new SimpleDateFormat("kk:mm:ss");
                                String timeString = sdfT.format(date);
                                tTime.setText(timeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    //getting data back from other activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 6 && resultCode == RESULT_OK)
        {
            String name = data.getExtras().getString("NAME");
            String date = data.getExtras().getString("DATE");
            String method = data.getExtras().getString("METHOD2");
            String step = data.getExtras().getString("STEP");

            Info info = new Info(name, date, method, step);
            arrayListInfo.add(info);
            farmList.setAdapter(adapterView());
            saveData();
        }
        if (requestCode == 7 && resultCode == RESULT_OK)
        {
            String name = data.getExtras().getString("O_NAME");
            String date = data.getExtras().getString("O_DATE");
            String method = data.getExtras().getString("O_METHOD");
            String step = data.getExtras().getString("O_STEP");
            int pos = data.getExtras().getInt("O_POSITION");

            //changing item values
            Info info = new Info(name, date, method, step);
            arrayListInfo.set(pos, info);
            farmList.setAdapter(adapterView());
            //saving values
            saveData();

            //loading data after changes
            arrayListInfo = loadData();
            adapterView();
            farmList.setAdapter(adapterView());
        }
    }

    //saving arrayList data using json
    private void saveData(){
        SharedPreferences pref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayListInfo);
        editor.putString("ListToSave", json);
        editor.apply();
    }

    //loading data to arrayList
    private ArrayList<Info> loadData(){
        SharedPreferences pref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("ListToSave", null);
        Type type =new TypeToken<ArrayList<Info>>(){}.getType();
        ArrayList<Info> obj =gson.fromJson(json,type);
        return obj;
    }

    //creating new arrayList or loading previous one
    private void createNewArrayListOrLoadPreviousOne()
    {
        arrayListInfo = new ArrayList<Info>();
        arrayListInfo = loadData();
        if(arrayListInfo == null)
        {
            arrayListInfo = new ArrayList<Info>();
        }
        farmList.setAdapter(adapterView());
    }

    //changing colors of listView items
    private InfoListAdapter adapterView()
    {
        InfoListAdapter adapter = new InfoListAdapter(BasicFarmActivity.this,
                R.layout.listview_layout, arrayListInfo){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                Info inf = arrayListInfo.get(position);
                String dateTday = currentDateTV.getText().toString();
                String farmProcessDate = inf.getDateInfo();
                View view = super.getView(position, convertView, parent);

                if((dateTday.equals(farmProcessDate) || farmProcessDate.equals("brak")) &&
                    !inf.getStep().contains("Koniec"))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.colorGreenForList));
                }
                else if(inf.getStep().contains("Koniec"))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.colorGrayForList));
                }
                else {
                    try {
                        Date dateTdayParsed = new SimpleDateFormat("dd-MM-yyyy").
                                parse(dateTday);
                        Date farmProcessDateParsed = new SimpleDateFormat("dd-MM-yyyy").
                                parse(farmProcessDate);

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
