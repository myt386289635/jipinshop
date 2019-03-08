package com.example.administrator.jipinshop.fragment.balance;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.BudgetDetailAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentBudgetBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 收支明细
 */
public class BudgetDetailFragment extends DBBaseFragment implements OnRefreshListener {

    private FragmentBudgetBinding mBinding;
    private Boolean[] once = {true};
    private List<String> mList;
    private BudgetDetailAdapter mAdapter;

    @Inject
    BudgetDetailPresenter mPresenter;

    public static BudgetDetailFragment getInstance() {
        BudgetDetailFragment fragment = new BudgetDetailFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_common,container,false);
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_budget,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);

//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mList = new ArrayList<>();
//        mAdapter = new BudgetDetailAdapter(getContext(),mList);
//        mBinding.recyclerView.setAdapter(mAdapter);

//        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,((MyWalletActivity)getActivity()).getBar(),once);
        mBinding.swipeToLoad.setOnRefreshListener(this);
//        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        stopResher();
        initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
//        mBinding.recyclerView.setVisibility(View.GONE);
//        for (int i = 0; i < 10; i++) {
//            mList.add("");
//        }
//        mAdapter.notifyDataSetChanged();
        once[0] = false;
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
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

}
