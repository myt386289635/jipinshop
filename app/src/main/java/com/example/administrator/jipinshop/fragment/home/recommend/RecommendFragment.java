package com.example.administrator.jipinshop.fragment.home.recommend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.RecommendFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.FragmentRecommendBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 推荐榜
 */
public class RecommendFragment extends DBBaseFragment implements OnRefreshListener, RecommendFragmentAdapter.OnItem, RecommendFragmentView {

    @Inject
    RecommendFragmentPresenter mPresenter;

    protected FragmentRecommendBinding binding;
    private RecommendFragmentAdapter mAdapter;
    private List<RecommendFragmentBean.DataBean> mList;

    private Boolean[] once = {true};

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        binding.recyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        mAdapter = new RecommendFragmentAdapter(mList, getContext());
        mAdapter.setOnItem(this);
        binding.recyclerView.setAdapter(mAdapter);

        binding.recyclerView.setFocusable(false);
        binding.swipeToLoad.setOnRefreshListener(this);

        mPresenter.solveScoll(binding.recyclerView,binding.swipeToLoad,((HomeFragment)getParentFragment()).getBar(),once);
        binding.swipeToLoad.post(() -> binding.swipeToLoad.setRefreshing(true));
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.getDate(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(), ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onSuccess(RecommendFragmentBean recommendFragmentBean) {
        if (recommendFragmentBean.getCode() == 0) {
            if(recommendFragmentBean.getData() != null && recommendFragmentBean.getData().size() != 0){
                mList.clear();
                binding.inClude.qsNet.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                mList.addAll(recommendFragmentBean.getData());
                if (recommendFragmentBean.getAdList().size() != 0) {
                    mAdapter.setHeadImage(recommendFragmentBean.getAdList().get(0).getImg());
                }
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                binding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            binding.recyclerView.setVisibility(View.GONE);
        }
        stopResher();
        if(once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        binding.recyclerView.setVisibility(View.GONE);
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
}
