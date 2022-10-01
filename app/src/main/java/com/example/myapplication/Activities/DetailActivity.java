package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ProductDetail;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private Button btnAddToCart;
    private TextView tvNameDetail,tvPriceDetail,tvCpuDetail,tvVgaDetail,tvRAMDetail,tvScreenDetail,tvDiskDetail,tvSystemDetail,tvWeightDetail,tvBattery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgDetail = findViewById(R.id.imgDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        tvNameDetail = findViewById(R.id.tvNameDetail);
        tvPriceDetail = findViewById(R.id.tvPriceDetail);
        tvCpuDetail = findViewById(R.id.tvCpuDetail);
        tvVgaDetail = findViewById(R.id.tvVgaDetail);
        tvRAMDetail = findViewById(R.id.tvRAMDetail);
        tvScreenDetail = findViewById(R.id.tvScreenDetail);
        tvDiskDetail = findViewById(R.id.tvDiskDetail);
        tvSystemDetail = findViewById(R.id.tvSystemDetail);
        tvWeightDetail = findViewById(R.id.tvWeightDetail);
        tvBattery = findViewById(R.id.tvBatteryDetail);
        Intent i = getIntent();
        String  productId = i.getStringExtra("productID");
        String phonenumber = AppHelper.AppCheck.getInstance(DetailActivity.this).getLocalStorageManager().getUserPhoneNumber();
        ProductDetail productDetail = new ProductDetail(productId);
        Toast.makeText(this, ""+productId , Toast.LENGTH_SHORT).show();
        ApiController.ApiService.getService(DetailActivity.this).get_product_detail(productDetail).enqueue(ApiCallback.getProductDetail(DetailActivity.this,imgDetail,tvNameDetail,tvPriceDetail,tvCpuDetail,tvVgaDetail,tvRAMDetail,tvScreenDetail,tvDiskDetail,tvSystemDetail,tvWeightDetail,tvBattery));
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DetailActivity", "" + phonenumber);
                Cart cart = new Cart(productId,null,phonenumber,"1");
                ApiController.ApiService.getService(DetailActivity.this).add_to_cart(cart).enqueue(ApiCallback.addToCart(DetailActivity.this));

            };
        });
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();

    }
}