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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MethodPickActivity extends AppCompatActivity {
    public TextView methodInfo;
    public RadioGroup methodChoice;
    public Button addButton;
    String textOfMethod;
    public static Context context = MainActivity.getContext();
    final String wwrbm = context.getString(R.string.WWRBMMethodInfo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_pick);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adding XML objects
        methodInfo = findViewById(R.id.methodInfoID);
        methodChoice = findViewById(R.id.radioGroupID);
        addButton = findViewById(R.id.NextID);
        final RadioButton metoda1 = findViewById(R.id.radioButton1);
        final RadioButton metoda2 = findViewById(R.id.radioButton2);
        final RadioButton metoda3 = findViewById(R.id.radioButton3);
        final RadioButton metoda4 = findViewById(R.id.radioButton4);
        final RadioButton metoda5 = findViewById(R.id.radioButton5);

        //setting buttons listeners
        methodChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    //currently only 1 method implemented
                    case R.id.radioButton1:
                        methodInfo.setText(wwrbm);
                        textOfMethod = metoda1.getText().toString();
                        addButton.setEnabled(true);
                        break;
                    //future methons
                    case R.id.radioButton2:
                        methodInfo.setText("2");
                        textOfMethod = metoda2.getText().toString();
                        break;
                    case R.id.radioButton3:
                        methodInfo.setText("3");
                        textOfMethod = metoda3.getText().toString();
                        break;
                    case R.id.radioButton4:
                        methodInfo.setText("4");
                        textOfMethod = metoda4.getText().toString();
                        break;
                    case R.id.radioButton5:
                        methodInfo.setText("5");
                        textOfMethod = metoda5.getText().toString();
                        break;
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MethodPickActivity.this,
                    AddingFarmActivity.class);
                intent.putExtra("METHOD", textOfMethod);
                startActivityForResult(intent, 66);
            }
        });
    }

    //sending data back to BasicFarmActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 66 && resultCode == RESULT_OK)
        {
            setResult(MethodPickActivity.RESULT_OK, data);
            finish();
        }
    }
}

