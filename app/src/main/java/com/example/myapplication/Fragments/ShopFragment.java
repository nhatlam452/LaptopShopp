package com.example.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.FilterActivity;
import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class ShopFragment extends Fragment {
    TextView tvFilter, tvSort;
    RelativeLayout layoutBottomSheetSort;
    RecyclerView rcvItemShop;
    SearchView searchView;


    private BottomSheetBehavior bottomSheetBehavior;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }

    private void initUI(View view) {

        layoutBottomSheetSort = view.findViewById(R.id.layoutSortItem);
        searchView = view.findViewById(R.id.searchView);
        rcvItemShop = view.findViewById(R.id.rcvItemShop);
        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheetSort);
        searchView.clearFocus();
        ApiController.ApiService.getService(getContext()).get_all_product().enqueue(ApiCallback.getAllProduct(getContext(),rcvItemShop,searchView));

    }

    private void onClickOpenSortDialog() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        return view;
    }


}