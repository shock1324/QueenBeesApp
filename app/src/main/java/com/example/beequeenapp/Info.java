/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-01-14.
 *
 ******************************************************************************/

package com.example.beequeenapp;

import android.app.Application;
import android.content.Context;
import java.lang.*;
import java.io.Serializable;

public class Info extends Application  implements  Serializable {
    public static Context context = MainActivity.getContext();

     String farmNameInfo;
     String step;
     String dateInfo;
     String choosenMethodInfo;
     int days;
     String processInfo;
     String nextProcessInfo;
     String processDescriptionInfo;
     final String Technical = "Wymagane zaplecze techniczne:";

    Info(String farmNameInfo, String dateInfo, String choosenMethodInfo)
    {
        this.farmNameInfo = farmNameInfo;
        this.dateInfo = dateInfo;
        this.choosenMethodInfo = choosenMethodInfo;
    }

    Info(String farmNameInfo, String dateInfo, String choosenMethodInfo, String step)
    {
        this.farmNameInfo = farmNameInfo;
        this.dateInfo = dateInfo;
        this.choosenMethodInfo = choosenMethodInfo;
        this.step = step;
    }
    public String getFarmNameInfo() {
        return farmNameInfo;
    }

    public String getDateInfo() {
        return dateInfo;
    }

    public String getChoosenMethodInfo() {
        return choosenMethodInfo;
    }

    public String getStep() {
        return step;
    }

    public String getProcessInfo() {
        return processInfo;
    }

    public String getNextProcessInfo() {
        return nextProcessInfo;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getProcessDescriptionInfo() {
        return processDescriptionInfo;
    }

    public int getDays() {
        return days;
    }

    public void step1()
    {
        this.processInfo = "Osierocenie rodziny i ustawienie gniazda";
        this.nextProcessInfo = "Poddanie ramki hodowlanej";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep1)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny";
        this.days = 0;
    }
    public void step2()
    {
        this.processInfo = "Poddanie ramki hodowlanej";
        this.nextProcessInfo="Sprawdzenie przyjęć, komasacja oraz zerwanie mateczników ratunkowych";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep2)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny";
        this.days = 1;
    }
    public void step3()
    {
        this.processInfo = "Sprawdzenie przyjęć, komasacja oraz zerwanie mateczników ratunkowych";
        this.nextProcessInfo = "Przeniesienie mateczników do cieplarki lub dalszy wychów w rodzinie";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep3) + "\n\n" +
                Technical + "\nDrobny sprzęt pasieczny";
        this.days = 7;
    }
    public void step4()
    {
        this.processInfo = "Kontrola mateczników oraz zerwanie mateczników ratunkowych";
        this.nextProcessInfo = "Brakowanie mateczników oraz ich izolacja";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep4) + "\n\n" +
                Technical + "\nDrobny sprzęt pasieczny. W razie potrzeby podkarmienia, podkarmiaczki z pokarmem!";
        this.days = 2;
    }
    public void step5()
    {
        this.processInfo = "Brakowanie mateczników oraz ich izolacja";
        this.nextProcessInfo = "Ponowne poddanie matki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep5) + "\n\n" +
                Technical + "\nDrobny sprzęt pasieczny, izolatory";
        this.days = 1;
    }
    public void step6()
    {
        this.processInfo = "Ponowne poddanie matki";
        this.nextProcessInfo = "Koniec hodowli";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep6) + "\n\n" +
                Technical + "\nDrobny sprzęt pasieczny, młoda matka np. klateczce";
    }
    public void step4warm()
    {
        this.processInfo = "Przeniesienie mateczników do cieplarki";
        this.nextProcessInfo = "Ponowne poddanie matki do rodziny wychowującej";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep4warm)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny, cieplarka";
        this.days = 1;
    }
    public void step5warm()
    {
        this.processInfo = "Ponowne poddanie matki do rodziny wychowującej";
        this.nextProcessInfo = "Brakowanie mateczników oraz ich izolacja";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep5warm)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny, młoda matka np. w klateczce";
        this.days = 4;
    }
    public void step6warm()
    {
        this.processInfo = "Brakowanie mateczników oraz ich izolacja";
        this.nextProcessInfo = "Koniec hodowli";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep6warm) + "\n\n" +
            Technical + "\nDrobny sprzęt pasieczny, izolatory";
    }
}

