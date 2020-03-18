/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-07.
 *
 ******************************************************************************/

package com.example.beequeenapp;
import android.app.Application;
import android.content.Context;
import java.lang.*;
import java.io.Serializable;

public class InfoQueen extends Application implements Serializable {

    public static Context context = MainActivity.getContext();
    String QueenFarmNameInfo;
    String Number;
    String Step;
    String DateInfo;
    int days;
    String processInfo;
    String nextProcessInfo;
    String processDescriptionInfo;
    String KindOfQueenToFarm;
    String HowToAddQueenToFarm;
    final String Technical = "Wymagane zaplecze techniczne:";

    public InfoQueen(String QueenFarmNameInfo, String DateInfo, String Number, String Step) {
        this.QueenFarmNameInfo = QueenFarmNameInfo;
        this.Number = Number;
        this.DateInfo = DateInfo;
        this.Step = Step;
    }

    public int getDays() {
        return days;
    }
    public String getProcessInfo() {
        return processInfo;
    }

    public String getNextProcessInfo() {
        return nextProcessInfo;
    }

    public String getProcessDescriptionInfo() {
        return processDescriptionInfo;
    }

    public String getQueenFarmNameInfoQ() {
        return QueenFarmNameInfo;
    }

    public String getNumberQ() {
        return Number;
    }

    public String getStepQ() {
        return Step;
    }

    public String getDateInfoQ() {
        return DateInfo;
    }

    public String getKindOfQueenToFarm() {
        return KindOfQueenToFarm;
    }

    public String getHowToAddQueenToFarm() {
        return HowToAddQueenToFarm;
    }

    public void setStepQ(String step) {
        Step = step;
    }

    public void MethodChanger(String step)
    {
        if (step.contains("mn") || step.contains("(mn)"))
        {
            this.KindOfQueenToFarm = "Wychów matki nieunasienionej";
            this.HowToAddQueenToFarm = "brak";
        }
        else if(step.contains("muNW") || step.contains("(muNW)")){
            this.KindOfQueenToFarm = "Wychów matki unasienionej";
            this.HowToAddQueenToFarm = "Poddanie niewygryzionego matecznika";
        }
        else
        {
            this.KindOfQueenToFarm = "Wychów matki unasienionej";
            this.HowToAddQueenToFarm = "Poddanie matki po wygryzieniu";
        }
    }
    //////steps for not inseminated queens//////
    public void step1mn()
    {
        this.processInfo = "Oczekiwanie na wygryzienie";
        this.nextProcessInfo = "Znakowanie matki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep1mnQ);
        this.days = 0;
    }
    public void step2mn()
    {
        this.processInfo = "Znakowanie matki";
        this.nextProcessInfo = "Koniec hodowli (mn)";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep2mnQ )
                + "\n\n" + Technical + "\nZnaczki, klateczki transportowe, klej, " +
                "przyrządy do łapania matek, drobny sprzęt pasieczny";
        this.days = 0;
    }
    public void koniecmn()
    {
        this.processInfo = "brak";
        this.nextProcessInfo = "brak";
        this.processDescriptionInfo = "brak";
        this.days = 0;
    }

    ////// steps for inseminated queens added before born //////
    public void step1muNW()
    {
        this.processInfo = "Przygotowanie ulika weselnego";
        this.nextProcessInfo = "Podanie niewygryzionego matecznika";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep1muNWQ)
                + "\n\n" + Technical + "\nUlik weselny, drobny sprzęt pasieczny";
        this.days = 0;
    }
    public void step2muNW()
    {
        this.processInfo = "Podanie niewygryzionego matecznika";
        this.nextProcessInfo = "Kontrola wygryzienia i przyjecia matek";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep2muNWQ)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny";
        this.days = 2;
    }
    public void step3muNW()
    {
        this.processInfo = "Kontrola wygryzienia i przyjecia matek";
        this.nextProcessInfo = "Kontrola czerwiu unasienionej matki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep3muNWQ)
                + "\n\n" + Technical + "\nDrobny sprzęt pasieczny";
        this.days = 10;
    }
    public void step4muNW()
    {
        this.processInfo = "Kontrola czerwiu unasienionej matki";
        this.nextProcessInfo = "Wyłapanie matki, znakowanie oraz przeniesienie do klateczki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep4muNWQ) + "\n\n" +
            Technical + "\nDrobny sprzęt pasieczny";
        this.days = 0;
    }
    public void step5muNW()
    {
        this.processInfo = "Wyłapanie matki, znakowanie oraz przeniesienie do klateczki";
        this.nextProcessInfo = "Koniec hodowli (muNW)";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep5muNWQ) + "\n\n" +
            Technical + "\nZnaczki, klateczki transportowe, klej, przyrządy do łapania matek, drobny sprzęt pasieczny";
        this.days = 0;
    }
    public void koniecmuNW()
    {
        this.processInfo = "brak";
        this.nextProcessInfo = "brak";
        this.processDescriptionInfo = "brak";
        this.days = 0;
    }

    ////// steps for inseminated queens added after born //////
    public void step1muW()
    {
        this.processInfo = "Oczekiwanie na wygryzienie matek";
        this.nextProcessInfo = "Przygotowanie ulika weselnego";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep1muWQ);
        this.days = 0;
    }
    public void step2muW()
    {
        this.processInfo = "Przygotowanie ulika weselnego";
        this.nextProcessInfo = "Podanie matki do ulika weselnego";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep2muWQ) + "\n\n" +
            Technical + "\nUlik weselny, drobny sprzęt pasieczny";
        this.days = 0;
    }
    public void step3muW()
    {
        this.processInfo = "Podanie matki do ulika weselnego";
        this.nextProcessInfo = "Kontrola czerwiu unasienionej matki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep3muWQ) + "\n\n" +
            Technical + "\nDrobny sprzęt pasieczny";
        this.days = 10;
    }

    public void step4muW()
    {
        this.processInfo = "Kontrola czerwiu unasienionej matki";
        this.nextProcessInfo = "Wyłapanie matki, znakowanie oraz przeniesienie do klateczki";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep4muWQ) + "\n\n" +
            Technical + "\nDrobny sprzęt pasieczny";
        this.days = 0;
    }
    public void step5muW()
    {
        this.processInfo = "Wyłapanie matki, znakowanie oraz przeniesienie do klateczki";
        this.nextProcessInfo = "Koniec hodowli (muW)";
        this.processDescriptionInfo = context.getString(R.string.processDescriptionInfoStep5muWQ) + "\n\n" +
            Technical + "\nZnaczki, klateczki transportowe, klej, przyrządy do łapania matek, drobny sprzęt pasieczny";
        this.days = 0;
    }
    public void koniecmuW()
    {
        this.processInfo = "brak";
        this.nextProcessInfo = "brak";
        this.processDescriptionInfo = "brak";
        this.days = 0;
    }
}


