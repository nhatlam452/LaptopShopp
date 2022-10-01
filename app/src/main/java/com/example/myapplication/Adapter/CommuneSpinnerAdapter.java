package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Models.AddressDetail.Commune;
import com.example.myapplication.R;

import java.util.List;

public class CommuneSpinnerAdapter extends ArrayAdapter<Commune> {
    TextView edtSpinner,tvPDC;


    public CommuneSpinnerAdapter(Context context, int resource, List<Commune> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_selected_spinner, parent, false);
        edtSpinner = convertView.findViewById(R.id.edtSpinner);

        Commune commune = this.getItem(position);
        if (commune != null) {
            edtSpinner.setText(commune.getName());
            notifyDataSetChanged();
        }


        return convertView;


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_spinner, parent, false);
        tvPDC = convertView.findViewById(R.id.tvPDC);

        Commune commune = this.getItem(position);
        if (commune != null) {
            tvPDC.setText(commune.getName());
        }
        return convertView;
    }
}
