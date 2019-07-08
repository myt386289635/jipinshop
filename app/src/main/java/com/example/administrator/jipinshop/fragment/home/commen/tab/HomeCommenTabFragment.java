package com.example.administrator.jipinshop.fragment.home.commen.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeCommenTabAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ChildrenTabBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.gridview.MyGridView;
import com.example.administrator.jipinshop.view.viewpager.AutoHeightViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 莫小婷
 * @create 2019/2/25
 * @Describe
 */
public class HomeCommenTabFragment extends DBBaseFragment implements HomeCommenTabAdapter.OnItem {

    @BindView(R.id.grid_view)
    MyGridView mGridView;
    private Unbinder unbinder;

    private int position;//记录这是第几个fragment; 从0开始
    private int set;//记录一级菜单的位置。从1开始
    private TabBean mTabBean;
    private HomeCommenTabAdapter mTabAdapter;
    private List<ChildrenTabBean> mChildrenBeans;

    public static HomeCommenTabFragment getInstance(int set, int pos) {
        HomeCommenTabFragment fragment = new HomeCommenTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("set", set);
        bundle.putInt("fset", pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        position = getArguments().getInt("set", 0);
        set = getArguments().getInt("fset", 1);
        AutoHeightViewPager.setViewPosition(rootView, set, position);
        return rootView;
    }

    @Override
    public void initView() {
        mTabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab), TabBean.class);

        mChildrenBeans = new ArrayList<>();
        initDate();
        mTabAdapter = new HomeCommenTabAdapter(mChildrenBeans, getContext());
        mTabAdapter.setOnItem(this);
        mGridView.setAdapter(mTabAdapter);
    }

    private void initDate() {
        int mun = (position * 10) + 10;
        if (mTabBean.getData().get(set).getChildren().size() >= mun) {
            mun = (position * 10) + 10;
        } else {
            mun = mTabBean.getData().get(set).getChildren().size();
        }

        for (int i = position * 10; i < mun; i++) {
            mChildrenBeans.add(new ChildrenTabBean(mTabBean.getData().get(set).getChildren().get(i).getCategoryName(), false,
                    mTabBean.getData().get(set).getChildren().get(i).getCategoryId(), mTabBean.getData().get(set).getChildren().get(i).getImg()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 点击
     */
    @Override
    public void onItem(int pos) {
        ((HomeNewFragment) (HomeCommenTabFragment.this.getParentFragment())).onItemTab(mChildrenBeans.get(pos).getCategoryid(), mChildrenBeans.get(pos).getName());
        //用户行为统计
        UAppUtil.twoTab(getContext(), mTabBean.getData().get(set).getCategoryName(), mChildrenBeans.get(pos).getName());
    }
}
