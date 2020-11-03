package com.example.administrator.jipinshop.activity.mine.welfare;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityOfficialBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.example.administrator.jipinshop.util.share.PlatformUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.yanzhenjie.permission.Permission;

import java.io.File;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/10/30
 * @Describe 官方福利群
 */
public class OfficialWelfareActivity extends BaseActivity implements View.OnClickListener, OfficialWelfareView {

    @Inject
    OfficialWelfarePresenter mPresenter;

    private ActivityOfficialBinding mBinding;
    private String officialWeChat = "";
    private String officialWeChatQR = "";
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_official);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("极品城官方福利群");
        officialWeChat = getIntent().getStringExtra("officialWeChat");
        officialWeChatQR = getIntent().getStringExtra("officialWeChatQR");
        GlideApp.loderImage(this,officialWeChatQR,mBinding.officialCode,0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.official_save:
                HasPermissionsUtil.permission(this,new HasPermissionsUtil(){
                    //有权限了
                    @Override
                    public void hasPermissionsSuccess() {
                        super.hasPermissionsSuccess();
                        mDialog = new ProgressDialogView().createLoadingDialog(OfficialWelfareActivity.this, "");
                        mDialog.show();
                        mPresenter.downLoadImg(OfficialWelfareActivity.this,officialWeChatQR,OfficialWelfareActivity.this.bindToLifecycle());
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.official_vx:
                ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", officialWeChat);
                clip.setPrimaryClip(clipData);
                SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat);
                DialogUtil.listingDetele(this, "复制成功！", "打开“微信-右上角+-添加朋\n友”粘贴搜索即可",
                        "打开微信", "取消", R.color.color_202020, R.color.color_4A90E2, R.color.color_9D9D9D,
                        R.color.color_565252, true, false, v1 -> {
                            Intent intent = PlatformUtil.sharePYQ_images(OfficialWelfareActivity.this);
                            startActivity(intent);
                        });
                break;
        }
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSuccess() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        DialogUtil.listingDetele(this, "保存成功！", "打开“微信-扫一扫”选择相册中\n图片即可进群",
                "打开微信", "取消", R.color.color_202020, R.color.color_4A90E2, R.color.color_9D9D9D,
                R.color.color_565252, true, false, v1 -> {
                    Intent intent = PlatformUtil.sharePYQ_images(OfficialWelfareActivity.this);
                    startActivity(intent);
                });
    }
}
