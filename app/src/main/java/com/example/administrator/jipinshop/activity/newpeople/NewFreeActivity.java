package com.example.administrator.jipinshop.activity.newpeople;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.detail.NewFreeDetailActivity;
import com.example.administrator.jipinshop.adapter.NewFreeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.databinding.ActivityNewFreeBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog4;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/5/22
 * @Describe 新人免单
 */
public class NewFreeActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, NewFreeAdapter.OnClickItem, NewFreeView, ShareBoardDialog4.onShareListener {

    @Inject
    NewFreePresenter mPresenter;
    private ActivityNewFreeBinding mBinding;
    private List<NewFreeBean.DataBean> mList;
    private NewFreeAdapter mAdapter;
    private Dialog mDialog;
    private String officialWeChat = "jpkele";//客服微信
    private ShareBoardDialog4 mShareBoardDialog;
    private Boolean startPop = true;//是否弹出关闭确认弹窗
    private NewFreeBean bean = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_free);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        if (SPUtils.getInstance().getBoolean(CommonDate.CheapDialog, true)){
            //当cheapDialog没有值时默认为true，即第一次打开app没有弹出过津贴弹窗
            SPUtils.getInstance().put(CommonDate.CheapDialog,true);//开启首页返回弹窗
        }
        mBinding.inClude.titleTv.setText("新人免单专区");
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_white));

        mList = new ArrayList<>();
        mAdapter = new NewFreeAdapter(this,mList);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                if (bean != null){
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                        //未登录
                        twoFinishPage();
                    }else {
                        //登陆了
                        long endTime = bean.getEndTime();
                        long time =  (endTime * 1000) - System.currentTimeMillis();
                        if (time > 0){
                            //未超时
                            twoFinishPage();
                        }else {
                            finish();
                        }
                    }
                }else {
                    finish();
                }
                break;
            case R.id.free_bottom:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivityForResult(new Intent(this, LoginActivity.class),302);
                    return;
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog4.getInstance("保存图片");
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog4");
                }
                break;
        }
    }

    public void twoFinishPage(){
        if (mList.size() != 0 && mList.size() >= 3){
            if (startPop){
                DialogUtil.outDialog2(this, bean, v1 -> {
                    finish();
                });
                startPop = false;
            }else {
                finish();
            }
        }else {
            finish();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getData(this.bindToLifecycle());
    }

    public void initError(int id, String title, String content) {
        mBinding.freeBottom.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onBuy(int position) {
        if (mList.get(position).getTotal() <= 0){
            ToastUtil.show("当前商品已售罄，看看其他商品吧");
            return;
        }
        startActivity(new Intent(this, NewFreeDetailActivity.class)
                .putExtra("freeId",mList.get(position).getId())
                .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId()));
    }

    @Override
    public void onCopy() {
        ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("jipinshop", officialWeChat);
        clip.setPrimaryClip(clipData);
        ToastUtil.show("微信号复制成功");
        SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat);
    }

    @Override
    public void onLeft(NewFreeBean.Ad1Bean ad1) {
        ShopJumpUtil.openBanner(this,ad1.getType(),
                ad1.getObjectId(),ad1.getName());
    }

    @Override
    public void onRight(NewFreeBean.Ad2Bean ad2) {
        ShopJumpUtil.openBanner(this,ad2.getType(),
                ad2.getObjectId(),ad2.getName());
    }

    @Override
    public void onSuccess(NewFreeBean bean) {
        stopResher();
        if (bean.getData() != null && bean.getData().size() != 0) {
            this.bean = bean;
            officialWeChat = bean.getOfficialWechat();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.freeBottom.setVisibility(View.VISIBLE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.setAd1(bean.getAd1());
            mAdapter.setAd2(bean.getAd2());
            mAdapter.setEndTime(bean.getEndTime());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        ToastUtil.show(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        if (share_media.equals(SHARE_MEDIA.WEIXIN) || share_media.equals(SHARE_MEDIA.QQ)
                || share_media.equals(SHARE_MEDIA.SINA)){
            new ShareUtils(this,share_media,mDialog)
                    .shareWeb(this, RetrofitModule.H5_URL + "new-free?query=\""
                                    + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId) + "\"",
                            "新人免单，仅剩3天！","价值30元免单礼品任意选",
                            "https://jipincheng.cn/freeShare.png",R.mipmap.logo);
        }else {
            //还是点击时获取吧，万一出现进入页面后快速进行分享，可能会导致没有获取到海报就分享的情况
            mPresenter.freeGetIndexPosterImg(share_media,this.bindToLifecycle());
        }
    }

    @Override
    public void download(int type) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        mPresenter.freeGetIndexPosterImg(null,this.bindToLifecycle());
    }

    @Override
    public void onShareSuc(ImageBean imageBean, SHARE_MEDIA share_media) {
        if (share_media != null){
            new ShareUtils(this, share_media, mDialog)
                    .shareImage(this,imageBean.getData());
        }else {
            Glide.with(this)
                    .asBitmap()
                    .load(imageBean.getData())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            FileManager.saveFile(resource, NewFreeActivity.this);
                            ToastUtil.show("已保存到相册");
                        }
                    });
        }
    }

    @Override
    public void onCommenFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200 && requestCode == 302){
            onRefresh();
        }
    }

    @Override
    public void onBackPressed() {
        if (bean != null){
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                //未登录
                twoFinishPage();
            }else {
                //登陆了
                long endTime = bean.getEndTime();
                long time = ( endTime * 1000) - System.currentTimeMillis();
                if (time > 0){
                    //未超时
                    twoFinishPage();
                }else {
                    super.onBackPressed();
                }
            }
        }else {
            super.onBackPressed();
        }
    }
}
