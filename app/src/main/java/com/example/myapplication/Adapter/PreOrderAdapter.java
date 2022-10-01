package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.List;

public class PreOrderAdapter  extends RecyclerView.Adapter<PreOrderAdapter.PreOrdeerItemHolder>{
    private List<Cart> cartList;
    Context context;

    public PreOrderAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public PreOrdeerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreOrderAdapter.PreOrdeerItemHolder(View.inflate(parent.getContext(), R.layout.pre_order_layout, null));    }

    @Override
    public void onBindViewHolder(@NonNull PreOrdeerItemHolder holder, int position) {
        Cart cart = cartList.get(position);
        float price = Float.parseFloat(cart.getPrice());
        Glide.with(context).load(cart.getUrl()).into(holder.imgPreOrder);
        holder.tvQuantityPre.setText(cart.getQuantity());
        holder.tvPreOrderName.setText(cart.getProductName());
        holder.tvPricePreOrder.setText(NumberFormat.getInstance().format(Float.parseFloat(cart.getPrice())) + " đ");
    }

    @Override
    public int getItemCount() {
        if (cartList == null) {
            return 0;
        } else {
            return cartList.size();
        }    }

    public class PreOrdeerItemHolder extends RecyclerView.ViewHolder {
        ImageView imgPreOrder;
        TextView tvPreOrderName,tvQuantityPre,tvPricePreOrder;

        public PreOrdeerItemHolder(View itemView) {
            super(itemView);
            imgPreOrder = itemView.findViewById(R.id.imgPreOrder);
            tvPreOrderName = itemView.findViewById(R.id.tvPreOrderName);
            tvQuantityPre = itemView.findViewById(R.id.tvQuantityPre);
            tvPricePreOrder = itemView.findViewById(R.id.tvPricePreOrder);

        }
    }
}
