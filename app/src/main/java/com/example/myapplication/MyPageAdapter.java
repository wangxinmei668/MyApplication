package com.example.myapplication;

import android.util.Log;

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

        }else if(position==2){
            return new ThirdFragment();
        }else{
            return new FourFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position) {
        Log.i("pageAdapter", "getPageTitle:课程 ");
        if(position ==0){
            return "课程1";
        }else if(position==1){
            return "课程2";
        }else if(position==2){
            return "课程3";
        }else{
            return "课程4";
        }
    }
}
