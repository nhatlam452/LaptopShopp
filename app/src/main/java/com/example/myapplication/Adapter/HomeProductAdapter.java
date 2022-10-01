package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.HomeProduct;
import com.example.myapplication.R;

import java.util.List;

public class HomeProductAdapter extends  RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder> {
        List<List<HomeProduct>> mList;
        Context context;

public HomeProductAdapter(List<List<HomeProduct>> mList, Context context) {
        this.mList = mList;
        this.context = context;
        }

@Override
public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeProductViewHolder(View.inflate(parent.getContext(), R.layout.layout_item_home_product, null));
        }

@Override
public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position) {
        List<HomeProduct> productList = (List<HomeProduct>) mList.get(position);
        HomeProduct product = productList.get(position);
        holder.tvHomeProduct.setText(product.getTitle());
        ProductAdapter productAdapter = new ProductAdapter(product.getmList(),context);
        holder.rcvHomeProduct.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rcvHomeProduct.setAdapter(productAdapter);

        }

@Override
public int getItemCount() {
        if(mList == null) {
        return 0;
        }
        else{
        return mList.size();
        }    }

public class HomeProductViewHolder extends RecyclerView.ViewHolder {
    RecyclerView rcvHomeProduct;
    TextView tvHomeProduct;
    public HomeProductViewHolder(@NonNull View itemView) {
        super(itemView);
        rcvHomeProduct = itemView.findViewById(R.id.rcvHomeProduct);
        tvHomeProduct = itemView.findViewById(R.id.tvHomeProduct);
    }
}
}