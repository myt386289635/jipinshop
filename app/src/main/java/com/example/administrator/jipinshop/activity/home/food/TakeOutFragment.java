package com.example.administrator.jipinshop.activity.home.food;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.newGift.NewGiftActivity;
import com.example.administrator.jipinshop.adapter.TakeOutAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.databinding.FragmentSreachgoodsBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/3/10
 * @Describe 外卖集合
 */
public class TakeOutFragment extends DBBaseFragment implements OnRefreshListener, TakeOutView{

    @Inject
    TakeOutPresenter mPresenter;

    private FragmentSreachgoodsBinding mBinding;
    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private TakeOutAdapter mAdapter;
    private Boolean[] once = {true};//记录第一次进入页面标示

    public static TakeOutFragment getInstance(){
        TakeOutFragment fragment = new TakeOutFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sreachgoods,container,false);
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_F5F5F5));

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new TakeOutAdapter(mList,getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        if (getActivity() instanceof NewGiftActivity){
            mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,
                    ((NewGiftActivity)getActivity()).getBar(),once);
        }else {
            mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        }
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onRefresh() {
        mPresenter.adList(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(SucBean<EvaluationTabBean.DataBean.AdListBean> bean) {
        dissRefresh();
        if (bean.getData() != null && bean.getData().size() != 0){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_news, "暂无数据", "还没有任何数据哦，先休息一下吧");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        once[0] = false;
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show(error);
        once[0] = false;
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }
}
