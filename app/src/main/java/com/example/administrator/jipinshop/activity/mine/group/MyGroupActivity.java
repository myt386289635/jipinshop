package com.example.administrator.jipinshop.activity.mine.group;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.adapter.ShoppingUserLikeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.GroupInfoBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.databinding.ActivityGroupBinding;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.TimeUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/11/25
 * @Describe 拼团详情
 */
public class MyGroupActivity extends BaseActivity implements View.OnClickListener, ShoppingUserLikeAdapter.OnItem, MyGroupView {

    @Inject
    MyGroupPresenter mPresenter;
    private String id = "";
    private ActivityGroupBinding mBinding;
    private Dialog mDialog;
    private CountDownTimer countDownTimer;
    private long timer = 0L;
    private Boolean startPop = true;//是否弹出关闭确认弹窗
    private GroupInfoBean.DataBean bean = null;
    //猜你喜欢
    private List<SimilerGoodsBean.DataBean> mUserLikeList;
    private ShoppingUserLikeAdapter mLikeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_group);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar, mBinding.groupContainer,this);
        id = getIntent().getStringExtra("id");
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();

        //猜你喜欢
        mBinding.sreachUserLike.setLayoutManager(new GridLayoutManager(this,2));
        mBinding.sreachUserLike.setNestedScrollingEnabled(false);
        mUserLikeList = new ArrayList<>();
        mLikeAdapter = new ShoppingUserLikeAdapter(mUserLikeList,this);
        mLikeAdapter.setOnItem(this);
        mBinding.sreachUserLike.setAdapter(mLikeAdapter);

        mPresenter.groupInfo(id,this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.group_invation:
            case R.id.group_share:
                //分享
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.initShare(id,this.bindToLifecycle());
                break;
            case R.id.title_back:
                if (startPop){
                    DialogUtil.groupOutDialog(this, bean, v1 -> {
                        finish();
                    });
                    startPop = false;
                }else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onItemShare(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        TaoBaoUtil.openTB(this, () -> {
            startActivity(new Intent(this, ShareActivity.class)
                    .putExtra("otherGoodsId",mUserLikeList.get(position).getOtherGoodsId())
                    .putExtra("source","2")
            );
        });
    }

    @Override
    public void LikeSuccess(SimilerGoodsBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mUserLikeList.clear();
        mUserLikeList.addAll(bean.getData());
        mLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSuccess(GroupInfoBean bean) {
        this.bean = bean.getData();
        GlideApp.loderRoundImage(this,bean.getData().getImg(),mBinding.groupImage);
        mBinding.groupName.setText(bean.getData().getGoodsName());
        mBinding.groupPrice.setText("￥" + bean.getData().getUpFee());
        String html = "未拼成返<b><font color='#E25838'>￥"+ bean.getData().getFee() + "</font></b>";
        mBinding.groupPrice2.setText(Html.fromHtml(html));
        mBinding.groupInvation.setText("邀请好友参团，成团返￥" + bean.getData().getUpFee());
        for (int i = 0; i < bean.getData().getAvatarList().size(); i++) {
            if (i == 0){
                GlideApp.loderCircleImage(this,bean.getData().getAvatarList().get(i),mBinding.groupGrouper,0,0);
            }else if (i == 1){
                GlideApp.loderCircleImage(this,bean.getData().getAvatarList().get(i),mBinding.groupMember,0,0);
            }
        }
        timer = (bean.getData().getTimeToEndTime() * 1000) - System.currentTimeMillis();
        if (timer > 0) {//开始倒计时
            countDownTimer = new CountDownTimer(timer, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String html = "还差<b><font color='#E25838'>"+bean.getData().getLeftCount() +
                            "人</font></b>拼团成功，时间仅剩<b><font color='#E25838'>" +
                            TimeUtil.getCountTimeByLong3(millisUntilFinished) + "</font></b>";
                    mBinding.groupTime.setText(Html.fromHtml(html));
                }

                @Override
                public void onFinish() {
                    startPop = false;
                    String html = "还差<b><font color='#E25838'>"+bean.getData().getLeftCount() +
                            "人</font></b>拼团成功，时间仅剩<b><font color='#E25838'>00:00:00</font></b>";
                    mBinding.groupTime.setText(Html.fromHtml(html));
                }
            }.start();
        }else {
            startPop = false;
            mBinding.groupTime.setText("恭喜拼团成功，返现￥" + bean.getData().getUpFee());
        }
        Map<String,String> map =  DeviceUuidFactory.getIdfa(this);
        mPresenter.listSimilerGoods(map,this.bindToLifecycle());
    }

    @Override
    public void initShare(ShareInfoBean bean) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),
                        bean.getData().getDesc(),bean.getData().getImgUrl(),R.mipmap.share_logo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (startPop){
            DialogUtil.groupOutDialog(this, bean, v1 -> {
                super.onBackPressed();
            });
            startPop = false;
        }else {
            super.onBackPressed();
        }
    }
}
