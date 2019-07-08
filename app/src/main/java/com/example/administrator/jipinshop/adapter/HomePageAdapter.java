package com.example.administrator.jipinshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/27
 * @Describe 榜单首页的viewpager刷新
 */
public class HomePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private FragmentManager mFragmentManager;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position):null;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public void updateData(List<Fragment> fragmentTemp) {
        ArrayList<Fragment> fragmentsTemp = new ArrayList<>();
        fragmentsTemp.addAll(fragmentTemp);

        if(fragments != null && fragments.size() != 0){
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for(Fragment f : fragments){
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
        }
        fragments = fragmentsTemp;
        notifyDataSetChanged();
    }
}
