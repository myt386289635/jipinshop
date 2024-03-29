package com.example.administrator.jipinshop.fragment.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.adapter.HomeTabAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TitleBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.FragmentHomeBinding;
import com.example.administrator.jipinshop.fragment.home.commen.HomeCommenFragment;
import com.example.administrator.jipinshop.fragment.home.recommend.RecommendFragment;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragment extends DBBaseFragment implements Badge.OnDragStateChangedListener, View.OnClickListener, HomeFragmentView, HomeTabAdapter.OnClickItem, ViewPager.OnPageChangeListener {

    public static final String subTab = "HomeFragment2subFragments";
    public static final String MsgRefersh = "MsgRefersh";

    @Inject
    HomeFragmentPresenter mHomeFragmentPresenter;

    private FragmentHomeBinding mBinding;

    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    private QBadgeView mQBadgeView;

    private List<TitleBean> mTabBeans;
    private HomeTabAdapter mTabAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int set = 0;// 记录上一个位置

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

        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);

        mQBadgeView = new QBadgeView(getContext());
        mHomeFragmentPresenter.initBadgeView(mQBadgeView, mBinding.homeMessage, this);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false);
        mBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
        mTabBeans = new ArrayList<>();
        mTabAdapter = new HomeTabAdapter(mTabBeans,getContext());
        mTabAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mTabAdapter);
        initTab();

        mHomeFragmentPresenter.goodsCategory(this.bindToLifecycle());
        mHomeFragmentPresenter.unMessage(this.bindToLifecycle());

        mBinding.viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
    }


    public void successTab(TabBean tabBean) {
        mTabBeans.clear();
        if (tabBean.getData() != null && tabBean.getData().size() != 0) {
            for (int i = 0; i < tabBean.getData().size(); i++) {
                if (i == 0) {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(), true));
                } else {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(), false));
                    mFragments.add(HomeCommenFragment.getInstance(i));
                }
            }
        }
        mTabAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
    }

    public void initTab(){
        mTabBeans.add(new TitleBean("总榜",true));
        mFragments.add(RecommendFragment.getInstance());
        mTabAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    public void errorTab(){
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab,""))){
            mTabBeans.clear();
            TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab),TabBean.class);
            for (int i = 0; i < tabBean.getData().size(); i++) {
                if(i == 0){
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),true));
                }else {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),false));
                    mFragments.add(HomeCommenFragment.getInstance(i));
                }
            }
            mTabAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
            mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_sreach:
                startActivity(new Intent(getContext(), SreachActivity.class));
                UAppUtil.message(getContext(),1);
                return;
        }
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.home_message:
                startActivity(new Intent(getContext(), MessageActivity.class));
                UAppUtil.message(getContext(),0);
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
        successTab(tabBean);
        EventBus.getDefault().post(HomeFragment.subTab);
        UAppUtil.oneTab(getContext(),mTabBeans.get(0).getString());
    }

    /**
     * 获取tab成功
     */
    @Override
    public void Faile(String error) {
        errorTab();
        EventBus.getDefault().post(HomeFragment.subTab);//通知榜单里的4个fragment初始化二级导航栏
        ToastUtil.show(error);
        UAppUtil.oneTab(getContext(),mTabBeans.get(0).getString());
    }

    /**
     * 获取未读消息成功回调
     * @param unMessageBean
     */
    @Override
    public void unMessageSuc(UnMessageBean unMessageBean) {
        if(unMessageBean.getData() != 0) {
            if (unMessageBean.getData() <= 99) {
                mQBadgeView.setBadgeText("" + unMessageBean.getData());
            } else {
                mQBadgeView.setBadgeText("99+");
            }
            EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,mQBadgeView.getBadgeText()));
        }else { 
            mQBadgeView.hide(false);
            EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,"0"));
        }
    }

    /**
     * 获取未读消息成功回调
     */
    @Override
    public void unMessageFaile(String error) {
        ToastUtil.show(error);
        EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,"0"));
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

    /**
     * tab 点击时的情况
     * @param pos
     */
    @Override
    public void onClickItem(int pos) {

        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((pos - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mBinding.recyclerView.getChildAt((pos - 1) - firstPosition).getLeft();
            mBinding.recyclerView.smoothScrollBy(des,0);
        }else if(pos - 1 >= 0 && (pos - 1) - firstPosition <= 0){
            //从右边点击到左边
            mBinding.recyclerView.smoothScrollToPosition(pos - 1);
        }
        mBinding.viewPager.setCurrentItem(pos,false);
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(pos).setTag(true);
        mTabAdapter.notifyDataSetChanged();
        set = pos;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int des = 0;
        if((i - 1) - firstPosition > 0){
            ////从左边到点击到右边
            des = mBinding.recyclerView.getChildAt((i - 1) - firstPosition).getLeft();
            mBinding.recyclerView.smoothScrollBy(des,0);
        }else if(i - 1 >= 0 && (i - 1) - firstPosition <= 0){
            //从右边点击到左边
            mBinding.recyclerView.smoothScrollToPosition(i - 1);
        }
        mTabBeans.get(set).setTag(false);
        mTabBeans.get(i).setTag(true);
        mTabAdapter.notifyDataSetChanged();

        set = i;

        UAppUtil.oneTab(getContext(),mTabBeans.get(i).getString());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

//    public AppBarLayout getBar(){
//        return mBinding.appbar;
//    }
}
