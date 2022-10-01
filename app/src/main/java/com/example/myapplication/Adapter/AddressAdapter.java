package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.R;

import java.util.List;

public class AddressAdapter  extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{
    List<AddressModel> mList;
    Context context;

    public AddressAdapter(List<AddressModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.AddressViewHolder(View.inflate(parent.getContext(), R.layout.layout_address_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel addressModel = mList.get(position);
        holder.tvLocation.setText(addressModel.getLocation());
        holder.tvCommune.setText(addressModel.getCommune());
        holder.tvDistrict.setText(addressModel.getDistrict());
        holder.tvProvince.setText(addressModel.getProvince());
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation,tvCommune,tvDistrict,tvProvince;
        public AddressViewHolder( View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvUserLocation);
            tvCommune = itemView.findViewById(R.id.tvUserCommune);
            tvDistrict = itemView.findViewById(R.id.tvUserDistrict);
            tvProvince = itemView.findViewById(R.id.tvUserProvince);
        }
    }
}
