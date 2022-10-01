package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.Adapter.AddressAdapter;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.Models.ModelResponse.AddressResponse;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    FloatingActionButton fabAddAddress;
    RecyclerView rcvAddress;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        rcvAddress = findViewById(R.id.rcvAddress);
        phoneNumber = AppHelper.AppCheck.getInstance(AddressActivity.this).getLocalStorageManager().getUserPhoneNumber();
        fabAddAddress = findViewById(R.id.fabAddAddress);
        fabAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));

            }
        });
        AddressModel addressModel = new AddressModel(phoneNumber);
        ApiController.ApiService.getService(AddressActivity.this).get_all_address(addressModel)
                .enqueue(new Callback<AddressResponse>() {
                    @Override
                    public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                        if (response.isSuccessful()){
                            AddressResponse addressResponse = response.body();
                            AddressAdapter adapter = new AddressAdapter(addressResponse.getData(),AddressActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this);
                            rcvAddress.setLayoutManager(linearLayoutManager);
                            rcvAddress.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        Log.d("====>",""+response.code());
                    }

                    @Override
                    public void onFailure(Call<AddressResponse> call, Throwable t) {
                        Log.d("====>",""+t);

                    }
                });
    }
}