package com.example.administrator.jipinshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position):null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
