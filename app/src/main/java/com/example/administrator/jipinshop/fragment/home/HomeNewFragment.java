package com.example.administrator.jipinshop.fragment.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.home.newarea.NewAreaActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.adapter.HomeNewAdapter;
import com.example.administrator.jipinshop.adapter.HomePageAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TitleBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.FragmentHome2Binding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/27
 * @Describe  注意消息在这里被拿掉了。要么在这里请求消息数据，要么在我的页面请求
 */
public class HomeNewFragment extends DBBaseFragment implements HomeNewView, OnLoadMoreListener, HomeNewAdapter.OnClickItem, View.OnClickListener {

    @Inject
    HomeNewPresenter mPresenter;
    private FragmentHome2Binding mBinding;

    private List<TitleBean> mTabBeans;
    private List<TopCategorysListBean.DataBean> mList;
    private HomeNewAdapter mAdapter;
    private HomePageAdapter mPagerAdapter;
    private int page = 1;
    private Boolean once = true;
    private String image = "";
    private String id = "";

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home2,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setStatusBarHight(mBinding.toolBar,mBinding.homeSreach,getContext());
        mPresenter.setView(this);

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mTabBeans = new ArrayList<>();
        mList = new ArrayList<>();
        mPagerAdapter = new HomePageAdapter(getChildFragmentManager());
        mAdapter = new HomeNewAdapter(getContext());
        mAdapter.setTabBeans(mTabBeans);
        mAdapter.setList(mList);
        mAdapter.setPagerAdapter(mPagerAdapter);
        mAdapter.setPresenter(mPresenter);
        mAdapter.setOnClickItem(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.goodsCategory(this.bindToLifecycle());
        mPresenter.unMessage(this.bindToLifecycle());
        mPresenter.getTopCategorysList(page,this.bindToLifecycle());
    }


    @Override
    public void Success(TabBean tabBean) {
        SPUtils.getInstance().put(CommonDate.SubTab,new Gson().toJson(tabBean));
        if (tabBean.getData() != null && tabBean.getData().size() != 0) {
            for (int i = 0; i < tabBean.getData().size(); i++) {
                if (i == 0) {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(), true));
                } else {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(), false));
                }
            }
        }
        mAdapter.setTabBean(tabBean);
        mAdapter.notifyDataSetChanged();
        UAppUtil.oneTab(getContext(),mTabBeans.get(0).getString());
    }

    @Override
    public void Faile(String error) {
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab,""))){
            TabBean tabBean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.SubTab),TabBean.class);
            for (int i = 0; i < tabBean.getData().size(); i++) {
                if(i == 0){
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),true));
                }else {
                    mTabBeans.add(new TitleBean(tabBean.getData().get(i).getCategoryName(),false));
                }
            }
            mAdapter.setTabBean(tabBean);
            mAdapter.notifyDataSetChanged();
            UAppUtil.oneTab(getContext(),mTabBeans.get(0).getString());
        }
        ToastUtil.show(error);
    }

    @Override
    public void unMessageSuc(UnMessageBean unMessageBean) {
        if(unMessageBean.getData() != 0) {
            String nuMessage = "0";
            if (unMessageBean.getData() <= 99) {
                nuMessage = unMessageBean.getData() + "";
            } else {
                nuMessage = "99+";
            }
            EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,nuMessage));
        }else {
            EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,"0"));
        }
    }

    @Override
    public void unMessageFaile(String error) {
        ToastUtil.show(error);
        EventBus.getDefault().post(new EditNameBus(HomeFragment.MsgRefersh,"0"));
    }

    @Override
    public void SuccessList(TopCategorysListBean bean) {
        stopLoading();
        if (bean.getData().size() != 0){
            if (once){
                mList.clear();
                mAdapter.setNewShopImage(bean.getAd().getImg());
                image = bean.getAd().getImg();
                id = bean.getAd().getObjectId();
            }
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            if (once){
                ToastUtil.show("暂无数据");
            }else {
                ToastUtil.show("已经是最后一页了");
                page--;
            }
        }
        if (once){
            once = false;
        }
    }

    @Override
    public void FaileList(String error) {
        stopLoading();
        ToastUtil.show(error);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
    }

    /**
     * 二级菜单点击 跳转到分类榜单列表
     */
    public void onItemTab(String category2Id,String category2Name){
        startActivity(new Intent(getContext(), ClassifyActivity.class)
                .putExtra("title",category2Name + "榜单")
                .putExtra("id",category2Id)
        );
    }

    @Override
    public void onLoadMore() {
        page++;
        mPresenter.getTopCategorysList(page,this.bindToLifecycle());
    }

    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    /**
     * 跳转到新品专区
     */
    @Override
    public void onClickNewShop() {
        startActivity(new Intent(getContext(), NewAreaActivity.class)
                .putExtra("image",image)
                .putExtra("id",id)
        );
    }

    /**
     * 跳转到分类榜单列表
     */
    @Override
    public void onClickNewDetail(int position) {
        startActivity(new Intent(getContext(), ClassifyActivity.class)
                .putExtra("title",mList.get(position).getCategoryName() + "榜单")
                .putExtra("id", mList.get(position).getCategoryId())
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_sreach:
                startActivity(new Intent(getContext(), SreachActivity.class));
                UAppUtil.message(getContext(),1);
                break;
        }
    }
}
