/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-19
 *  
 ******************************************************************************/

package com.example.beequeenapp;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DisplayTimeAndDate {

    TextView time, date;

    public DisplayTimeAndDate(TextView time, TextView date) {
        this.time = time;
        this.date = date;
    }

    void displayTimeMethod()
    {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                            TextView tTime = time;
                            long date = System.currentTimeMillis();
                            SimpleDateFormat sdfT = new SimpleDateFormat("kk:mm:ss");
                            String timeString = sdfT.format(date);
                            tTime.setText(timeString);
                            }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
    void displayDateMethod(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateStr = dateFormat.format(calendar.getTime());
        date.setText(currentDateStr);
    }
}
