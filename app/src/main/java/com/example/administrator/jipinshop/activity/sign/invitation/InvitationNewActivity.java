package com.example.administrator.jipinshop.activity.sign.invitation;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.InvitationNewAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.InvitationBean;
import com.example.administrator.jipinshop.databinding.ActivityInvitation2Binding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.viewpager.transformer.ScalePagerTransformer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe 邀请好友页面更改
 */
public class InvitationNewActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, InvitationNewView {

    @Inject
    InvitationNewPresenter mPresenter;
    private ActivityInvitation2Binding mBinding;
    private InvitationNewAdapter mAdapter;
    private List<String> mList;
    private int position = 0;//当前是第几个位置，默认为第一个
    private Dialog mDialog;
    private String linkUrlContent = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_invitation2);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("邀请有礼");
        mList = new ArrayList<>();
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        mPresenter.getQRcodeImgs(this.bindToLifecycle());
        //解决了viewpager只有中间划动的问题
        mBinding.viewpagerContainer.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            v.performClick();
            return mBinding.viewPager.dispatchTouchEvent(event);
        });
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.share_wechat:
                if (mList.size() > position){
                    new ShareUtils(this, SHARE_MEDIA.WEIXIN)
                            .shareImage(this, mList.get(position));
                }else {
                    ToastUtil.show("邀请页面不存在请确认");
                }
                break;
            case R.id.share_pyq:
                if (mList.size() > position){
                    new ShareUtils(this, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .shareImage(this, mList.get(position));
                }else {
                    ToastUtil.show("邀请页面不存在请确认");
                }
                break;
            case R.id.share_weibo:
                if (mList.size() > position){
                    new ShareUtils(this, SHARE_MEDIA.SINA)
                            .shareImage(this, mList.get(position));
                }else {
                    ToastUtil.show("邀请页面不存在请确认");
                }
                break;
            case R.id.share_copy:
                if (TextUtils.isEmpty(linkUrlContent)){
                    ToastUtil.show("邀请链接为空，请重新进入页面");
                    return;
                }
                ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", linkUrlContent);
                clip.setPrimaryClip(clipData);
                ToastUtil.show("链接复制成功");
                SPUtils.getInstance().put(CommonDate.CLIP, linkUrlContent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        position = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    public void onSuccess(InvitationBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        linkUrlContent = bean.getData().getLinkUrlContent();
        mList.addAll(bean.getData().getPosterImgs());
        mAdapter = new InvitationNewAdapter(this,mList);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mList.size() - 1);
        mBinding.viewPager.setPageMargin((int) getResources().getDimension(R.dimen.x16));
        ScalePagerTransformer transformer = new ScalePagerTransformer();
        mBinding.viewPager.setPageTransformer(true,transformer);
        mBinding.invitationTitle.setText(bean.getData().getShareContent());
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
