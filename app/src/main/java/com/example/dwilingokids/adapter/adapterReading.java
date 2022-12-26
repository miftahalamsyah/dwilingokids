package com.example.dwilingokids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dwilingokids.R;
import com.example.dwilingokids.model.modelReading;

import java.util.ArrayList;

public class adapterReading extends ArrayAdapter<modelReading> {


    public adapterReading(@NonNull Context context, int resource, @NonNull ArrayList<modelReading> modelReadings) {
        super(context, resource, modelReadings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_materi, parent,false);

        }

        modelReading currentItem = getItem(position);

        TextView tvID = listItemView.findViewById(R.id.tvID);
        tvID.setText(currentItem.getIdCat());
        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        tvTitle.setText(currentItem.getTitleReading());
        TextView tvContent = listItemView.findViewById(R.id.tvContent);
        tvContent.setText(currentItem.getContentReading());
        TextView tvCreated = listItemView.findViewById(R.id.tvCreated);
        tvCreated.setText(currentItem.getCreated_AT());

        return listItemView;
    }
}
