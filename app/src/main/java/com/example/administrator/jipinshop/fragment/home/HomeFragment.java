package com.example.administrator.jipinshop.fragment.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.system.SystemMessageActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.databinding.FragmentHomeBinding;
import com.example.administrator.jipinshop.fragment.home.electricity.ElectricityFragment;
import com.example.administrator.jipinshop.fragment.home.health.HealthFragment;
import com.example.administrator.jipinshop.fragment.home.household.HouseholdFragment;
import com.example.administrator.jipinshop.fragment.home.kitchen.KitchenFragment;
import com.example.administrator.jipinshop.fragment.home.recommend.RecommendFragment;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragment extends DBBaseFragment implements Badge.OnDragStateChangedListener, View.OnClickListener, HomeFragmentView {

    public static final String subTab = "HomeFragment2subFragments";

    @Inject
    HomeFragmentPresenter mHomeFragmentPresenter;

    private FragmentHomeBinding mBinding;

    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    private QBadgeView mQBadgeView;
    private List<String> tabList;
    private List<TextView> tabTextView;

    public HomeFragment() {
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mHomeFragmentPresenter.setStatusBarHight(mBinding.statusBar, getContext());
        mHomeFragmentPresenter.setView(this);
        EventBus.getDefault().register(this);

        tabList = new ArrayList<>();
        tabTextView = new ArrayList<>();

        mFragments = new ArrayList<>();
        mFragments.add(RecommendFragment.getInstance());
        mFragments.add(HealthFragment.getInstance());
        mFragments.add(KitchenFragment.getInstance());
        mFragments.add(HouseholdFragment.getInstance());
        mFragments.add(ElectricityFragment.getInstance());

        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(4);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

        mQBadgeView = new QBadgeView(getContext());
        mHomeFragmentPresenter.initBadgeView(mQBadgeView, mBinding.homeMessage, this);

        tabList.add(SPUtils.getInstance().getString(CommonDate.HomeTab1, "推荐榜"));
        tabList.add(SPUtils.getInstance().getString(CommonDate.HomeTab2, "个护健康"));
        tabList.add(SPUtils.getInstance().getString(CommonDate.HomeTab3, "厨卫电器"));
        tabList.add(SPUtils.getInstance().getString(CommonDate.HomeTab4, "生活家电"));
        tabList.add(SPUtils.getInstance().getString(CommonDate.HomeTab5, "家用大电"));
        initTab(null);

        mHomeFragmentPresenter.goodsCategory(this.bindToLifecycle());
        mHomeFragmentPresenter.unMessage(this.bindToLifecycle());
    }


    @Override
    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
    }


    public void initTab(TabBean tabBean) {
        if (tabBean != null) {
            if(tabBean.getList() != null && tabBean.getList().size() != 0){
                tabList.clear();
                tabList.add(tabBean.getList().get(0).getTilte().getCategoryname());
                tabList.add(tabBean.getList().get(1).getTilte().getCategoryname());
                tabList.add(tabBean.getList().get(2).getTilte().getCategoryname());
                tabList.add(tabBean.getList().get(3).getTilte().getCategoryname());
                tabList.add(tabBean.getList().get(4).getTilte().getCategoryname());
                SPUtils.getInstance().put(CommonDate.HomeTab1, tabList.get(0));
                SPUtils.getInstance().put(CommonDate.HomeTab2, tabList.get(1));
                SPUtils.getInstance().put(CommonDate.HomeTab3, tabList.get(2));
                SPUtils.getInstance().put(CommonDate.HomeTab4, tabList.get(3));
                SPUtils.getInstance().put(CommonDate.HomeTab5, tabList.get(4));
                mHomeFragmentPresenter.initTabLayout(getContext(), mBinding.tabLayout, tabList, tabTextView);
            }else {
                mHomeFragmentPresenter.initTabLayout(getContext(), mBinding.tabLayout, tabList, tabTextView);
            }
        } else {
            mHomeFragmentPresenter.initTabLayout(getContext(), mBinding.tabLayout, tabList, tabTextView);
        }
    }

    @Override
    public void onClick(View view) {
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.home_sreach:
                startActivity(new Intent(getContext(), SreachActivity.class));
                break;
            case R.id.home_message:
//                startActivity(new Intent(getContext(), MessageActivity.class));
                startActivity(new Intent(getContext(), SystemMessageActivity.class));
                break;
        }
    }

    /**
     * 获取tab成功
     * @param tabBean
     */
    @Override
    public void Success(TabBean tabBean) {
        SPUtils.getInstance().put(CommonDate.SubTab,new Gson().toJson(tabBean));
        EventBus.getDefault().post(HomeFragment.subTab);
        initTab(tabBean);
    }

    /**
     * 获取tab成功
     */
    @Override
    public void Faile(String error) {
        EventBus.getDefault().post(HomeFragment.subTab);//通知榜单里的4个fragment初始化二级导航栏
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取未读消息成功回调
     * @param unMessageBean
     */
    @Override
    public void unMessageSuc(UnMessageBean unMessageBean) {
        if(unMessageBean.getCount() != 0) {
            if (unMessageBean.getCount() <= 99) {
                mQBadgeView.setBadgeText("" + unMessageBean.getCount());
            } else {
                mQBadgeView.setBadgeText("99+");
            }
        }else { 
            mQBadgeView.hide(false);
        }
    }

    /**
     * 获取未读消息成功回调
     */
    @Override
    public void unMessageFaile(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public  void  unMessage(String s){
        if(!TextUtils.isEmpty(s) && s.equals(JPushReceiver.TAG)){
            mHomeFragmentPresenter.unMessage(this.bindToLifecycle());
        }
    }
}
