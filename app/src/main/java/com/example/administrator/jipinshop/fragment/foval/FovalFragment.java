package com.example.administrator.jipinshop.fragment.foval;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.FovalAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.databinding.FragmentFovalBinding;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe 收藏/足迹
 */
public class FovalFragment extends DBBaseFragment implements OnRefreshListener, View.OnClickListener, OnLoadMoreListener, FovalView {

    @Inject
    FovalPresenter mPresenter;

    private Boolean once = true;
    private FovalAdapter mFovalAdapter;
    private List<FovalBean.ListBean> mList;

    private FragmentFovalBinding mBinding;

    private int page = 0;
    private Boolean refresh = true;
    private Dialog dialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getArguments().getString("type").equals("2")){
            if(isVisibleToUser && once){
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    public static FovalFragment getInstance(String type) {
        FovalFragment fragment = new FovalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 页面刷新
     */
    @Override
    public void onRefresh() {
        refresh = true;
        page = 0;
        mPresenter.collect(page,this.bindToLifecycle());
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_foval,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mBinding.setListener(this);
        mPresenter.setView(this);

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mList = new ArrayList<>();
        mFovalAdapter = new FovalAdapter(mList,getContext());
        mBinding.swipeTarget.setAdapter(mFovalAdapter);

        if(getArguments().getString("type").equals("1")){
            mBinding.swipeToLoad.setRefreshing(true);
            once = false;
        }

        mPresenter.solveScoll(mBinding.swipeTarget);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.net_clude:
                if(mBinding.netClude.errorTitle.getText().toString().equals("网络出错")){
                    dialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
                    dialog.show();
                    onRefresh();
                }
                break;
        }
    }

    /**
     * 页面加载
     */
    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        mPresenter.collect(page,this.bindToLifecycle());
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
    }

    public void dissLoading(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    /**
     * 收藏列表获取成功
     * @param fovalBean
     */
    @Override
    public void FovalSuccess(FovalBean fovalBean) {
        if(refresh){
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }else {
            dissLoading();
        }
        if(fovalBean.getList() != null && fovalBean.getList().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            if(refresh){
                mList.clear();
            }
            mList.addAll(fovalBean.getList());
            mFovalAdapter.notifyDataSetChanged();
        }else {
            //没有数据
            if(refresh){
                //刷新时
                initError(R.mipmap.qs_collection, "暂无收藏", "收藏内容为空，先去逛逛吧");
            }else {
                //加载时
                page--;
                Toast.makeText(getContext(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 收藏列表获取失败
     * @param throwable
     */
    @Override
    public void FovalFaileNet(String throwable) {
        if(refresh){
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        }else {
            dissLoading();
            page--;
            Toast.makeText(getContext(), throwable, Toast.LENGTH_SHORT).show();
        }
    }
}
