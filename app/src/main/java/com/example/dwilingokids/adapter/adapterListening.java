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
import com.example.dwilingokids.model.modelListening;

import java.util.ArrayList;

public class adapterListening extends ArrayAdapter<modelListening> {

    public adapterListening(@NonNull Context context, int resource, @NonNull ArrayList<modelListening> modelListenings) {
        super(context, resource, modelListenings);
    }

    //CTRL+O -> getView

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_materi, parent,false);

        }

        modelListening currentItem = getItem(position);
        TextView tvID = listItemView.findViewById(R.id.tvID);
        tvID.setText(currentItem.getIdCat());
        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        tvTitle.setText(currentItem.getTitleListening());
        TextView tvContent = listItemView.findViewById(R.id.tvContent);
        tvContent.setText(currentItem.getContentListening());
        TextView tvCreated = listItemView.findViewById(R.id.tvCreated);
        tvCreated.setText(currentItem.getCreated_AT());

        return listItemView;
    }
}
