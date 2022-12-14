package com.example.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Fragments.AccountFragment;
import com.example.myapplication.Fragments.CartFragment;
import com.example.myapplication.Fragments.FavoriteFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.ShopFragment;
import com.example.myapplication.Fragments.HomeFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ShopFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
