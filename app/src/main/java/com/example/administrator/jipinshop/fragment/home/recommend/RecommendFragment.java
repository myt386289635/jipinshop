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
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.RecommendFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.FragmentRecommendBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

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
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))){
            mPresenter.getDate(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }else {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            binding.recyclerView.setVisibility(View.GONE);
//            Toast.makeText(getContext(), "网络请求错误,请重新开启app", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.getList().get(pos).getGoodsId())
                    .putExtra("goodsName",mList.getList().get(pos).getGoodsName())
                    .putExtra("priceNow",mList.getList().get(pos).getActualPrice())
                    .putExtra("priceOld",mList.getList().get(pos).getOtherPrice())
                    .putExtra("price",mList.getList().get(pos).getCutPrice())
                    .putExtra("state",mList.getList().get(pos).getSourceStatus() + "")
            );
        }

    }

    @Override
    public void onSuccess(RecommendFragmentBean recommendFragmentBean) {
        if (recommendFragmentBean.getCode() == 200) {
            if(recommendFragmentBean.getList() != null && recommendFragmentBean.getList().size() != 0){
                binding.inClude.qsNet.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                mList = recommendFragmentBean;
                mAdapter.setList(recommendFragmentBean);
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
                binding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.RecommendFragmentDATA,""))){
                initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
                binding.recyclerView.setVisibility(View.GONE);
            }
            Toast.makeText(getContext(), recommendFragmentBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
        stopResher();
    }

    @Override
    public void onFile(String error) {
        stopResher();
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.RecommendFragmentDATA,""))){
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            binding.recyclerView.setVisibility(View.GONE);
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

    public void stopResher(){
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
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initSubTab(String string) {
        if (string.equals(HomeFragment.subTab)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.SubTab, ""))) {
                binding.swipeToLoad.post(() -> binding.swipeToLoad.setRefreshing(true));
            }
        }
    }
}
