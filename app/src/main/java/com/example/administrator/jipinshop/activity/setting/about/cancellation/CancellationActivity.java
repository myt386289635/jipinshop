package com.example.administrator.jipinshop.activity.setting.about.cancellation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityCancelBinding;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.sp.CommonDate;

/**
 * @author 莫小婷
 * @create 2020/5/15
 * @Describe 注销账号页面
 */
public class CancellationActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCancelBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cancel);
        mBinding.setListener(this);
        mBinding.inClude.titleTv.setText("注销账号");
        mBinding.cancelNumber.setText(FileManager.editPhone(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 401 && resultCode == 201){
            //从注销成功返回
            setResult(201);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_wait:
            case R.id.title_back:
                finish();
                break;
            case R.id.cancle_go:
                startActivityForResult(new Intent(this,VerificationActivity.class)
                        ,401);
                break;
        }
    }
}
