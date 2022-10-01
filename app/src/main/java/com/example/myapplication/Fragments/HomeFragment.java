package com.example.myapplication.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Activities.LoginActivity;
import com.example.myapplication.Adapter.HomeProductAdapter;
import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.HomeProduct;
import com.example.myapplication.Models.ModelResponse.ProductResponse;
import com.example.myapplication.Models.Products;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    List<Products> mListBackToSchool;
    List<Products> mListSales;
    List<Products> mListNew;
    List<HomeProduct> homeProductList;
    List<List<HomeProduct>> mList;
    private boolean isExpanded;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniUI(view);
        initToolbar();
        initToolbarAnimation();
    }

    private void iniUI(View view) {
        appBarLayout=view.findViewById(R.id.AppBarLayout);
        collapsingToolbarLayout=view.findViewById(R.id.CollapsingToolbarLayout);
        toolbar=view.findViewById(R.id.toolbarhome);
        recyclerView=view.findViewById(R.id.rcv_home);
        mListBackToSchool = new ArrayList<>();
        mListSales = new ArrayList<>();
        mListNew = new ArrayList<>();
        homeProductList = new ArrayList<>();
        mList = new ArrayList<>();
        ApiController.ApiService.getService(getContext()).get_all_product().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();
                for (int i = 0; i < productResponse.getData().size(); i++) {
                    if (Double.parseDouble(productResponse.getData().get(i).getPrice()) < 30000000){
                        mListBackToSchool.add(productResponse.getData().get(i));
                    }
                    if (productResponse.getData().get(i).getState().equals(1)){
                        mListSales.add(productResponse.getData().get(i));
                    }
                }
                for (int i =productResponse.getData().size() -1; i >= (productResponse.getData().size()-10) ; i-- ){
                    mListNew.add(productResponse.getData().get(i));
                }

                homeProductList.add(new HomeProduct("San Pham Moi", mListNew));
                mList.add(homeProductList);
                homeProductList.add(new HomeProduct("Dang Khuyen Mai", mListSales));
                mList.add(homeProductList);
                homeProductList.add(new HomeProduct("Back To School", mListBackToSchool));
                mList.add(homeProductList);
                Log.d("====>",mList.size()+"");
                HomeProductAdapter adapter = new HomeProductAdapter(mList, getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("========>", t.getMessage() + "");

            }
        });    }
    private void initToolbar(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.setSupportActionBar(toolbar);
        if(appCompatActivity.getSupportActionBar() != null){
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }
    private void initToolbarAnimation(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.laptopbannermain);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor= palette.getLightVibrantColor(getResources().getColor(R.color.color_selected_bottom_sheet_dialog));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.black_trans));
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200){
                    isExpanded = false;
                }else {
                    isExpanded = true;
                }
                appCompatActivity.invalidateOptionsMenu();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        if (item.getItemId() == R.id.icon_thongbao_menu){
        }
        return super.onOptionsItemSelected(item);
    }
}