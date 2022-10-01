package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Models.AddressDetail.Province;
import com.example.myapplication.R;

import java.util.List;

public class ProvinceSpinnerAdapter extends ArrayAdapter<Province> {
    TextView edtSpinner,tvPDC;


    public ProvinceSpinnerAdapter(Context context, int resource, List<Province> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_selected_spinner, parent, false);
        edtSpinner = convertView.findViewById(R.id.edtSpinner);

        Province province = this.getItem(position);
        if (province != null) {
            edtSpinner.setText(province.getName());
            notifyDataSetChanged();
        }


        return convertView;


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_spinner, parent, false);
        tvPDC = convertView.findViewById(R.id.tvPDC);

        Province province = this.getItem(position);
        if (province != null) {
            tvPDC.setText(province.getName());
        }
        return convertView;
    }
}
