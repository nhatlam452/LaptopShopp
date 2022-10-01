package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.PaymentActivity;
import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ModelResponse.CartResponse;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.google.android.gms.common.api.Api;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    TextView tvSumCart;
    Button btnPay;
    String phonenumber;
    EditText edtNote;
    List<Cart> listCart;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcvCart);

        tvSumCart = view.findViewById(R.id.tvSumCart);
        btnPay = view.findViewById(R.id.btnPay);
        edtNote = view.findViewById(R.id.edtNote);
        listCart = new ArrayList<>();
        phonenumber = AppHelper.AppCheck.getInstance(getContext()).getLocalStorageManager().getUserPhoneNumber();
        Cart cart = new Cart(phonenumber);

        ApiController.ApiService.getService(getContext()).get_cart_by_phonenumber(cart).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {
                        CartAdapter adapter = new CartAdapter(cartResponse.getData(), getContext(), new CartAdapter.OnChangeData() {
                            @Override
                            public void onChangeTotal(List<Cart> cartList) {
                                float total = 0;
                                for (int i = 0; i < cartResponse.getData().size(); i++) {
                                    total = total + cartList.get(i).getTotalPrice();
                                    Log.d("total =>>", cartResponse.getData().size() + "----" + cartList.get(i).getTotalPrice() + "---" + i + "");
                                    listCart.add(cartList.get(i));
                                }
                                tvSumCart.setText(NumberFormat.getInstance().format(total));
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter.notifyDataSetChanged();

                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                int position = viewHolder.getAdapterPosition();
                                Cart cartRemove = new Cart(listCart.get(position).getCartID(),null);
                                ApiController.ApiService.getService(getContext()).remove_cart(cartRemove).enqueue(ApiCallback.removeCart(getContext()));
                            }
                        });
                        itemTouchHelper.attachToRecyclerView(recyclerView);

                    }
                    Log.d("========>", cartResponse.getMessage() + "");
                }
                Log.d("===>>", "" + response.code());

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("========>", t.getMessage() + "");
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Order order = new Order(null, phonenumber,"1",tvSumCart.getText().toString(),"",edtNote.getText().toString());
//
//                ApiController.ApiService.getService(getContext()).insert_order(order).enqueue(ApiCallback.insertOrder(getContext()));

                for (int i = 0; i < listCart.size(); i++) {
                    Cart cart = new Cart(listCart.get(i).getCartID(), listCart.get(i).getQuantity());
                    ApiController.ApiService.getService(getContext()).update_cart(cart).enqueue(ApiCallback.updateCart(getContext()));
                }

                Intent i = new Intent(getContext(), PaymentActivity.class);
                i.putExtra("note", edtNote.getText().toString());
                startActivity(i);

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Cart cart = new Cart(phonenumber);
        ApiController.ApiService.getService(getContext()).get_cart_by_phonenumber(cart).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {
                        CartAdapter adapter = new CartAdapter(cartResponse.getData(), getContext(), new CartAdapter.OnChangeData() {
                            @Override
                            public void onChangeTotal(List<Cart> cartList) {
                                float total = 0;
                                for (int i = 0; i < cartResponse.getData().size(); i++) {
                                    total = total + cartList.get(i).getTotalPrice();
                                    Log.d("total =>>", cartResponse.getData().size() + "----" + cartList.get(i).getTotalPrice() + "---" + i + "");
                                    listCart.add(cartList.get(i));
                                }
                                tvSumCart.setText(NumberFormat.getInstance().format(total));
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter.notifyDataSetChanged();

                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                int position = viewHolder.getAdapterPosition();
                                Cart cartRemove = new Cart(cartResponse.getData().get(position).getCartID(),null);
                                ApiController.ApiService.getService(getContext()).remove_cart(cartRemove).enqueue(ApiCallback.removeCart(getContext()));
                            }
                        });
                        itemTouchHelper.attachToRecyclerView(recyclerView);

                    }
                    Log.d("========>", cartResponse.getMessage() + "");
                }
                Log.d("===>>", "" + response.code());

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("========>", t.getMessage() + "");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < listCart.size(); i++) {
            Cart cart = new Cart(listCart.get(i).getCartID(), listCart.get(i).getQuantity());
            ApiController.ApiService.getService(getContext()).update_cart(cart).enqueue(ApiCallback.updateCart(getContext()));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }
}