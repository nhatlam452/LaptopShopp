package com.example.myapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.LoginActivity;
import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.OtpVerifyActivity;
import com.example.myapplication.Activities.RegisterActivity;
import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ModelResponse.CartResponse;
import com.example.myapplication.Models.ModelResponse.OrderResponse;
import com.example.myapplication.Models.ModelResponse.ProductDetailResponse;
import com.example.myapplication.Models.ModelResponse.ProductResponse;
import com.example.myapplication.Models.ModelResponse.UserResponse;
import com.example.myapplication.Models.Order;
import com.example.myapplication.Models.ProductDetail;
import com.example.myapplication.Models.Products;
import com.example.myapplication.Utilities.AppHelper;
import com.example.myapplication.Utilities.SnapHelperOneByOne;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallback {
    public static Callback<UserResponse> registerAccount(Context context) {
        Callback<UserResponse> registerAccountCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse.getResponseCode() == 1) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        ((OtpVerifyActivity) context).finish();
                        context.startActivity(intent);

                    }
                    Log.d("========>",userResponse.getMessage()+"");

                } else {

                    Log.d("=====>", "" + response.code());
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("=====>", "" + t.getMessage());
            }
        };
        return registerAccountCallback;
    }

    public static Callback<UserResponse> getCheckLogin(Context context) {
        Callback<UserResponse> check_loginCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse.getResponseCode() == 0) {
                        Log.d("========>",userResponse.getMessage()+"");
                    } else if (userResponse.getResponseCode() == 1) {
                        AppHelper.AppCheck.getInstance(context).getLocalStorageManager().setUserLogin(true);
                        Log.d("========>",userResponse.getMessage()+"");
                        Intent intent = new Intent(context, MainActivity.class);
                        ((LoginActivity) context).finishAffinity();
                        context.startActivity(intent);
                    } else {
                        Log.d("========>",userResponse.getMessage()+"");

                    }
                } else {
                    Log.d("======>", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("========>",t.getMessage()+"");

            }
        };
        return check_loginCallback;
    }

    public static Callback<ProductResponse> getAllProduct(Context context, RecyclerView recyclerView, SearchView searchView) {
        Callback<ProductResponse> getAllProductCallBack = new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse.getResponseCode() == 1) {
                        ProductAdapter adapter = new ProductAdapter(productResponse.getData(), context);
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
//                        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
//                        linearSnapHelper.attachToRecyclerView(recyclerView);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        if (searchView != null) {
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    List<Products> filterList = new ArrayList<>();
                                    for (Products products : productResponse.getData()) {
                                        if (products.getProductName().toLowerCase().contains(newText.toLowerCase().trim())) {
                                            filterList.add(products);
                                        }
                                    }
                                    if (filterList.isEmpty()) {
                                        ProductAdapter adapter = new ProductAdapter(null, context);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        ProductAdapter adapter = new ProductAdapter(filterList, context);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                                        adapter.notifyDataSetChanged();
                                    }
                                    return true;
                                }
                            });
                        }
                    }
                }
                Log.d("===>>", "" + response.code());

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("========>",t.getMessage()+"");

            }
        };
        return getAllProductCallBack;
    }

    public static Callback<ProductDetailResponse> getProductDetail(Context context, ImageView imgDetail, TextView tvNameDetail, TextView tvPriceDetail, TextView tvCpuDetail, TextView tvVgaDetail, TextView tvRAMDetail, TextView tvScreenDetail, TextView tvDiskDetail, TextView tvSystemDetail, TextView tvWeightDetail, TextView tvBattery) {
        Callback<ProductDetailResponse> getAllProductCallBack = new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.isSuccessful()) {
                    ProductDetailResponse productDetailResponse = response.body();
                    if (productDetailResponse.getResponseCode() == 1) {
                        ProductDetail productDetail = productDetailResponse.getData().get(0);
                        Glide.with(context).load(productDetail.getUrl()).into(imgDetail);
                        tvNameDetail.setText(productDetail.getProductName());
                        tvPriceDetail.setText(productDetail.getPrice() + "");
                        tvCpuDetail.setText(productDetail.getCpu());
                        tvVgaDetail.setText(productDetail.getGpu());
                        tvRAMDetail.setText(productDetail.getRam());
                        tvScreenDetail.setText(productDetail.getScreen());
                        tvDiskDetail.setText(productDetail.getDisk());
                        tvSystemDetail.setText(productDetail.getSystem());
                        tvWeightDetail.setText(productDetail.getWeight());
                        tvBattery.setText(productDetail.getBattery());
                    }
                    Log.d("========>",productDetailResponse.getMessage()+"");

                }
                Log.d("===>>", "" + response.code());

            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Log.d("========>",t.getMessage()+"");

            }
        };
        return getAllProductCallBack;
    }



    public static Callback<CartResponse> addToCart(Context context) {
        Callback<CartResponse> addCartCallBack = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {
                        Toast.makeText(context, "Them gio hang thanh cong", Toast.LENGTH_SHORT).show();

                    }
                    Log.d("=====>", cartResponse.getMessage());
                } else {

                    Log.d("=====>", "" + response.code());
                }

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("=====>", "" + t.getMessage());
            }
        };
        return addCartCallBack;
    }
    public static Callback<OrderResponse> insertOrder(Context context) {
        Callback<OrderResponse> registerAccountCallback = new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse.getResponseCode() == 1) {
                        Toast.makeText(context, "thanh toan thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("========>",orderResponse.getMessage()+"");

                } else {

                    Log.d("=====>", "" + response.code());
                }

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("=====>", "" + t.getMessage());
            }
        };
        return registerAccountCallback;
    }

    public static Callback<CartResponse> updateCart(Context context) {
        Callback<CartResponse> updateCartCallback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {
                        Log.d("=======>","Cap nhat so luong thanh cong");
                    }
                    Log.d("========>",cartResponse.getMessage()+"");

                } else {

                    Log.d("=====>", "" + response.code());
                }

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("=====>", "" + t.getMessage());
            }
        };
        return updateCartCallback;
    }

    public static Callback<CartResponse> removeCart(Context context) {
        Callback<CartResponse> removeCartCallback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {
                        Log.d("=======>","Xoa san pham thanh cong");
                    }
                    Log.d("========>",cartResponse.getMessage()+"");

                } else {

                    Log.d("=====>", "" + response.code());
                }

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("=====>", "" + t.getMessage());
            }
        };
        return removeCartCallback;
    }
}
