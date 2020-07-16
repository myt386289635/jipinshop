package com.example.administrator.jipinshop.activity.sign.market;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.databinding.ActivityMarketBinding;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.io.File;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/7/13
 * @Describe 应用市场好评页面
 */
public class MarketActivity extends BaseActivity implements View.OnClickListener, SelectPicDialog.ChoosePhotoCallback, MarketView {

    @Inject
    MarketPresenter mPresenter;
    private ActivityMarketBinding mBinding;
    private SelectPicDialog mSelectPicDialog;
    private  Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_market);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("应用市场好评");
        mPresenter.feedbackGet(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(ImageBean bean) {
        if (!TextUtils.isEmpty(bean.getData())){
            mBinding.marketUpImage.setVisibility(View.GONE);
            GlideApp.loderImage(this,bean.getData(),mBinding.marketShopImage,0,0);
            mBinding.marketUpImageNotice.setText("好评截图已上传，请等待客服审核\n通过后即可获得极币奖励~");
        }else {
            mBinding.marketUpImage.setVisibility(View.VISIBLE);
            mBinding.marketUpImageNotice.setText("点击上传好评图片");
        }
    }

    @Override
    public void onFile() {
        mBinding.marketUpImage.setVisibility(View.VISIBLE);
        mBinding.marketUpImageNotice.setText("点击上传好评图片");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.market_goto:
                ShopJumpUtil.jumpMarkets(this);
                break;
            case R.id.market_upImage:
                if(mSelectPicDialog == null){
                    mSelectPicDialog = new SelectPicDialog();
                    mSelectPicDialog.setChoosePhotoCallback(this);
                }
                if(!mSelectPicDialog.isAdded()){
                    mSelectPicDialog.show(getSupportFragmentManager(), SelectPicDialog.TAG);
                }
                break;
        }
    }

    @Override
    public void getAbsolutePicPath(String picFile) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        //修改背景(不进行质量压缩)
        picFile = ImageCompressUtil.getimage1(this,ImageCompressUtil.getPicture(this,picFile));
        mPresenter.importCustomer(this.bindToLifecycle(),mDialog,new File(picFile));
    }

    @Override
    public void uploadPicSuccess(Dialog mDialog, String picPath) {
        mPresenter.feedBook(picPath,this.bindToLifecycle());
    }

    @Override
    public void uploadPicFailed(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void OpinionSuccess(String content) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.marketUpImage.setVisibility(View.GONE);
        GlideApp.loderImage(this,content,mBinding.marketShopImage,0,0);
        mBinding.marketUpImageNotice.setText("好评截图已上传，请等待客服审核\n通过后即可获得极币奖励~");
    }
}
