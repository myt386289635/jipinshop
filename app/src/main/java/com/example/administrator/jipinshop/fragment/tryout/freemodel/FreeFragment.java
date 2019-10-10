package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.adapter.FreeAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.FreeBean;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/18
 * @Describe 免单首页
 */
public class FreeFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, FreeView, FreeAdapter.OnClickItem {

    @Inject
    FreePresenter mPresenter;
    private FragmentEvaluationCommonBinding mBinding;
    private List<FreeBean.DataBean> mList;
    private FreeAdapter mAdapter;
    private Boolean once = true;
    private int page = 1;
    private Boolean refersh = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation_common,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        mList = new ArrayList<>();
        mAdapter = new FreeAdapter(mList,getContext());
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
    }


    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.freeList(page,this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.freeList(page,this.bindToLifecycle());
    }

    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    public void stopResher(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
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

    @Override
    public void onSuccess(FreeBean bean) {
        if (refersh){
            stopResher();
            if (bean.getData() != null && bean.getData().size() != 0){
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            }
        }else {
            stopLoading();
            if (bean.getData() != null && bean.getData().size() != 0) {
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
        if(once){
            once = false;
        }
    }

    @Override
    public void onFile(String error) {
        if (refersh) {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        } else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
        if(once){
            once = false;
        }
    }

    @Override
    public void onClickItem(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), FreeDetailActivity.class)
                    .putExtra("id",mList.get(position).getId())
            );
        }
    }
}
