package com.example.administrator.jipinshop.activity.report.cover;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityCoverReportBinding;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog;

import java.io.File;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe 上传封面
 */
public class CoverReportActivity extends BaseActivity implements View.OnClickListener, SelectPicDialog.ChoosePhotoCallback, CoverReportView {

    @Inject
    CoverReportPresenter mPresenter;
    private ActivityCoverReportBinding mBinding;
    private SelectPicDialog mPicDialog;
    private String Conver = "";//封面
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cover_report);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("试用报告");
        Conver = getIntent().getStringExtra("conver");
        if (!TextUtils.isEmpty(Conver)){
            mBinding.coverUpload.setVisibility(View.GONE);
            mBinding.coverImg.setVisibility(View.VISIBLE);
            mBinding.coverChange.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(Conver)
                    .into(mBinding.coverImg);
        }else {
            mBinding.coverUpload.setVisibility(View.VISIBLE);
            mBinding.coverImg.setVisibility(View.GONE);
            mBinding.coverChange.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                setResult(330);
                finish();
                break;
            case R.id.cover_save:
                //保存
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                mDialog.show();
                Log.e("moxiaoting", getIntent().getStringExtra("content"));
                mPresenter.saveReport(getIntent().getStringExtra("trialId"),
                        getIntent().getStringExtra("title"),Conver,getIntent().getStringExtra("content"),this.bindToLifecycle());
                break;
            case R.id.cover_submit:
                //提交
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                mDialog.show();
                Log.e("moxiaoting", getIntent().getStringExtra("content"));
                mPresenter.submitReport(getIntent().getStringExtra("trialId"),
                        getIntent().getStringExtra("title"),Conver,getIntent().getStringExtra("content"),this.bindToLifecycle());
                break;
            case R.id.cover_upload:
            case R.id.cover_change:
                //上传图片
                if(mPicDialog == null){
                    mPicDialog = new SelectPicDialog();
                    mPicDialog.setChoosePhotoCallback(this);
                }
                if(!mPicDialog.isAdded()){
                    mPicDialog.show(getSupportFragmentManager(), SelectPicDialog.TAG);
                }
                break;
        }
    }

    @Override
    public void getAbsolutePicPath(String picFile) {
        Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        mDialog.show();
        picFile = ImageCompressUtil.getimage(this,ImageCompressUtil.getPicture(this,picFile));
        mPresenter.importCustomer(this.bindToLifecycle(),mDialog,new File(picFile));
    }

    @Override
    public void uploadPicSuccess(String picPath, File file) {
        Conver = picPath;
        mBinding.coverUpload.setVisibility(View.GONE);
        mBinding.coverImg.setVisibility(View.VISIBLE);
        mBinding.coverChange.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(file)
                .into(mBinding.coverImg);
    }

    @Override
    public void uploadPicFailed(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onSuccessSave() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        setResult(331);
        finish();
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
