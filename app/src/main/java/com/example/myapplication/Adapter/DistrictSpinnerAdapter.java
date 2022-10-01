package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Models.AddressDetail.District;
import com.example.myapplication.R;

import java.util.List;

public class DistrictSpinnerAdapter extends ArrayAdapter<District> {
    TextView edtSpinner,tvPDC;


    public DistrictSpinnerAdapter(Context context, int resource, List<District> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_selected_spinner, parent, false);
        edtSpinner = convertView.findViewById(R.id.edtSpinner);

        District district = this.getItem(position);
        if (district != null) {
            edtSpinner.setText(district.getName());
            notifyDataSetChanged();
        }
        return convertView;


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_spinner, parent, false);
        tvPDC = convertView.findViewById(R.id.tvPDC);

        District district = this.getItem(position);
        if (district != null) {
            tvPDC.setText(district.getName());
        }
        return convertView;
    }
}
