package com.example.administrator.jipinshop.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> title;

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public HomeFragmentAdapter(FragmentManager fm) {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null && title.size() != 0){
            return title.get(position);
        }else {
            return super.getPageTitle(position);
        }
    }
}
