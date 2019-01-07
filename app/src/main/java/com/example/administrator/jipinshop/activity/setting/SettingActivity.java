package com.example.administrator.jipinshop.activity.setting;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.base.BaseAsyncTask;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.MyDataCleanManager;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.CleanCacheDialog;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 设置页面
 */
public class SettingActivity extends BaseActivity implements CleanCacheDialog.OnItemDialog, SettingView {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.setting_opinionImage)
    ImageView mSettingOpinionImage;
    @BindView(R.id.setting_opinionContainer)
    RelativeLayout mSettingOpinionContainer;
    @BindView(R.id.setting_cleanImage)
    TextView mSettingCleanImage;
    @BindView(R.id.setting_cleanContainer)
    RelativeLayout mSettingCleanContainer;
    @BindView(R.id.setting_serviceText)
    TextView mSettingServiceText;
    @BindView(R.id.setting_serviceContainer)
    RelativeLayout mSettingServiceContainer;
    @BindView(R.id.setting_goodImage)
    ImageView mSettingGoodImage;
    @BindView(R.id.setting_goodContainer)
    RelativeLayout mSettingGoodContainer;
    @BindView(R.id.setting_userImage)
    ImageView mSettingUserImage;
    @BindView(R.id.setting_userContainer)
    RelativeLayout mSettingUserContainer;
    @BindView(R.id.setting_exitLogin)
    TextView mSettingExitLogin;

    private ClearTask mClearTask;
    private CleanCacheDialog mCleanCacheDialog;

    @Inject
    SettingPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        mButterKnife = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("设置");
        try {
            mSettingCleanImage.setText(MyDataCleanManager.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            mSettingExitLogin.setVisibility(View.GONE);
        }else {
            mSettingExitLogin.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        if (mClearTask != null) {
            mClearTask.cancel(true);
            mClearTask = null;
        }
        mButterKnife.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.title_back, R.id.setting_opinionContainer, R.id.setting_cleanContainer, R.id.setting_serviceContainer, R.id.setting_goodContainer, R.id.setting_userContainer, R.id.setting_exitLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                return;
            case R.id.setting_cleanContainer:
                if (mCleanCacheDialog == null) {
                    mCleanCacheDialog = mCleanCacheDialog.getInstance();
                    mCleanCacheDialog.setOnItemDialog(this);
                }
                if (!mCleanCacheDialog.isAdded()) {
                    mCleanCacheDialog.show(getSupportFragmentManager(), mCleanCacheDialog.TAG);
                }
                return;
            case R.id.setting_serviceContainer:
                //联系客服
                DialogUtil.LoginDialog(this, "极品城想打开您的电话", v -> diallPhone(mSettingServiceText.getText().toString()));
                return;
            case R.id.setting_goodContainer:
                if(!ShopJumpUtil.toQQDownload(this,"com.example.administrator.jipinshop")){
                    if(!ShopJumpUtil.toMarket(this,"com.example.administrator.jipinshop",null)){
                        Toast.makeText(this, "没有找到您手机里的应用商店，请确认", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            case R.id.setting_userContainer:
                //用户协议
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"user-agreement.html")
                        .putExtra(WebActivity.title,"用户协议")
                );
                return;
        }
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.setting_opinionContainer:
                //我要反馈
                startActivity(new Intent(this,OpinionActivity.class));
                break;
            case R.id.setting_exitLogin:
                //退出登陆
                DialogUtil.LoginDialog(this, "您确定要退出登录吗？","确定","取消", v -> {
                    Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "退出登录...");
                    mDialog.show();
                    mPresenter.loginOut(this.<SuccessBean>bindToLifecycle(),mDialog);
                });
                break;
        }
    }



    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * 该功能不需要动态申请权限的
     * @param phoneNum 电话号码
     */
    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * 清除缓存
     */
    @Override
    public void onItemDialog(View view) {
        if (mClearTask != null) {
            mClearTask.cancel(true);
        }
        mClearTask = new ClearTask(SettingActivity.this);
        mClearTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 退出登陆
     * @param msg
     */
    @Override
    public void loginOutSuccess(SuccessBean msg) {
        if(msg.getCode() == 0){
            setResult(201);
            finish();
            Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, msg.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }


    class ClearTask extends BaseAsyncTask<Void, Void, Void> {

        public ClearTask(Context context) {
            super(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //开始清除时候
//            clearLoading.setVisibility(View.VISIBLE);
//            tvCacheNum.setVisibility(View.GONE);
        }

        @Override
        protected Void realDoInBackground(Void... voids) {
            MyDataCleanManager.clearAllCache(getApplicationContext());
//            clearAllCache();
            return null;
        }

        @Override
        protected void realOnPostExecute(Void aVoid) {
//            clearLoading.setVisibility(View.GONE);
//            tvCacheNum.setVisibility(View.VISIBLE);
            mSettingCleanImage.setText(MyDataCleanManager.getFormatSize(0L));
        }
    }
}
