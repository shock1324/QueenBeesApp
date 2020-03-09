/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-01-14.
 *
 ******************************************************************************/

package com.example.beequeenapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class InfoQueenListAdapter extends  ArrayAdapter<InfoQueen>
{
    private Context mContext;
    private int mResource;


    public InfoQueenListAdapter(@NonNull Context context, int resource, ArrayList<InfoQueen>
    objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getQueenFarmNameInfoQ();
        String date = getItem(position).getDateInfoQ();
        String number = getItem(position).getNumberQ();
        String step = getItem(position).getStepQ();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.QueenFarmNameLayID);
        TextView tvDate = convertView.findViewById(R.id.QueenFarmDateLayID);
        TextView tvNumber = convertView.findViewById(R.id.QueenHomeLayID);
        TextView tvStep = convertView.findViewById(R.id.QueenStepLayID);

        tvName.setText(name);
        tvNumber.setText(number);
        tvDate.setText(date);
        tvStep.setText(step);

        return convertView;
    }
}


