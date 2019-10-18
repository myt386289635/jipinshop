package com.example.administrator.jipinshop.fragment.balance.budget;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.budget.BudgetDetailActivity;
import com.example.administrator.jipinshop.adapter.BudgetDetailAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.BudgetDetailBean;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 收支明细
 */
public class BudgetDetailFragment extends DBBaseFragment implements OnRefreshListener, BudgetDetailAdapter.OnClickItem, BudgetDetailView {

    private FragmentFindCommonBinding mBinding;
    private List<BudgetDetailBean.DataBean> mList;
    private BudgetDetailAdapter mAdapter;
    private BudgetDetailBean mBudgetDetailBean;

    @Inject
    BudgetDetailPresenter mPresenter;

    public static BudgetDetailFragment getInstance() {
        BudgetDetailFragment fragment = new BudgetDetailFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_common,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new BudgetDetailAdapter(getContext(),mList);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);//禁止加载
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onRefresh() {
        mPresenter.getCommssionDetail(this.bindToLifecycle());
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
     * 点击item的  查看完整明细>>
     */
    @Override
    public void onClickItem(int position) {
        startActivity(new Intent(getContext(), BudgetDetailActivity.class)
                .putExtra("position",position)
                .putExtra("date",mBudgetDetailBean)
        );
    }

    @Override
    public void onSuccess(BudgetDetailBean bean) {
        stopResher();
        if (bean.getData() != null && bean.getData().size() != 0){
            mBudgetDetailBean = bean;
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }

    }

    @Override
    public void onFile(String error) {
        stopResher();
        ToastUtil.show(error);
        initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
    }
}
