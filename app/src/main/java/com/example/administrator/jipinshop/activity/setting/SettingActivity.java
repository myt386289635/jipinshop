package com.example.administrator.jipinshop.activity.setting;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.setting.about.AboutActivity;
import com.example.administrator.jipinshop.activity.setting.bind.BindWXActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.jpush.JPushAlias;
import com.example.administrator.jipinshop.util.ClearTask;
import com.example.administrator.jipinshop.util.MyDataCleanManager;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.CleanCacheDialog;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

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
    @BindView(R.id.setting_exitLogin)
    TextView mSettingExitLogin;
    @BindView(R.id.setting_versonText)
    TextView mSettingVersonText;
    @BindView(R.id.setting_weiChat)
    TextView mSettingWeiChat;

    private ClearTask mClearTask;
    private CleanCacheDialog mCleanCacheDialog;
    private String officialWeChat = "";//客服电话

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
        } else {
            mSettingExitLogin.setVisibility(View.VISIBLE);
        }
        mSettingVersonText.setText(getVerName(this));
        officialWeChat = getIntent().getStringExtra("officialWeChat");
        mSettingServiceText.setText(officialWeChat);
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.wechat, ""))) {
            mSettingWeiChat.setText("未填写");
        }else {
            mSettingWeiChat.setText("已填写");
        }
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "v" + verName;
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

    @OnClick({R.id.title_back,R.id.setting_cleanContainer,
            R.id.setting_serviceContainer, R.id.setting_goodContainer,
            R.id.setting_exitLogin, R.id.mine_address,
            R.id.setting_weiChatContainer, R.id.setting_verson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                return;
            case R.id.mine_address:
                //我的收货地址
                startActivity(new Intent(this, MyAddressActivity.class));
                UAppUtil.mine(this, 11);
                break;
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
                DialogUtil.LoginDialog(this, "官方客服微信：" + mSettingServiceText.getText().toString(), "复制", "取消", v -> {
                    ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("jipinshop", mSettingServiceText.getText().toString());
                    clip.setPrimaryClip(clipData);
                    ToastUtil.show("微信号复制成功");
                    SPUtils.getInstance().put(CommonDate.CLIP, mSettingServiceText.getText().toString());
                });
                return;
            case R.id.setting_goodContainer:
                if (!ShopJumpUtil.toQQDownload(this, "com.example.administrator.jipinshop")) {
                    if (!ShopJumpUtil.toMarket(this, "com.example.administrator.jipinshop", null)) {
                        ToastUtil.show("没有找到您手机里的应用商店，请确认");
                    }
                }
                return;
        }
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            ToastUtil.show("请先登录");
            return;
        }
        switch (view.getId()) {
            case R.id.setting_exitLogin:
                //退出登陆
                DialogUtil.LoginDialog(this, "您确定要退出登录吗？", "确定", "取消", v -> {
                    Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "退出登录...");
                    mDialog.show();
                    mPresenter.loginOut(this.<SuccessBean>bindToLifecycle(), mDialog);
                });
                break;
            case R.id.setting_weiChatContainer:
                //微信
                startActivityForResult(new Intent(this, BindWXActivity.class),201);
                break;
            case R.id.setting_verson:
                startActivityForResult(new Intent(this, AboutActivity.class),401);
                break;
        }
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * 该功能不需要动态申请权限的
     *
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
        mClearTask = new ClearTask(SettingActivity.this,mSettingCleanImage);
        mClearTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 退出登陆
     *
     * @param msg
     */
    @Override
    public void loginOutSuccess(SuccessBean msg) {
        if (msg.getCode() == 0) {
            JPushAlias.deleteAlias(this);
            String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
            if (deviceBrand.equals("xiaomi")){
                MiPushClient.unsetAlias(MyApplication.getInstance(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),null);
            }else if (deviceBrand.equals("vivo")){
                PushClient.getInstance(MyApplication.getInstance()).
                        unBindAlias(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId, ""), i -> { });
            }
            TaoBaoUtil.aliLogout();//淘宝退出登陆
            setResult(201);
            finish();
            ToastUtil.show("退出登录成功");
        } else {
            ToastUtil.show(msg.getMsg());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == 200){
            //从填写微信号返回
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.wechat, ""))) {
                mSettingWeiChat.setText("未填写");
            }else {
                mSettingWeiChat.setText("已填写");
            }
        }else if (requestCode == 401 && resultCode == 201){
            //从注销账号回来
            JPushAlias.deleteAlias(this);
            String deviceBrand = ShopJumpUtil.getDeviceBrand().toLowerCase();
            if (deviceBrand.equals("xiaomi")){
                MiPushClient.unsetAlias(MyApplication.getInstance(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),null);
            }else if (deviceBrand.equals("vivo")){
                PushClient.getInstance(MyApplication.getInstance()).
                        unBindAlias(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId, ""), i -> { });
            }
            TaoBaoUtil.aliLogout();//淘宝退出登陆
            setResult(201);
            finish();
        }
    }
}
