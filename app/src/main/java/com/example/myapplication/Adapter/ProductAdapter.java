package com.example.myapplication.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.DetailActivity;
import com.example.myapplication.Models.Products;
import com.example.myapplication.R;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemHolder> {
    private List<Products> productList;
    Context context;

    public ProductAdapter(List<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ProductItemHolder(View.inflate(parent.getContext(), R.layout.item_home_layout, null));
    }

    @Override
    public void onBindViewHolder( ProductItemHolder holder, int position) {
        Products product = productList.get(position);
        holder.tvBrand.setText(product.getBrand());
        holder.tvNameProduct.setText(product.getProductName());
        holder.tvPriceProduct.setText(NumberFormat.getInstance().format(Float.parseFloat(product.getPrice())) +" đ");
        holder.tvPriceProductSale.setText(NumberFormat.getInstance().format(Float.parseFloat(product.getPrice())) + " đ");
        Glide.with(context).load(product.getUrl()).into(holder.imvProduct);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra("productID",product.getProductID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        if(productList == null) {
            return 0;
        }
        else{
            return productList.size();
        }
    }


    public class  ProductItemHolder extends RecyclerView.ViewHolder{
        LinearLayout layout_item;
        ImageView imvProduct;
        TextView tvNameProduct, tvPriceProduct,tvPriceProductSale,tvBrand;

        public ProductItemHolder(View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.product_image);
            layout_item = itemView.findViewById(R.id.layout_item);
            tvNameProduct = itemView.findViewById(R.id.product_name);
            tvPriceProduct = itemView.findViewById(R.id.product_price);
            tvPriceProductSale = itemView.findViewById(R.id.product_price_sale);
            tvBrand = itemView.findViewById(R.id.category);
        }
    }
}
