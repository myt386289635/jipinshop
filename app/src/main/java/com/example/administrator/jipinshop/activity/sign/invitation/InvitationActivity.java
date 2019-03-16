package com.example.administrator.jipinshop.activity.sign.invitation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.InvitationBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/16
 * @Describe 邀请好友页面
 */
public class InvitationActivity extends BaseActivity {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.invitation_image)
    ImageView mInvitationImage;

    @Inject
    Repository mRepository;

    private Dialog mDialog;
    private String shareUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("邀请好友");
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        getDate(this.bindToLifecycle());
    }

    @OnClick({R.id.title_back, R.id.share_wechat, R.id.share_pyq, R.id.share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.share_wechat:
                new ShareUtils(this, SHARE_MEDIA.WEIXIN)
                        .shareImage(this, shareUrl);
                break;
            case R.id.share_pyq:
                new ShareUtils(this, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .shareImage(this, shareUrl);
                break;
            case R.id.share_weibo:
                new ShareUtils(this, SHARE_MEDIA.SINA)
                        .shareImage(this, shareUrl);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        mButterKnife.unbind();
        super.onDestroy();
    }

    public void getDate(LifecycleTransformer<InvitationBean> transformer){
        mRepository.invitation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(invitationBean -> {
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    if (invitationBean.getCode() == 0){
                        shareUrl = invitationBean.getData().getPosterImg();
                        GlideApp.loderImage(this,invitationBean.getData().getQrcodeImg(),mInvitationImage,0,0);
                    }else {
                        ToastUtil.show(invitationBean.getMsg());
                    }
                }, throwable -> {
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    ToastUtil.show(throwable.getMessage());
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
