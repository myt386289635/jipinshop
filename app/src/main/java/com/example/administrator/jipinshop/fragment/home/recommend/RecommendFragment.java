package com.example.administrator.jipinshop.fragment.home.recommend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.RecommendFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.FragmentRecommendBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * 推荐榜
 */
public class RecommendFragment extends DBBaseFragment implements OnRefreshListener, RecommendFragmentAdapter.OnItem, RecommendFragmentView {

    @Inject
    RecommendFragmentPresenter mPresenter;

    protected FragmentRecommendBinding binding;
    private RecommendFragmentAdapter mAdapter;
    private RecommendFragmentBean mList;

    public static RecommendFragment getInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater,ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recommend,container,false);
        return  binding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        binding.recyclerView.setLayoutManager(layoutManager);
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.RecommendFragmentDATA,""))){
            mList = new RecommendFragmentBean();
        }else {
            mList = new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.RecommendFragmentDATA,""),RecommendFragmentBean.class);
        }
        mAdapter = new RecommendFragmentAdapter(mList, getContext());
        mAdapter.setOnItem(this);
        binding.recyclerView.setAdapter(mAdapter);

        binding.recyclerView.setFocusable(false);
        binding.swipeToLoad.setOnRefreshListener(this);

        mPresenter.solveScoll(binding.recyclerView,binding.swipeToLoad);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.getDate(this.<RecommendFragmentBean>bindToLifecycle());
    }

    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick()) {
            return;
        }else{
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("pos",pos)
            );
        }

    }

    @Override
    public void onSuccess(RecommendFragmentBean recommendFragmentBean) {
        if (recommendFragmentBean.getCode() == 200) {
            binding.inClude.qsNet.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            mAdapter.setList(recommendFragmentBean);
            mAdapter.notifyDataSetChanged();
        } else {
            initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
            binding.recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), recommendFragmentBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (binding.swipeToLoad != null && binding.swipeToLoad.isRefreshing()) {
            if(!binding.swipeToLoad.isRefreshEnabled()){
                binding.swipeToLoad.setRefreshEnabled(true);
                binding.swipeToLoad.setRefreshing(false);
                binding.swipeToLoad.setRefreshEnabled(false);
            }else {
                binding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    @Override
    public void onFile(String error) {
        if (binding.swipeToLoad != null && binding.swipeToLoad.isRefreshing()) {
            if(!binding.swipeToLoad.isRefreshEnabled()){
                binding.swipeToLoad.setRefreshEnabled(true);
                binding.swipeToLoad.setRefreshing(false);
                binding.swipeToLoad.setRefreshEnabled(false);
            }else {
                binding.swipeToLoad.setRefreshing(false);
            }
        }
        Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
        Log.d("RecommendFragmentPresen", error);
    }

    public void initError(int id, String title, String content) {
        binding.inClude.qsNet.setVisibility(View.VISIBLE);
        binding.inClude.errorImage.setBackgroundResource(id);
        binding.inClude.errorTitle.setText(title);
        binding.inClude.errorContent.setText(content);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initSubTab(String string) {
        if (string.equals(HomeFragment.subTab)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
                binding.swipeToLoad.setRefreshing(true);
            }
        }
    }
}
