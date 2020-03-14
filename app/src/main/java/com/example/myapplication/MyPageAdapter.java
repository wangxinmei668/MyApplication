package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {

    public MyPageAdapter(FragmentManager fm){
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return new FirrstFragment();
        }else if(position==1){
            return new SecondFragment();

        }else {
            return new ThirdFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
