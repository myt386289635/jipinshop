package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeNewDetailActivity;
import com.example.administrator.jipinshop.adapter.FreeNewAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PosterShareBean;
import com.example.administrator.jipinshop.bean.V2FreeListBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/11/8
 * @Describe 免单新页面
 */
public class FreeNewFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, FreeNewAdapter.OnClickItem, FreeNewView, ShareBoardDialog2.onShareListener {

    @Inject
    FreeNewPresenter mPresenter;

    private FragmentEvaluationCommonBinding mBinding;
    private Boolean once = true;
    private int page = 1;
    private Boolean refersh = true;
    private List<V2FreeListBean.DataBean> mList;
    private FreeNewAdapter mAdapter;
    //分享面板
    private ShareBoardDialog2 mShareBoardDialog;
    private Dialog mDialog;
    private String shareUrl = "";
    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";
    private String postUrl = "";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common,container,false);
        EventBus.getDefault().register(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);

        mList = new ArrayList<>();
        mAdapter = new FreeNewAdapter(mList,getContext());
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
        mPresenter.getData(page,this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getData(page,this.bindToLifecycle());
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
    public void onItem(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), FreeNewDetailActivity.class)
                .putExtra("freeId",mList.get(position).getId())
        );
    }

    @Override
    public void onBuy(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getContext(), FreeNewDetailActivity.class)
                .putExtra("freeId",mList.get(position).getId())
        );
    }

    @Override
    public void onInvation() {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "正在为您生成海报");
        mDialog.show();
        mPresenter.createFreePosterIndex(this.bindToLifecycle());
    }

    @Override
    public void onRule() {
        startActivity(new Intent(getContext(), WebActivity.class)
                .putExtra(WebActivity.url, RetrofitModule.H5_URL+"free-desc.html")
                .putExtra(WebActivity.title,"规则说明")
        );
    }

    @Override
    public void onSuccess(V2FreeListBean bean) {
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
    public void onPoster(PosterShareBean bean) {
        if (mDialog!= null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        shareUrl = bean.getData().getShareUrl();
        shareImage = bean.getData().getShareImg();
        shareName = bean.getData().getShareTitle();
        shareContent = bean.getData().getShareContent();
        postUrl = bean.getData().getPosterImg();
        if (mShareBoardDialog == null) {
            mShareBoardDialog = ShareBoardDialog2.getInstance();
            mShareBoardDialog.setOnShareListener(this);
        }
        if (!mShareBoardDialog.isAdded()) {
            mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog2");
        }
    }

    @Override
    public void onPosterFile(String error) {
        if (mDialog!= null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        if (share_media.equals(SHARE_MEDIA.WEIXIN)){
            //分享小程序
            String path = "pages/main/main-v2-list/index?fromUserId=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId);
            new ShareUtils(getContext(), share_media, mDialog)
                    .shareWXMin2(getActivity(),shareUrl,shareImage,shareName,shareContent,path);
        }else if (share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            //分享图片到朋友圈
            new ShareUtils(getContext(), share_media, mDialog)
                    .shareImage(getActivity(), postUrl);
        }
    }

    @Override
    public void onDestroyView() {
        UMShareAPI.get(getContext()).release();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe//登陆后刷新免单模块首页
    public void onLogin(CommonEvaluationBus commonEvaluationBus){
        if(commonEvaluationBus != null && commonEvaluationBus.getRefersh().equals(LoginActivity.refresh)){
            if (!once  && mBinding.swipeToLoad != null){
                if(!mBinding.swipeToLoad.isRefreshEnabled()){
                    mBinding.swipeToLoad.setRefreshEnabled(true);
                    mBinding.swipeToLoad.setRefreshing(true);
                    mBinding.swipeToLoad.setRefreshEnabled(false);
                }else {
                    mBinding.swipeToLoad.setRefreshing(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
