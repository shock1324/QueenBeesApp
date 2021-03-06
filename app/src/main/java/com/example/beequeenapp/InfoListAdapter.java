/*******************************************************************************
 *
 *  * Created by Cezary Wasilewski.
 *  * Copyright (c) 2020. All rights reserved.
 *  * Last modified 2020-03-19
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

public class InfoListAdapter extends ArrayAdapter<Info>
{

    private final Context mContext;
    private final int mResource;


    public InfoListAdapter(@NonNull Context context, int resource, ArrayList<Info> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getFarmNameInfo();
        String date = getItem(position).getDateInfo();
        String step = getItem(position).getStep();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvName = convertView.findViewById(R.id.farmNameLayID);
        TextView tvDate = convertView.findViewById(R.id.farmDateLayID);
        TextView tvStep = convertView.findViewById(R.id.stepLayID);

        tvName.setText(name);
        tvDate.setText(date);
        tvStep.setText(step);
        return convertView;
    }

}

