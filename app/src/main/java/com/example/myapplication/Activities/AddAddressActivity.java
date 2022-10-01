package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Adapter.CommuneSpinnerAdapter;
import com.example.myapplication.Adapter.DistrictSpinnerAdapter;
import com.example.myapplication.Adapter.ProvinceSpinnerAdapter;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.Models.AddressDetail.Commune;
import com.example.myapplication.Models.AddressDetail.District;
import com.example.myapplication.Models.AddressDetail.Province;
import com.example.myapplication.Models.Cart;
import com.example.myapplication.Models.ModelResponse.AddressResponse;
import com.example.myapplication.Models.ModelResponse.CommuneResponse;
import com.example.myapplication.Models.ModelResponse.DistrictResponse;
import com.example.myapplication.Models.ModelResponse.ProvinceResponse;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class AddAddressActivity extends AppCompatActivity {
    EditText edtAddAddress;
    Spinner spnAddProvince, spnAddDistrict, spnAddCommune;
    Button btnAddAddress;
    private List<Province> provinceList;
    private List<District> districtList;
    private List<Commune> communeList;
    ProvinceSpinnerAdapter provinceSpinnerAdapter;
    DistrictSpinnerAdapter districtSpinnerAdapter;
    CommuneSpinnerAdapter communeSpinnerAdapter;
    String province = null;
    String district = null;
    String commune = null;
    String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        spnAddProvince = findViewById(R.id.spnAddProvince);
        spnAddDistrict = findViewById(R.id.spnAddDistrict);
        spnAddCommune = findViewById(R.id.spnAddCommune);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        edtAddAddress = findViewById(R.id.edtAddAddress);
        phonenumber = AppHelper.AppCheck.getInstance(AddAddressActivity.this).getLocalStorageManager().getUserPhoneNumber();

        provinceList = new ArrayList<>();
        districtList = new ArrayList<>();
        communeList = new ArrayList<>();
        Call<ProvinceResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PaymentActivity.AddressApi.class)
                .getProvince();
        call.enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if (response.code() == 200) {
                    ProvinceResponse provinceResponse = response.body();
                    provinceList.add(new Province("", "Chọn tỉnh/thành phố"));
                    districtList.add(new District("", "Chọn quận/huyện", ""));
                    communeList.add(new Commune("", "Chọn phường/xã", "", ""));

                    for (int i = 0; i < provinceResponse.getResults().size(); i++) {
                        provinceList.add(provinceResponse.getResults().get(i));
                    }
                    districtSpinnerAdapter = new DistrictSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, districtList);
                    spnAddDistrict.setAdapter(districtSpinnerAdapter);
                    communeSpinnerAdapter = new CommuneSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, communeList);
                    spnAddCommune.setAdapter(communeSpinnerAdapter);
                    provinceSpinnerAdapter = new ProvinceSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, provinceList);
                    spnAddProvince.setAdapter(provinceSpinnerAdapter);

                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
            }
        });
        //lấy thông tin quận/huyện
        spnAddProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = provinceSpinnerAdapter.getItem(position).getName();
                if (!provinceSpinnerAdapter.getItem(position).getCode().isEmpty()) {
                    Call<DistrictResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(PaymentActivity.AddressApi.class)
                            .getDistrict(provinceSpinnerAdapter.getItem(position).getCode());
                    call.enqueue(new Callback<DistrictResponse>() {
                        @Override
                        public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                            if (response.code() == 200) {
                                districtList.clear();
                                districtList.add(new District("", "Chọn quận/huyện", ""));
                                DistrictResponse provinceResponse = response.body();
                                for (int i = 0; i < provinceResponse.getResults().size(); i++) {
                                    districtList.add(provinceResponse.getResults().get(i));
                                }
                                districtSpinnerAdapter = new DistrictSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, districtList);
                                spnAddDistrict.setAdapter(districtSpinnerAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<DistrictResponse> call, Throwable t) {
                        }
                    });
                } else {
                    districtList.clear();
                    districtList.add(new District("", "Chọn quận/huyện", ""));
                    districtSpinnerAdapter = new DistrictSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, districtList);
                    spnAddDistrict.setAdapter(districtSpinnerAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //lấy thông tin phường xã
        spnAddDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district = districtSpinnerAdapter.getItem(position).getName();
                if (!districtSpinnerAdapter.getItem(position).getCode().isEmpty()) {
                    Call<CommuneResponse> call = new Retrofit.Builder().baseUrl("https://api.mysupership.vn/v1/partner/areas/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(PaymentActivity.AddressApi.class)
                            .getCommune(districtSpinnerAdapter.getItem(position).getCode());
                    call.enqueue(new Callback<CommuneResponse>() {
                        @Override
                        public void onResponse(Call<CommuneResponse> call, Response<CommuneResponse> response) {
                            if (response.code() == 200) {
                                CommuneResponse communeResponse = response.body();
                                communeList.clear();
                                communeList.add(new Commune("", "Chọn phường/xã", "", ""));
                                for (int i = 0; i < communeResponse.getResults().size(); i++) {
                                    communeList.add(communeResponse.getResults().get(i));
                                }
                                communeSpinnerAdapter = new CommuneSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, communeList);
                                spnAddCommune.setAdapter(communeSpinnerAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<CommuneResponse> call, Throwable t) {
                        }
                    });
                } else {
                    communeList.clear();
                    communeList.add(new Commune("", "Chọn phường/xã", "", ""));
                    communeSpinnerAdapter = new CommuneSpinnerAdapter(AddAddressActivity.this, R.layout.layout_address_selected_spinner, communeList);
                    spnAddCommune.setAdapter(communeSpinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnAddCommune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                commune = communeSpinnerAdapter.getItem(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAddAddress.getText().toString().isEmpty()
                        || province.equals(provinceSpinnerAdapter.getItem(0).getName())
                        || district.equals(districtSpinnerAdapter.getItem(0).getName())
                        || commune.equals(communeSpinnerAdapter.getItem(0).getName())
                ) {
                    Toast.makeText(AddAddressActivity.this, "Vui long nhap du thong tin", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("===========>", "" + edtAddAddress.getText().toString() + commune + district + province);
                    Cart asd = new Cart("afsasf");
                    AddressModel addressModel = new AddressModel(null, edtAddAddress.getText().toString(), commune, district, province, phonenumber, "0");
                    ApiController.ApiService.getService(AddAddressActivity.this).insert_address(addressModel).enqueue(new Callback<AddressResponse>() {
                        @Override
                        public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                            if (response.isSuccessful()) {
                                startActivity(new Intent(AddAddressActivity.this, AddressActivity.class));
                                finish();
                            }
                            Log.d("======>",""+response.code());
                        }

                        @Override
                        public void onFailure(Call<AddressResponse> call, Throwable t) {
                            Log.d("===========>", t+"");
                        }
                    });
                }
            }
        });
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