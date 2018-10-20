package com.example.administrator.jipinshop.activity.wellcome;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.RuntimeRationale;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/1
 * @Describe
 */
public class WellComeActivity extends AppCompatActivity{

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        permission();
    }


    CountDownTimer timer = new CountDownTimer(3 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(SPUtils.getInstance().getBoolean(CommonDate.FIRST,true)){
                //跳转到引导页
                startActivity(new Intent(WellComeActivity.this, IndexActivity.class));
                finish();
                SPUtils.getInstance().put(CommonDate.FIRST,false);
            }else {
                startActivity(new Intent(WellComeActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void permission(){
        if (AndPermission.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)) {
            //有权限了
            if (timer != null) {
                timer.start();
            }
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA);
        }
    }

    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(permissions1 -> {
//                        Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
                    if (timer != null) {
                        timer.start();
                    }
                })
                .onDenied(permissions12 -> {
//                        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                    if (AndPermission.hasAlwaysDeniedPermission(this, permissions12)) {
                        showSettingDialog(this, permissions12);
                    }else {
                        if (timer != null) {
                            timer.start();
                        }
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     * 这个是用户勾了再也不要提示后，请求权限失败调用该方法
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = "点击设置打开" + permissionNames.get(0) + "权限";
        String title = "该操作需要访问您的" + permissionNames.get(0) + "权限";
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> setPermission())
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    if (timer != null) {
                        timer.start();
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(() -> {
//                        Toast.makeText(getContext(), R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    if (timer != null) {
                        timer.start();
                    }
                })
                .start();
    }

}
