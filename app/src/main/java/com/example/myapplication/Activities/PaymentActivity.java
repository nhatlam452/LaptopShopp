package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Adapter.AddressAdapter;
import com.example.myapplication.Adapter.CommuneSpinnerAdapter;
import com.example.myapplication.Adapter.DistrictSpinnerAdapter;
import com.example.myapplication.Adapter.ProvinceSpinnerAdapter;
import com.example.myapplication.Adapter.PreOrderAdapter;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.AddressDetail.Commune;
import com.example.myapplication.Models.AddressDetail.District;
import com.example.myapplication.Models.AddressDetail.Province;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ModelResponse.AddressResponse;
import com.example.myapplication.Models.ModelResponse.CartResponse;
import com.example.myapplication.Models.ModelResponse.CommuneResponse;
import com.example.myapplication.Models.ModelResponse.DistrictResponse;
import com.example.myapplication.Models.ModelResponse.ProvinceResponse;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PaymentActivity extends AppCompatActivity {
    float total = 0;
    RecyclerView rcvPayment;
    String phonenumber;
    CheckBox cbAddress;
    ProvinceSpinnerAdapter provinceSpinnerAdapter;
    DistrictSpinnerAdapter districtSpinnerAdapter;
    CommuneSpinnerAdapter communeSpinnerAdapter;
    List<Province> provinceList;
    List<District> districtList;
    List<Commune> communeList;
    TextView tvTotalOrder, tvTamTinh, tvTotalPrice,tvProvince,tvDistrict,tvCommune;
    Spinner spnProvince, spnDistrict, spnCommune,spnPayment;
    LinearLayout lllAddress,lllAddressDefault;
EditText edtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        rcvPayment = findViewById(R.id.rcvPayment);
        spnPayment = findViewById(R.id.spnPayment);
        edtLocation = findViewById(R.id.edtLocation);
        cbAddress = findViewById(R.id.cbAddress);
        tvTotalOrder = findViewById(R.id.tvTotalCart);
        tvProvince = findViewById(R.id.tvProvince);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvCommune = findViewById(R.id.tvCommune);
        lllAddress = findViewById(R.id.lllAddress);
        lllAddressDefault = findViewById(R.id.lllAddressDefault);
        tvTamTinh = findViewById(R.id.tvTamTinh);
        tvTotalPrice = findViewById(R.id.tvTotalPriceOrder);
        spnProvince = findViewById(R.id.spnProvince);
        spnDistrict = findViewById(R.id.spnDistrict);
        spnCommune = findViewById(R.id.spnCommune);
        phonenumber = AppHelper.AppCheck.getInstance(PaymentActivity.this).getLocalStorageManager().getUserPhoneNumber();

        //Lấy thông tin giở hàng
        Cart cart = new Cart(phonenumber);
        ApiController.ApiService.getService(PaymentActivity.this).get_cart_by_phonenumber(cart).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.getResponseCode() == 1) {

                        for (int i = 0; i < cartResponse.getData().size(); i++) {
                            total = total + (Integer.parseInt(cartResponse.getData().get(i).getQuantity()) * Float.parseFloat(cartResponse.getData().get(i).getPrice()));
                        }
                        tvTotalOrder.setText(NumberFormat.getInstance().format(total) + " d");
                        tvTotalPrice.setText(NumberFormat.getInstance().format(total) + " d");
                        tvTamTinh.setText(NumberFormat.getInstance().format(total) + " d");
                        PreOrderAdapter adapter = new PreOrderAdapter(cartResponse.getData(), PaymentActivity.this);
                        rcvPayment.setAdapter(adapter);
                        rcvPayment.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));
                        adapter.notifyDataSetChanged();


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
        cbAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbAddress.isChecked()){
                    lllAddress.setVisibility(View.GONE);
                    lllAddressDefault.setVisibility(View.VISIBLE);
                    AddressModel addressModel = new AddressModel(phonenumber);
                    ApiController.ApiService.getService(PaymentActivity.this).get_all_address(addressModel)
                            .enqueue(new Callback<AddressResponse>() {
                                @Override
                                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                                    if (response.isSuccessful()){
                                        AddressModel addressModel1 = response.body().getData().get(0);
                                        edtLocation.setText(addressModel1.getLocation());
                                        tvCommune.setText(addressModel1.getCommune());
                                        tvDistrict.setText(addressModel1.getDistrict());
                                        tvProvince.setText(addressModel1.getProvince());
                                    }
                                    Log.d("====>",""+response.code());
                                }

                                @Override
                                public void onFailure(Call<AddressResponse> call, Throwable t) {
                                    Log.d("====>",""+t);

                                }
                            });
                }else {
                    lllAddress.setVisibility(View.VISIBLE);
                    lllAddressDefault.setVisibility(View.GONE);
                }
            }
        });
        //lấy thông tin tỉnh thành
        provinceList = new ArrayList<>();
        districtList = new ArrayList<>();
        communeList = new ArrayList<>();
        Call<ProvinceResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AddressApi.class)
                .getProvince();
        call.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if (response.code() == 200) {
                    ProvinceResponse provinceResponse = response.body();
                    provinceList.add(new Province("","Chọn tỉnh/thành phố"));
                    districtList.add(new District("","Chọn quận/huyện",""));
                    communeList.add(new Commune("","Chọn phường/xã","",""));

                    for (int i = 0; i < provinceResponse.getResults().size(); i++) {
                        provinceList.add(provinceResponse.getResults().get(i));
                    }
                    districtSpinnerAdapter = new DistrictSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, districtList);
                    spnDistrict.setAdapter(districtSpinnerAdapter);
                    communeSpinnerAdapter = new CommuneSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, communeList);
                    spnCommune.setAdapter(communeSpinnerAdapter);
                    provinceSpinnerAdapter = new ProvinceSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, provinceList);
                    spnProvince.setAdapter(provinceSpinnerAdapter);

                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
            }
        });

        //lấy thông tin quận/huyện
        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!provinceSpinnerAdapter.getItem(position).getCode().isEmpty()){
                    Call<DistrictResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(AddressApi.class)
                            .getDistrict(provinceSpinnerAdapter.getItem(position).getCode());
                    call.enqueue(new Callback<DistrictResponse>() {
                        @Override
                        public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                            if (response.code() == 200) {
                                districtList.clear();
                                districtList.add(new District("","Chọn quận/huyện",""));
                                DistrictResponse provinceResponse = response.body();
                                for (int i = 0; i < provinceResponse.getResults().size(); i++) {
                                    districtList.add(provinceResponse.getResults().get(i));
                                }
                                districtSpinnerAdapter = new DistrictSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, districtList);
                                spnDistrict.setAdapter(districtSpinnerAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<DistrictResponse> call, Throwable t) {
                        }
                    });
                }else {
                    districtList.clear();
                    districtList.add(new District("","Chọn quận/huyện",""));
                    districtSpinnerAdapter = new DistrictSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, districtList);
                    spnDistrict.setAdapter(districtSpinnerAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //lấy thông tin phường xã
        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!districtSpinnerAdapter.getItem(position).getCode().isEmpty()){
                    Call<CommuneResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(AddressApi.class)
                            .getCommune(districtSpinnerAdapter.getItem(position).getCode());
                    call.enqueue(new Callback<CommuneResponse>() {
                        @Override
                        public void onResponse(Call<CommuneResponse> call, Response<CommuneResponse> response) {
                            if (response.code() == 200) {
                                CommuneResponse communeResponse = response.body();
                                communeList.clear();
                                communeList.add(new Commune("","Chọn phường/xã","",""));
                                for (int i = 0; i < communeResponse.getResults().size(); i++) {
                                    communeList.add(communeResponse.getResults().get(i));
                                }
                                communeSpinnerAdapter = new CommuneSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, communeList);
                                spnCommune.setAdapter(communeSpinnerAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<CommuneResponse> call, Throwable t) {
                        }
                    });
                }else {
                    communeList.clear();
                    communeList.add(new Commune("","Chọn phường/xã","",""));
                    communeSpinnerAdapter = new CommuneSpinnerAdapter(PaymentActivity.this, R.layout.layout_address_selected_spinner, communeList);
                    spnCommune.setAdapter(communeSpinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] arTT = {"Thanh Toan Khi Giao Hang"};

        spnPayment.setAdapter(new ArrayAdapter(PaymentActivity.this, android.R.layout.simple_spinner_dropdown_item,arTT));



    }

    public interface AddressApi {
        @GET("province")
        Call<ProvinceResponse> getProvince();
        @GET("district")
        Call<DistrictResponse> getDistrict(@Query("province") String province);
        @GET("commune")
        Call<CommuneResponse> getCommune(@Query("district") String district);
    }
}

