package com.example.administrator.jipinshop.fragment.tryout.mine;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.report.create.CreateReportActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.adapter.TrialCommonAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.TrialCommonBean;
import com.example.administrator.jipinshop.databinding.FragmentSreachgoodsBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/4/2
 * @Describe 申请中 0、申请成功 1、申请失败页面 -1
 */
public class TrialCommonFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, TrialCommonAdapter.OnClickItem, TrialCommonView, ShareBoardDialog.onShareListener {

    @Inject
    TrialCommonPresenter mPresenter;

    private FragmentSreachgoodsBinding mBinding;
    private int page = 1;
    private Boolean refersh = true;
    private Boolean[] once = {true};
    private List<TrialCommonBean.DataBean> mList;
    private TrialCommonAdapter mAdapter;
    /**
     * 分享面板
     */
    private ShareBoardDialog mShareBoardDialog;
    private String shareTitle = "";
    private String shareContent  = "";
    private String shareImg  = "";
    private String shareUrl  = "";

    private Boolean getAddressDef = false;//没有默认地址
    private Dialog mDialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once[0]){
            if(!getArguments().getString("type","").equals("0")){
                mBinding.swipeToLoad.setRefreshing(true);
            }
            if (getArguments().getString("type","").equals("1")){
                //申请成功页面调用默认地址接口
                mPresenter.defaultAddress(this.bindToLifecycle());
            }
        }
    }

    public static TrialCommonFragment getInstance(String type) {
        TrialCommonFragment fragment = new TrialCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sreachgoods,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new TrialCommonAdapter(mList, getContext());
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        if(getArguments().getString("type","").equals("0") && once[0]){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.getDate(getArguments().getString("type",""),page + "" , this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getDate(getArguments().getString("type",""),page + "" , this.bindToLifecycle());
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
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

    /**
     * 停止mDialog
     */
    public void stopDialog(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    /**
     * 分享拉赞
     */
    @Override
    public void onClickShareItem(int position) {
        shareTitle = mList.get(position).getVoteShareTitle();
        shareContent = mList.get(position).getVoteShareContent();
        shareImg = mList.get(position).getVoteShareImg();
        shareUrl = mList.get(position).getVoteShareUrl();
        if (mShareBoardDialog == null) {
            mShareBoardDialog = new ShareBoardDialog();
            mShareBoardDialog.setOnShareListener(this);
        }
        if (!mShareBoardDialog.isAdded()) {
            mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog");
        }
    }

    /**
     * 确认参与
     */
    @Override
    public void onClickSureItem(int position) {
        if (getAddressDef){
            mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "正在请求...");
            mDialog.show();
            mPresenter.myTrialConfirm(mList.get(position).getId(),this.bindToLifecycle());
        }else {
            DialogUtil.buleDialog(getContext(), "请先选择默认地址后才能确认参与", "填写地址", v -> {
                startActivityForResult(new Intent(getContext(), MyAddressActivity.class),333);
            });
        }
    }

    /**
     * 跳转到填写报告
     */
    @Override
    public void onClickReport(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivityForResult(new Intent(getContext(), CreateReportActivity.class)
                    .putExtra("trialId",mList.get(position).getId())
            ,334);
        }
    }

    /**
     * 跳转到试用商品详情
     */
    @Override
    public void onItemClick(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(),TryDetailActivity.class)
                    .putExtra("id",mList.get(position).getId())
                    .putExtra("pos",-1)
            );
        }
    }

    @Override
    public void onSuccess(TrialCommonBean bean) {
        if(refersh){
            stopResher();
            if(bean.getData() != null && bean.getData().size() != 0){
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
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
                if (mBinding.swipeToLoad.isLoadMoreEnabled())  mBinding.swipeToLoad.setLoadMoreEnabled(mPresenter.isSlideToBottom( mBinding.recyclerView));
            }
        }
        if(once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onFile(String error) {
        if(refersh){
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
        if(once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onAddressSuccess(DefaultAddressBean bean) {
        if (bean.getData() != null){
            //有默认地址
            getAddressDef = true;
        }else {
            //没有默认地址
            getAddressDef = false;
        }
    }

    @Override
    public void onAddressFile(String error) {
        getAddressDef = false;
        ToastUtil.show(error);
    }

    @Override
    public void onConfirmSuccess() {
        stopDialog();
        ToastUtil.show("已成功确认参与");
        if(!mBinding.swipeToLoad.isRefreshEnabled()){
            mBinding.swipeToLoad.setRefreshEnabled(true);
            mBinding.swipeToLoad.setRefreshing(true);
            mBinding.swipeToLoad.setRefreshEnabled(false);
        }else {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public void onConfirmFile(String error) {
        stopDialog();
        ToastUtil.show(error);
    }

    /**
     * 分享
     */
    @Override
    public void share(SHARE_MEDIA share_media) {
        new ShareUtils(getContext(), share_media)
                .shareWeb(getActivity(), shareUrl, shareTitle, shareContent, shareImg, R.mipmap.share_logo);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 333://退出填写收货地址页面后回调
                if (getArguments().getString("type","").equals("1")){
                    mPresenter.defaultAddress(this.bindToLifecycle());
                }
                break;
        }
        switch (resultCode){
            case 334://提交或者保存报告回来后
                if (getArguments().getString("type","").equals("1")){
                    if(!mBinding.swipeToLoad.isRefreshEnabled()){
                        mBinding.swipeToLoad.setRefreshEnabled(true);
                        mBinding.swipeToLoad.setRefreshing(true);
                        mBinding.swipeToLoad.setRefreshEnabled(false);
                    }else {
                        mBinding.swipeToLoad.setRefreshing(true);
                    }
                }
                break;
        }
    }
}
