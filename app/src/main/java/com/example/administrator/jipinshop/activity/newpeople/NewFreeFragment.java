package com.example.administrator.jipinshop.activity.newpeople;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.newGift.NewGiftActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.detail.NewFreeDetailActivity;
import com.example.administrator.jipinshop.adapter.NewFreeAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.databinding.FragmentNewFreeBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog4;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/3/9
 * @Describe 新人免单
 */
public class NewFreeFragment extends DBBaseFragment implements View.OnClickListener, NewFreeAdapter.OnClickItem, NewFreeView, OnRefreshListener, ShareBoardDialog4.onShareListener {

    @Inject
    NewFreePresenter mPresenter;
    private FragmentNewFreeBinding mBinding;
    private List<NewFreeBean.DataBean> mList;
    private NewFreeAdapter mAdapter;
    private Dialog mDialog;
    private ShareBoardDialog4 mShareBoardDialog;
    private String officialWeChat = "jpkele";//客服微信
    private List<MemberNewBean.DataBean.MessageListBean> messageList;//人物轮播
    private Boolean[] once = {true};//记录第一次进入页面标示

    public static NewFreeFragment getInstance(){
        NewFreeFragment fragment = new NewFreeFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_free,container,false);
        mBinding.setListener(this);
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBinding.swipeToLoad.setBackgroundColor(getResources().getColor(R.color.color_white));

        mList = new ArrayList<>();
        mAdapter = new NewFreeAdapter(getContext(),mList);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mBinding.recyclerView.setAdapter(mAdapter);
        messageList = new ArrayList<>();

        if (getActivity() instanceof NewGiftActivity){
            mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,
                    ((NewGiftActivity)getActivity()).getBar(),once);
        }else {
            mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        }
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.free_image:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class),302);
                    return;
                }
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog4.getInstance("保存图片");
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog4");
                }
                break;
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
    public void onResume() {
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
        startActivityForResult(new Intent(getContext(), NewFreeDetailActivity.class)
                        .putExtra("freeId",mList.get(position).getId())
                        .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                ,500
        );
    }

    @Override
    public void onCopy() {
        ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("jipinshop", officialWeChat);
        clip.setPrimaryClip(clipData);
        ToastUtil.show("微信号复制成功");
        SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat);
    }

    @Override
    public void onLeft(NewFreeBean.Ad1Bean ad1) {
        ShopJumpUtil.openBanner(getContext(),ad1.getType(),
                ad1.getObjectId(),ad1.getName(),ad1.getSource() , ad1.getRemark());
    }

    @Override
    public void onRight(NewFreeBean.Ad2Bean ad2) {
        ShopJumpUtil.openBanner(getContext(),ad2.getType(),
                ad2.getObjectId(),ad2.getName(),ad2.getSource() , ad2.getRemark());
    }

    @Override
    public void onSuccess(NewFreeBean bean) {
        stopResher();
        if (bean.getData() != null && bean.getData().size() != 0) {
            officialWeChat = bean.getOfficialWechat();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.freeBottom.setVisibility(View.VISIBLE);
            //广告(人物广告，单开页时需要)
            if (getActivity() instanceof NewFreeActivity ){
                messageList.clear();
                messageList.addAll(bean.getMessageList());
                ((NewFreeActivity)getActivity()).onAd(messageList);
            }
            //数据
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.setAd1(bean.getAd1());
            mAdapter.setAd2(bean.getAd2());
            mAdapter.setEndTime(bean.getEndTime());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
        }
        once[0] = false;
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        ToastUtil.show(error);
        once[0] = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UMShareAPI.get(getContext()).release();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mPresenter.taskFinish(this.bindToLifecycle());
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        if (share_media.equals(SHARE_MEDIA.WEIXIN) || share_media.equals(SHARE_MEDIA.QQ)
                || share_media.equals(SHARE_MEDIA.SINA)){
            new ShareUtils(getContext(),share_media,mDialog)
                    .shareWeb(getActivity(), RetrofitModule.H5_URL + "new-free?query=\""
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
        mPresenter.taskFinish(this.bindToLifecycle());
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        mPresenter.freeGetIndexPosterImg(null,this.bindToLifecycle());
    }

    @Override
    public void onShareSuc(ImageBean imageBean, SHARE_MEDIA share_media) {
        if (share_media != null){
            new ShareUtils(getContext(), share_media, mDialog)
                    .shareImage(getActivity(),imageBean.getData());
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
                            FileManager.saveFile(resource, getContext());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200 && requestCode == 302){
            onRefresh();
        }else if (resultCode == 200 && requestCode == 500){
            //从详情回来
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                startActivityForResult(new Intent(getContext(), LoginActivity.class),302);
                return;
            }
            if (mShareBoardDialog == null) {
                mShareBoardDialog = ShareBoardDialog4.getInstance("保存图片");
                mShareBoardDialog.setOnShareListener(this);
            }
            if (!mShareBoardDialog.isAdded()) {
                mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog4");
            }
        }
    }
}
