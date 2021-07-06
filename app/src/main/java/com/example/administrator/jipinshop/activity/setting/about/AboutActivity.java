package com.example.administrator.jipinshop.activity.setting.about;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.setting.about.cancellation.CancellationActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.databinding.ActivityAboutBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.update.UpDataUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/5/15
 * @Describe 关于极品城  页面
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener, AboutView {

    @Inject
    AboutPresenter mPresenter;
    private ActivityAboutBinding mBinding;
    private Dialog mDialog;
    private  Boolean tag = false;//默认不强制更新

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_about);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mBinding.inClude.titleTv.setText("关于极品城");
        mBinding.aboutVersionName.setText(SettingActivity.getVerName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.about_version:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
                break;
            case R.id.about_privacy:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "privacy.html")
                        .putExtra(WebActivity.title, "隐私政策")
                );
                break;
            case R.id.about_user:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "agreement.html")
                        .putExtra(WebActivity.title, "用户协议")
                );
                break;
            case R.id.about_cancellation:
                startActivityForResult(new Intent(this, CancellationActivity.class)
                        ,401);
                break;
        }
    }

    @Override
    public void onSuccess(AppVersionbean versionbean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (versionbean.getData().getVersionCode() > UpDataUtil.getPackageVersionCode()) {
            if (versionbean.getData().getNeedUpdate() == 0) {
                tag = false;
            }else {
                tag = true;
            }
            UpDataUtil.newInstance(this).downloadApk(this, versionbean.getData().getVersionName(), tag,
                    versionbean.getData().getContent(), versionbean.getData().getDownloadUrl(), () -> {});
        }else {
            ToastUtil.show("已经是最新版本");
        }
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 401 && resultCode == 201){
            //从注销页面返回
            setResult(201);
            finish();
        }
    }
}
