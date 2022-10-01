package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.DetailActivity;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.Products;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder> {
    private List<Cart> cartList;
    Context context;
    OnChangeData onChangeData;
    public interface OnChangeData{
        void onChangeTotal(List<Cart> mCartList);
    }



    public CartAdapter(List<Cart> cartList, Context context,OnChangeData onChangeData) {
        this.cartList = cartList;
        this.context = context;
        this.onChangeData = onChangeData;
    }

    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.CartItemHolder(View.inflate(parent.getContext(), R.layout.custom_cart, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        Cart cart = cartList.get(position);
        float price = Float.parseFloat(cart.getPrice());
        Glide.with(context).load(cart.getUrl()).into(holder.imgCart);
        holder.tvCartQuantity.setText(cart.getQuantity());
        holder.tvCartName.setText(cart.getProductName());
        holder.tvCartPrice.setText(NumberFormat.getInstance().format(Float.parseFloat(cart.getPrice())) + " đ");
        holder.tvCartPriceSale.setText(NumberFormat.getInstance().format(Float.parseFloat(cart.getPrice())) + " đ");

        holder.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("productID", cart.getProductID());
                context.startActivity(i);
            }
        });
        float total = Float.parseFloat(cart.getQuantity()) * price;
        cart.setTotalPrice(total);
        onChangeData.onChangeTotal(cartList);
        holder.imgIncreased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(cart.getQuantity()) <= 2) {
                    cart.setQuantity(Integer.valueOf(cart.getQuantity()) + 1 + ""); ;
                    holder.tvCartQuantity.setText(Integer.valueOf(cart.getQuantity()) + "");
                    float total = Integer.valueOf(cart.getQuantity()) * price;
                    cart.setTotalPrice(total);
                    onChangeData.onChangeTotal(cartList);

                }
            }
        });
        holder.imgDecreased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(cart.getQuantity()) > 1) {
                    cart.setQuantity(Integer.valueOf(cart.getQuantity()) - 1 + ""); ;
                    holder.tvCartQuantity.setText(Integer.valueOf(cart.getQuantity()) + "");
                    float total = Integer.valueOf(cart.getQuantity()) * price;
                    cart.setTotalPrice(total);
                    onChangeData.onChangeTotal(cartList);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        if (cartList == null) {
            return 0;
        } else {
            return cartList.size();
        }
    }

    public class CartItemHolder extends RecyclerView.ViewHolder {
        ImageView imgCart, imgDecreased, imgIncreased;
        TextView tvCartName, tvCartQuantity, tvCartPriceSale, tvCartPrice;

        public CartItemHolder(View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.imgCart);
            imgDecreased = itemView.findViewById(R.id.imgDecreased);
            imgIncreased = itemView.findViewById(R.id.imgIncreased);
            tvCartName = itemView.findViewById(R.id.tvCartName);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvCartPriceSale = itemView.findViewById(R.id.tvCartPriceSale);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
        }
    }
}
