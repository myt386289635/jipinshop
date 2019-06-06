package com.example.administrator.jipinshop.fragment.balance.withdraw;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.WithdrawDetailAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/3
 * @Describe 提现明细
 */
public class WithdrawDetailFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener {

    @Inject
    WithdrawDetailPresenter mPresenter;
    private FragmentFindCommonBinding mBinding;
    private Boolean once = true;
    private List<String> mList;
    private WithdrawDetailAdapter mAdapter;

    public static WithdrawDetailFragment getInstance() {
        WithdrawDetailFragment fragment = new WithdrawDetailFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_common,container,false);
        return  mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new WithdrawDetailAdapter(mList,getContext());
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setBackgroundResource(R.color.color_F5F5F5);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_F5F5F5));
    }

    @Override
    public void onRefresh() {
        stopResher();
//        initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        mList.clear();
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mAdapter.notifyDataSetChanged();
        once = false;
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.qsNet.setBackgroundColor(getResources().getColor(R.color.color_F5F5F5));
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBinding.netClude.errorContainer.getLayoutParams();
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.y50);
        mBinding.netClude.errorContainer.setLayoutParams(layoutParams);
    }

    /**
     * 停止刷新
     */
    public void stopResher() {
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

    /**
     * 停止加载
     */
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

    @Override
    public void onLoadMore() {
        stopLoading();
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        mAdapter.notifyDataSetChanged();
    }
}