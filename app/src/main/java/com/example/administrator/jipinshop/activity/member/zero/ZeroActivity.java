package com.example.administrator.jipinshop.activity.member.zero;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.member.zero.detail.ZeroDetailActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.adapter.ZeroAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.databinding.ActivityNewFreeBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog4;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe 0元购
 */
public class ZeroActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, ZeroView, ZeroAdapter.OnClickItem, ShareBoardDialog4.onShareListener {

    @Inject
    ZeroPresenter mPresenter;
    private ActivityNewFreeBinding mBinding;
    private Dialog mDialog;
    private Animation animation;
    private List<NewFreeBean.DataBean> mList;
    private ShareBoardDialog4 mShareBoardDialog;
    private ZeroAdapter mAdapter;
    private String officialWeChat = "jpkele";//客服微信
    private int status = 1;  //本月是否领取  0已领  1未领
    private String refreshTime = "";//0元购刷新时间

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
        mBinding.inClude.titleTv.setText("会员0元购专区");
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_white));
        animation = AnimationUtils.loadAnimation(this, R.anim.free_scale);
        mBinding.freeImage.startAnimation(animation);
        mBinding.freeImage.setText("立即送好友0元福利");
        mBinding.titleRule.setVisibility(View.GONE);

        mList = new ArrayList<>();
        mAdapter = new ZeroAdapter(this,mList);
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
                finish();
                break;
            case R.id.free_bottom:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(new Intent(this, LoginActivity.class));
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

    @Override
    public void onRefresh() {
       mPresenter.vipIndex(this.bindToLifecycle());
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
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
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
                            "我已经领到了！快来免费领取新人免单商品！",
                            "极品城百万新人补贴，注册就送108元补贴大礼包！",
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
    public void onFile(String error) {
        stopResher();
        ToastUtil.show(error);
    }

    @Override
    public void initShare(ImageBean imageBean, SHARE_MEDIA share_media) {
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
                            FileManager.saveFile(resource, ZeroActivity.this);
                            ToastUtil.show("已保存到相册");
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void onBuy(int position) {
        //进入详情
        startActivityForResult(new Intent(this, ZeroDetailActivity.class)
                .putExtra("freeId",mList.get(position).getId())
                .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                .putExtra("status",status)
                .putExtra("refreshTime",refreshTime)
        ,300);
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
                ad1.getObjectId(),ad1.getName(),ad1.getSource() ,ad1.getRemark() );
    }

    @Override
    public void onRight(NewFreeBean.Ad2Bean ad2) {
        ShopJumpUtil.openBanner(this,ad2.getType(),
                ad2.getObjectId(),ad2.getName(),ad2.getSource(),ad2.getRemark());
    }

    @Override
    public void onSuccess(NewFreeBean bean) {
        stopResher();
        if (bean.getData() != null && bean.getData().size() != 0) {
            officialWeChat = bean.getOfficialWechat();
            status = bean.getStatus();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.freeBottom.setVisibility(View.VISIBLE);
            animation.start();//动画
            mList.clear();
            mList.addAll(bean.getData());
            refreshTime = bean.getRefreshTime();
            if (bean.getStatus() == 0){
                mAdapter.setTitle("本月已领，"+ bean.getRefreshTime() +"刷新资格");
            }else {
                mAdapter.setTitle("本月未领，"+ bean.getRefreshTime() +"刷新资格");
            }
            mAdapter.setAd1Bean(bean.getAd1());
            mAdapter.setAd2Bean(bean.getAd2());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }
    }

    @Override
    public void onCommenFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == 200){
            if (data != null){
                status = data.getIntExtra("status", 0);
                refreshTime = data.getStringExtra("refreshTime");
                if (status == 0) {
                    mAdapter.setTitle("本月已领，" + refreshTime + "刷新资格");
                } else {
                    mAdapter.setTitle("本月未领，" + refreshTime + "刷新资格");
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
